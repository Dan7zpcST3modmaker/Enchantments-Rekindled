package net.dan7zpc.enchantments_rekindled.block.entity;

import net.dan7zpc.enchantments_rekindled.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AbyssalFountainEntity extends BlockEntity {
    private int countdown = 500;
    public AbyssalFountainEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ABYSSAL_FOUNTAIN_ENTITY.get(), pos, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState blockState){
        if(level.isClientSide){return;}
        if(countdown == 500){ServerLevel s_level = (ServerLevel) level;spawnVoidWaves(pos,s_level);}
        countdown--;
        if(countdown <= 0){
            level.destroyBlock(pos,false);
        }
    }

    private void spawnVoidWaves(BlockPos pos, ServerLevel serverLevel) {
        serverLevel.sendParticles(ModParticles.ABYSSAL_FOUNTAIN_PARTICLES.get(), pos.getX()+0.5f,pos.above().getY()-1.2f,pos.getZ()+0.5f,2,0,0,0,0);
    }
}
