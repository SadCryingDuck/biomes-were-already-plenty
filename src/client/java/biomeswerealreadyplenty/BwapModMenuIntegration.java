package biomeswerealreadyplenty;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
//? if >=1.21.11 {
/*import me.shedaniel.autoconfig.AutoConfigClient;
*///?} else {
import me.shedaniel.autoconfig.AutoConfig;
//?}

public class BwapModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        //? if >=1.21.11 {
        /*return parent -> AutoConfigClient.getConfigScreen(BwapConfig.class, parent).get();
        *///?} else {
        return parent -> AutoConfig.getConfigScreen(BwapConfig.class, parent).get();
        //?}
    }
}
