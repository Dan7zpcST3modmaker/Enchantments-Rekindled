package net.dan7zpc.enchantments_rekindled.particle;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class AbyssalFountainParticleType extends ParticleType<AbyssalFountainParticleOptions> {
    protected AbyssalFountainParticleType(boolean overrideLimitter) {
        super(overrideLimitter);
    }

    @Override
    public MapCodec<AbyssalFountainParticleOptions> codec() {
        return AbyssalFountainParticleOptions.CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, AbyssalFountainParticleOptions> streamCodec() {
        return AbyssalFountainParticleOptions.STREAM_CODEC;
    }
}
