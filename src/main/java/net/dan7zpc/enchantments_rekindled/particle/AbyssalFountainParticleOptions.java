package net.dan7zpc.enchantments_rekindled.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class AbyssalFountainParticleOptions implements ParticleOptions {
    private final int power;

    public static final MapCodec<AbyssalFountainParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("power").forGetter(o -> o.power)
            ).apply(instance,AbyssalFountainParticleOptions::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf,AbyssalFountainParticleOptions> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, o -> o.power,
            AbyssalFountainParticleOptions::new
    );

    public AbyssalFountainParticleOptions(int power){
        this.power = power;
    }

    public int getPower() {return power;}

    @Override
    public ParticleType<?> getType() {
        return ModParticles.ABYSSAL_FOUNTAIN_PARTICLES.get();
    }
}
