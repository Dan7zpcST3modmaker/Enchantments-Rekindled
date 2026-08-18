package net.dan7zpc.enchantments_rekindled.block.entity;

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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class PedestalEntity extends SynchronizedBlockEntity {
    protected int progress = 0;
    protected int maxProgress = 100;
    public final ItemStackHandler inventory = new ItemStackHandler(1){
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
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

    public PedestalEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> PedestalEntity.this.progress;
                    case 1 -> PedestalEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: PedestalEntity.this.progress = value;
                    case 1: PedestalEntity.this.maxProgress = value;
                };
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("progress",this.getProgress());
        tag.putInt("maxProgress",this.getMaxProgress());
        tag.put("inventory",inventory.serializeNBT(registries));
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
                items.getLast()
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
        return this.isEmpty();
    }

    @Override
    public boolean canTakeItem(Container target, int slot, ItemStack stack) {
        return !this.isEmpty() && (this.progress==-1);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return null;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.progress = tag.getInt("progress");
        this.maxProgress = tag.getInt("maxProgress");
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

    public int getProgress() {
        return this.progress;
    }
    public int getMaxProgress() {
        return this.maxProgress;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }
}
