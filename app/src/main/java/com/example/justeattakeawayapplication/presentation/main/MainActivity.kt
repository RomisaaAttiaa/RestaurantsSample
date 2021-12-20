package com.example.justeattakeawayapplication.presentation.main

import androidx.activity.viewModels
import com.example.justeattakeawayapplication.databinding.ActivityMainBinding
import com.example.justeattakeawayapplication.presentation.base.Action
import com.example.justeattakeawayapplication.presentation.base.BaseActivity
import com.example.justeattakeawayapplication.presentation.base.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, Action, State, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun getBinding() = ActivityMainBinding.inflate(layoutInflater)


    override fun handleState(state: State) {
        // Implementation Not Needed
    }

    override fun initViews() {
        // Implementation Not Needed
    }

    override fun showLoading(isLoading: Boolean) {
        // Implementation Not Needed
    }

}