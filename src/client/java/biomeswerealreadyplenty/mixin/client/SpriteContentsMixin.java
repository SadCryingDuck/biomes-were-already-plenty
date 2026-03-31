package biomeswerealreadyplenty.mixin.client;

import biomeswerealreadyplenty.BwapConfig;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.SpriteTicker;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpriteContents.class)
public abstract class SpriteContentsMixin {

    @Shadow
    public abstract ResourceLocation name();

    @Inject(method = "createTicker", at = @At("HEAD"), cancellable = true)
    private void bwap$freezeAnomalyTexture(CallbackInfoReturnable<SpriteTicker> cir) {
        if (BwapConfig.get().getFreezeAnomalyTexture()) {
            ResourceLocation name = this.name();
            if (name.getNamespace().equals("biomesoplenty") && name.getPath().contains("anomaly")) {
                cir.setReturnValue(null);
            }
        }
    }
}
