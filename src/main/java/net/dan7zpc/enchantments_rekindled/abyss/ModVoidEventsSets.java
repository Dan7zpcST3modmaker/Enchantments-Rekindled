package net.dan7zpc.enchantments_rekindled.abyss;

import net.dan7zpc.enchantments_rekindled.util.PriceList;
import net.dan7zpc.enchantments_rekindled.util.PricedPair;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class ModVoidEventsSets {
    public static final StructurePlaceSettings default_settings = new StructurePlaceSettings()
            .setRotation(Rotation.NONE)
            .setMirror(Mirror.NONE)
            .setIgnoreEntities(false)
            .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);

    public static final VoidEventsSet lesserEvents = new VoidEventsSet(new PriceList<VoidEvent>(
            new PricedPair<>(1, ModVoidEvents.broker)
    ));
}
