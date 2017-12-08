package com.xu.crease.tinkersample

import android.content.Context
import android.os.SystemClock
import android.util.Log
import com.tencent.tinker.lib.tinker.TinkerApplicationHelper
import com.tencent.tinker.lib.util.TinkerLog
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals

/**
 * Copied from Tencent Samples
 *
 * @author Crease
 * @version 1.0
 */
internal class UncaughtExceptionHandler : Thread.UncaughtExceptionHandler {

    companion object {
        private const val TAG = "UncaughtExceptionH"
        private const val QUICK_CRASH_ELAPSE = 10 * 1000
        public const val MAX_CRASH_COUNT = 3
        private const val DALVIK_XPOSED_CRASH = "Class ref in pre-verified class resolved to unexpected implementation"
    }

    private val ueh = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        Log.e(TAG, "uncaughtException:${e?.message}")
        tinkerFastCrashProtect()
        tinkerPreVerifiedCrashHandler(e)
        ueh.uncaughtException(t, e)
    }

    private fun tinkerPreVerifiedCrashHandler(ex: Throwable?) {

        val applicationLike = TinkerManager.applicationLike
        if (null == applicationLike || applicationLike.application == null) {
            Log.e(TAG, "application is null")
            return
        }

        if (! TinkerApplicationHelper.isTinkerLoadSuccess(applicationLike)) {
            Log.e(TAG, "tinker is not loaded")
            return
        }

        var throwable = ex
        var isXposed = false
        while (null != throwable) {

            if (! isXposed)
                isXposed = TinkerUtil.isXposedExists(throwable)

            if (isXposed) {
                var isCasueByXposed = false

                val message = throwable.message
                if (throwable is IllegalAccessError && null != message && message.contains(DALVIK_XPOSED_CRASH))
                    isCasueByXposed = true

                if (isCasueByXposed) {
                    SampleTinkerReport.onXposedCrash()
                    Log.e(TAG, "have xposed: just clean tinker")

                    ShareTinkerInternals.killAllOtherProcess(applicationLike.application)

                    TinkerApplicationHelper.cleanPatch(applicationLike)
                    ShareTinkerInternals.setTinkerDisableWithSharedPreferences(applicationLike.application)
                }
            }

            throwable = throwable.cause

        }

    }

    private fun tinkerFastCrashProtect(): Boolean {
        val applicationLike = TinkerManager.applicationLike

        if (applicationLike == null || applicationLike.application == null) {
            return false
        }
        if (! TinkerApplicationHelper.isTinkerLoadSuccess(applicationLike)) {
            return false
        }

        val elapsedTime = SystemClock.elapsedRealtime() - applicationLike.applicationStartElapsedTime
        //this process may not install tinker, so we use TinkerApplicationHelper api
        if (elapsedTime < QUICK_CRASH_ELAPSE) {
            val currentVersion = TinkerApplicationHelper.getCurrentVersion(applicationLike)
            if (ShareTinkerInternals.isNullOrNil(currentVersion)) {
                return false
            }

            val sp = applicationLike.application.getSharedPreferences(ShareConstants
                    .TINKER_SHARE_PREFERENCE_CONFIG, Context.MODE_MULTI_PROCESS)
            val fastCrashCount = sp.getInt(currentVersion, 0) + 1
            if (fastCrashCount >= MAX_CRASH_COUNT) {
                SampleTinkerReport.onFastCrashProtect()
                TinkerApplicationHelper.cleanPatch(applicationLike)
                TinkerLog.e(TAG, "tinker has fast crash more than $fastCrashCount, we just clean patch!")
                return true
            } else {
                sp.edit().putInt(currentVersion, fastCrashCount).apply()
                TinkerLog.e(TAG, "tinker has fast crash $fastCrashCount times")
            }
        }

        return false
    }

}