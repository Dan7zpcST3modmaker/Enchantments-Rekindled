package net.dan7zpc.enchantments_rekindled.block.entity;

import net.dan7zpc.enchantments_rekindled.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EventManagerEntity extends BlockEntity {
    public EventManagerEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EVENT_MANAGER_ENTITY.get(), pos, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState blockState){
        RandomSource random = level.getRandom();
        if(level instanceof ServerLevel serverLevel){
            if(random.nextInt(15)>13) spawnVoidWaves(pos.below(),serverLevel);
        }
    }

    private void spawnVoidWaves(BlockPos pos, ServerLevel serverLevel) {
        serverLevel.sendParticles(ModParticles.BIGGER_VOID_WAVE_PARTICLES.get(), pos.getX()+0.5f,pos.above().getY(),pos.getZ()+0.5f,2,0,0,0,0);
    }
}
