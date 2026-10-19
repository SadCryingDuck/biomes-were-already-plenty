package biomeswerealreadyplenty

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
//? if >=1.21.11 {
/*import me.shedaniel.autoconfig.AutoConfigClient
*///?} else {
import me.shedaniel.autoconfig.AutoConfig
//?}
import net.minecraft.client.gui.screens.Screen

class BwapModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent: Screen ->
            //? if >=1.21.11 {
            /*AutoConfigClient.getConfigScreen(BwapConfig::class.java, parent).get()
            *///?} else {
            AutoConfig.getConfigScreen(BwapConfig::class.java, parent).get()
            //?}
        }
    }
}
