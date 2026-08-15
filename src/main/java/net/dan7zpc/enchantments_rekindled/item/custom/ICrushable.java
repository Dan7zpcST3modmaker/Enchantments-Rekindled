package net.dan7zpc.enchantments_rekindled.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface ICrushable {
    void crush(Level level, Player player, InteractionHand usedHand);
}
