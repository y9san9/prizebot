# Utils
> This module contains simple extensions for standard library (like `Any?.unit`) that may be used from any module

## Structure
The structure of this module is very simple. All extensions located in `me.y9san9.extensons.[snake_case_receiver]` package and files named `[extensionFunctionName].kt`

There are also some special structures like [OffsetDateTimeSerializer](https://github.com/y9san9/prizebot/blob/dev/utils/src/main/kotlin/me/y9san9/extensions/offset_date_time/OffsetDateTimeSerializer.kt) and [FlowParallelLauncher](https://github.com/y9san9/prizebot/blob/dev/utils/src/main/kotlin/me/y9san9/extensions/flow/FlowParallelLauncher.kt) that localed in package related to class it extends
