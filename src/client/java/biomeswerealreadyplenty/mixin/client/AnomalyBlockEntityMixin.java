package biomeswerealreadyplenty.mixin.client;

import biomeswerealreadyplenty.BwapConfig;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "biomesoplenty.block.entity.AnomalyBlockEntity", remap = false)
public class AnomalyBlockEntityMixin {

    @Inject(method = "getRenderState", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
    private void bwap$returnOwnState(CallbackInfoReturnable<BlockState> cir) {
        if (BwapConfig.get().getFixAnomalyFlickering()) {
            cir.setReturnValue(((BlockEntity) (Object) this).getBlockState());
        }
    }
}
