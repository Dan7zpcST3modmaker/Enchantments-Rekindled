package net.dan7zpc.enchantments_rekindled.block.custom;

import net.dan7zpc.enchantments_rekindled.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class SoapyBlock extends Block {
    public SoapyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (level.isClientSide()) return;
        Random random = new Random();
        ((ServerLevel)level).sendParticles(
                ModParticles.SOAP_PARTICLES.get(),
                pos.getX()+0.5,pos.getY()+1.1,pos.getZ()+0.5,
                random.nextFloat()>0.9?1:0,0.5,0,0.5,0.3f
        );
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!level.isClientSide()){
            ((ServerLevel)level).sendParticles(
                ModParticles.SOAP_PARTICLES.get(),
                pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,
                5,0.5,0.5,0.5,0.3f
            );
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
