package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.saveable.rememberSaveable
import dev.marcellogalhardo.staccato.core.internal.StaccatoStore
import java.util.*

@Composable
fun StaccatoScope(content: @Composable () -> Unit) {
    val composeId = rememberSaveable { UUID.randomUUID().toString() }
    val scopedStore = LocalStaccatoRootStore.current.getOrCreate(key1 = composeId) {
        StaccatoStore()
    }

    OnClearedHandler(scopedStore) { scopedStore.clear() }

    CompositionLocalProvider(
        LocalStaccatoScopedStore provides scopedStore,
    ) {
        content()
    }
}
