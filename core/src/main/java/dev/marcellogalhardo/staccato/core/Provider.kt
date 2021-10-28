package dev.marcellogalhardo.staccato.core

import androidx.compose.runtime.Composable

fun <R> Provider(block: @Composable () -> R): Provider<R> {
    return object : Provider<R> {

        @Composable
        override fun get(): R = block()
    }
}

fun <R, T1> ParametrizedProvider(block: @Composable (parameter: T1) -> R): ParametrizedProvider<R, T1> {
    return object : ParametrizedProvider<R, T1> {

        @Composable
        override fun get(parameter: T1): R = block(parameter)
    }
}

interface Provider<R> {

    @Composable
    fun get(): R
}


interface ParametrizedProvider<R, T1> {

    @Composable
    fun get(parameter: T1): R
}
