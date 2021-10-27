package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.staticCompositionLocalOf

@PublishedApi
internal val LocalStaccatoRootStore = staticCompositionLocalOf<StaccatoStore> {
    error("CompositionLocal LocalStaccatoRootStore not present")
}

@PublishedApi
internal val LocalStaccatoScopedStore = staticCompositionLocalOf<StaccatoStore> {
    error("CompositionLocal LocalStaccatoScopedStore not present")
}

@PublishedApi
internal val LocalStaccatoConfigurations = staticCompositionLocalOf<StaccatoConfigurations> {
    error("CompositionLocal LocalStaccatoConfigurations not present")
}
