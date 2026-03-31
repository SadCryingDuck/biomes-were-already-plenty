package biomeswerealreadyplenty

import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer

@Config(name = "biomes-were-already-plenty")
class BwapConfig : ConfigData {

    @ConfigEntry.Gui.Tooltip
    var fixAnomalyFlickering: Boolean = true

    @ConfigEntry.Gui.Tooltip
    var freezeAnomalyTexture: Boolean = false

    companion object {
        @JvmStatic
        fun register() {
            AutoConfig.register(BwapConfig::class.java, ::GsonConfigSerializer)
        }

        @JvmStatic
        fun get(): BwapConfig = AutoConfig.getConfigHolder(BwapConfig::class.java).config
    }
}
