package com.example.justeattakeawayapplication.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.justeattakeawayapplication.R
import com.example.justeattakeawayapplication.utils.CustomAlertDialog

abstract class BaseActivity<VB : ViewBinding, A : Action, S : State, VM : BaseViewModel<A, S>> :
    AppCompatActivity(), BaseView {

    private var binding: VB? = null
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getBinding()
        setContentView(binding?.root)
        setupObservers()
        initViews()
    }


    abstract fun getBinding(): VB

    private fun setupObservers() {
        viewModel.liveData.observe(this) {
            handleState(it)
        }
    }

    abstract fun handleState(state: S)

    abstract fun initViews()

    override fun showError(errorMsg: String) {
        CustomAlertDialog.showDialogError(
            this, getString(R.string.error_title), errorMsg, getString(
                R.string.ok
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}