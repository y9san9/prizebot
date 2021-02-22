package me.y9san9.random


object Random {
    suspend fun <T> shuffled(list: List<T>): List<T> {
        // If there is only one element, return list copy
        if(list.size <= 1)
            return list.toList()

        val sequence = RandomOrgAPI.getSequence(min = 0, max = list.size - 1)

        return List(list.size) { list[sequence[it]] }
    }
}
