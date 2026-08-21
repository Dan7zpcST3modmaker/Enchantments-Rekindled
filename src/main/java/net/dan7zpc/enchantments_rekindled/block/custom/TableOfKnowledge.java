package net.dan7zpc.enchantments_rekindled.block.custom;

import com.mojang.serialization.MapCodec;
import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.block.entity.EnchantmentsPedestalEntity;
import net.dan7zpc.enchantments_rekindled.block.entity.ModBlockEntities;
import net.dan7zpc.enchantments_rekindled.block.entity.TableOfKnowledgeEntity;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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

public class TableOfKnowledge extends BaseEntityBlock {
    public static final MapCodec<TableOfKnowledge> CODEC = simpleCodec(TableOfKnowledge::new);
    public static final VoxelShape SHAPE = Block.box(1,0,1,15,13,15);
    public TableOfKnowledge(Properties properties) {
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
        return new TableOfKnowledgeEntity(pos,state);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(state.getBlock()!=newState.getBlock()){
            if(level.getBlockEntity(pos) instanceof TableOfKnowledgeEntity tableOfKnowledgeEntity){
                tableOfKnowledgeEntity.drops();
                level.updateNeighbourForOutputSignal(pos,this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof TableOfKnowledgeEntity tableOfKnowledgeEntity){
            if(tableOfKnowledgeEntity.inventory.getStackInSlot(0).isEmpty() && !stack.isEmpty()){
                tableOfKnowledgeEntity.inventory.insertItem(0,stack.copy(),false);
                stack.shrink(1);
                level.playSound(player,pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1f,2f);
            } else if (stack.isEmpty() && !tableOfKnowledgeEntity.inventory.getStackInSlot(0).isEmpty()) {
                ItemStack onPedestal = tableOfKnowledgeEntity.inventory.extractItem(0,1,false);
                player.addItem(onPedestal);
                tableOfKnowledgeEntity.clearContents();
                level.playSound(player,pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1f,1f);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltip(stack,context,tooltipComponents,tooltipFlag,"table_of_knowledge");
        shiftTooltip(stack,context,tooltipComponents,tooltipFlag,"table_of_knowledge_shift");
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

        return createTickerHelper(blockEntityType, ModBlockEntities.TABLE_OF_KNOWLEDGE_BE.get(),
                (level1, pos, state1, blockEntity) -> blockEntity.tick(level1,pos,state1));
    }
}
