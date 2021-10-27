package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
internal fun OnClearedHandler(
    key1: Any?,
    effect: () -> Unit,
) {
    val configurations = LocalStaccatoConfigurations.current
    DisposableEffect(key1) {
        onDispose {
            if (!configurations.isChangingConfigurations()) {
                effect()
            }
        }
    }
}
