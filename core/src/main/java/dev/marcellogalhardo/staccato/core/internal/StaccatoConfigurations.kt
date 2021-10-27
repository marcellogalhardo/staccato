package dev.marcellogalhardo.staccato.core.internal

internal data class StaccatoConfigurations(
    val isChangingConfigurations: () -> Boolean = { false },
)
