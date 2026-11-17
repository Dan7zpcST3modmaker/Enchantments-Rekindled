package net.dan7zpc.enchantments_rekindled.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AbyssalFountainParticles extends RotatedParticle {
    private SpriteSet spriteSet;
    private double initial_y;
    private int power;
    protected AbyssalFountainParticles(ClientLevel level, double x, double y, double z, SpriteSet _spriteSet, double xSpeed, double ySpeed, double zSpeed, int power) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.initial_y = y;
        Random rand = new Random();
        this.lifetime = 10;
        //this.gravity = 1;
        this.hasPhysics = false;
        this.friction=0f;
        this.quadSize = 3;
        this.spriteSet = _spriteSet;
        this.power = power;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        int trueAge = Math.min(power+age,500);
        float my_size = ((float) (500 - trueAge) /500)*3;
        float size_fluctuation = ((float) Math.sin(trueAge)/30);
        float remaining_size = Math.max(my_size - size_fluctuation,0f);
        this.quadSize = remaining_size;
        this.setPos(this.x,this.initial_y+(remaining_size),this.z);
        render_with_y_rotation(buffer,renderInfo,partialTicks,0);
        render_with_y_rotation(buffer,renderInfo,partialTicks,90);
        render_with_y_rotation(buffer,renderInfo,partialTicks,180);
        render_with_y_rotation(buffer,renderInfo,partialTicks,270);
    }

    public static class Provider implements ParticleProvider<AbyssalFountainParticleOptions>{
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(AbyssalFountainParticleOptions type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {

            return new AbyssalFountainParticles(level,x,y,z,spriteSet,0,0,0, type.getPower());
        }
    }
}
