package biomeswerealreadyplenty

import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer
import net.fabricmc.api.ClientModInitializer

object BwapClient : ClientModInitializer {

    @JvmStatic
    val config: BwapConfig
        get() = AutoConfig.getConfigHolder(BwapConfig::class.java).config

    override fun onInitializeClient() {
        AutoConfig.register(BwapConfig::class.java, ::GsonConfigSerializer)
    }
}
