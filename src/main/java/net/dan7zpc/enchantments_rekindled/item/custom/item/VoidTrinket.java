package net.dan7zpc.enchantments_rekindled.item.custom.item;


import net.dan7zpc.enchantments_rekindled.util.color.ModColors;
import net.dan7zpc.enchantments_rekindled.data.ModCustomDataTypes;
import net.minecraft.client.telemetry.TelemetryProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public class VoidTrinket extends CustomModItem{
    public VoidTrinket(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        ResourceLocation dimResourceLocation = level.dimension().location();
        if(dimResourceLocation.toString().equals("enchantments_rekindled:abyss_dim")){
            int amount = stack.getOrDefault(ModCustomDataTypes.STORED_POWER,2400);
            stack.set(ModCustomDataTypes.STORED_POWER,amount-1);
            if (amount>1){return;}
            BlockPos altarPos = stack.getOrDefault(ModCustomDataTypes.STORED_POSITION, new BlockPos(0,0,0));
            teleportPlayerToDim(entity,Level.OVERWORLD,altarPos.getX()+0.5,altarPos.above().getY()+0.5,altarPos.getZ()+0.5);
            stack.setCount(0);
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        BlockPos altarPos = stack.getOrDefault(ModCustomDataTypes.STORED_POSITION, new BlockPos(0,0,0));

        ResourceLocation dimResourceLocation = level.dimension().location();
        if(dimResourceLocation.toString().equals("enchantments_rekindled:abyss_dim")) {
            level.playSound(player, player.blockPosition() , SoundEvents.PLAYER_SWIM, SoundSource.BLOCKS, 1f,1.8f);
            teleportPlayerToDim(player,Level.OVERWORLD,altarPos.getX()+0.5,altarPos.above().getY()+0.5,altarPos.getZ()+0.5);
            setPlayerGamemode(player, GameType.DEFAULT_MODE);
        }else {
            if(altarPos.distToCenterSqr(player.getX(), player.getY(), player.getZ()) > 100){return InteractionResultHolder.fail(stack);}
            level.playSound(player, player.blockPosition() , SoundEvents.PLAYER_SWIM, SoundSource.BLOCKS, 1f,0.2f);
            teleportPlayerToDim((Entity) player, ResourceKey.create(Registries.DIMENSION,ResourceLocation.parse("enchantments_rekindled:abyss_dim")),player.getX(),216,player.getZ());
            setPlayerGamemode(player,GameType.ADVENTURE);
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        player.addItem(item);
        return super.onDroppedByPlayer(item, player);
    }

    private void teleportPlayerToDim(Entity entity, ResourceKey<Level> targetDim, double x, double y, double z){
        if(!(entity instanceof ServerPlayer player)){return;}
        MinecraftServer server = player.server;
        ServerLevel targetLevel = server.getLevel(targetDim);//ResourceKey.create(Registries.DIMENSION,ResourceLocation.parse(""))
        if(targetLevel == null)return;
        player.teleportTo(targetLevel, x,y,z,player.getYRot(),player.getXRot());
    }

    private void setPlayerGamemode(Entity entity, GameType gameMode){
        if(!(entity instanceof ServerPlayer player)){return;}
        player.setGameMode(gameMode);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return ModColors.ABYSSAL_RED;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F * Mth.clamp(getRemainingPower(stack) / ((float) 2400), 0, 1));
        //return stack.getOrDefault(ModCustomDataTypes.STORED_POWER,2400)*16/2400;
    }

    public int getRemainingPower(ItemStack stack){
        return stack.getOrDefault(ModCustomDataTypes.STORED_POWER,2400);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }
}
