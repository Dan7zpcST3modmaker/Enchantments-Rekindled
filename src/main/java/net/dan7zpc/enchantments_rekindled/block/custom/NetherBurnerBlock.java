package net.dan7zpc.enchantments_rekindled.block.custom;

import com.mojang.serialization.MapCodec;
import net.dan7zpc.enchantments_rekindled.block.entity.EnchantmentsPedestalEntity;
import net.dan7zpc.enchantments_rekindled.block.entity.ModBlockEntities;
import net.dan7zpc.enchantments_rekindled.block.entity.NetherBurnerBlockEntity;
import net.dan7zpc.enchantments_rekindled.item.ModItemTagProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class NetherBurnerBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public static final MapCodec<NetherBurnerBlock> CODEC = simpleCodec(NetherBurnerBlock::new);

    @Override
    public MapCodec<NetherBurnerBlock> codec() {return CODEC;}
    @Override
    protected RenderShape getRenderShape(BlockState state) {return RenderShape.MODEL;}

    public NetherBurnerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.valueOf(false)));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!(level.getBlockEntity(pos) instanceof NetherBurnerBlockEntity burnerBlockEntity))return ItemInteractionResult.SUCCESS;
        if(stack.is(ModItemTagProvider.NETHER_BURNER_FUEL)){
            int timeLeft = burnerBlockEntity.getBurnTime();
            if (timeLeft>1000) return ItemInteractionResult.SUCCESS;
            if(level.isClientSide()) {
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
                return ItemInteractionResult.SUCCESS;
            }
            burnerBlockEntity.setBurnTime(burnerBlockEntity.getBurnTime()+100);
            stack.shrink(1);
            return ItemInteractionResult.CONSUME;
        }
        if(player.isCrouching()){
            if(burnerBlockEntity.isEmpty())return ItemInteractionResult.SUCCESS;
            level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            if(level.isClientSide)return ItemInteractionResult.SUCCESS;
            ItemStack firstSlot = burnerBlockEntity.getItem(0);
            ItemStack secondSlot = burnerBlockEntity.getItem(1);
            ItemStack outputSlot = burnerBlockEntity.getItem(2);
            if(!outputSlot.isEmpty()){
                player.addItem(outputSlot.copy());
                outputSlot.shrink(outputSlot.getCount());
            } else if (!firstSlot.isEmpty()) {
                player.addItem(firstSlot.copy());
                firstSlot.shrink(firstSlot.getCount());
            }else if (!secondSlot.isEmpty()) {
                player.addItem(secondSlot.copy());
                secondSlot.shrink(secondSlot.getCount());
            }
            return ItemInteractionResult.SUCCESS;
        }else {
            boolean isFirstSlotFree = burnerBlockEntity.canPlaceItem(0,stack);
            boolean isSecondSlotFree = burnerBlockEntity.canPlaceItem(1,stack);
            if (!isSecondSlotFree && !isFirstSlotFree) return ItemInteractionResult.SUCCESS;
            if(level.isClientSide){
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
                return ItemInteractionResult.SUCCESS;
            }
            ItemStack firstSlot = burnerBlockEntity.getItem(0);
            ItemStack secondSlot = burnerBlockEntity.getItem(1);
            ItemStack output = stack.copy();
            if(isFirstSlotFree){
                output.setCount(stack.getCount()+firstSlot.getCount());
                burnerBlockEntity.setItem(0,output);
            } else {
                output.setCount(stack.getCount()+secondSlot.getCount());
                burnerBlockEntity.setItem(1,output);
            }
            stack.shrink(stack.getCount());
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NetherBurnerBlockEntity(pos,state);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock()!=newState.getBlock()){
            if(level.getBlockEntity(pos) instanceof NetherBurnerBlockEntity netherBurnerBlockEntity){
                netherBurnerBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos,this);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()){
            return null;
        }

        return createTickerHelper(blockEntityType, ModBlockEntities.NETHER_BURNER_BE.get(),
                (level1, pos, state1, blockEntity) -> blockEntity.tick(level1,pos,state1));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            double d0 = (double)pos.getX() + 0.5;
            double d1 = (double)pos.getY();
            double d2 = (double)pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                level.playLocalSound(d0, d1, d2, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52;
            double d4 = random.nextDouble() * 0.6 - 0.3;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52 : d4;
            double d6 = random.nextDouble() * 9.0 / 16.0;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52 : d4;
            level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0, 0.0, 0.0);
        }
    }

}
