package net.dan7zpc.enchantments_rekindled.block.entity;

import net.dan7zpc.enchantments_rekindled.block.ModBlocks;
import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.dan7zpc.enchantments_rekindled.recipe.EnchantmentsPedestalRecipe;
import net.dan7zpc.enchantments_rekindled.recipe.EnchantmentsPedestalRecipeInput;
import net.dan7zpc.enchantments_rekindled.recipe.ModRecipes;
import net.dan7zpc.enchantments_rekindled.util.EnchantmentHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnchantmentsPedestalEntity extends PedestalEntity {
    protected List<EnchantmentInstance> enchantments = new ArrayList<>(0);
    public EnchantmentsPedestalEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ENCHANTMENTS_PEDESTAL_BE.get(), pos, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState blockState){
        if(!level.isClientSide()){serverBoundTick(level,pos,blockState);}
    }

    private void clientBoundTick(Level level, BlockPos pos, BlockState blockState){

    }
    private void serverBoundTick(Level level, BlockPos pos, BlockState blockState){
        if(!hasRecipe()){resetProgress();return;}
        if(!isCraftingBegan()) {startCrafting();}
        increaseProgress();
        if(isCraftingFinished()) {craft();resetProgress();}
    }

    private void increaseProgress() {
        this.progress++;
    }

    private void bothBoundTick(Level level, BlockPos pos, BlockState blockState){

    }

    private void craft() {
        ItemStack stack = inventory.getStackInSlot(0);
        ItemStack result = enchantable(stack)? enchanterCraft():infuserCraft();
        this.clearContents();
        this.inventory.insertItem(0,result,false);
        this.notifyUpdate();
    }

    private ItemStack infuserCraft(){
        Optional<RecipeHolder<EnchantmentsPedestalRecipe>> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().value().outputItem();
        return result;
    }

    private ItemStack enchanterCraft(){
        return EnchantmentHelper.applyEnchantment(inventory.getStackInSlot(0),"sharpness",5,level);
    }

    private boolean enchantable(ItemStack stack){
        return stack.is(ModItems.EXPERIOLITE.get());
    }

    private boolean isCraftingFinished() {
        return this.getProgress() >= this.getMaxProgress();
    }

    private void startCrafting() {
        resetProgress();
        recalculateModules();
        this.notifyUpdate();
    }

    private void recalculateModules() {
        if(is_there_sculk_under_me()){this.maxProgress -= 30;}
        this.maxProgress -= (enchantments_stones_count()*30);
        assert level != null;
        if(level.isDay()){this.maxProgress -= 30;}
        this.maxProgress -= (bookshelfs_amount()*22);
        this.notifyUpdate();
    }

    private int bookshelfs_amount() {
        int count = 0;
        BlockPos pos = this.worldPosition;
        for(int dx = -2; dx <= 2; dx++){
            for(int dz = -2; dz <= 2; dz++){
                if (dx == 0 && dz == 0){continue;}
                if(Math.abs(dx) <= 1 && Math.abs(dz)<=1){continue;}
                BlockPos checkPos = pos.offset(dx,0,dz);
                if(isBookshelf(checkPos)){count++;}
                if(isBookshelf(checkPos.above())){count++;}
            }
        }
        if(count > 20){count = 20;}
        return count;
    }

    private boolean isBookshelf(BlockPos checkPos) {
        return level.getBlockState(checkPos).is(Blocks.BOOKSHELF);
    }

    private int enchantments_stones_count() {
        int amount = 0;

        assert level != null;

        if(level.getBlockState(this.worldPosition.below().south()).is(ModBlocks.ENCHANTMENTS_STONE.get())){amount+=1;}
        if(level.getBlockState(this.worldPosition.below().north()).is(ModBlocks.ENCHANTMENTS_STONE.get())){amount+=1;}
        if(level.getBlockState(this.worldPosition.below().east()).is(ModBlocks.ENCHANTMENTS_STONE.get())){amount+=1;}
        if(level.getBlockState(this.worldPosition.below().west()).is(ModBlocks.ENCHANTMENTS_STONE.get())){amount+=1;}

        if(level.getBlockState(this.worldPosition.below().south().east()).is(ModBlocks.ENCHANTMENTS_STONE.get())){amount+=1;}
        if(level.getBlockState(this.worldPosition.below().south().west()).is(ModBlocks.ENCHANTMENTS_STONE.get())){amount+=1;}

        if(level.getBlockState(this.worldPosition.below().north().east()).is(ModBlocks.ENCHANTMENTS_STONE.get())){amount+=1;}
        if(level.getBlockState(this.worldPosition.below().north().west()).is(ModBlocks.ENCHANTMENTS_STONE.get())){amount+=1;}


        return amount;
    }

    private boolean is_there_sculk_under_me() {
        return level.getBlockState(this.worldPosition.below()).is(Blocks.SCULK);
    }

    private boolean isCraftingBegan() {
        return this.getProgress() > 0;
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 1000;
        this.notifyUpdate();
    }

    private boolean hasRecipe() {
        ItemStack item = this.inventory.getStackInSlot(0);
        Optional<RecipeHolder<EnchantmentsPedestalRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()){
            boolean enchanted = item.isEnchanted();
            boolean experiolite = item.is(ModItems.EXPERIOLITE);
            if(enchanted || !experiolite){return false;}
            return true;
        }
        ItemStack output = recipe.get().value().outputItem();
        return true;
    }

    private Optional<RecipeHolder<EnchantmentsPedestalRecipe>> getCurrentRecipe() {
        return this.level.getRecipeManager().
                getRecipeFor(ModRecipes.ENCHANTMENTS_PEDESTAL_TYPE.get(), new EnchantmentsPedestalRecipeInput(inventory.getStackInSlot(0)),level);
    }
}
