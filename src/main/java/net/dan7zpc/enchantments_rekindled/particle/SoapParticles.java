package net.dan7zpc.enchantments_rekindled.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dan7zpc.enchantments_rekindled.util.color.ColorHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SoapParticles extends TextureSheetParticle {
    protected SoapParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.lifetime = 10;
        this.friction = 0.9f;
        this.gravity = -0.2f;
        this.setSpriteFromAge(spriteSet);
        Random rand = new Random();

        float[] color = ColorHelper.hsvToRgb(rand.nextFloat(),0.25f,1f);

        this.rCol = color[0];
        this.gCol = color[1];
        this.bCol = color[2];

        float size = (float) (this.lifetime - this.age) /40;
        this.quadSize = size;
        this.setSize(size,size);
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        float size = (float) (this.lifetime - this.age) /40;
        this.quadSize = size;
        super.render(buffer, renderInfo, partialTicks);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType>{
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SoapParticles(level,x,y,z,spriteSet,xSpeed,ySpeed,zSpeed);
        }
    }
}
