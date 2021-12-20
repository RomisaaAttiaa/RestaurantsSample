package com.example.justeattakeawayapplication.utils

import android.content.Context
import com.example.justeattakeawayapplication.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object CustomAlertDialog {
    fun showDialog(
        context: Context, title: String, message: String?, positiveButtonTitle: String,
        positiveButtonAction: (() -> Unit)? = null
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(positiveButtonTitle) { dialog, _ ->
                positiveButtonAction?.apply { invoke() }
                dialog.dismiss()
            }
            .setIcon(R.drawable.ic_tick_inside_circle)
            .show()
    }

    fun showDialogError(
        context: Context, title: String, message: String?, positiveButtonTitle: String,
        positiveButtonAction: (() -> Unit)? = null
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton(positiveButtonTitle) { dialog, _ ->
                positiveButtonAction?.apply { invoke() }
                dialog.dismiss()
            }
            .setIcon(R.drawable.ic_error_black)
            .show()
    }

    fun showDialogWithTwoActions(
        context: Context,
        title: String,
        message: String,
        positiveButtonTitle: String,
        negativeButtonTitle: String,
        positiveButtonAction: (() -> Unit)? = null,
        negativeButtonAction: (() -> Unit)? = null
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonTitle) { dialog, _ ->
                positiveButtonAction?.apply { invoke() }
                dialog.dismiss()
            }.setNegativeButton(negativeButtonTitle) { dialog, _ ->
                negativeButtonAction?.apply { invoke() }
                dialog.dismiss()
            }.setCancelable(false)
            .setIcon(R.drawable.ic_error_black)
            .show()
    }
}