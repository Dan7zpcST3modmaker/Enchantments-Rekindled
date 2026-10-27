package net.dan7zpc.enchantments_rekindled.abyss;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public record VoidEvent(String name, ResourceLocation[] structures) {
    public void call(ServerLevel level, BlockPos position){
        for (ResourceLocation structure : structures) {
            level.getStructureManager().get(structure).ifPresent(template -> {
                template.placeInWorld(level, position,position, ModVoidEventsSets.default_settings,level.random,3);
            });
            System.out.println(structure.toString() + " placed");
        }
        System.out.println(name+" called");
    }
}
