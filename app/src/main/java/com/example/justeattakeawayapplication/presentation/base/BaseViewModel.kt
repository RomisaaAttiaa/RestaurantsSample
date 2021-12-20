package com.example.justeattakeawayapplication.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<A : Action, S : State>(
    private val foregroundContext: CoroutineContext,
    private val backgroundContext: CoroutineContext
) : ViewModel() {

    val liveData: MutableLiveData<S> by lazy {
        MutableLiveData()
    }

    abstract fun handleAction(action: A)

    fun updateState(state: S) {
        liveData.postValue(state)
    }

    protected fun executeOnBackground(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(backgroundContext, block = block)
    }

    protected fun executeOnForeground(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(foregroundContext, block = block)
    }

}