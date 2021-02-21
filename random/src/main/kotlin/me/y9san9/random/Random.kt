package me.y9san9.random


object Random {
    suspend fun <T> shuffled(list: List<T>): List<T> {
        val sequence = RandomOrgAPI.getSequence(min = 0, max = list.size - 1)
        return List(list.size) { list[sequence[it]] }
    }
}
