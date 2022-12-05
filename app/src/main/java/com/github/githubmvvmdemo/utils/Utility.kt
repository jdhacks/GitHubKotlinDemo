package com.github.githubmvvmdemo.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.github.githubmvvmdemo.R
import com.github.githubmvvmdemo.interfaces.AlertDialogCallback
import java.text.SimpleDateFormat
import java.util.*

class Utility {

    companion object {


        fun alertDialog(context: Activity?, message: String?, cancelable: Boolean, alertDialogCallback: AlertDialogCallback) {
            if (context != null) {
                val dialog = Dialog(context)
                dialog.window!!
                    .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(cancelable)
                dialog.setCanceledOnTouchOutside(cancelable)
                /*dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) */
                /*dialog.getWindow().setGravity(Gravity.TOP) */
                dialog.setContentView(R.layout.dialog_warning)
                val txtTitle: AppCompatTextView
                val retry_button: AppCompatTextView
                retry_button = dialog.findViewById(R.id.btnRetryWarning)
                val yes_button: AppCompatTextView
                yes_button = dialog.findViewById(R.id.btnokWarning)
                txtTitle = dialog.findViewById(R.id.txt_warning)

                    txtTitle.text = message
                    // txtTitle.setText(context.getString(R.string.general_error))
                    try {
                        if (!dialog.isShowing) {
                            dialog.window!!
                                .setLayout(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                )
                            dialog.show()
                        }
                    } catch (e: Exception) {
                        Log.e("TAG", "PARSE ERROR" + e.message)
                    }
                yes_button.setOnClickListener {
                    dialog.dismiss()
                    alertDialogCallback.onOkClick()
                }
                retry_button.setOnClickListener {
                    dialog.dismiss()
                    alertDialogCallback.onRetryClick()
                }
            }
        }

        fun nointernetAlertDialog(
            context: Context?,
            message: String?,
            cancelable: Boolean,
            alertDialogCallback: AlertDialogCallback
        ) {
            if (context != null) {
                val dialog = Dialog(context)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(cancelable)
                dialog.setCanceledOnTouchOutside(cancelable)
                /*dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) */
                /*dialog.getWindow().setGravity(Gravity.TOP) */dialog.setContentView(R.layout.dialo_no_internet)
                val txt_warning: AppCompatTextView
                val txt_header: AppCompatTextView
                val btnokWarning: AppCompatTextView
                btnokWarning = dialog.findViewById(R.id.btnokWarning)
                val btnretryWarning: AppCompatTextView
                btnretryWarning = dialog.findViewById(R.id.btnRetryWarning)
                txt_header = dialog.findViewById(R.id.txt_header)
                txt_warning = dialog.findViewById(R.id.txt_warning)
                txt_warning.text = message
                txt_header.text = context.getString(R.string.no_internet_header)

                btnokWarning.setOnClickListener {
                    dialog.dismiss()
                    alertDialogCallback.onOkClick()
                }
                btnretryWarning.setOnClickListener {
                    dialog.dismiss()
                    alertDialogCallback.onRetryClick()
                }
                try {
                    if (!dialog.isShowing) {
                        dialog.window?.setLayout(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        dialog.show()
                    }
                } catch (e: Exception) {
                    Log.e("TAG", "PARSE ERROR" + e.message)
                }
            }
        }

        fun displayMessage(context: Context?, message: String?) {
            val t: Toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            t.setGravity(Gravity.CENTER, 0, 0)
            t.show()
        }


        fun GetDateBeforeTwomonth(): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -2)
            val date = calendar.time
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val dateOutput = format.format(date)
            return dateOutput
        }


        fun isInternetAvailable(context: Context): Boolean {
            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                result = when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }

            return result
        }

        fun getCircularProgressDrawable(context: Context): CircularProgressDrawable {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.setStyle(CircularProgressDrawable.DEFAULT)
            circularProgressDrawable.setColorSchemeColors(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            circularProgressDrawable.start()
            return circularProgressDrawable
        }
    }
}