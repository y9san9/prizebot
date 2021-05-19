# Onboarding
> No more emojis, I used to make them for end users while documentation style is stricter

You may visit this file in two cases:
1. You want just check some interesting files and sources, but don't know how to discover the code (maybe you even don't familiar with gradle projects).
2. You want to review in detail the code and maybe even contribute later.

## Just show me the bot
- [Main](../src/main/kotlin/me/y9san9/prizebot/Main.kt) - start file that collects env variables and does check after which bot shouldn't crash.
- [Prizebot](../bot/src/main/kotlin/me/y9san9/prizebot/Prizebot.kt) - bot implementation.
- [Resources](../bot/src/main/kotlin/me/y9san9/prizebot/resources) - directory contains all bot content, so you can find and edit there typos, provide localization (see [Locale](../bot/src/main/kotlin/me/y9san9/prizebot/resources/locales/Locale.kt)), etc.
- [Random](../random) - module responsible for secure random.

## I want details
Good news for you, every module has its own documentation in README. If you feel lack of descriptions, please create an issue, and I would be glad to explain any moments.
