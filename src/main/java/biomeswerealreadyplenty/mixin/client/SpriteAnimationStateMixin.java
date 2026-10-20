package biomeswerealreadyplenty.mixin.client;

import net.minecraft.client.renderer.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
//? if >=1.21.11 {
/*import biomeswerealreadyplenty.FrozenAnimation;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?}

//? if >=1.21.11 {
/*@Mixin(SpriteContents.AnimationState.class)
public abstract class SpriteAnimationStateMixin implements FrozenAnimation {

    @Unique
    private boolean bwap$frozen;

    @Override
    public void bwap$freeze() {
        this.bwap$frozen = true;
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void bwap$holdFirstFrame(CallbackInfo ci) {
        if (this.bwap$frozen) {
            ci.cancel();
        }
    }
}
*///?} else {
@Mixin(SpriteContents.class)
public abstract class SpriteAnimationStateMixin {
}
//?}
