package net.micaxs.smokeleaf.block.entity;

import net.micaxs.smokeleaf.component.ModDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BaseWeedCropBlockEntity extends BlockEntity {

    private static final int MAX_PERCENT = 100;
    private static final int MAX_PH = 14;

    private int thc;
    private int cbd;
    private int ph;
    private int nitrogen;
    private int phosphorus;
    private int potassium;

    public BaseWeedCropBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.BASE_WEED_CROP_BE.get(), pos, blockState);
    }

    // Getters
    public int getThc() { return thc; }
    public int getCbd() { return cbd; }
    public int getPh() { return ph; }
    public int getNitrogen() { return nitrogen; }
    public int getPhosphorus() { return phosphorus; }
    public int getPotassium() { return potassium; }

    // Setters (clamped)
    public void setThc(int v) { this.thc = Mth.clamp(v, 0, MAX_PERCENT); }
    public void setCbd(int v) { this.cbd = Mth.clamp(v, 0, MAX_PERCENT); }
    public void setPh(int v) { this.ph = Mth.clamp(v, 0, MAX_PH); }
    public void setNitrogen(int v) { this.nitrogen = Mth.clamp(v, 0, MAX_PERCENT); }
    public void setPhosphorus(int v) { this.phosphorus = Mth.clamp(v, 0, MAX_PERCENT); }
    public void setPotassium(int v) { this.potassium = Mth.clamp(v, 0, MAX_PERCENT); }

    // Convenience increase/decrease
    public void addThc(int d) { setThc(this.thc + d); }
    public void addCbd(int d) { setCbd(this.cbd + d); }
    public void addPh(int d) { setPh(this.ph + d); }
    public void addNitrogen(int d) { setNitrogen(this.nitrogen + d); }
    public void addPhosphorus(int d) { setPhosphorus(this.phosphorus + d); }
    public void addPotassium(int d) { setPotassium(this.potassium + d); }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("thc", this.thc);
        tag.putInt("cbd", this.cbd);
        tag.putInt("ph", this.ph);
        tag.putInt("nitrogen", this.nitrogen);
        tag.putInt("phosphorus", this.phosphorus);
        tag.putInt("potassium", this.potassium);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.thc = tag.getInt("thc");
        this.cbd = tag.getInt("cbd");
        this.ph = tag.getInt("ph");
        this.nitrogen = tag.getInt("nitrogen");
        this.phosphorus = tag.getInt("phosphorus");
        this.potassium = tag.getInt("potassium");
    }

    public void writeToItem(ItemStack stack) {
        stack.set(ModDataComponentTypes.THC.get(), thc);
        stack.set(ModDataComponentTypes.CBD.get(), cbd);
    }

}
