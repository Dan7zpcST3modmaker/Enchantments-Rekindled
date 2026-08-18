package net.dan7zpc.enchantments_rekindled.block.custom;

import net.dan7zpc.enchantments_rekindled.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SoapyBlock extends Block {
    public SoapyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (level.isClientSide()) return;
        ((ServerLevel)level).sendParticles(
                ModParticles.SOAP_PARTICLES.get(),
                pos.getX()+0.5,pos.getY()+1,pos.getZ()+0.5,
                1,0.5,0,0.5,0.3f
        );
    }
}
