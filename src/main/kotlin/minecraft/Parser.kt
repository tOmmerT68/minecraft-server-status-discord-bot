package minecraft

import minecraft.ConfigManager.configuration
import java.lang.String.join

object Parser {

    fun createStatusMessage(players: List<String>): String {
        if (ConfigManager.isInitialized) {
            val message: String = configuration?.getString("status_message").orEmpty()
            val separator: String = configuration?.getString("player_separator_text").orEmpty()
            val noPlayerMessage: String = configuration?.getString("no_player_message").orEmpty()

            return shorten(if (players.isEmpty()) noPlayerMessage else readMessage(message, separator, players))
        } else return ""
    }

    private fun shorten(str: String): String {
        return if (str.length > 128) str.substring(0, 124) + "..." else str
    }

    private fun readMessage(message: String, separator: String, players: List<String>): String {
        var res = ""
        val CmeChars = message.toCharArray()
        var partVarName: CharArray? = null
        var partVarNameLength = 0
        var readingVarName = false
        for (c in CmeChars) {
            if (c == '$') {
                readingVarName = !readingVarName
                if (readingVarName) {
                    partVarName = CharArray(message.length - 2)
                    partVarNameLength = 0
                } else {
                    res += getFromVarName(
                        String(partVarName!!).substring(
                            0,
                            partVarNameLength
                        ), separator, players
                    )
                }
            } else {
                if (readingVarName) {
                    partVarName!![partVarNameLength] = c
                    partVarNameLength++
                } else {
                    res += c.toString()
                }
            }
        }
        return res
    }

    private fun getFromVarName(varName: String, separator: String, players: List<String>): String {
        when (varName) {
            "AOP" -> return players.size.toString()
            "PL" -> return join(separator, players)
            else -> {
                println("Statusbot: Unknown variable: $varName")
                return ""
            }
        }
    }
}
