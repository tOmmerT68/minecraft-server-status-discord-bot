package minecraft

import minecraft.ConfigManager.configuration
import java.lang.String.join

object Parser {

    fun createStatusMessage(players: List<String>): String {
        return if (ConfigManager.isInitialized) {
            val message: String = configuration?.getString("status_message").orEmpty()
            val separator: String = configuration?.getString("player_separator_text").orEmpty()
            val noPlayerMessage: String = configuration?.getString("no_player_message").orEmpty()

            shorten(if (players.isEmpty()) noPlayerMessage else readMessage(message, separator, players))
        } else ""
    }

    private fun shorten(str: String): String {
        return if (str.length > 128) str.substring(0, 124) + "..." else str
    }

    private fun readMessage(message: String, separator: String, players: List<String>): String {
        return message.split("$").joinToString(separator = " ") { getFromVarName(it, separator, players) ?: it }
    }

    private fun getFromVarName(varName: String, separator: String, players: List<String>): String? {
        return when (varName) {
            "AOP" -> players.size.toString()
            "PL" -> join(separator, players)
            else -> null
        }
    }
}
