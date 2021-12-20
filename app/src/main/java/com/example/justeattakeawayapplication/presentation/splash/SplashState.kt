package com.example.justeattakeawayapplication.presentation.splash

import com.example.justeattakeawayapplication.presentation.base.State

sealed class SplashState : State {
    object SplashEnded : SplashState()
}