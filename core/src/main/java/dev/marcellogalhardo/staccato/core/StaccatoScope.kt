package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import dev.marcellogalhardo.staccato.core.internal.LocalStaccatoRootStore
import dev.marcellogalhardo.staccato.core.internal.LocalStaccatoScopedStore
import dev.marcellogalhardo.staccato.core.internal.OnClearedHandler
import dev.marcellogalhardo.staccato.core.internal.StaccatoStore
import java.util.*

@Composable
@InternalStaccatoApi
fun StaccatoScope(
    onClearedHandler: @Composable (Any, () -> Unit) -> Unit,
    content: @Composable () -> Unit,
) {
    val composeId = rememberSaveable { UUID.randomUUID().toString() }
    val scopedStore = LocalStaccatoRootStore.current.getOrCreate(key1 = composeId) {
        StaccatoStore()
    }

    onClearedHandler(scopedStore, scopedStore::clear)

    CompositionLocalProvider(
        LocalStaccatoScopedStore provides scopedStore,
    ) {
        content()
    }
}

@OptIn(InternalStaccatoApi::class)
@Composable
fun StaccatoScope(content: @Composable () -> Unit) {
    StaccatoScope(
        onClearedHandler = { key, clear ->
            OnClearedHandler(key) { clear() }
        },
        content = content,
    )
}
