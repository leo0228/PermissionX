package com.permissionx.tianjiaodev.kotlin

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import kotlin.system.exitProcess

//typealias 可以用于给任意类型指定一个别名
//接收Boolean, List<String>两种类型的参数
typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {

    private lateinit var callback: PermissionCallback

    private lateinit var mPermissions: Map<String, Boolean>

    //vararg 可变长度的permission
    fun requestNow(cb: PermissionCallback, p: Map<String, Boolean>) {
        callback = cb
        mPermissions = p

        var keys: Set<String> = mPermissions.keys
        var p: Array<String> = keys.toTypedArray()

        requestPermissions(p, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            //用来记录所有被用户拒绝的权限
            val deniedList = ArrayList<String>()

            for ((index, result) in grantResults.withIndex()) {
                val name = permissions[index]
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(name)
                    if (mPermissions.getValue(name)) {
                        showTipDialog()
                    } else {
                        if (!activity?.let { ActivityCompat.shouldShowRequestPermissionRationale(it, name) }!!) {
                            //拒绝，不要再询问
                        }
                    }
                }
            }

            //是否所有申请的权限均已被授权的标识，deniedList为空就是均被授权
            val allGranted = deniedList.isEmpty()
            callback(allGranted, deniedList)
        }
    }


    private fun showTipDialog() {
        val builder = activity?.let { AlertDialog.Builder(it) }
        builder.let {
            it?.setTitle("提示")
            it?.setMessage("如需开启相关功能，必须授权，\n请前去设置")
            it?.setPositiveButton("去设置") { dialog, _ ->
                dialog.dismiss()
                settingToActivity()
            }
            it?.setNegativeButton("") { dialog, _ ->
                dialog.dismiss()
                activity?.finish()
                exitProcess(0)
            }

            it?.show();
        }
    }

    private fun settingToActivity() {
        val intent = Intent()
        intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        intent.data = Uri.parse("package:" + activity?.packageName)
        activity?.startActivityForResult(intent, 2)
    }
}