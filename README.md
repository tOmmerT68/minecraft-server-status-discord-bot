# Discord Status Plugin
## Overview
This Minecraft plugin allows seamless integration between your Spigot/Paper server and a user-made Discord bot. By connecting to the Discord bot using its token, the plugin displays the server status and the number of online players in the corresponding Discord server. Installation is simple; just drag the JAR file into the plugins folder.

## Features
- Connects to a Discord bot using its token.
- Displays server status in the connected Discord server.
- Shows the number of online players in Discord.
## Installation
#### Download the JAR file:

Visit the releases section.
Download the latest version of the JAR file.

#### Install the plugin:
Drag the downloaded JAR file into the plugins folder of your Spigot/Paper server.

#### Configure Discord bot token:

- Open your server's plugins folder.
- Find the `Discord-StatusBot` folder
- Locate the config.yml file.
- Add your Discord bot token under the `bot_token` configuration.
  
``` yaml
bot_token: "YOUR_DISCORD_BOT_TOKEN"
```

## Usage
Once the plugin is installed and the Discord bot token is configured, the integration is automatic. The server status and player count will be displayed in the connected Discord server.

## Issues
If you encounter any issues or have suggestions, please [create an issue](https://github.com/tOmmerT68/minecraft-server-status-discord-bot/issues) on our issue tracker.

## License
This project is licensed under the [MIT License](https://github.com/tOmmerT68/minecraft-server-status-discord-bot/blob/master/LICENSE).
