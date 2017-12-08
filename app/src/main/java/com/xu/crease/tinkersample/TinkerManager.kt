package com.xu.crease.tinkersample

import android.annotation.SuppressLint
import android.util.Log
import com.tencent.tinker.lib.patch.UpgradePatch
import com.tencent.tinker.lib.tinker.TinkerInstaller
import com.tencent.tinker.lib.util.UpgradePatchRetry
import com.tencent.tinker.loader.app.ApplicationLike

/**
 * Copied from Tencent Samples
 *
 * @author Crease
 * @version 1.0
 */
internal object TinkerManager {

    private const val TAG = "TinkerManager"

    @SuppressLint("StaticFieldLeak")
    var applicationLike: ApplicationLike? = null
    private var uncaughtExceptionHandler: UncaughtExceptionHandler? = null

    private var isInstalled = false


    fun initFastCrashProtect() {
        if (uncaughtExceptionHandler == null) {
            uncaughtExceptionHandler = UncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler)
        }
    }

    fun setUpgradeRetryEnable(enable: Boolean) {
        applicationLike?.let {
            UpgradePatchRetry.getInstance(it.application).setRetryEnable(enable)
        }
    }


    /**
     * all use default class, simply Tinker install method
     */
    fun sampleInstallTinker(appLike: ApplicationLike) {
        if (isInstalled) {
            Log.w(TAG, "install tinker, but has installed, ignore")
            return
        }
        TinkerInstaller.install(appLike)
        isInstalled = true
    }

    fun installTinker(appLike: ApplicationLike) {
        if (isInstalled) {
            Log.w(TAG, "install tinker, but has installed, ignore")
            return
        }
        //or you can just use DefaultLoadReporter
        val loadReporter = SampleLoadReporter(appLike.application)
        //or you can just use DefaultPatchReporter
        val patchReporter = SamplePatchReporter(appLike.application)
        //or you can just use DefaultPatchListener
        val patchListener = SamplePatchListener(appLike.application)
        //you can set your own upgrade patch if you need
        val upgradePatchProcessor = UpgradePatch()

        TinkerInstaller.install(appLike,
                loadReporter, patchReporter, patchListener,
                ResultService::class.java, upgradePatchProcessor)

        isInstalled = true
    }
}