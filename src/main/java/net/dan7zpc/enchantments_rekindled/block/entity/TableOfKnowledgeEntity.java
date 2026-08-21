package net.dan7zpc.enchantments_rekindled.block.entity;

import net.dan7zpc.enchantments_rekindled.block.ModBlocks;
import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.dan7zpc.enchantments_rekindled.recipe.*;
import net.dan7zpc.enchantments_rekindled.util.EnchantmentHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TableOfKnowledgeEntity extends PedestalEntity {
    protected List<EnchantmentInstance> enchantments = new ArrayList<>(0);
    public TableOfKnowledgeEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TABLE_OF_KNOWLEDGE_BE.get(), pos, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState blockState){
        if(!level.isClientSide()){serverBoundTick(level,pos,blockState);}
    }

    private void clientBoundTick(Level level, BlockPos pos, BlockState blockState){

    }
    private void serverBoundTick(Level level, BlockPos pos, BlockState blockState){
        if(this.isEmpty()){resetProgress();return;}
        if(!isPedestal(worldPosition.north())){resetProgress();return;}
        if(!isPedestal(worldPosition.east())){resetProgress();return;}
        if(!isPedestal(worldPosition.south())){resetProgress();return;}
        if(!isPedestal(worldPosition.west())){resetProgress();return;}
        if(!hasRecipe()){resetProgress();this.progress--;return;}
        if(!isCraftingBegan()) {startCrafting();}
        increaseProgress();
        if(isCraftingFinished()) {craft();resetProgress();}
    }

    private void increaseProgress() {
        this.progress++;
        ((ServerLevel)level).sendParticles(ParticleTypes.ENCHANT,
                worldPosition.getX() +0.5, worldPosition.getY()+1, worldPosition.getZ()+0.5,
                1,
                0.1,0,0.1,
                1
        );
        notifyUpdate();
    }

    private void bothBoundTick(Level level, BlockPos pos, BlockState blockState){

    }

    @Override
    public void clearContents() {
        super.clearContents();
    }

    private void drainRunes(){
        ItemStack rune1 = getRune(worldPosition.north());
        ItemStack rune2 = getRune(worldPosition.east());
        ItemStack rune3 = getRune(worldPosition.south());
        ItemStack rune4 = getRune(worldPosition.west());
        rune1.shrink(1);
        rune2.shrink(1);
        rune3.shrink(1);
        rune4.shrink(1);
    }

    private void craft() {
        ItemStack result = enchanterCraft();
        if (!result.isEmpty()) {
            this.clearContents();
            this.drainRunes();
            this.inventory.insertItem(0, result, false);
            level.playSound(level.getNearestPlayer(
                    worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5,
                    15, true
            ), worldPosition, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1f, 1f);
            this.notifyUpdate();
        }
    }

    private ItemStack enchanterCraft(){
        Optional<RecipeHolder<TableOfKnowledgeRecipe>> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().value().outputItem().copy();
        return result;
    }

    private boolean isPedestal(BlockPos pos){
        assert level != null;
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return false;
        if (blockEntity instanceof BaseContainerBlockEntity baseContainerBlockEntity) {
            if(baseContainerBlockEntity.getContainerSize() == 1 && baseContainerBlockEntity.canTakeItem(baseContainerBlockEntity,0,baseContainerBlockEntity.getItem(0))) return true;
        }
        return false;
    }

    private boolean isCraftingFinished() {
        return this.getProgress() >= this.getMaxProgress();
    }

    private void startCrafting() {
        resetProgress();
        this.notifyUpdate();
    }


    private boolean isCraftingBegan() {
        return this.getProgress() > 0;
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 100;
        this.notifyUpdate();
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<TableOfKnowledgeRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()){
            return false;
        }
        ItemStack output = recipe.get().value().outputItem();
        return !output.isEmpty();
    }

    private Optional<RecipeHolder<TableOfKnowledgeRecipe>> getCurrentRecipe() {
        ItemStack rune1 = getRune(worldPosition.north());
        ItemStack rune2 = getRune(worldPosition.east());
        ItemStack rune3 = getRune(worldPosition.south());
        ItemStack rune4 = getRune(worldPosition.west());
        return this.level.getRecipeManager().
                getRecipeFor(ModRecipes.TABLE_OF_KNOWLEDGE_TYPE.get(), new TableOfKnowledgeRecipeInput(inventory.getStackInSlot(0),
                        rune1,
                        rune2,
                        rune3,
                        rune4
                        ),level);
    }

    private ItemStack getRune(BlockPos pos){
        if (!isPedestal(pos)) return  ItemStack.EMPTY;
        BaseContainerBlockEntity pedestal = (BaseContainerBlockEntity) level.getBlockEntity(pos);
        return pedestal.getItem(0);
    }
}
