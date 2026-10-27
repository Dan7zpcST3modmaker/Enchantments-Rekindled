package net.dan7zpc.enchantments_rekindled.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BrokerBlock extends Block {
    public BrokerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(random.nextInt(15)<13) return;
        ChainCollapse(level, pos, new BlockPos(0,1,0));
        ChainCollapse(level, pos, new BlockPos(0,-1,0));
        //super.randomTick(state, level, pos, random);
    }

    protected void collapse(ServerLevel level, BlockPos pos){
        level.playLocalSound(pos, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.BLOCKS,1,0.2f,false);
        level.destroyBlock(pos,false);
    }

    protected void ChainCollapse(ServerLevel level,BlockPos origin, BlockPos offset){
        BlockPos nextBlockPos = origin.offset(offset.getX(),offset.getY(),offset.getZ());
        Block nextBlock = level.getBlockState(nextBlockPos).getBlock();
        if(nextBlock instanceof BrokerBlock brokerBlock)
            brokerBlock.ChainCollapse(level,nextBlockPos,offset);
        collapse(level, origin);
    }
}
