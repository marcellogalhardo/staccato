package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.util.*
import kotlin.reflect.KClass

@PublishedApi
internal class StaccatoStore constructor(
    private val instances: MutableMap<Id, Any> = mutableMapOf(),
) {

    @Composable
    inline fun <reified T : Any> getOrCreate(
        key1: String? = null,
        key2: String? = null,
        key3: String? = null,
        noinline instantiate: () -> T,
    ): T = getOrCreate(T::class, key1, key2, key3, instantiate)

    @Composable
    fun <T : Any> getOrCreate(
        type: KClass<T>,
        key1: String? = null,
        key2: String? = null,
        key3: String? = null,
        instantiate: () -> T,
    ): T {
        val id = remember { Id(type, key1, key2, key3) }
        return instances.getOrPut(id) { instantiate() } as T
    }

    fun clear() {
        instances.clear()
    }

    data class Id(
        val type: KClass<*>,
        val key1: String?,
        val key2: String?,
        val key3: String?,
    )
}
