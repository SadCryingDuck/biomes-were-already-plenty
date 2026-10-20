package biomeswerealreadyplenty.fabric;

import biomeswerealreadyplenty.Bwap;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class BwapModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return Bwap::configScreen;
    }
}
