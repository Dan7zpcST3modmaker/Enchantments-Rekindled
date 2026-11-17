package net.dan7zpc.enchantments_rekindled.block.entity;

import net.dan7zpc.enchantments_rekindled.particle.AbyssalFountainParticleOptions;
import net.dan7zpc.enchantments_rekindled.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
        if(countdown % 10 == 0){ServerLevel s_level = (ServerLevel) level;
            spawnFountainParticles(pos,s_level);}
        countdown--;
        if(countdown <= 0){
            level.destroyBlock(pos,false);
        }
    }

    private void spawnFountainParticles(BlockPos pos, ServerLevel serverLevel) {
        serverLevel.sendParticles(new AbyssalFountainParticleOptions(500-countdown), pos.getX()+0.5f,pos.above().getY()-1.2f,pos.getZ()+0.5f,2,0,0,0,0);
    }
}
