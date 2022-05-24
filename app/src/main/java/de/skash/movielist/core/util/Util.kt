package de.skash.movielist.core.util

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.skash.movielist.R
import java.net.UnknownHostException

fun Context.showErrorDialog(errorType: ErrorType, onDismiss: (Unit) -> Unit = {}) {
    MaterialAlertDialogBuilder(this)
        .setTitle(resources.getString(R.string.error_alert_title))
        .setMessage(resources.getString(errorType.error))
        .setOnDismissListener {
            onDismiss(Unit)
        }
        .setPositiveButton(resources.getString(R.string.error_alert_ok_button_text), null)
        .show()
}

fun Throwable.getErrorType(): ErrorType? {
    return when (this) {
        is UnknownHostException -> ErrorType.NoInternet
        else -> null
    }
}