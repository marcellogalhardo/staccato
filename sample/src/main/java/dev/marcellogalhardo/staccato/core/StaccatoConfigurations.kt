package dev.marcellogalhardo.staccato.core

data class StaccatoConfigurations(
    val isChangingConfigurations: () -> Boolean = { false },
)
