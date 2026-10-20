package biomeswerealreadyplenty.fabric;

import biomeswerealreadyplenty.Bwap;
import net.fabricmc.api.ClientModInitializer;

public class BwapFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Bwap.registerConfig();
    }
}
