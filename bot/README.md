# Bot
> This is main module that provides [Prizebot](src/main/kotlin/me/y9san9/prizebot/Prizebot.kt) (pure bot implementation, it does not use anything from env) separated from `src`

## Structure
This is main bot module that contains most of all project code, so here is the most complex structure:

- [actors](src/main/kotlin/me/y9san9/prizebot/actors) - don't relate to what does actor is at all (see [#51](https://github.com/y9san9/prizebot/issues/51#issuecomment-843402568)), any naming suggestions are welcome. Now it is something like entities that do one action intuitive guessed by its name
- [database](src/main/kotlin/me/y9san9/prizebot/database) - database stuff. Almost all there are Storages (wrappers that encapsulating tables) except [migrations](src/main/kotlin/me/y9san9/prizebot/database/migrations) and [States Storage](src/main/kotlin/me/y9san9/prizebot/database/states_storage) (implementing FSMStorage)
- [di](src/main/kotlin/me/y9san9/prizebot/di) - only model with all bot dependencies there
- [extensions](src/main/kotlin/me/y9san9/prizebot/extensions) - extensions for classes related only to this module
- [handlers](src/main/kotlin/me/y9san9/prizebot/handlers) - all bot updates handled there, they are split with package by type of update
- [resources](src/main/kotlin/me/y9san9/prizebot/resources) - any kind of content the bot replies with is generated there
