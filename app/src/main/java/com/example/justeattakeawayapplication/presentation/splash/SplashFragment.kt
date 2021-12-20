package com.example.justeattakeawayapplication.presentation.splash

import android.animation.ObjectAnimator
import androidx.core.animation.doOnEnd
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.justeattakeawayapplication.R
import com.example.justeattakeawayapplication.databinding.SplashFragmentBinding
import com.example.justeattakeawayapplication.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<SplashFragmentBinding, SplashAction, SplashState, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override fun getViewBinding() = SplashFragmentBinding.inflate(layoutInflater)


    override fun handleState(state: SplashState) {
        when (state) {
            SplashState.SplashEnded -> {
                animateLogo()
            }
        }
    }

    private fun navigateToRestaurantFragment() {
        findNavController().navigate(
            SplashFragmentDirections.actionSplashFragmentToRestaurantsFragment(),
            NavOptions.Builder()
                .setPopUpTo(
                    R.id.splashFragment,
                    true
                ).build()
        )
    }

    private fun animateLogo() {
        val transitionDistance = binding?.let {
            it.logoFl.y + it.logoFl.height - it.logoTv.y
        }
        ObjectAnimator.ofFloat(binding?.logoTv, "translationY", transitionDistance ?: 0f)
            .apply {
                duration = 1000
                start()
            }.doOnEnd {
                navigateToRestaurantFragment()
            }
    }

    override fun initViews() {
        viewModel.handleAction(SplashAction.StartSplash)
    }

    override fun showLoading(isLoading: Boolean) {
        // Implementation Not Needed
    }


}