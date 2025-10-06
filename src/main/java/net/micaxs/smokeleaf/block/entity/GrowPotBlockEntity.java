// Java
package net.micaxs.smokeleaf.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.micaxs.smokeleaf.block.custom.BaseWeedCropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class GrowPotBlockEntity extends BlockEntity {

    public static int FULL_GROWTH_SECONDS_FAST = 60;
    public static int FULL_GROWTH_SECONDS_SLOW = 90;

    @Nullable private BlockState soilState;
    @Nullable private BaseWeedCropBlock cropBlock;
    private int cropAge;
    private int growthProgressTicks;

    public GrowPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GROW_POT.get(), pos, state);
    }

    private record PotData(Optional<ResourceLocation> soil, Optional<ResourceLocation> crop, int age, int prog) {
        static final Codec<PotData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ResourceLocation.CODEC.optionalFieldOf("soil").forGetter(PotData::soil),
                ResourceLocation.CODEC.optionalFieldOf("crop").forGetter(PotData::crop),
                Codec.INT.fieldOf("age").forGetter(PotData::age),
                Codec.INT.fieldOf("prog").forGetter(PotData::prog)
        ).apply(inst, PotData::new));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GrowPotBlockEntity be) {
        if (!(level instanceof ServerLevel)) return;
        if (!be.hasCrop()) return;

        int light = level.getMaxLocalRawBrightness(pos.above());
        if (light < 12) return;

        int maxAge = be.cropBlock.getMaxAge();
        if (be.cropAge >= maxAge) return;

        int fullGrowthTicks = be.getFullGrowthTicksForSoil();
        int perStepTicks = Math.max(1, fullGrowthTicks / Math.max(1, maxAge));

        int startAge = be.cropAge;

        be.growthProgressTicks++;
        while (be.growthProgressTicks >= perStepTicks && be.cropAge < maxAge) {
            be.growthProgressTicks -= perStepTicks;
            be.cropAge++;
        }

        if (be.cropAge != startAge) {
            be.setChangedAndSync();
        }
    }

    private int getFullGrowthTicksForSoil() {
        boolean fast = soilState != null && soilState.getBlock() instanceof FarmBlock;
        int seconds = fast ? FULL_GROWTH_SECONDS_FAST : FULL_GROWTH_SECONDS_SLOW;
        return Math.max(1, seconds * 20);
    }

    public boolean hasSoil() { return soilState != null; }
    public boolean hasCrop() { return cropBlock != null; }

    public void setSoil(BlockState soil) {
        this.soilState = soil;
        setChangedAndSync();
    }

    public void clearSoil() {
        this.soilState = null;
        clearCrop();
    }

    public void plantCrop(BaseWeedCropBlock crop) {
        this.cropBlock = crop;
        this.cropAge = 0;
        this.growthProgressTicks = 0;
        setChangedAndSync();
    }

    public void clearCrop() {
        this.cropBlock = null;
        this.cropAge = 0;
        this.growthProgressTicks = 0;
        setChangedAndSync();
    }

    @Nullable public BlockState getSoilState() { return soilState; }

    @Nullable
    public BlockState getBottomCropStateForRender() {
        if (cropBlock == null) return null;
        return cropBlock.defaultBlockState()
                .setValue(BaseWeedCropBlock.AGE, Math.min(cropAge, cropBlock.getMaxAge()))
                .setValue(cropBlock.getTop(), Boolean.FALSE);
    }

    @Nullable
    public BlockState getTopCropStateForRender() {
        if (cropBlock == null) return null;
        int tallAge = cropBlock.getTallAge();
        if (cropAge < tallAge) return null;
        return cropBlock.defaultBlockState()
                .setValue(BaseWeedCropBlock.AGE, Math.min(cropAge, cropBlock.getMaxAge()))
                .setValue(cropBlock.getTop(), Boolean.TRUE);
    }

    public boolean canHarvest() {
        return hasCrop() && cropAge >= cropBlock.getMaxAge();
    }

    public boolean applyBonemeal(Level level) {
        if (!hasCrop()) return false;
        int maxAge = cropBlock.getMaxAge();
        if (cropAge >= maxAge) return false;

        int inc = Mth.nextInt(level.random, 2, 5);
        int newAge = Math.min(maxAge, cropAge + inc);
        if (newAge != cropAge) {
            cropAge = newAge;
            growthProgressTicks = 0;
            return true;
        }
        return false;
    }

    public void harvest(ServerLevel serverLevel) {
        if (!canHarvest()) return;

        BlockState lootState = cropBlock.defaultBlockState()
                .setValue(BaseWeedCropBlock.AGE, cropBlock.getMaxAge())
                .setValue(cropBlock.getTop(), Boolean.FALSE);

        LootParams.Builder builder = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(worldPosition))
                .withParameter(LootContextParams.TOOL, ItemStack.EMPTY);

        List<ItemStack> drops = lootState.getDrops(builder);

        Item seedItem = cropBlock.getBaseSeedId().asItem();
        drops.removeIf(stk -> stk.is(seedItem));

        for (ItemStack drop : drops) {
            Block.popResource(serverLevel, worldPosition, drop);
        }

        this.cropAge = 0;
        this.growthProgressTicks = 0;
        setChangedAndSync();
    }

    // --- Shift-right-click removals ---
    public boolean removeCropAndGiveSeed(ServerLevel level, Player player) {
        if (!hasCrop()) return false;
        Item seedItem = cropBlock.getBaseSeedId().asItem();
        ItemStack seed = new ItemStack(seedItem);
        if (!player.addItem(seed)) {
            Block.popResource(level, worldPosition, seed);
        }
        clearCrop();
        return true;
    }

    public boolean removeSoilAndGiveBack(ServerLevel level, Player player) {
        if (!hasSoil() || hasCrop()) return false; // only when no crop
        ItemStack soil = new ItemStack(soilState.getBlock());
        if (!player.addItem(soil)) {
            Block.popResource(level, worldPosition, soil);
        }
        clearSoil();
        return true;
    }

    public void setChangedAndSync() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Nullable
    public static BaseWeedCropBlock resolveCropBySeed(net.minecraft.world.item.Item seed) {
        for (Block b : BuiltInRegistries.BLOCK) {
            if (b instanceof BaseWeedCropBlock crop && crop.getBaseSeedId().asItem() == seed) {
                return crop;
            }
        }
        return null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        Optional<ResourceLocation> soilId = soilState != null
                ? Optional.ofNullable(BuiltInRegistries.BLOCK.getKey(soilState.getBlock()))
                : Optional.empty();
        Optional<ResourceLocation> cropId = cropBlock != null
                ? Optional.ofNullable(BuiltInRegistries.BLOCK.getKey(cropBlock))
                : Optional.empty();

        PotData data = new PotData(soilId, cropId, cropAge, growthProgressTicks);
        PotData.CODEC.encodeStart(NbtOps.INSTANCE, data)
                .resultOrPartial(err -> {})
                .ifPresent(encoded -> tag.put("Pot", encoded));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        this.soilState = null;
        this.cropBlock = null;
        this.cropAge = 0;
        this.growthProgressTicks = 0;

        if (tag.contains("Pot")) {
            PotData.CODEC.parse(NbtOps.INSTANCE, tag.get("Pot"))
                    .result()
                    .ifPresent(data -> {
                        data.soil().ifPresent(rl -> {
                            Block b = BuiltInRegistries.BLOCK.get(rl);
                            if (b != null) this.soilState = b.defaultBlockState();
                        });
                        data.crop().ifPresent(rl -> {
                            Block b = BuiltInRegistries.BLOCK.get(rl);
                            if (b instanceof BaseWeedCropBlock crop) this.cropBlock = crop;
                        });
                        this.cropAge = Math.max(0, data.age());
                        this.growthProgressTicks = Math.max(0, data.prog());
                    });
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        loadAdditional(tag, registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
