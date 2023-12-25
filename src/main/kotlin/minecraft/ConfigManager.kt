package minecraft

import org.simpleyaml.configuration.file.YamlFile
import org.simpleyaml.configuration.implementation.api.QuoteStyle
import java.io.File
import java.io.IOException

object ConfigManager {

    var isInitialized: Boolean = false
        private set

    private var configFile: File? = null

    var configuration: YamlFile? = null
        private set

    fun init(plugin: StatusPlugin) {
        configFile = File(plugin.configPath + File.separator + "config.yml")
        try {
            configuration = YamlFile(configFile)
            configuration!!.options().quoteStyleDefaults().setDefaultQuoteStyle(QuoteStyle.SINGLE)
            configuration!!.createOrLoadWithComments()

            createDefaults()
            configuration!!.save()

            isInitialized = true
        } catch (error: IOException) {
            println("[Discord-StatusBot] Something went wrong while creating the config file!")
            error.printStackTrace()
        }
    }

    private fun createDefaults() {
        configuration!!.addDefault("bot_token", "enter token here")
        configuration!!.addDefault("status_mode", "playing")
        configuration!!.addDefault("player_separator_text", ", ")
        configuration!!.addDefault("status_message", "\$AOP$ player(s) online: \$PL$")
        configuration!!.addDefault("no_player_message", "No one is online")
    }
}
