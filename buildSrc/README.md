# Build Sources

> This module included into gradle build files, so it has utilities such dependencies constants, dependencies versions, project modules and custom plugins. <br>
> Btw I like this solution for dependencies instead introduced in gradle 7.0 version catalogs etc.

## Structure

All kinds of shortcuts is unpackaged (including due to imports doesn't work in `plugins` block):

[AppInfo](https://github.com/y9san9/prizebot/blob/dev/buildSrc/src/main/kotlin/AppInfo.kt) - basic application info <br>
[Dependencies](https://github.com/y9san9/prizebot/blob/dev/buildSrc/src/main/kotlin/Dependencies.kt) - dependencies string constants<br>
[Version](https://github.com/y9san9/prizebot/blob/dev/buildSrc/src/main/kotlin/Version.kt) - all kinds of versions extracted here <br>
[Plugins](https://github.com/y9san9/prizebot/blob/dev/buildSrc/src/main/kotlin/Plugins.kt) - gradle plugins string constants <br>
[Modules](https://github.com/y9san9/prizebot/blob/dev/buildSrc/src/main/kotlin/Modules.kt) - project modules shortcuts

Otherwise, plugins are packaged:

[Deploy](https://github.com/y9san9/prizebot/blob/dev/buildSrc/src/main/kotlin/me/y9san9/deploy/Deploy.kt) - plugin for convenient deployment bot to server via ssh with `systemctl restart`
