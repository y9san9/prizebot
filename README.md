# Motivation
There is no open source telegram bot that can purely raffle prizes, so I decided to do the one

The official bot is hosted at [@secure_prize_bot](https://t.me/secure_prize_bot)

# Running 
To run this bot by yourself provide the bot token with `BOT_TOKEN` environment variable.

### Database
By default, all data stored in `/data/` directory in json for startup convenience, but you can configure the database as following.

*Environment*:
- `DATABASE_URL`
- `DATABASE_USER`
- `DATABASE_PASSWORD`
- `DATABASE_DRIVER` - driver class name (optional, by default 'Exposed' will try to extract it from url)

Do not forget to include the driver dependency via gradle, Postgres included by default

*Database Schema*:

> it will be created automatically, but if it will change during releases you have to update schema manually. Note that there are no plans for compatibility or DB migrations

Table `states`: <br>

field | type
---|---
channel | BIGINT NOT NULL
data | TEXT NOT NULL

Table `giveaways`:

field | type
---|---
id | BIGINT AUTOINCREMENT
ownerId | BIGINT NOT NULL
title | TEXT NOT NULL
participateButton | TEXT NOT NULL
languageCode | TEXT DEFAULT NULL
winnerId | BIGINT DEFAULT NULL
raffleDate | TEXT DEFAULT NULL

Table `giveaways_active_messages`:

field | type
---|---
rowId | BIGINT AUTOINCREMENT
giveawayId | BIGINT NOT NULL
inlineMessageId | TEXT NOT NULL

Table `language_codes`:

field | type
---|---
userId | BIGINT NOT NULL
languageCode | TEXT NOT NULL

# Licence
[MIT](https://github.com/y9san9/prizebot/LICENCE)

# TODO
- [Integrate](https://github.com/y9san9/prizebot/issues/24) with random.org draws for important giveaways
