# Running
    Dockerfile available for running, please check Dockerfile and prizebot.env

To run this bot by yourself please provide:
- The bot token with `BOT_TOKEN` environment variable
- Random.org api key with `RANDOM_ORG_API_KEY` variable from [api.random.org](https://api.random.org)

You can also specify `LOG_CHAT_ID` if you want to send crashes to some chat.

## Database Setup

First, provide this _environment_ variables:
- `DATABASE_URL`
- `DATABASE_USER`
- `DATABASE_PASSWORD`
- `DATABASE_DRIVER` - driver class name (optional, by default 'Exposed' will try to extract it from url)

Do not forget to [include](../buildSrc/src/main/kotlin/Dependencies.kt#L9) the driver dependency via gradle, Postgres included by default

## Schema
    Created automatically and have auto schema migrations for releases.

### :warning: Be careful :warning: 
> There are migrations **only** for releases because official instance deploys on release only. So if you trying to deploy bot from last commit, incomplete migration may be applied (with name `21-22` for example) and if there will be added some other changes in `21-22` migration before release, program won't apply them since the `21-22` migration was already applied. This has been done for decreasing migrations count.
