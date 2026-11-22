package net.dan7zpc.enchantments_rekindled.block.entity;

import net.dan7zpc.enchantments_rekindled.recipe.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;
import java.util.Optional;

public class NetherBurnerBlockEntity extends SynchronizedBlockEntity{
    public final ItemStackHandler inventory = new ItemStackHandler(1){
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return stack.getMaxStackSize();
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
            }
        }
    };
    public NetherBurnerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.NETHER_BURNER_BE.get(), pos, blockState);
    }

    public void tryCraft(Level level, BlockPos pos, BlockState blockState) {
        setLit(level,pos,blockState,true);

        Optional<RecipeHolder<NetherBurnerRecipe>> recipe = getCurrentRecipe();

        if(!hasRecipe(recipe)){return;}
        if(!canPlaceItem(0,getRecipeResult(recipe))){return;}

        craft(recipe);

        this.notifyUpdate();
    }

    private void setLit(Level level, BlockPos pos, BlockState blockState, boolean lit) {
        level.setBlockAndUpdate(pos,blockState.setValue(BlockStateProperties.LIT,lit));
    }

    private void craft(Optional<RecipeHolder<NetherBurnerRecipe>> recipe) {
        ItemStack result = getRecipeResult(recipe);
        ItemStack output = new ItemStack(result.getItem(),this.inventory.getStackInSlot(0).getCount()+result.getCount());
        int size = getRecipeSize(recipe);
        this.consumeUp(size);
        this.clearContents();

        this.inventory.insertItem(0,output,false);

        this.notifyUpdate();
    }

    private void consumeUp(int size) {
        BlockPos pos = this.getBlockPos();
        for (int i = 0; i < size; i++) {
            pos = pos.above();
            assert level != null;
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }

    private ItemStack getRecipeResult(Optional<RecipeHolder<NetherBurnerRecipe>> recipe) {
        return recipe.get().value().outputItem().copy();
    }

    private int getRecipeSize(Optional<RecipeHolder<NetherBurnerRecipe>> recipe){
        return recipe.get().value().getIngredients().size();
    }

    public Optional<RecipeHolder<NetherBurnerRecipe>> getCurrentRecipe() {
        List<Ingredient> blocks = new java.util.ArrayList<>(List.of());
        BlockPos pos = this.getBlockPos().above();
        Level level1 = this.getLevel();
        while(true){
            BlockState state = level1.getBlockState(pos);
            Block block = state.getBlock();
            if(block.isEmpty(state)) break;
            blocks.add(Ingredient.of(block.asItem()));
            pos = pos.above();
        }
        assert this.level != null;
        return this.level.getRecipeManager().
                getRecipeFor(ModRecipes.NETHER_BURNER_TYPE.get(), new NetherBurnerRecipeInput(blocks),level);
    }

    public boolean hasRecipe(Optional<RecipeHolder<NetherBurnerRecipe>> recipe) {
        return recipe.isPresent();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory",inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.deserializeNBT(registries,tag.getCompound("inventory"));
    }

    public void clearContents(){
        inventory.setStackInSlot(0,ItemStack.EMPTY);
    }

    public void drops(){
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++){
            inv.setItem(i,inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level,this.worldPosition,inv);
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> invItems = NonNullList.of(
                ItemStack.EMPTY, inventory.getStackInSlot(0)
        );
        return invItems;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        inventory.insertItem(0,
                items.get(0)
                ,false);
        notifyUpdate();
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.insertItem(slot,stack,false);
    }

    @Override
    public boolean isEmpty() {
        return inventory.getStackInSlot(0).isEmpty();
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        ItemStack current_stack = this.inventory.getStackInSlot(slot);
        if(current_stack.isEmpty()) return true;
        ItemStack comparable_current = current_stack.copy();
        ItemStack comparable_suggested = stack.copy();
        comparable_current.setCount(1);
        comparable_suggested.setCount(1);
        if(!comparable_current.equals(comparable_suggested)) return false;
        if(current_stack.getCount() + stack.getCount() <= current_stack.getMaxStackSize()) return true;
        return false;
    }

    @Override
    public boolean canTakeItem(Container target, int slot, ItemStack stack) {
        return !this.inventory.getStackInSlot(slot).isEmpty();
    }


    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }
}
