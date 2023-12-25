package minecraft

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import org.simpleyaml.configuration.file.YamlConfiguration

class Main : JavaPlugin(), Listener {

    init {
        initAll()
    }

    private fun initAll() {
        ConfigManager.init(this)
    }

    fun addConfigDefaults(configuration: YamlConfiguration?) {

    }

    val configPath: String
        get() = dataFolder.absolutePath

    override fun onEnable() {
        initAll()
        server.pluginManager.registerEvents(this, this)

        BotManager.registerBot(
            ConfigManager.configuration?.getString("bot_token"),
            Parser.createStatusMessage(makeStringList(server.onlinePlayers.toList()))
        )
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        BotManager.registerBot(
            ConfigManager.configuration?.getString("bot_token"),
            Parser.createStatusMessage(makeStringList(server.onlinePlayers.toList()))
        )
    }

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent) {
        BotManager.registerBot(
            ConfigManager.configuration?.getString("bot_token"),
            Parser.createStatusMessage(makeStringList(server.onlinePlayers.toList(), event.player.name))
        )
    }

    private fun makeStringList(players: List<Player>, excluded: String? = null): List<String> {
        val list = mutableListOf<String>()
        for (player in players) {
            if (player.name != excluded) list.add(player.name)
        }
        return list
    }

    override fun onDisable() {
        super.onDisable()
        BotManager.jda.shutdownNow()
    }

    override fun onLoad() {
        super.onLoad()
        println("Plugin loaded!")
    }
}

fun main() {
    Main()
}
