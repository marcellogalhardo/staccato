package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
inline fun <reified T : Any> scoped(
    key1: String? = null,
    key2: String? = null,
    key3: String? = null,
    saver: Saver<T, T>? = null,
    noinline instantiate: () -> T,
): T {
    val instance = LocalStaccatoScopedStore.current
        .getOrCreate(T::class, key1, key2, key3, instantiate)
    if (saver != null) rememberSaveable(saver = saver) { instance }
    return instance
}

@Composable
inline fun <reified T : Any> singleton(
    key1: String? = null,
    key2: String? = null,
    key3: String? = null,
    saver: Saver<T, T>? = null,
    noinline instantiate: () -> T,
): T {
    val instance = LocalStaccatoRootStore.current
        .getOrCreate(T::class, key1, key2, key3, instantiate)
    if (saver != null) rememberSaveable(saver = saver) { instance }
    return instance
}
