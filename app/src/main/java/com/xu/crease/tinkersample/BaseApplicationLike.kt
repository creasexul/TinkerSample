package com.xu.crease.tinkersample

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import com.tencent.tinker.anno.DefaultLifeCycle
import com.tencent.tinker.lib.tinker.Tinker
import com.tencent.tinker.loader.app.DefaultApplicationLike
import com.tencent.tinker.loader.shareutil.ShareConstants

/**
 * Copied from Tencent Samples
 *
 * @author Crease
 * @version 1.0
 */
@Suppress("unused")
@DefaultLifeCycle(application = "tinker.sample.android.app.BaseApplication", flags = ShareConstants
        .TINKER_ENABLE_ALL, loadVerifyFlag = false)
public class BaseApplicationLike(
        application: Application,
        tinkerFlag: Int,
        tinkerLoadVerifyFlag: Boolean,
        applicationStartElapsedTime: Long,
        applicationStartMillisTime: Long,
        tinkerResultIntent: Intent
) : DefaultApplicationLike(application, tinkerFlag, tinkerLoadVerifyFlag,
        applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent) {

    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)

        TinkerManager.applicationLike = this

        TinkerManager.initFastCrashProtect()
        TinkerManager.setUpgradeRetryEnable(true)

        TinkerManager.installTinker(this)
        Tinker.with(application)
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun registerActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callback)
    }

}