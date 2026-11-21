package net.dan7zpc.enchantments_rekindled.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;

public class LootItemReplacer extends LootModifier {
    public static final MapCodec<LootItemReplacer> CODEC = RecordCodecBuilder.mapCodec(inst ->
            codecStart(inst).and(
                    inst.group(
                            BuiltInRegistries.ITEM.byNameCodec().fieldOf("target_item").forGetter(m -> m.targetItem),
                            BuiltInRegistries.ITEM.byNameCodec().fieldOf("replacement_item").forGetter(m -> m.replacementItem)
                    )
            ).apply(inst,LootItemReplacer::new));

    private final Item targetItem;
    private final Item replacementItem;

    protected LootItemReplacer(LootItemCondition[] conditionsIn, Item targetItem, Item replacementItem) {
        super(conditionsIn);
        this.targetItem = targetItem;
        this.replacementItem = replacementItem;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        try {
            context.getParam(LootContextParams.BLOCK_STATE);
        }catch (NoSuchElementException e){
            for (int i = 0; i < generatedLoot.size(); i++){
                ItemStack stack = generatedLoot.get(i);
                if (stack.is(targetItem)){
                    generatedLoot.set(i,new ItemStack(replacementItem,stack.getCount()));
                }
                if (stack.isEnchanted()){
                    generatedLoot.set(i, new ItemStack(stack.getItem(),stack.getCount()));
                    //EnchantmentsRekindled.LOGGER.info("Removed enchantments from"+stack.getItem().getName(stack));
                }
            }
        }
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
