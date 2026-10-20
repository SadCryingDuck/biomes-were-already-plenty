package biomeswerealreadyplenty;

import me.shedaniel.autoconfig.AutoConfig;
//? if >=1.21.11
//import me.shedaniel.autoconfig.AutoConfigClient;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.client.gui.screens.Screen;

public final class Bwap {

    private Bwap() {
    }

    public static void registerConfig() {
        AutoConfig.register(BwapConfig.class, GsonConfigSerializer::new);
    }

    public static BwapConfig config() {
        return AutoConfig.getConfigHolder(BwapConfig.class).getConfig();
    }

    public static Screen configScreen(Screen parent) {
        //? if >=1.21.11 {
        /*return AutoConfigClient.getConfigScreen(BwapConfig.class, parent).get();
        *///?} else {
        return AutoConfig.getConfigScreen(BwapConfig.class, parent).get();
        //?}
    }
}
