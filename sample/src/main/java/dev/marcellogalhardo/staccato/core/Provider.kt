package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable

class Provider<R>(
    private val block: @Composable () -> R
) {

    @Composable
    fun get(): R = block()
}

class ParametrizedProvider<R, T1>(
    private val block: @Composable (T1) -> R
) {

    @Composable
    fun get(arg: T1): R = block(arg)
}
