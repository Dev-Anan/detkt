package jp.vertice.devkt.manager.http

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager

object Helper {

    const val loadingTitle = "ロード中。。。"
    const val loadingMessage = "少々お待ちください。"
    const val txtTitleDialogOpenNetwork = "ネットワークに接続できません。"
    const val txtContentDialogOpenNetwork = "申し訳ございませんが、モバイルネットワークの接続をご確認の上、再度お試しください。"
    const val txtDialogNoConnectedInternet = "インターネット接続が確認できませんでした。電波の良いところで確認してください"

    //check network connect
    fun isNetworkConnected(context: Context, activity: Activity, flag: Boolean?): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo

        return if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
            true
        } else {
            AlertDialog.Builder(activity)
                .setTitle(txtTitleDialogOpenNetwork)
                .setMessage(txtContentDialogOpenNetwork)
                .setPositiveButton("OK") { _, _ ->
                    if (flag!!) {
                        activity.finish()
                    }
                }
                .show()
                .setCanceledOnTouchOutside(false)
            false
        }
    }
}