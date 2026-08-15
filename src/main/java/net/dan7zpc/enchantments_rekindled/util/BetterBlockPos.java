package net.dan7zpc.enchantments_rekindled.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

public class BetterBlockPos extends BlockPos {
    public BetterBlockPos(Vec3i vector) {
        super(vector);
    }
    public BetterBlockPos(int x, int y, int z) {
        super(x, y, z);
    }

    public BetterBlockPos(BlockPos blockPos){
        super(blockPos.getX(), blockPos.getY(),blockPos.getZ());
    }

    public BlockPos offset(int x1, int y1, int z1){
        return new BlockPos(this.getX()+x1,this.getY()+y1,this.getZ()+z1);
    }

}
