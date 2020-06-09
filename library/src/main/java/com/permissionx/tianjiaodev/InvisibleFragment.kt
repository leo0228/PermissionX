package com.permissionx.tianjiaodev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

//typealias 可以用于给任意类型指定一个别名
//接收Boolean, List<String>两种类型的参数
typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {

    private var callback: PermissionCallback? = null

    //vararg 可变长度的permission
    fun requestNow(cb: PermissionCallback, vararg permission: String) {
        callback = cb
        requestPermissions(permission, 1)
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
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }

            //是否所有申请的权限均已被授权的标识，deniedList为空就是均被授权
            val allGranted = deniedList.isEmpty()
            callback?.let { it(allGranted, deniedList) }
        }
    }
}