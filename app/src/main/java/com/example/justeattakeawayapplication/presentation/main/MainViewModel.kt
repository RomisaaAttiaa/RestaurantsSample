package com.example.justeattakeawayapplication.presentation.main

import com.example.justeattakeawayapplication.di.DispatcherBackground
import com.example.justeattakeawayapplication.di.DispatcherForeground
import com.example.justeattakeawayapplication.presentation.base.Action
import com.example.justeattakeawayapplication.presentation.base.BaseViewModel
import com.example.justeattakeawayapplication.presentation.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    @DispatcherForeground val dispatcherForeground: CoroutineContext,
    @DispatcherBackground val dispatcherBackground: CoroutineContext
) : BaseViewModel<Action, State>(dispatcherForeground, dispatcherBackground) {

    override fun handleAction(action: Action) {
        // Implementation Not Needed
    }

}