package net.dan7zpc.enchantments_rekindled.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class VoidPlopParticles extends RotatedParticle {
    protected VoidPlopParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.lifetime = 8;
        this.gravity = 1;
        this.hasPhysics = true;
        this.friction=0.5f;
        this.quadSize = 0.4f;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        render_with_y_rotation(buffer,renderInfo,partialTicks,0);
        render_with_y_rotation(buffer,renderInfo,partialTicks,90);
        render_with_y_rotation(buffer,renderInfo,partialTicks,180);
        render_with_y_rotation(buffer,renderInfo,partialTicks,270);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType>{
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new VoidPlopParticles(level,x,y,z,spriteSet,0,500,0);
        }
    }
}
