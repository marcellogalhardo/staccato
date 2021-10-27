package dev.marcellogalhardo.staccato.core.internal

@PublishedApi
internal class StaccatoEngine {
    val singletonStore = StaccatoStore()

    companion object {
        private var _INSTANCE: StaccatoEngine? = null

        val INSTANCE: StaccatoEngine
            get() = _INSTANCE ?: StaccatoEngine().also { engine ->
                _INSTANCE = engine
            }
    }
}
