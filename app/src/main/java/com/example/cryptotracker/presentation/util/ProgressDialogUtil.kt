package com.example.cryptotracker.presentation.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.cryptotracker.R

object ProgressDialogUtil {

    fun setProgressDialog(context: Context): AlertDialog.Builder {
        val builder = AlertDialog.Builder(context, R.style.DialogCustomTheme)
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        builder.setCancelable(false)
        builder.setView(mDialogView)
        return builder
    }
}