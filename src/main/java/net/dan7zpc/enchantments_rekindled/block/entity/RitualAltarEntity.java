package net.dan7zpc.enchantments_rekindled.block.entity;

import net.dan7zpc.enchantments_rekindled.block.ModBlockTagProvider;
import net.dan7zpc.enchantments_rekindled.item.ModItemTagProvider;
import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.dan7zpc.enchantments_rekindled.particle.ModParticles;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
    protected boolean correctStructure = false;
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

    @Override
    public void onLoad() {
        super.onLoad();
        recalculateModules();
    }

    public void recalculateModules(){
        this.correctStructure = checkForAltar();
    }

    private boolean checkForAltar(){
        int slabs = 0;
        int blocks = 0;
        for(int x = -1; x<2;x++){for(int z = -1; z<2; z++){
            BlockPos currentlyChecking = this.worldPosition.east(x).north(z);
            assert level != null;
            BlockState currentlyCheckingBlockState = level.getBlockState(currentlyChecking);
            if(currentlyCheckingBlockState.is(ModBlockTagProvider.STONE_BLOCKS) || currentlyCheckingBlockState.is(ModBlockTagProvider.NETHER_STONES) || currentlyCheckingBlockState.is(ModBlockTagProvider.SANDSTONE_BLOCKS) || currentlyCheckingBlockState.is(ModBlockTagProvider.END_STONES)){
                blocks++;
            } else if (currentlyCheckingBlockState.is(ModBlockTagProvider.SLABS)) {
                slabs++;
            }
        }}
        return blocks>=2 && slabs>=2 && blocks+slabs >= 8;
    }

    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity){
        if(!this.correctStructure)return;
        ItemEntity itemEntity = (ItemEntity) entity;
        ItemStack item = itemEntity.getItem();
        int price = item.getCount();
        price *= getPriceMultiplier(item);
        if(price < 1){return;}
        this.addBalance(price);
        if(!level.isClientSide()){
            ServerLevel serverLevel = (ServerLevel) level;
            serverLevel.sendParticles(ModParticles.VOID_WAVE_PARTICLES.get(), pos.getX()+0.5f,pos.above().getY(),pos.getZ()+0.5f,(int)Math.ceil((double) item.getCount()/8),0,0,0,0);
        }
        level.playSound(level.getNearestPlayer(
                worldPosition.getX() +0.5, worldPosition.getY()+0.5, worldPosition.getZ()+0.5,
                15,true
        ),worldPosition, SoundEvents.PLAYER_SWIM, SoundSource.BLOCKS, 1f,0.2f);
        itemEntity.kill();
    }

    private int getPriceMultiplier(ItemStack item){
        //region BigBadCode
        if(item.is(ModItemTagProvider.ABYSS_BLACKLIST)){return  0;}
        else if(item.is(ModItemTagProvider.GEMS)){return 27;}
        else if(item.is(ModItemTagProvider.STONES)){return 2;}
        else if(item.is(ModItemTagProvider.ORES)){return 3;}
        else if(item.is(ModItemTagProvider.INGOTS)){return 18;}
        else if(item.is(ModItemTagProvider.RAW_MATERIALS)){return 9;}
        else if(item.is(ModItemTagProvider.NUGGETS)){return 2;}
        else if(item.is(ModItemTagProvider.LOGS)){return 4;}
        else if(item.is(ItemTags.FOOT_ARMOR)){return 2;}
        else if(item.is(ItemTags.LEG_ARMOR)){return 3;}
        else if(item.is(ItemTags.CHEST_ARMOR)){return 7;}
        else if(item.is(ModItemTagProvider.LEATHERS)){return 3;}
        else if(item.is(ItemTags.HEAD_ARMOR)){return 2;}
        else if(item.is(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)){return 7;}
        else if(item.is(ItemTags.TRIM_TEMPLATES)){return 5;}
        else if(item.is(ModItemTagProvider.SEEDS)){return 2;}
        else if(item.is(ModItemTagProvider.FOODS)){return 3;}
        else if(item.is(ModItemTagProvider.CROPS)){return 2;}
        else if(item.is(ModItemTagProvider.STORAGE_BLOCKS)){return 36;}
        return 1;
        //endregion BigBadCode
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
        assert level != null;
        if(!level.isClientSide()){
            ServerLevel serverLevel = (ServerLevel) level;
            serverLevel.sendParticles(ModParticles.VOID_WAVE_PARTICLES.get(), this.worldPosition.getX()+0.5f,this.worldPosition.above().getY(),this.worldPosition.getZ()+0.5f,1,0,0,0,0);
        }
        level.playSound(level.getNearestPlayer(
                worldPosition.getX() +0.5, worldPosition.getY()+0.5, worldPosition.getZ()+0.5,
                15,true
        ),worldPosition, SoundEvents.PLAYER_SWIM, SoundSource.BLOCKS, 1f,1.8f);
        Containers.dropContents(level,worldPosition.above(),drops);
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
