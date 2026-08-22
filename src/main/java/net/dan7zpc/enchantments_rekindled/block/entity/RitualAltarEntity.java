package net.dan7zpc.enchantments_rekindled.block.entity;

import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.dan7zpc.enchantments_rekindled.util.PriceList;
import net.dan7zpc.enchantments_rekindled.util.PricedPair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RitualAltarEntity extends BlockEntity {
    protected int balance = 0;
    protected int timer = 80;
    protected PriceList out_price_list = new PriceList(
            new PricedPair(1, ModItems.VOIDPOWDER),
            new PricedPair(4, ModItems.METAL_SCRAP),
            new PricedPair(16, ModItems.BROKER_SHARD),
            new PricedPair(32, ModItems.NOCTETHER),
            new PricedPair(128, ModItems.ABYSSAL_JAM),
            new PricedPair(256, ModItems.VOID_TRINKET)
    );
    public RitualAltarEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.RITUAL_ALTAR_BE.get(), pos, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState blockState){
        if(!level.isClientSide()){serverBoundTick(level,pos,blockState);}
        bothBoundTick(level,pos,blockState);
    }

    private void clientBoundTick(Level level, BlockPos pos, BlockState blockState){

    }

    public void addBalance(int amount){
        balance += amount;
        reset_timer();
    }

    private void serverBoundTick(Level level, BlockPos pos, BlockState blockState){
        if (trading_started()){step_timer();}
    }

    private void bothBoundTick(Level level, BlockPos pos, BlockState blockState){
        if(time_is_up()){
            buyMaxPossible();reset_timer();}
    }

    private void buyMaxPossible() {
        List<PricedPair> itemsPricedPairs = out_price_list.getMostExpensiveProductSetAvailable(balance);
        SimpleContainer drops = new SimpleContainer(itemsPricedPairs.size());
        for (int i = 0; i < itemsPricedPairs.size(); i++) {
            ItemStack stack = new ItemStack((ItemLike) itemsPricedPairs.get(i).object());
            drops.addItem(stack);
        }
        Containers.dropContents(level,worldPosition,drops);
        flush_balance();
    }

    private void flush_balance() {
        this.balance = 0;
        notifyUpdate();
    }

    private boolean time_is_up() {
        return  timer <= 0;
    }

    private void step_timer() {
        timer--;
        notifyUpdate();
    }

    private void reset_timer(){
        timer = 80;
        notifyUpdate();
    }

    private boolean trading_started(){
        return this.balance > 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("balance",balance);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.balance = tag.getInt("balance");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return writeClient(new CompoundTag(),registries);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        readClient(tag,registries);
    }

    public void readClient(CompoundTag tag, HolderLookup.Provider registries) {
        loadAdditional(tag,registries);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries) {
        CompoundTag tag = pkt.getTag();
        readClient(tag==null? new CompoundTag():tag,registries);
    }

    public CompoundTag writeClient(CompoundTag tag, HolderLookup.Provider registries){
        saveAdditional(tag,registries);
        return tag;
    }

    public void sendData(){
        if(level instanceof ServerLevel serverLevel)
            serverLevel.getChunkSource().blockChanged(getBlockPos());
    }

    public void notifyUpdate(){
        setChanged();
        sendData();
    }

    public int getBalance(){
        return this.balance;
    }
}
