package biomeswerealreadyplenty;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

public class BwapClient implements ClientModInitializer {

    public static BwapConfig getConfig() {
        return AutoConfig.getConfigHolder(BwapConfig.class).getConfig();
    }

    @Override
    public void onInitializeClient() {
        AutoConfig.register(BwapConfig.class, GsonConfigSerializer::new);
    }
}
