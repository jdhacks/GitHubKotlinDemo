package com.github.githubmvvmdemo.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.github.githubmvvmdemo.R
import java.text.SimpleDateFormat
import java.util.*

public class Utility {

    companion object {
        fun isNetworkAvailable(context: Context?): Boolean {
            return if (context != null) {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                activeNetworkInfo != null && activeNetworkInfo.isConnected
            } else {
                false
            }
        }
        fun alertDialog(context: Activity?, message: String?, cancelable: Boolean) {
            if (context != null) {
                val dialog = Dialog(context)
                dialog.window!!
                    .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(cancelable)
                dialog.setCanceledOnTouchOutside(cancelable)
                /*dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);*/
                /*dialog.getWindow().setGravity(Gravity.TOP);*/dialog.setContentView(R.layout.dialog_warning)
                val txtTitle: AppCompatTextView
                val yes_button: AppCompatTextView
                yes_button = dialog.findViewById(R.id.btnokWarning)
                txtTitle = dialog.findViewById(R.id.txt_warning)
                if (com.github.githubmvvmdemo.utils.Utility.isNetworkAvailable(context)) {
                    txtTitle.text = message
                    // txtTitle.setText(context.getString(R.string.general_error));
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
                } else {

                    nointernetAlertDialog(
                        context,
                        context.getString(R.string.no_internet),
                        false
                    ) {
                    }
                }
                yes_button.setOnClickListener {
                    context.onBackPressed()
                    dialog.dismiss()
                }
            }
        }

        fun nointernetAlertDialog(
            context: Context?,
            message: String?,
            cancelable: Boolean,
            alertDialogCallback: () -> Unit
        ) {
            if (context != null) {
                val dialog = Dialog(context)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(cancelable)
                dialog.setCanceledOnTouchOutside(cancelable)
                /*dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);*/
                /*dialog.getWindow().setGravity(Gravity.TOP);*/dialog.setContentView(R.layout.dialo_no_internet)
                val txt_warning: AppCompatTextView
                val txt_header: AppCompatTextView
                val btnokWarning: AppCompatTextView
                btnokWarning = dialog.findViewById(R.id.btnokWarning)
                txt_header = dialog.findViewById(R.id.txt_header)
                txt_warning = dialog.findViewById(R.id.txt_warning)
                txt_warning.text = message
                txt_header.text = context.getString(R.string.no_internet_header)
                btnokWarning.setOnClickListener { v: View? ->
                    dialog.dismiss()
                }
                try {
                    if (!dialog.isShowing) {
                        dialog.window!!.setLayout(
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
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }


        fun GetDateBeforeTwomonth() : String
        {
             val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -2)
            val date = calendar.time
            val format = SimpleDateFormat("yyyy-MM-dd")
            val dateOutput = format.format(date)
             return dateOutput
        }
        fun getCircularProgressDrawable(context: Context): CircularProgressDrawable? {
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