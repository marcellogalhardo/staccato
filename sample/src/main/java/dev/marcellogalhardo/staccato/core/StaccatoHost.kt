package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@PublishedApi
internal lateinit var STACCATO_ENGINE: StaccatoEngine

@PublishedApi
internal class StaccatoEngine {
    val store = StaccatoStore()
}

@Composable
fun StaccatoHost(
    configurations: StaccatoConfigurations = StaccatoConfigurations(),
    content: @Composable () -> Unit,
) {
    if (!::STACCATO_ENGINE.isInitialized) {
        STACCATO_ENGINE = StaccatoEngine()
    }

    CompositionLocalProvider(
        LocalStaccatoConfigurations provides configurations,
        LocalStaccatoRootStore provides STACCATO_ENGINE.store,
    ) {
        OnClearedHandler(STACCATO_ENGINE) {
            STACCATO_ENGINE.store.clear()
        }

        StaccatoScope {
            content()
        }
    }
}
