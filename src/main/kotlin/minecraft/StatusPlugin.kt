package minecraft

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class StatusPlugin : JavaPlugin(), Listener {

    init {
        instance = this
        ConfigManager.init(this)
    }

    val configPath: String
        get() = dataFolder.absolutePath

    override fun onEnable() {
        super.onEnable()
        server.pluginManager.registerEvents(this, this)
        updateBotStatus()
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        updateBotStatus()
    }

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent) {
        updateBotStatus(event.player.name)
    }

    private fun updateBotStatus(excluded: String? = null) {
        val players = server.onlinePlayers.filter { it.name != excluded }.map { it.name }
        BotManager.updateBot(
            ConfigManager.configuration?.getString("bot_token"),
            Parser.createStatusMessage(players),
            players.isEmpty(),
        )
    }

    override fun onDisable() {
        super.onDisable()
        try {
            BotManager.jda.shutdownNow()
        } catch (e: UninitializedPropertyAccessException) {
            logger.info("JDA not initialized!")
        }
    }

    companion object {

        lateinit var instance: StatusPlugin
            private set
    }
}
