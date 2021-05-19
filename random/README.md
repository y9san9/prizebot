# Random
> Module built over [jsonRPC](jsonRPC) module required for generating secure [_true_](https://random.org) random numbers

## Structure
Main entities in the model are [RandomOrgAPI](src/main/kotlin/me/y9san9/random/RandomOrgAPI.kt) and [RandomOrgClient](src/main/kotlin/me/y9san9/random/RandomOrgClient.kt). <br>

The first one is internal and used only as random.org RPC client implementation, and the second one is a high-level API which used by bot.

There are also [rpc](src/main/kotlin/me/y9san9/random/rpc) package with rpc requests generators

### Cons
You can see, the api implementation is a little bit 'low level' as it uses JsonObjects everywhere instead of serializers, it is because I'm lazy to implement custom serializer like that:
```kotlin
@JRPCMethod (
    // Optional, by default should be extracted as [ClassName.camelCaseToSnakeCase]
    name = "generateIntegers"
)
class GenerateIntegersMethod (
    // Marker of rpc id property
    @JRPCId
    val rpcId: Int,
    val apiKey: String,
    val n: Int,
    val min: Int,
    val max: Int
)
```

So PRs with implementation are welcome
