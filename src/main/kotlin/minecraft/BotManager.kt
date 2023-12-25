package minecraft

import minecraft.ConfigManager.configuration
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus.DO_NOT_DISTURB
import net.dv8tion.jda.api.OnlineStatus.ONLINE
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.exceptions.InvalidTokenException

object BotManager {

    lateinit var jda: JDA
        private set

    fun updateBot(botToken: String?, status: String?, isEmpty: Boolean) {
        val logger = StatusPlugin.instance.logger
        if (botToken == null || botToken == "enter token here" || botToken == "") {
            logger.severe("Invalid Bot Token!")
            logger.severe("Get the token of your discord bot from here: https://discord.com/developers/applications")
            logger.severe("After that put the token in the config.yml file.")
            return
        }
        if (!this::jda.isInitialized) {
            try {
                val builder = JDABuilder.createDefault(botToken)
                if (!status.isNullOrEmpty()) {
                    when (configuration?.getString("status_mode")?.lowercase()) {
                        "playing" -> builder.setActivity(Activity.playing(status))
                        "competing" -> builder.setActivity(Activity.competing(status))
                        "listening" -> builder.setActivity(Activity.listening(status))
                        "streaming" -> builder.setActivity(Activity.streaming(status, null))
                        "watching" -> builder.setActivity(Activity.watching(status))
                        else -> {
                            builder.setActivity(Activity.of(Activity.ActivityType.PLAYING, status))
                            logger.warning("Invalid discord bot status mode: " + configuration?.getString("status_mode"))
                            logger.warning("Valid statuses are: playing, competing, listening, watching")
                        }
                    }
                } else {
                    builder.setActivity(null)
                }
                jda = builder.build()
            } catch (e: InvalidTokenException) {
                logger.severe("Invalid Bot Token!")
                logger.severe("Get the token of your discord bot from here: https://discord.com/developers/applications")
                logger.severe("After that put the token in the config.yml file.")
            }
        } else {
            if (!status.isNullOrEmpty()) {
                when (configuration?.getString("status_mode")?.lowercase()) {
                    "playing" -> jda.presence.activity = Activity.playing(status)
                    "competing" -> jda.presence.activity = Activity.competing(status)
                    "listening" -> jda.presence.activity = Activity.listening(status)
                    "streaming" -> jda.presence.activity = Activity.streaming(status, null)
                    "watching" -> jda.presence.activity = Activity.watching(status)
                    else -> {
                        jda.presence.activity = Activity.of(Activity.ActivityType.PLAYING, status)
                        logger.warning("Invalid discord bot status mode: " + configuration?.getString("status_mode"))
                        logger.warning("Valid statuses are: playing, competing, listening, watching")
                    }
                }
            }
        }
        runCatching { jda.presence.setStatus(if (isEmpty) DO_NOT_DISTURB else ONLINE) }
            .onFailure { logger.info("JDA not initialized!") }
    }
}
