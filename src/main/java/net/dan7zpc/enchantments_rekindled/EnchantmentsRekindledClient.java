package net.dan7zpc.enchantments_rekindled;

import net.dan7zpc.enchantments_rekindled.block.entity.ModBlockEntities;
import net.dan7zpc.enchantments_rekindled.block.entity.renderer.EnchantmentsPedestalEntityRenderer;
import net.dan7zpc.enchantments_rekindled.block.entity.renderer.TableOfKnowledgeEntityRenderer;
import net.dan7zpc.enchantments_rekindled.particle.ModParticles;
import net.dan7zpc.enchantments_rekindled.particle.SoapParticles;
import net.dan7zpc.enchantments_rekindled.particle.VoidPlopParticles;
import net.dan7zpc.enchantments_rekindled.particle.VoidWaveParticles;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = EnchantmentsRekindled.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = EnchantmentsRekindled.MOD_ID, value = Dist.CLIENT)
public class EnchantmentsRekindledClient {
    public EnchantmentsRekindledClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockEntities.ENCHANTMENTS_PEDESTAL_BE.get(), EnchantmentsPedestalEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.TABLE_OF_KNOWLEDGE_BE.get(), TableOfKnowledgeEntityRenderer::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        EnchantmentsRekindled.LOGGER.info("HELLO FROM CLIENT SETUP");
        EnchantmentsRekindled.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
    @SubscribeEvent
    public static void  RegisterParticleFactories(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(ModParticles.SOAP_PARTICLES.get(), SoapParticles.Provider::new);
        event.registerSpriteSet(ModParticles.VOID_PLOP_PARTICLES.get(), VoidPlopParticles.Provider::new);
        event.registerSpriteSet(ModParticles.VOID_WAVE_PARTICLES.get(), VoidWaveParticles.Provider::new);
    }
}
