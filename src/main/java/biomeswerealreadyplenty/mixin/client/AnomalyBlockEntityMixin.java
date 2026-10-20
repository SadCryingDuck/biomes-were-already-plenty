package biomeswerealreadyplenty.mixin.client;

import biomeswerealreadyplenty.Bwap;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "biomesoplenty.block.entity.AnomalyBlockEntity", remap = false)
public class AnomalyBlockEntityMixin {

    @Inject(method = "getRenderState", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
    private void bwap$returnStableState(CallbackInfoReturnable<BlockState> cir) {
        if (Bwap.config().fixAnomalyFlickering) {
            cir.setReturnValue(bwap$stableState(((BlockEntity) (Object) this).getBlockState()));
        }
    }

    private static BlockState bwap$stableState(BlockState state) {
        Property<?> type = state.getBlock().getStateDefinition().getProperty("type");
        return type == null ? state : bwap$withValue(state, type, "stable");
    }

    private static <T extends Comparable<T>> BlockState bwap$withValue(BlockState state, Property<T> property, String value) {
        return property.getValue(value).map(parsed -> state.setValue(property, parsed)).orElse(state);
    }
}
