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

    fun init(bot: Main) {
        configFile = File(bot.configPath + File.separator + "statusbot_config.yml")
        try {
            configuration = YamlFile(configFile)
            configuration!!.options().quoteStyleDefaults().setDefaultQuoteStyle(QuoteStyle.SINGLE)
            configuration!!.createOrLoadWithComments()

            createDefaults(bot)
            configuration!!.save()

            isInitialized = true
        } catch (ioException: IOException) {
            println("Statusbot: Something went wrong while creating the config file!")
            ioException.printStackTrace()
        }
    }

    private fun createDefaults(bot: Main) {
        configuration!!.addDefault("bot_token", "enter token here")
        configuration!!.addDefault("status_mode", "playing")
        configuration!!.addDefault("player_separator_text", ", ")
        configuration!!.addDefault("status_message", "\$AOP$ player(s) online: \$PL$")
        configuration!!.addDefault("no_player_message", "No one is online")
        bot.addConfigDefaults(configuration)
    }
}
