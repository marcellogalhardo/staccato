package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import dev.marcellogalhardo.staccato.core.internal.LocalStaccatoRootStore
import dev.marcellogalhardo.staccato.core.internal.LocalStaccatoScopedStore

@Composable
inline fun <reified T : Any> retainInScope(
    key1: String? = null,
    key2: String? = null,
    key3: String? = null,
    noinline instantiate: () -> T,
): T {
    return LocalStaccatoScopedStore.current.getOrCreate(key1, key2, key3, instantiate)
}

@Composable
inline fun <reified T : Any> retainInHost(
    key1: String? = null,
    key2: String? = null,
    key3: String? = null,
    noinline instantiate: () -> T,
): T {
    return LocalStaccatoRootStore.current.getOrCreate(key1, key2, key3, instantiate)
}
