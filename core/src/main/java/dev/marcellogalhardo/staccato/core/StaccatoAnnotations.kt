package dev.marcellogalhardo.staccato.core

@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is an internal API that may change frequently and without warning."
)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY
)
annotation class InternalStaccatoApi

@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This is an experimental API that may change frequently and without warning."
)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY
)
annotation class ExperimentalStaccatoApi
