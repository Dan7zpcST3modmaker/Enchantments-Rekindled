package net.dan7zpc.enchantments_rekindled.block.entity;

import net.dan7zpc.enchantments_rekindled.block.ModBlocks;
import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.dan7zpc.enchantments_rekindled.recipe.EnchantmentsPedestalRecipe;
import net.dan7zpc.enchantments_rekindled.recipe.EnchantmentsPedestalRecipeInput;
import net.dan7zpc.enchantments_rekindled.recipe.ModRecipes;
import net.dan7zpc.enchantments_rekindled.util.EnchantmentHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        if(this.isEmpty()){resetProgress();return;}
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

    private void craft() {
        ItemStack stack = inventory.getStackInSlot(0);
        ItemStack result = enchantable(stack)? enchanterCraft():infuserCraft();
        this.clearContents();
        this.inventory.insertItem(0,result,false);
        level.playSound(level.getNearestPlayer(
                worldPosition.getX() +0.5, worldPosition.getY()+0.5, worldPosition.getZ()+0.5,
                15,true
        ),worldPosition, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1f,1f);
        this.notifyUpdate();
    }

    private ItemStack infuserCraft(){
        Optional<RecipeHolder<EnchantmentsPedestalRecipe>> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().value().outputItem().copy();
        return result;
    }

    private ItemStack enchanterCraft(){
        String template = getEnchantmentsTemplate();
        if (Objects.equals(template, "none")) return EnchantmentHelper.affixRandomExperienceStamp(inventory.getStackInSlot(0),level);
        return EnchantmentHelper.affixExperienceStamp(inventory.getStackInSlot(0),template,level);
    }

    private String getEnchantmentsTemplate(){
        String to_return = "none";
        to_return = getTemplate(this.worldPosition.north());
        if (Objects.equals(to_return, "none")) to_return = getTemplate(this.worldPosition.west());
        if (Objects.equals(to_return, "none")) to_return = getTemplate(this.worldPosition.east());
        if (Objects.equals(to_return, "none")) to_return = getTemplate(this.worldPosition.south());
        return to_return;
    }

    private String getTemplate(BlockPos pos){
        if (!isPedestal(pos)) return "none";
        assert level != null;
        BlockEntity blockEntity = level.getBlockEntity(pos);
        BaseContainerBlockEntity  baseContainerBlockEntity = (BaseContainerBlockEntity) blockEntity;
        ItemStack template = baseContainerBlockEntity.getItem(0);
        ItemEnchantments enchantments = template.get(DataComponents.ENCHANTMENTS);
        if(enchantments==null||enchantments.isEmpty())return"none";
        Holder<Enchantment> enchantment = enchantments.keySet().iterator().next();

        ResourceLocation enchKey = enchantment.unwrapKey()
                .map(ResourceKey::location)
                .orElse(null);

        if(enchKey == null) return"none";

        baseContainerBlockEntity.removeItem(0,1);
        return EnchantmentHelper.getStampNameFromTemplate(enchKey);
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
