package com.example.shizukuterminal

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import rikka.shizuku.Shizuku

class MainActivity : AppCompatActivity() {
    
    private val permissionCode = 1001
    
    private val permissionListener = Shizuku.OnRequestPermissionResultListener { requestCode, grantResult ->
        if (requestCode == permissionCode) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Shizuku permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Shizuku permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        checkShizuku()
    }
    
    private fun checkShizuku() {
        if (Shizuku.pingBinder()) {
            if (Shizuku.isPreV11() || Shizuku.getVersion() < 11) {
                Toast.makeText(this, "Please update Shizuku", Toast.LENGTH_SHORT).show()
            } else {
                requestShizukuPermission()
            }
        } else {
            Toast.makeText(this, "Shizuku is not running", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun requestShizukuPermission() {
        Shizuku.addRequestPermissionResultListener(permissionListener)
        Shizuku.requestPermission(permissionCode)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Shizuku.removeRequestPermissionResultListener(permissionListener)
    }
}
