package com.xu.crease.tinkersample

import android.util.Log
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals

/**
 * Copied from Tencent Samples
 *
 * @author Crease
 * @version 1.0
 */
internal object SampleTinkerReport {
    const val TAG = "SampleTinkerReport"
    // KEY - PV
    private const val KEY_REQUEST = 0
    private const val KEY_DOWNLOAD = 1
    private const val KEY_TRY_APPLY = 2
    private const val KEY_TRY_APPLY_SUCCESS = 3
    private const val KEY_APPLIED_START = 4
    private const val KEY_APPLIED = 5
    private const val KEY_LOADED = 6
    private const val KEY_CRASH_FAST_PROTECT = 7
    private const val KEY_CRASH_CAUSE_XPOSED_DALVIK = 8
    private const val KEY_CRASH_CAUSE_XPOSED_ART = 9
    private const val KEY_APPLY_WITH_RETRY = 10

    //Key -- try apply detail
    private const val KEY_TRY_APPLY_UPGRADE = 70
    private const val KEY_TRY_APPLY_DISABLE = 71
    private const val KEY_TRY_APPLY_RUNNING = 72
    private const val KEY_TRY_APPLY_INSERVICE = 73
    private const val KEY_TRY_APPLY_NOT_EXIST = 74
    private const val KEY_TRY_APPLY_GOOGLEPLAY = 75
    private const val KEY_TRY_APPLY_ROM_SPACE = 76
    private const val KEY_TRY_APPLY_ALREADY_APPLY = 77
    private const val KEY_TRY_APPLY_MEMORY_LIMIT = 78
    private const val KEY_TRY_APPLY_CRASH_LIMIT = 79
    private const val KEY_TRY_APPLY_CONDITION_NOT_SATISFIED = 80
    private const val KEY_TRY_APPLY_JIT = 81

    //Key -- apply detail
    private const val KEY_APPLIED_UPGRADE = 100
    private const val KEY_APPLIED_UPGRADE_FAIL = 101

    private const val KEY_APPLIED_EXCEPTION = 120
    private const val KEY_APPLIED_DEXOPT_OTHER = 121
    private const val KEY_APPLIED_DEXOPT_EXIST = 122
    private const val KEY_APPLIED_DEXOPT_FORMAT = 123
    private const val KEY_APPLIED_INFO_CORRUPTED = 124
    //package check
    private const val KEY_APPLIED_PACKAGE_CHECK_SIGNATURE = 150
    private const val KEY_APPLIED_PACKAGE_CHECK_DEX_META = 151
    private const val KEY_APPLIED_PACKAGE_CHECK_LIB_META = 152
    private const val KEY_APPLIED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND = 153
    private const val KEY_APPLIED_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND = 154
    private const val KEY_APPLIED_PACKAGE_CHECK_META_NOT_FOUND = 155
    private const val KEY_APPLIED_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL = 156
    private const val KEY_APPLIED_PACKAGE_CHECK_RES_META = 157
    private const val KEY_APPLIED_PACKAGE_CHECK_TINKERFLAG_NOT_SUPPORT = 158

    //version check
    private const val KEY_APPLIED_VERSION_CHECK = 180
    //extract error
    private const val KEY_APPLIED_PATCH_FILE_EXTRACT = 181
    private const val KEY_APPLIED_DEX_EXTRACT = 182
    private const val KEY_APPLIED_LIB_EXTRACT = 183
    private const val KEY_APPLIED_RESOURCE_EXTRACT = 184
    //cost time
    private const val KEY_APPLIED_SUCC_COST_5S_LESS = 200
    private const val KEY_APPLIED_SUCC_COST_10S_LESS = 201
    private const val KEY_APPLIED_SUCC_COST_30S_LESS = 202
    private const val KEY_APPLIED_SUCC_COST_60S_LESS = 203
    private const val KEY_APPLIED_SUCC_COST_OTHER = 204

    private const val KEY_APPLIED_FAIL_COST_5S_LESS = 205
    private const val KEY_APPLIED_FAIL_COST_10S_LESS = 206
    private const val KEY_APPLIED_FAIL_COST_30S_LESS = 207
    private const val KEY_APPLIED_FAIL_COST_60S_LESS = 208
    private const val KEY_APPLIED_FAIL_COST_OTHER = 209


    // KEY -- load detail
    private const val KEY_LOADED_UNKNOWN_EXCEPTION = 250
    private const val KEY_LOADED_UNCAUGHT_EXCEPTION = 251
    private const val KEY_LOADED_EXCEPTION_DEX = 252
    private const val KEY_LOADED_EXCEPTION_DEX_CHECK = 253
    private const val KEY_LOADED_EXCEPTION_RESOURCE = 254
    private const val KEY_LOADED_EXCEPTION_RESOURCE_CHECK = 255


    private const val KEY_LOADED_MISMATCH_DEX = 300
    private const val KEY_LOADED_MISMATCH_LIB = 301
    private const val KEY_LOADED_MISMATCH_RESOURCE = 302
    private const val KEY_LOADED_MISSING_DEX = 303
    private const val KEY_LOADED_MISSING_LIB = 304
    private const val KEY_LOADED_MISSING_PATCH_FILE = 305
    private const val KEY_LOADED_MISSING_PATCH_INFO = 306
    private const val KEY_LOADED_MISSING_DEX_OPT = 307
    private const val KEY_LOADED_MISSING_RES = 308
    private const val KEY_LOADED_INFO_CORRUPTED = 309

    //load package check
    private const val KEY_LOADED_PACKAGE_CHECK_SIGNATURE = 350
    private const val KEY_LOADED_PACKAGE_CHECK_DEX_META = 351
    private const val KEY_LOADED_PACKAGE_CHECK_LIB_META = 352
    private const val KEY_LOADED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND = 353
    private const val KEY_LOADED_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND = 354
    private const val KEY_LOADED_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL = 355
    private const val KEY_LOADED_PACKAGE_CHECK_PACKAGE_META_NOT_FOUND = 356
    private const val KEY_LOADED_PACKAGE_CHECK_RES_META = 357
    private const val KEY_LOADED_PACKAGE_CHECK_TINKERFLAG_NOT_SUPPORT = 358

    private const val KEY_LOADED_SUCC_COST_500_LESS = 400
    private const val KEY_LOADED_SUCC_COST_1000_LESS = 401
    private const val KEY_LOADED_SUCC_COST_3000_LESS = 402
    private const val KEY_LOADED_SUCC_COST_5000_LESS = 403
    private const val KEY_LOADED_SUCC_COST_OTHER = 404

    private const val KEY_LOADED_INTERPRET_GET_INSTRUCTION_SET_ERROR = 450
    private const val KEY_LOADED_INTERPRET_INTERPRET_COMMAND_ERROR = 451
    private const val KEY_LOADED_INTERPRET_TYPE_INTERPRET_OK = 452

    interface Reporter {
        fun onReport(key: Int)

        fun onReport(message: String)
    }

    var reporter: Reporter? = null

    fun onTryApply(success: Boolean) {

        reporter?.let {
            it.onReport(KEY_TRY_APPLY)
            it.onReport(KEY_TRY_APPLY_UPGRADE)

            if (success)
                it.onReport(KEY_TRY_APPLY_SUCCESS)
        }
    }

    fun onTryApplyFailed(errorCode: Int) {
        reporter?.let {
            when (errorCode) {
                ShareConstants.ERROR_PATCH_NOTEXIST ->
                    it.onReport(KEY_TRY_APPLY_NOT_EXIST)
                ShareConstants.ERROR_PATCH_DISABLE ->
                    it.onReport(KEY_TRY_APPLY_DISABLE)
                ShareConstants.ERROR_PATCH_INSERVICE ->
                    it.onReport(KEY_TRY_APPLY_INSERVICE)
                ShareConstants.ERROR_PATCH_RUNNING ->
                    it.onReport(KEY_TRY_APPLY_RUNNING)
                ShareConstants.ERROR_PATCH_JIT ->
                    it.onReport(KEY_TRY_APPLY_JIT)
                ShareConstants.ERROR_PATCH_ALREADY_APPLY ->
                    it.onReport(KEY_TRY_APPLY_ALREADY_APPLY)
                TinkerUtil.ERROR_PATCH_ROM_SPACE ->
                    it.onReport(KEY_TRY_APPLY_ROM_SPACE)
                TinkerUtil.ERROR_PATCH_GOOGLE_PLAY ->
                    it.onReport(KEY_TRY_APPLY_GOOGLEPLAY)
                TinkerUtil.ERROR_PATCH_CRASH_LIMIT ->
                    it.onReport(KEY_TRY_APPLY_CRASH_LIMIT)
                TinkerUtil.ERROR_PATCH_MEMORY_LIMIT ->
                    it.onReport(KEY_TRY_APPLY_MEMORY_LIMIT)
                TinkerUtil.ERROR_PATCH_CONDITION_NOT_SATISFIED ->

                    it.onReport(KEY_TRY_APPLY_CONDITION_NOT_SATISFIED)
            }
        }
    }

    fun onLoadPackageCheckFailed(errorCode: Int) {
        reporter?.let {
            when (errorCode) {
                ShareConstants.ERROR_PACKAGE_CHECK_SIGNATURE_FAIL ->
                    it.onReport(KEY_LOADED_PACKAGE_CHECK_SIGNATURE)
                ShareConstants.ERROR_PACKAGE_CHECK_DEX_META_CORRUPTED ->
                    it.onReport(KEY_LOADED_PACKAGE_CHECK_DEX_META)
                ShareConstants.ERROR_PACKAGE_CHECK_LIB_META_CORRUPTED ->
                    it.onReport(KEY_LOADED_PACKAGE_CHECK_LIB_META)
                ShareConstants.ERROR_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND ->
                    it.onReport(KEY_LOADED_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND)
                ShareConstants.ERROR_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND ->
                    it.onReport(KEY_LOADED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND)
                ShareConstants.ERROR_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL ->
                    it.onReport(KEY_LOADED_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL)
                ShareConstants.ERROR_PACKAGE_CHECK_PACKAGE_META_NOT_FOUND ->
                    it.onReport(KEY_LOADED_PACKAGE_CHECK_PACKAGE_META_NOT_FOUND)
                ShareConstants.ERROR_PACKAGE_CHECK_RESOURCE_META_CORRUPTED ->
                    it.onReport(KEY_LOADED_PACKAGE_CHECK_RES_META)
                ShareConstants.ERROR_PACKAGE_CHECK_TINKERFLAG_NOT_SUPPORT ->
                    it.onReport(KEY_LOADED_PACKAGE_CHECK_TINKERFLAG_NOT_SUPPORT)
            }
        }
    }

    fun onLoaded(cost: Long) {
        reporter?.let {
            it.onReport(KEY_LOADED)

            when {
                cost < 0L ->
                    Log.e(TAG, "hp_reoirt reoirt load cost failed, invalid cost")
                cost < 500 ->
                    it.onReport(KEY_LOADED_SUCC_COST_500_LESS)
                cost < 1000 ->
                    it.onReport(KEY_LOADED_SUCC_COST_1000_LESS)
                cost < 3000 ->
                    it.onReport(KEY_LOADED_SUCC_COST_3000_LESS)
                cost < 5000 ->
                    it.onReport(KEY_LOADED_SUCC_COST_5000_LESS)
                else ->
                    it.onReport(KEY_LOADED_SUCC_COST_OTHER)
            }
        }
    }

    fun onLoadInfoCorrupted() {
        reporter?.onReport(KEY_LOADED_INFO_CORRUPTED)
    }

    fun onLoadFileNotFound(fileType: Int) {
        reporter?.let {
            when (fileType) {
                ShareConstants.TYPE_DEX_OPT -> it.onReport(KEY_LOADED_MISSING_DEX_OPT)
                ShareConstants.TYPE_DEX -> it.onReport(KEY_LOADED_MISSING_DEX)
                ShareConstants.TYPE_LIBRARY -> it.onReport(KEY_LOADED_MISSING_LIB)
                ShareConstants.TYPE_PATCH_FILE -> it.onReport(KEY_LOADED_MISSING_PATCH_FILE)
                ShareConstants.TYPE_PATCH_INFO -> it.onReport(KEY_LOADED_MISSING_PATCH_INFO)
                ShareConstants.TYPE_RESOURCE -> it.onReport(KEY_LOADED_MISSING_RES)
            }
        }
    }

    fun onLoadInterpretReport(type: Int, e: Throwable?) {
        reporter?.let {
            when (type) {

                ShareConstants.TYPE_INTERPRET_GET_INSTRUCTION_SET_ERROR -> {
                    it.onReport(KEY_LOADED_INTERPRET_GET_INSTRUCTION_SET_ERROR)
                    it.onReport("Tinker Exception:interpret occur exception ${TinkerUtil
                            .getExceptionCauseString(e)}")
                }
                ShareConstants.TYPE_INTERPRET_COMMAND_ERROR -> {
                    it.onReport(KEY_LOADED_INTERPRET_INTERPRET_COMMAND_ERROR)
                    it.onReport("Tinker Exception:interpret occur exception ${TinkerUtil
                            .getExceptionCauseString(e)}")
                }
                ShareConstants.TYPE_INTERPRET_OK ->
                    it.onReport(KEY_LOADED_INTERPRET_TYPE_INTERPRET_OK)

            }
        }
    }

    fun onLoadFileMisMatch(fileType: Int) {
        reporter?.let {
            when (fileType) {
                ShareConstants.TYPE_DEX -> it.onReport(KEY_LOADED_MISMATCH_DEX)
                ShareConstants.TYPE_LIBRARY -> it.onReport(KEY_LOADED_MISMATCH_LIB)
                ShareConstants.TYPE_RESOURCE -> it.onReport(KEY_LOADED_MISMATCH_RESOURCE)
            }
        }
    }

    fun onLoadException(throwable: Throwable, errorCode: Int) {
        reporter?.let {
            var isCheckFail = false
            when (errorCode) {
                ShareConstants.ERROR_LOAD_EXCEPTION_DEX -> {
                    val message = throwable.message?.contains(ShareConstants
                            .CHECK_RES_INSTALL_FAIL) ?: false
                    if (message) {
                        it.onReport(KEY_LOADED_EXCEPTION_RESOURCE_CHECK)
                        isCheckFail = true
                        Log.e(TAG, "tinker dex check fail:${throwable.message}")
                    } else {
                        it.onReport(KEY_LOADED_EXCEPTION_RESOURCE)
                        Log.e(TAG, "tinker dex reflect fail:${throwable.message}")
                    }
                }
                ShareConstants.ERROR_LOAD_EXCEPTION_RESOURCE -> {
                    val message = throwable.message?.contains(ShareConstants
                            .CHECK_RES_INSTALL_FAIL) ?: false
                    if (message) {
                        it.onReport(KEY_LOADED_EXCEPTION_RESOURCE_CHECK)
                        isCheckFail = true
                        Log.e(TAG, "tinker res check fail:${throwable.message}")
                    } else {
                        it.onReport(KEY_LOADED_EXCEPTION_RESOURCE)
                        Log.e(TAG, "tinker res reflect fail:${throwable.message}")
                    }
                }
                ShareConstants.ERROR_LOAD_EXCEPTION_UNCAUGHT -> it.onReport(KEY_LOADED_UNCAUGHT_EXCEPTION)
                ShareConstants.ERROR_LOAD_EXCEPTION_UNKNOWN -> it.onReport(KEY_LOADED_UNKNOWN_EXCEPTION)
            }
            //reporter exception, for dex check fail, we don't need to report stacktrace
            if (! isCheckFail) {
                it.onReport("Tinker Exception:load tinker occur exception " +
                        TinkerUtil.getExceptionCauseString(throwable))
            }
        }
    }

    fun onApplyPatchServiceStart() {
        reporter?.onReport(KEY_APPLIED_START)
    }

    fun onApplyDexOptFail(throwable: Throwable) {
        reporter?.let {
            val message = throwable.message
            if (null != message && message.contains(ShareConstants.CHECK_DEX_OAT_EXIST_FAIL)) {
                it.onReport(KEY_APPLIED_DEXOPT_EXIST)
            } else if (null != message && message.contains(ShareConstants.CHECK_DEX_OAT_FORMAT_FAIL)) {
                it.onReport(KEY_APPLIED_DEXOPT_FORMAT)
            } else {
                it.onReport(KEY_APPLIED_DEXOPT_OTHER)
                it.onReport("Tinker Exception:apply tinker occur exception " + TinkerUtil
                        .getExceptionCauseString(throwable))
            }
        }
    }

    fun onApplyInfoCorrupted() {
        reporter?.onReport(KEY_APPLIED_INFO_CORRUPTED)
    }

    fun onApplyVersionCheckFail() {
        reporter?.onReport(KEY_APPLIED_VERSION_CHECK)
    }

    fun onApplyExtractFail(fileType: Int) {
        reporter?.let {

            when (fileType) {
                ShareConstants.TYPE_DEX -> it.onReport(KEY_APPLIED_DEX_EXTRACT)
                ShareConstants.TYPE_LIBRARY -> it.onReport(KEY_APPLIED_LIB_EXTRACT)
                ShareConstants.TYPE_PATCH_FILE -> it.onReport(KEY_APPLIED_PATCH_FILE_EXTRACT)
                ShareConstants.TYPE_RESOURCE -> it.onReport(KEY_APPLIED_RESOURCE_EXTRACT)
            }
        }
    }

    fun onApplied(cost: Long, success: Boolean) {
        reporter?.let {

            if (success) {
                it.onReport(KEY_APPLIED)
            }

            if (success) {
                it.onReport(KEY_APPLIED_UPGRADE)
            } else {
                it.onReport(KEY_APPLIED_UPGRADE_FAIL)
            }

            Log.i(TAG, "hp_report report apply cost = $cost")

            if (cost < 0L) {
                Log.e(TAG, "hp_report report apply cost failed, invalid cost")
                return
            }

            when {
                cost <= 5000 -> {
                    it.onReport(if (success) {
                        KEY_APPLIED_SUCC_COST_5S_LESS
                    } else {
                        KEY_APPLIED_FAIL_COST_5S_LESS
                    })
                }
                cost <= 10 * 1000 -> {
                    it.onReport(if (success) {
                        KEY_APPLIED_SUCC_COST_10S_LESS
                    } else {
                        KEY_APPLIED_FAIL_COST_10S_LESS
                    })
                }
                cost <= 30 * 1000 -> {
                    it.onReport(if (success) {
                        KEY_APPLIED_SUCC_COST_30S_LESS
                    } else {
                        KEY_APPLIED_FAIL_COST_30S_LESS
                    })
                }
                cost <= 60 * 1000 -> {
                    it.onReport(if (success) {
                        KEY_APPLIED_SUCC_COST_60S_LESS
                    } else {
                        KEY_APPLIED_FAIL_COST_60S_LESS
                    })
                }
                else -> {
                    it.onReport(if (success) {
                        KEY_APPLIED_SUCC_COST_OTHER
                    } else {
                        KEY_APPLIED_FAIL_COST_OTHER
                    })
                }
            }
        }
    }

    fun onApplyPackageCheckFail(errorCode: Int) {
        Log.i(TAG, "hp_report package check failed, error = $errorCode")

        reporter?.let {
            when (errorCode) {
                ShareConstants.ERROR_PACKAGE_CHECK_SIGNATURE_FAIL ->
                    it.onReport(KEY_APPLIED_PACKAGE_CHECK_SIGNATURE)
                ShareConstants.ERROR_PACKAGE_CHECK_DEX_META_CORRUPTED ->
                    it.onReport(KEY_APPLIED_PACKAGE_CHECK_DEX_META)
                ShareConstants.ERROR_PACKAGE_CHECK_LIB_META_CORRUPTED ->
                    it.onReport(KEY_APPLIED_PACKAGE_CHECK_LIB_META)
                ShareConstants.ERROR_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND ->
                    it.onReport(KEY_APPLIED_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND)
                ShareConstants.ERROR_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND ->
                    it.onReport(KEY_APPLIED_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND)
                ShareConstants.ERROR_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL ->
                    it.onReport(KEY_APPLIED_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL)
                ShareConstants.ERROR_PACKAGE_CHECK_PACKAGE_META_NOT_FOUND ->
                    it.onReport(KEY_APPLIED_PACKAGE_CHECK_META_NOT_FOUND)
                ShareConstants.ERROR_PACKAGE_CHECK_RESOURCE_META_CORRUPTED ->
                    it.onReport(KEY_APPLIED_PACKAGE_CHECK_RES_META)
                ShareConstants.ERROR_PACKAGE_CHECK_TINKERFLAG_NOT_SUPPORT ->
                    it.onReport(KEY_APPLIED_PACKAGE_CHECK_TINKERFLAG_NOT_SUPPORT)
            }
        }
    }

    fun onApplyCrash(throwable: Throwable) {
        reporter?.let {
            it.onReport(KEY_APPLIED_EXCEPTION)
            it.onReport("Tinker Exception:apply tinker occur exception " + TinkerUtil
                    .getExceptionCauseString(throwable))
        }
    }

    fun onFastCrashProtect() {
        reporter?.onReport(KEY_CRASH_FAST_PROTECT)
    }

    fun onXposedCrash() {
        reporter?.onReport(if (ShareTinkerInternals.isVmArt()) {
            KEY_CRASH_CAUSE_XPOSED_ART
        } else {
            KEY_CRASH_CAUSE_XPOSED_DALVIK
        })
    }

    fun onReportRetryPatch() {
        reporter?.onReport(KEY_APPLY_WITH_RETRY)
    }
}