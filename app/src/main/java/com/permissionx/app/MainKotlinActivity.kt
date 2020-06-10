package com.permissionx.app

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.tianjiaodev.kotlin.PermissionX
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainKotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeCallBtn.setOnClickListener {
            val permissions: MutableMap<String, Boolean> = HashMap()
            permissions[Manifest.permission.CAMERA] = true
            permissions[Manifest.permission.CALL_PHONE] = true
            permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] = false

            PermissionX.request(
                this,
                permissions
            ) { allGranted, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "You denied $deniedList", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "You denied $deniedList", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
