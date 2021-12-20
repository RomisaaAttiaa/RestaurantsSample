package com.example.justeattakeawayapplication.presentation.splash

import com.example.justeattakeawayapplication.di.DispatcherBackground
import com.example.justeattakeawayapplication.di.DispatcherForeground
import com.example.justeattakeawayapplication.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SplashViewModel @Inject constructor(
    @DispatcherForeground dispatcherForeground: CoroutineContext,
    @DispatcherBackground dispatcherBackground: CoroutineContext
) : BaseViewModel<SplashAction, SplashState>(dispatcherForeground, dispatcherBackground) {

    override fun handleAction(action: SplashAction) {
        when (action) {
            is SplashAction.StartSplash -> {
                executeOnForeground {
                    delay(1000)
                    updateState(SplashState.SplashEnded)
                }
            }
        }
    }

}