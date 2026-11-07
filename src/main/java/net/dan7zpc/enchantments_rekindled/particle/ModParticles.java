package net.dan7zpc.enchantments_rekindled.particle;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.minecraft.client.particle.FlyTowardsPositionParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, EnchantmentsRekindled.MOD_ID);

    public static final Supplier<SimpleParticleType> SOAP_PARTICLES =
            PARTICLE_TYPES.register("soap_particles", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> VOID_PLOP_PARTICLES =
            PARTICLE_TYPES.register("void_plop_particles", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> VOID_WAVE_PARTICLES =
            PARTICLE_TYPES.register("void_wave_particles", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> BIGGER_VOID_WAVE_PARTICLES =
            PARTICLE_TYPES.register("bigger_void_wave_particles", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> ABYSSAL_FOUNTAIN_PARTICLES =
            PARTICLE_TYPES.register("abyssal_fountain_particles", () -> new SimpleParticleType(true));

    public static final void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
