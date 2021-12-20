package com.example.justeattakeawayapplication.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.justeattakeawayapplication.R
import com.example.justeattakeawayapplication.utils.CustomAlertDialog

abstract class BaseFragment<VB : ViewBinding, A : Action, S : State, VM : BaseViewModel<A, S>> :
    Fragment(), BaseView {

    protected var binding: VB? = null
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        initViews()
    }

    abstract fun getViewBinding(): VB

    private fun setupObservers() {
        viewModel.liveData.observe(this) {
            handleState(it)
        }
    }


    abstract fun handleState(state: S)

    abstract fun initViews()

    override fun showError(errorMsg: String) {
        CustomAlertDialog.showDialogError(
            this.requireContext(), getString(R.string.error_title), errorMsg, getString(
                R.string.ok
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}