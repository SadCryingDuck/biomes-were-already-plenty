package biomeswerealreadyplenty

import net.fabricmc.api.ClientModInitializer

object BwapClient : ClientModInitializer {
    override fun onInitializeClient() {
        BwapConfig.register()
    }
}
