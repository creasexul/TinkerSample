package com.xu.crease.tinkersample

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_content1.setOnClickListener {


            val channelName = try {
                val appInfo = packageManager.getApplicationInfo(this.packageName, PackageManager.GET_META_DATA)
                appInfo.metaData.getString("TINKER_ID")
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                ""
            }

            Toast.makeText(this, TinkerObjectTest.toastString + channelName, Toast.LENGTH_SHORT).show()
        }
    }
}

