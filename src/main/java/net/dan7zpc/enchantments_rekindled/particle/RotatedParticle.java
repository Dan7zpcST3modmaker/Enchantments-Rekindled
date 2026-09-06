package net.dan7zpc.enchantments_rekindled.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class RotatedParticle extends TextureSheetParticle {
    protected RotatedParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    protected void render_with_y_rotation(VertexConsumer buffer, Camera renderInfo, float partialTicks, float deg){
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotateY((float) Math.toRadians(deg));
        //if (this.roll != 0.0F) {
        //    quaternionf.rotateZ(Mth.lerp(partialTicks, this.oRoll, this.roll));
        //}

        this.renderRotatedQuad(buffer, renderInfo, quaternionf, partialTicks);
    }

    protected void render_with_x_rotation(VertexConsumer buffer, Camera renderInfo, float partialTicks, float deg){
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotateX((float) Math.toRadians(deg));
        //if (this.roll != 0.0F) {
        //    quaternionf.rotateZ(Mth.lerp(partialTicks, this.oRoll, this.roll));
        //}

        this.renderRotatedQuad(buffer, renderInfo, quaternionf, partialTicks);
    }

    protected void render_with_z_rotation(VertexConsumer buffer, Camera renderInfo, float partialTicks, float deg){
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotateZ((float) Math.toRadians(deg));
        //if (this.roll != 0.0F) {
        //    quaternionf.rotateZ(Mth.lerp(partialTicks, this.oRoll, this.roll));
        //}

        this.renderRotatedQuad(buffer, renderInfo, quaternionf, partialTicks);
    }
}
