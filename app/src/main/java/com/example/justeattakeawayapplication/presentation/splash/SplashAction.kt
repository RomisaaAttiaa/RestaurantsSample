package com.example.justeattakeawayapplication.presentation.splash

import com.example.justeattakeawayapplication.presentation.base.Action

sealed class SplashAction : Action {
    object StartSplash : SplashAction()
}