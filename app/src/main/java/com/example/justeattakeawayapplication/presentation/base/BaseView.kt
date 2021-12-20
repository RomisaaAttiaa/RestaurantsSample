package com.example.justeattakeawayapplication.presentation.base

interface BaseView {
    fun showLoading(isLoading: Boolean)
    fun showError(errorMsg: String)
}