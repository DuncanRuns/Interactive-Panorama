package me.duncanruns.intpan.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RotatingCubeMapRenderer.class)
public abstract class RotatingCubeMapRendererMixin {

    @Shadow
    private float time;

    @Shadow
    @Final
    private CubeMapRenderer cubeMap;

    @Shadow
    @Final
    private MinecraftClient client;

    private double curve(double x) {
        if (Math.abs(x) > 1) {
            x = Math.signum(x);
        }
        return Math.log(25 * Math.abs(x) + 1) /
                Math.log(26);
    }

    /**
     * @author DuncanRuns
     * @reason Custom Rotation
     */
    @Overwrite
    public void render(float delta, float alpha) {
        int width = client.getWindow().getWidth();
        float x = 2 * (float) (client.mouse.getX() - (width / 2.0)) / width;
        delta = delta * (float) (Math.signum(x) * curve(x));

        this.time += delta * 2;
        this.cubeMap.draw(this.client, MathHelper.sin(this.time * 0.001f) * 5.0f + 25.0f, -this.time * 0.1f, alpha);
    }
}
