<h1 align="center">Welcome to prizebot 👋</h1>
<p align="center">
    <a href="https://www.codacy.com/gh/y9san9/prizebot/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=y9san9/prizebot&amp;utm_campaign=Badge_Grade"><img alt="Codacy" src="https://app.codacy.com/project/badge/Grade/ef298b554e2340508e1f8b1635dcc6b9"/></a>
    <a href="https://hitsofcode.com/github/y9san9/prizebot/view?branch=master"><img alt="HoC" src="https://hitsofcode.com/github/y9san9/prizebot?branch=master"/></a>
    <img src="https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https://github.com/y9san9/prizebot&title=views%20daily/total" alt="Views" />
    <br><br>
    An open source telegram bot to purely raffle prizes with <a href="https://random.org">random.org</a>
</p>

The official bot is hosted at [@secure_prize_bot](https://t.me/secure_prize_bot)

# 🚩 TODO
- [Road](https://github.com/y9san9/prizebot/milestone/1) to production 

# 🚀 Running
    Dockerfile available for running, please check Dockerfile and prizebot.env

To run this bot by yourself provide the bot token with `BOT_TOKEN` environment variable.

You can always specify `LOG_CHAT_ID` if you want to send crashes to some chat.

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

# 📖 Licence
[MIT](https://github.com/y9san9/prizebot/blob/master/LICENCE)
