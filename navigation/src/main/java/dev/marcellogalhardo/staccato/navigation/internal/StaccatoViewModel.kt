package dev.marcellogalhardo.staccato.navigation.internal

import androidx.lifecycle.ViewModel

internal class StaccatoViewModel : ViewModel() {

    var onClearedListeners = mutableSetOf<() -> Unit>()

    override fun onCleared() {
        onClearedListeners.forEach { it.invoke() }
    }
}
