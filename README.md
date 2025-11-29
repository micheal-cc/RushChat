# RushChat ⚡

Join Our [Discord](https://micheal.cc/discord) or visit our [Website](https://micheal.cc)

A lightweight, fully asynchronous chat formatting plugin designed specifically for Paper 1.21.

## Features

* **Async Core**: Built on Paper's `AsyncChatEvent` to ensure chat formatting never lags the main server thread.
* **Hex & Legacy Colors**: Full support for `&#RRGGBB` hex codes and standard `&a` legacy codes.
* **PlaceholderAPI**: Native support for PAPI placeholders in prefixes, suffixes, and hover text.
* **Interactive Chat**: Configurable Hover events and Click actions (e.g., click to DM).
* **Moderation**: Built-in tools to Lock and Clear the chat.

## Installation

1. Drop `RushChat.jar` into your server's `plugins/` folder.
2. (Optional) Install **PlaceholderAPI** for placeholder support.
3. Restart your server.

## Commands

All commands are handled under the main `/rushchat` command.

Reloads the configuration files (config.yml and formats/format.yml)
```bash
/rushchat reload
```
Clears the chat for all players
```bash
/rushchat clear
```

Toggles the Global Chat Lock (prevents players from speaking)
```bash
/rushchat lock
```

## Permissions

| Permission Node | Description | Default |
| :--- | :--- | :--- |
| `rushchat.admin` | Allows using reload/clear/lock and bypassing the chat lock. | `OP` |

## Configuration

### Global Settings `config.yml`

The message shown when chat is cleared
```bash
clear-chat-message: "&aChat has been cleared by an administrator."
```

How many empty lines to print to clear the console/chat
```bash
clear-chat-lines: 100
```

Message sent to players trying to speak during a lock
```bash
chat-locked-message: "&cThe chat is currently locked."
```

### Formatting `formats/format.yml`


The visual format of the chat
```bash
chat-format: "%luckperms_prefix% &7%player_name% %luckperms_suffix% &8➠ &f"
```
Hover tooltip (Supports PAPI)
```bash
hover-message:
  - "&#ffc900%player_name%"
  - "&7Rank: %luckperms_primary_group_name%"
  - ""
  - "&e➠ &l&nCLICK&e to Message"
```

Action when clicking the prefix
```bash
click-action: "SUGGEST_COMMAND:/msg %player_name% "
```
Types: `SUGGEST_COMMAND, RUN_COMMAND, OPEN_URL, COPY_TO_CLIPBOARD`


**Developer's Note:**
```bash
While most of the project was made by myself and some help from my team,
this project still utilised the use of Artifical Intelligence in the cleanup & final touches of the code
```