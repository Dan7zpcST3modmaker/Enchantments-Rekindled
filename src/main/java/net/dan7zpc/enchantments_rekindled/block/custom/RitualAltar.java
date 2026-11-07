package net.dan7zpc.enchantments_rekindled.block.custom;

import com.mojang.serialization.MapCodec;
import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.block.entity.ModBlockEntities;
import net.dan7zpc.enchantments_rekindled.block.entity.RitualAltarEntity;
import net.dan7zpc.enchantments_rekindled.item.ModItemTagProvider;
import net.dan7zpc.enchantments_rekindled.particle.ModParticles;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RitualAltar extends BaseEntityBlock {
    public static final MapCodec<RitualAltar> CODEC = simpleCodec(RitualAltar::new);
    public RitualAltar(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RitualAltarEntity(pos,state);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        RitualAltarEntity altarEntity = (RitualAltarEntity) level.getBlockEntity(pos);
        if(entity instanceof ItemEntity){
            assert altarEntity != null;
            altarEntity.recalculateModules();
            altarEntity.stepOn(level,pos,state,entity);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltip(stack,context,tooltipComponents,tooltipFlag,"ritual_block");
        shiftTooltip(stack,context,tooltipComponents,tooltipFlag,"ritual_block_shift");
        controlTooltip(stack,context,tooltipComponents,tooltipFlag,"ritual_block_control");
    }

    public void tooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, String translationKey){
        tooltipComponents.add(Component.translatable("tooltip."+ EnchantmentsRekindled.MOD_ID+"."+translationKey));
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if(level.getBlockEntity(pos) instanceof RitualAltarEntity ritualAltarEntity){ritualAltarEntity.recalculateModules();}
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
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

        return createTickerHelper(blockEntityType, ModBlockEntities.RITUAL_ALTAR_BE.get(),
                (level1, pos, state1, blockEntity) -> blockEntity.tick(level1,pos,state1));
    }
}
