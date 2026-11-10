package net.dan7zpc.enchantments_rekindled.block.custom;

import com.mojang.serialization.MapCodec;
import net.dan7zpc.enchantments_rekindled.abyss.ModVoidEventsSets;
import net.dan7zpc.enchantments_rekindled.abyss.VoidEventsSet;
import net.dan7zpc.enchantments_rekindled.block.entity.EventManagerEntity;
import net.dan7zpc.enchantments_rekindled.block.entity.ModBlockEntities;
import net.dan7zpc.enchantments_rekindled.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class VoidEventManager extends BaseEntityBlock {
    VoidEventsSet eventsSet;

    public VoidEventManager(Properties properties, VoidEventsSet eventsSet) {
        super(properties);
        this.eventsSet = eventsSet;
    }

    private void spawnVoidWaves(BlockPos pos,ServerLevel serverLevel) {
        serverLevel.sendParticles(ModParticles.BIGGER_VOID_WAVE_PARTICLES.get(), pos.getX()+0.5f,pos.above().getY(),pos.getZ()+0.5f,2,0,0,0,0);
    }

    public static final MapCodec<VoidEventManager> CODEC = simpleCodec((properties1 -> new VoidEventManager(properties1, ModVoidEventsSets.lesserEvents)));
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EventManagerEntity(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()){
            return null;
        }

        return createTickerHelper(blockEntityType, ModBlockEntities.EVENT_MANAGER_ENTITY.get(),
                (level1, pos, state1, blockEntity) -> blockEntity.tick(level1,pos,state1));
    }
}
