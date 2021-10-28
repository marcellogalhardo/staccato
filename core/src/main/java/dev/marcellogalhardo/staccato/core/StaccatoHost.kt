package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import dev.marcellogalhardo.staccato.core.internal.LocalStaccatoConfigurations
import dev.marcellogalhardo.staccato.core.internal.LocalStaccatoRootStore
import dev.marcellogalhardo.staccato.core.internal.OnClearedHandler
import dev.marcellogalhardo.staccato.core.internal.StaccatoConfigurations
import dev.marcellogalhardo.staccato.core.internal.StaccatoEngine

@Composable
fun StaccatoHost(
    isChangingConfigurations: () -> Boolean = { false },
    content: @Composable () -> Unit,
) {
    val configurations = remember {
        StaccatoConfigurations(isChangingConfigurations)
    }
    val engine = StaccatoEngine.INSTANCE
    CompositionLocalProvider(
        LocalStaccatoConfigurations provides configurations,
        LocalStaccatoRootStore provides engine.singletonStore,
    ) {
        OnClearedHandler(engine) {
            engine.singletonStore.clear()
        }

        StaccatoScope {
            content()
        }
    }
}
