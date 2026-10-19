package biomeswerealreadyplenty.mixin.client;

import biomeswerealreadyplenty.BwapClient;
//? if >=1.21.11
//import biomeswerealreadyplenty.FrozenAnimation;
import net.minecraft.client.renderer.texture.SpriteContents;
//? if <1.21.11
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

    //? if >=1.21.11 {
    /*@Inject(method = "createAnimationState", at = @At("RETURN"))
    private void bwap$freezeAnomalyTexture(CallbackInfoReturnable<SpriteContents.AnimationState> cir) {
        if (cir.getReturnValue() != null && bwap$isFrozen()) {
            ((FrozenAnimation) (Object) cir.getReturnValue()).bwap$freeze();
        }
    }
    *///?} else {
    @Inject(method = "createTicker", at = @At("HEAD"), cancellable = true)
    private void bwap$freezeAnomalyTexture(CallbackInfoReturnable<SpriteTicker> cir) {
        if (bwap$isFrozen()) {
            cir.setReturnValue(null);
        }
    }
    //?}

    private boolean bwap$isFrozen() {
        if (!BwapClient.getConfig().freezeAnomalyTexture) {
            return false;
        }
        ResourceLocation name = this.name();
        return name.getNamespace().equals("biomesoplenty") && name.getPath().contains("anomaly");
    }
}
