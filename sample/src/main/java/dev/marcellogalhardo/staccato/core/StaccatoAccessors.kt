package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable

@Composable
inline fun <reified T : Any> scoped(
    key1: String? = null,
    key2: String? = null,
    key3: String? = null,
    noinline instantiate: () -> T,
): T = LocalStaccatoScopedStore.current.getOrCreate(T::class, key1, key2, key3, instantiate)

@Composable
inline fun <reified T : Any> singleton(
    key1: String? = null,
    key2: String? = null,
    key3: String? = null,
    noinline instantiate: () -> T,
): T = LocalStaccatoRootStore.current.getOrCreate(T::class, key1, key2, key3, instantiate)
