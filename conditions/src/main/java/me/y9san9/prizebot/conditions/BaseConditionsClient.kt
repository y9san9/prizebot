package me.y9san9.prizebot.conditions

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.types.queryField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import me.y9san9.aqueue.AQueue
import me.y9san9.aqueue.io
import me.y9san9.prizebot.conditions.tgbotapi.TgBotApiConditionsRepository

@Suppress("FunctionName")
fun TelegramConditionsClient(
    scope: CoroutineScope,
    bot: TelegramBot,
    queue: AQueue = AQueue.io(),
    timeoutMillis: Long = 2_000,
): BaseConditionsClient = BaseConditionsClient(
    scope = scope,
    conditionsRepository = TgBotApiConditionsRepository(bot),
    queue = queue,
    timeoutMillis = timeoutMillis
)

class BaseConditionsClient(
    private val scope: CoroutineScope,
    private val conditionsRepository: ConditionsRepository,
    queue: AQueue = AQueue.io(),
    private val timeoutMillis: Long = 2_000,
) {
    private val client = RawConditionsClient(queue)

    suspend fun check(
        userId: Long,
        conditions: List<Condition>,
        discriminator: Any = userId,
        firstHandler: suspend (Result) -> Unit = {}
    ): Result = client.checkWithTimeout(
        scope = scope,
        timeMillis = timeoutMillis,
        discriminator = discriminator,
        conditions = conditions.map { condition -> RawCondition(condition, userId, conditionsRepository) },
        firstHandler = { firstHandler(it.toResult()) }
    ).toResult()

    private class RawCondition(
        val underlying: Condition,
        private val userId: Long,
        private val repository: ConditionsRepository
    ) : RawConditionsClient.Condition {
        override suspend fun check(): Boolean = repository.check(userId, underlying)
    }

    private fun TimeoutResult.toResult(): Result = when (this) {
        is TimeoutResult.Success -> underlying.toResult()
        is TimeoutResult.Timeout -> Result.HighLoad(scope.async { deferred.await().toResult() })
    }

    private fun RawConditionsClient.Result.toResult() = when (this) {
        is RawConditionsClient.Result.Failed -> Result.ConditionUnmet(
            condition = (condition as RawCondition).underlying
        )
        is RawConditionsClient.Result.Success -> Result.Success
    }

    sealed interface Condition {
        object CanMention : Condition
        class PermanentUsername(val channelId: Long, val channelUsername: String) : Condition
        class MemberOfChannel(val channelId: Long) : Condition
    }

    interface ConditionsRepository {
        suspend fun check(userId: Long, condition: Condition): Boolean
    }

    sealed interface Result {
        sealed interface Calculated : Result {
            val condition: Condition? get() = null
        }

        object Success : Calculated
        class ConditionUnmet(override val condition: Condition) : Calculated
        // bot currently is busy and added you to queue, please check in a few minutes
        class HighLoad(val deferred: Deferred<Calculated>) : Result

        suspend fun await(): Calculated = when (this) {
            is Calculated -> this
            is HighLoad -> deferred.await()
        }
    }
}
