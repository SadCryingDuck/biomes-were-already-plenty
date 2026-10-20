package biomeswerealreadyplenty.forge;

import biomeswerealreadyplenty.Bwap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
//? if =1.21 {
/*import net.minecraftforge.fml.ModLoadingContext;
*///?} else
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("biomes_were_already_plenty")
public class BwapForge {

    //? if =1.21 {
    /*public BwapForge() {
        ModLoadingContext context = ModLoadingContext.get();
    *///?} else
    public BwapForge(FMLJavaModLoadingContext context) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            Bwap.registerConfig();
            context.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> Bwap.configScreen(parent))
            );
        }
    }
}
