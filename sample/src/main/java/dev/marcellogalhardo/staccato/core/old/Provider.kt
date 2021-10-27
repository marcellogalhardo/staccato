package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import java.util.*
import kotlin.reflect.KClass

private val SingletonProviderCollection = ProviderStore()

private val ScopedProviderCollection = ProviderStore()

@Composable
fun ProviderRoot(
    isChangingConfigurations: () -> Boolean = { false },
    content: @Composable (ProviderScope.() -> Unit),
) {
    val singletonProviderCollection = SingletonProviderCollection.getOrCreate { ProviderStore() }
    DisposableEffect(singletonProviderCollection) {
        ProviderStore.Configurations.isChangingConfigurations = isChangingConfigurations
        ProviderStore.Configurations.isRootAttached = true
        onDispose {
            ProviderStore.Configurations.isRootAttached = false
            if (!ProviderStore.Configurations.isChangingConfigurations()) {
                singletonProviderCollection.dispose()
            }
        }
    }

    val scope = remember { ProviderScope(singletonProviderCollection) }
    content(scope)
}

@Composable
fun ProviderScope(autoDispose: Boolean = true, content: @Composable ProviderScope.() -> Unit) {
    require(ProviderStore.Configurations.isRootAttached)

    val singletonProviderCollection = SingletonProviderCollection.getOrCreate { ProviderStore() }

    val scopedProvider = ScopedProviderCollection.getOrCreate { ProviderStore() }
    DisposableEffect(scopedProvider) {
        onDispose {
            if (!ProviderStore.Configurations.isChangingConfigurations() && autoDispose) {
                scopedProvider.dispose()
            }
        }
    }

    val scope = remember { ProviderScope(singletonProviderCollection, scopedProvider) }
    content(scope)
}

class ProviderScope internal constructor(
    @PublishedApi
    internal val singletonProviders: ProviderStore,

    @PublishedApi
    internal val scopedProviders: ProviderStore = singletonProviders,
) {

    @Composable
    inline fun <reified T : Any> scoped(
        key1: String? = null,
        key2: String? = null,
        key3: String? = null,
        noinline instantiate: () -> T,
    ): T = scopedProviders.getOrCreate(T::class, key1, key2, key3, instantiate)

    @Composable
    inline fun <reified T : Any> singleton(
        key1: String? = null,
        key2: String? = null,
        key3: String? = null,
        noinline instantiate: () -> T,
    ): T = singletonProviders.getOrCreate(T::class, key1, key2, key3, instantiate)
}

class ProviderStore internal constructor(
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
        val name = rememberSaveable { UUID.randomUUID().toString() }
        val id = remember { Id(name, type, key1, key2, key3) }
        return instances.getOrPut(id) { instantiate() } as T
    }

    fun dispose() {
        instances.clear()
    }

    data class Id(
        val name: String,
        val type: KClass<*>,
        val key1: String?,
        val key2: String?,
        val key3: String?,
    )

    object Configurations {
        var isChangingConfigurations: () -> Boolean = { false }
        internal var isRootAttached: Boolean = false
    }
}
