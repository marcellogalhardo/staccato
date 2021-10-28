package dev.marcellogalhardo.staccato.core.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import dev.marcellogalhardo.staccato.core.internal.LocalStaccatoConfigurations

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
