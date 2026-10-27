package net.dan7zpc.enchantments_rekindled.block.custom;

import net.dan7zpc.enchantments_rekindled.abyss.ModVoidEventsSets;
import net.dan7zpc.enchantments_rekindled.block.ModBlocks;
import net.dan7zpc.enchantments_rekindled.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class VoidBarrierBlock extends Block {
    public VoidBarrierBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(!level.getBlockState(pos.above()).is(Blocks.AIR)) return;
        if(random.nextInt(6400)>6398) smallVoidBreachEvent(state, level, pos, random);
        //super.randomTick(state, level, pos, random);
    }

    private void smallVoidBreachEvent(BlockState state, ServerLevel level, BlockPos pos, RandomSource random){
        spawnVoidWaves(pos,level);
        spawnLesserVoidEvent(level,pos.above(),random);
    }

    private void spawnLesserVoidEvent(ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlockAndUpdate(pos, ModBlocks.LESSER_VOID_EVENTS_MANAGER.get().defaultBlockState());
    }

    private void spawnVoidWaves(BlockPos pos,ServerLevel serverLevel) {
        serverLevel.sendParticles(ModParticles.BIGGER_VOID_WAVE_PARTICLES.get(), pos.getX()+0.5f,pos.above().getY(),pos.getZ()+0.5f,2,0,0,0,0);
    }
}
