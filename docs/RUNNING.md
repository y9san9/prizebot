# Running
    Dockerfile available for running, please check Dockerfile and prizebot.env

To run this bot by yourself provide the bot token with `BOT_TOKEN` environment variable.

You can also specify `LOG_CHAT_ID` if you want to send crashes to some chat.

Since 2.0 version, there is also `RANDOM_ORG_API_KEY` variable required from [api.random.org](https://api.random.org)

## Database Setup

First, provide this _environment_ variables:
- `DATABASE_URL`
- `DATABASE_USER`
- `DATABASE_PASSWORD`
- `DATABASE_DRIVER` - driver class name (optional, by default 'Exposed' will try to extract it from url)

Do not forget to [include](https://github.com/y9san9/prizebot/blob/dev/buildSrc/src/main/kotlin/Dependencies.kt#L9) the driver dependency via gradle, Postgres included by default

## Schema
    Created automatically. Since 2.0 version, there are also auto migrations of schema for releases.

> **Note**: There are migrations **only** for releases because official instance deployed on release only. So if you trying to deploy bot from last commit, incomplete migration will be applied (`21-22` for example) and if there will be added some other changes in `21-22` migration, program won't apply them since the `21-22` migration was already applied. This has been done for decreasing migrations count.
