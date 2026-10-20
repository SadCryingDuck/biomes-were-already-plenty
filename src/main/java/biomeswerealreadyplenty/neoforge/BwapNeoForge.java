package biomeswerealreadyplenty.neoforge;

import biomeswerealreadyplenty.Bwap;
import net.neoforged.fml.common.Mod;
//? if >=1.20.6 {
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
//?} else {
/*import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.ConfigScreenHandler;
*///?}

//? if >=1.20.6 {
@Mod(value = "biomes_were_already_plenty", dist = Dist.CLIENT)
public class BwapNeoForge {

    public BwapNeoForge(ModContainer container) {
        Bwap.registerConfig();
        container.registerExtensionPoint(IConfigScreenFactory.class, (ignored, parent) -> Bwap.configScreen(parent));
    }
}
//?} else {
/*@Mod("biomes_were_already_plenty")
public class BwapNeoForge {

    public BwapNeoForge() {
        if (FMLEnvironment.dist.isClient()) {
            Bwap.registerConfig();
            ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> Bwap.configScreen(parent))
            );
        }
    }
}
*///?}
