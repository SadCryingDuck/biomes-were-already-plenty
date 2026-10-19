package biomeswerealreadyplenty;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "biomes-were-already-plenty")
public class BwapConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean fixAnomalyFlickering = true;

    @ConfigEntry.Gui.Tooltip
    public boolean freezeAnomalyTexture = false;
}
