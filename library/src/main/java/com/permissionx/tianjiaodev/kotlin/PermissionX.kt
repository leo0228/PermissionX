package com.permissionx.tianjiaodev.kotlin


import androidx.fragment.app.FragmentActivity

object PermissionX {

    private const val TAG = "InvisibleFragment"

    /**
     * permissions: Map<String, Boolean>   String表示要申请的权限，Boolean表示对应权限是否必须
     */
    fun request(
        activity: FragmentActivity,
        permissions: Map<String, Boolean>,
        callback: PermissionCallback
    ) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        fragment.requestNow(callback, permissions)
    }
}