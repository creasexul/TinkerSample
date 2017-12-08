package com.xu.crease.tinkersample

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import com.tencent.tinker.lib.listener.DefaultPatchListener
import com.tencent.tinker.lib.util.TinkerLog
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals
import java.io.File

/**
 * Copied from Tencent Samples
 *
 * @author Crease
 * @version 1.0
 */
internal class SamplePatchListener(context: Context) : DefaultPatchListener(context) {
    companion object {
        private const val TAG = "SamplePatchListener"
        private const val NEW_PATCH_RESTRICTION_SPACE_SIZE_MIN = (60 * 1024 * 1024).toLong()
    }

    private val maxMemory = (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).memoryClass

    init {

        Log.i(TAG, "application mexMemory : $maxMemory")
    }

    /**
     * because we use the defaultCheckPatchReceived method
     * the error code define by myself should after `ShareConstants.ERROR_RECOVER_INSERVICE
     *
     * path
     * newPatch
    ` *
     */
    public override fun patchCheck(path: String?, patchMd5: String?): Int {
        val patchFile = File(path)
        TinkerLog.i(TAG, "receive a patch file: %s, file size:%d", path, SharePatchFileUtil.getFileOrDirectorySize(patchFile))
        var returnCode = super.patchCheck(path, patchMd5)

        if (returnCode == ShareConstants.ERROR_PATCH_OK) {
            returnCode = TinkerUtil.checkForPatchRecover(NEW_PATCH_RESTRICTION_SPACE_SIZE_MIN, maxMemory)
        }

        if (returnCode == ShareConstants.ERROR_PATCH_OK) {
            val sp = context.getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, Context.MODE_MULTI_PROCESS)
            //optional, only disable this patch file with md5
            val fastCrashCount = sp.getInt(patchMd5, 0)
            if (fastCrashCount >= UncaughtExceptionHandler.MAX_CRASH_COUNT) {
                returnCode = TinkerUtil.ERROR_PATCH_CRASH_LIMIT
            }
        }
        // Warning, it is just a sample case, you don't need to copy all of these
        // Interception some of the request
        if (returnCode == ShareConstants.ERROR_PATCH_OK) {
            val properties = ShareTinkerInternals.fastGetPatchPackageMeta(patchFile)
            if (properties == null) {
                returnCode = TinkerUtil.ERROR_PATCH_CONDITION_NOT_SATISFIED
            } else {
                val platform = properties.getProperty(TinkerUtil.PLATFORM)
                if (null != platform) {
                    TinkerLog.i(TAG, "get platform:" + platform)
                    // check patch platform require
                    if (platform != BuildConfig.PLATFORM) {
                        returnCode = TinkerUtil.ERROR_PATCH_CONDITION_NOT_SATISFIED
                    }
                } else {
                    returnCode = TinkerUtil.ERROR_PATCH_CONDITION_NOT_SATISFIED
                }
            }
        }

        SampleTinkerReport.onTryApply(returnCode == ShareConstants.ERROR_PATCH_OK)
        return returnCode
    }
}