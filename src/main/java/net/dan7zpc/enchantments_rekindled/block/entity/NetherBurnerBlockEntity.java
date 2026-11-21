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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.Optional;

public class NetherBurnerBlockEntity extends SynchronizedBlockEntity{
    protected int progress = 0;
    protected int maxProgress = 100;
    protected int burn_time = 0;
    public final ItemStackHandler inventory = new ItemStackHandler(3){
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
    protected final ContainerData data;
    public NetherBurnerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.NETHER_BURNER_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> NetherBurnerBlockEntity.this.progress;
                    case 1 -> NetherBurnerBlockEntity.this.maxProgress;
                    case 2 -> NetherBurnerBlockEntity.this.burn_time;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: NetherBurnerBlockEntity.this.progress = value;
                    case 1: NetherBurnerBlockEntity.this.maxProgress = value;
                    case 2: NetherBurnerBlockEntity.this.burn_time = value;
                };
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    public void tick(Level level, BlockPos pos, BlockState blockState){
        if(!level.isClientSide()){serverBoundTick(level,pos,blockState);}
    }

    private void serverBoundTick(Level level, BlockPos pos, BlockState blockState) {
        if(burn_time < 1){progress=0;setLit(level,pos,blockState,false);return;}

        setLit(level,pos,blockState,true);

        if(!hasRecipe()){progress=0;return;}
        if(!canPlaceItem(2,getRecipeResult())){progress=0;return;}
        progress++;
        if(progress >= maxProgress){
            progress = 0;
            craft();
        }

        burn_time--;
    }

    private void setLit(Level level, BlockPos pos, BlockState blockState, boolean lit) {
        level.setBlockAndUpdate(pos,blockState.setValue(BlockStateProperties.LIT,lit));
    }

    private void craft() {
        ItemStack inp1 = this.inventory.getStackInSlot(0);
        ItemStack inp2 = this.inventory.getStackInSlot(1);
        ItemStack result = getRecipeResult();
        ItemStack output = new ItemStack(result.getItem(),this.inventory.getStackInSlot(2).getCount()+result.getCount());

        inp1.shrink(1);
        inp2.shrink(1);
        this.setItem(2,output);

        this.notifyUpdate();
    }

    private ItemStack getRecipeResult() {
        Optional<RecipeHolder<NetherBurnerRecipe>> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().value().outputItem().copy();
        return result;
    }

    private Optional<RecipeHolder<NetherBurnerRecipe>> getCurrentRecipe() {
        return this.level.getRecipeManager().
                getRecipeFor(ModRecipes.NETHER_BURNER_TYPE.get(), new NetherBurnerRecipeInput(inventory.getStackInSlot(0),inventory.getStackInSlot(1)),level);
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<NetherBurnerRecipe>> recipe = getCurrentRecipe();
        return recipe.isPresent();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("progress",this.getProgress());
        tag.putInt("maxProgress",this.getMaxProgress());
        tag.putInt("burn_time",this.getBurnTime());
        tag.put("inventory",inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.progress = tag.getInt("progress");
        this.maxProgress = tag.getInt("maxProgress");
        this.burn_time = tag.getInt("burn_time");
        this.inventory.deserializeNBT(registries,tag.getCompound("inventory"));
    }

    public void clearContents(){
        inventory.setStackInSlot(0,ItemStack.EMPTY);
        inventory.setStackInSlot(1,ItemStack.EMPTY);
        inventory.setStackInSlot(2,ItemStack.EMPTY);
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
        NonNullList<ItemStack> invItems = NonNullList.of(ItemStack.EMPTY,inventory.getStackInSlot(0));
        return invItems;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        inventory.insertItem(0,
                items.get(0)
                ,false);
        inventory.insertItem(1,
                items.get(1)
                ,false);
        inventory.insertItem(2,
                items.get(2)
                ,false);
        notifyUpdate();
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.insertItem(slot,stack,false);
    }

    @Override
    public boolean isEmpty() {
        return inventory.getStackInSlot(0).isEmpty()&&inventory.getStackInSlot(1).isEmpty()&&inventory.getStackInSlot(2).isEmpty();
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
        return slot==2 && !this.inventory.getStackInSlot(slot).isEmpty();
    }


    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return null;
    }

    public int getProgress() {
        return this.progress;
    }
    public int getMaxProgress() {
        return this.maxProgress;
    }
    public int getBurnTime() {
        return this.burn_time;
    }
    public void setBurnTime(int time){
        this.burn_time = time;
    }

    @Override
    public int getContainerSize() {
        return 3;
    }
}
