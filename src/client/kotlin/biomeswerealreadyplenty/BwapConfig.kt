package biomeswerealreadyplenty

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = "biomes-were-already-plenty")
class BwapConfig : ConfigData {

    @ConfigEntry.Gui.Tooltip
    var fixAnomalyFlickering: Boolean = true

    @ConfigEntry.Gui.Tooltip
    var freezeAnomalyTexture: Boolean = false
}
