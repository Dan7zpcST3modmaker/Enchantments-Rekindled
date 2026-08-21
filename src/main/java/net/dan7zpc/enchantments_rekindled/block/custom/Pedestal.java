package net.dan7zpc.enchantments_rekindled.block.custom;

import com.mojang.serialization.MapCodec;
import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.block.entity.EnchantmentsPedestalEntity;
import net.dan7zpc.enchantments_rekindled.block.entity.ModBlockEntities;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class Pedestal extends BaseEntityBlock {
    public static final MapCodec<Pedestal> CODEC = simpleCodec(Pedestal::new);
    public static final VoxelShape SHAPE = Block.box(1,0,1,15,13,15);
    public Pedestal(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnchantmentsPedestalEntity(pos,state);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(state.getBlock()!=newState.getBlock()){
            if(level.getBlockEntity(pos) instanceof EnchantmentsPedestalEntity enchantmentsPedestalEntity){
                enchantmentsPedestalEntity.drops();
                level.updateNeighbourForOutputSignal(pos,this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof EnchantmentsPedestalEntity enchantmentsPedestalEntity){
            if(enchantmentsPedestalEntity.inventory.getStackInSlot(0).isEmpty() && !stack.isEmpty()){
                enchantmentsPedestalEntity.inventory.insertItem(0,stack.copy(),false);
                stack.shrink(1);
                level.playSound(player,pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1f,2f);
            } else if (stack.isEmpty() && !enchantmentsPedestalEntity.inventory.getStackInSlot(0).isEmpty()) {
                ItemStack onPedestal = enchantmentsPedestalEntity.inventory.extractItem(0,1,false);
                player.addItem(onPedestal);
                enchantmentsPedestalEntity.clearContents();
                level.playSound(player,pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1f,1f);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltip(stack,context,tooltipComponents,tooltipFlag,"pedestal");
        shiftTooltip(stack,context,tooltipComponents,tooltipFlag,"pedestal_shift");
        controlTooltip(stack,context,tooltipComponents,tooltipFlag,"pedestal_control");
    }

    public void tooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, String translationKey){
        tooltipComponents.add(Component.translatable("tooltip."+ EnchantmentsRekindled.MOD_ID+"."+translationKey));
    }

    public void shiftTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, String translationKey){
        if(Screen.hasShiftDown()){
            tooltipComponents.add(Component.translatable("tooltip."+ EnchantmentsRekindled.MOD_ID+"."+translationKey));
        }else {
            tooltipComponents.add(Component.translatable("tooltip."+ EnchantmentsRekindled.MOD_ID+".hold_shift"));
        }
    }

    public void controlTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, String translationKey){
        if(Screen.hasControlDown()){
            tooltipComponents.add(Component.translatable("tooltip."+ EnchantmentsRekindled.MOD_ID+"."+translationKey));
        }else {
            tooltipComponents.add(Component.translatable("tooltip."+ EnchantmentsRekindled.MOD_ID+".hold_control"));
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()){
            return null;
        }

        return createTickerHelper(blockEntityType, ModBlockEntities.ENCHANTMENTS_PEDESTAL_BE.get(),
                (level1, pos, state1, blockEntity) -> blockEntity.tick(level1,pos,state1));
    }
}
