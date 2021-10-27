package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun StaccatoScope(content: @Composable () -> Unit) {
    val scopedStore = LocalStaccatoRootStore.current.getOrCreate { StaccatoStore() }

    OnClearedHandler(scopedStore) { scopedStore.clear() }

    CompositionLocalProvider(
        LocalStaccatoScopedStore provides scopedStore,
    ) {
        content()
    }
}
