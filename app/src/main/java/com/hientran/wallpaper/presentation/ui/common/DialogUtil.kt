package com.hientran.wallpaper.presentation.ui.common

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hientran.wallpaper.R

interface DialogUtil {
    fun showProgressDialog(view: View?)
    fun hideProgressDialog()
    fun showDialog(
        view: View?,
        message: String?,
        positiveMessageRes: Int,
        negativeMessageRes: Int,
        positiveListener: DialogInterface.OnClickListener? = null,
        negativeListener: DialogInterface.OnClickListener? = null
    )
}

class DialogUtilImpl : DialogUtil {
    private var progressDialog: AlertDialog? = null
    private var currentDialog: AlertDialog? = null

    override fun showProgressDialog(view: View?) {
        view?.let {
            progressDialog?.dismiss()
            progressDialog = MaterialAlertDialogBuilder(it.context)
                .setView(R.layout.dialog_loading_default)
                .setCancelable(false)
                .create()
                .apply {
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    show()
                }
        }
    }

    override fun hideProgressDialog() {
        progressDialog?.dismiss()
    }

    override fun showDialog(
        view: View?,
        message: String?,
        positiveMessageRes: Int,
        negativeMessageRes: Int,
        positiveListener: DialogInterface.OnClickListener?,
        negativeListener: DialogInterface.OnClickListener?
    ) {
        currentDialog?.dismiss()
        view?.let {
            currentDialog = MaterialAlertDialogBuilder(it.context, R.style.AlertDialogTheme)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(positiveMessageRes, positiveListener)
                .setNegativeButton(negativeMessageRes, negativeListener)
                .setOnDismissListener { currentDialog = null }
                .show()
        }
    }
}
