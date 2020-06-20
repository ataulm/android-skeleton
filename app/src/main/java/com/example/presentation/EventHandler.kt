package com.example.presentation

/**
 * Wraps lambdas so we can override eq/hashcode, treating them all as equal (general case + tests),
 * except when the [comparisonKey] is distinct.
 *
 * This is handy when only the event handler changes, so it's important the change is recognised:
 *
 * ```
 * data class ButtonUiModel(val text: CharSequence, val clickHandler: EventHandler<Unit>)
 * ...
 * val eventHandler = if (userIsSignedIn) {
 *     EventHandler(comparisonKey = "home") { /* go to home */ }
 * } else {
 *     EventHandler(comparisonKey = "sign-in") { /* go to sign-in screen */ }
 * }
 * ButtonUiModel(text = "Continue", clickHandler = eventHandler)
 * ```
 */
class EventHandler<T>(val comparisonKey: String = "", val handler: (T) -> Unit) {

    override fun equals(other: Any?): Boolean {
        if (other != null && other is EventHandler<*>) return comparisonKey == other.comparisonKey
        return false
    }

    override fun hashCode(): Int = comparisonKey.hashCode()
}
