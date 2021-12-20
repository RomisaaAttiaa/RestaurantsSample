package com.example.justeattakeawayapplication.presentation.base

class ConsumableValue<T>(val data: T) {
    private var consumed = false

    fun consume(block: ConsumableValue<T>.(T) -> Unit) {
        if (!consumed) {
            consumed = true
            block(data)
        }
    }
}