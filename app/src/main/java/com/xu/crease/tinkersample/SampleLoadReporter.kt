package com.xu.crease.tinkersample

import android.content.Context
import android.os.Looper
import com.tencent.tinker.lib.reporter.DefaultLoadReporter
import com.tencent.tinker.lib.util.UpgradePatchRetry
import com.tencent.tinker.loader.shareutil.ShareConstants
import java.io.File

/**
 * Copied from Tencent Samples
 *
 * @author Crease
 * @version 1.0
 */
internal class SampleLoadReporter(context: Context) : DefaultLoadReporter(context) {

    override fun onLoadPatchListenerReceiveFail(patchFile: File, errorCode: Int) {
        super.onLoadPatchListenerReceiveFail(patchFile, errorCode)
        SampleTinkerReport.onTryApplyFailed(errorCode)
    }

    override fun onLoadResult(patchDirectory: File, loadCode: Int, cost: Long) {
        super.onLoadResult(patchDirectory, loadCode, cost)
        when (loadCode) {
            ShareConstants.ERROR_LOAD_OK -> SampleTinkerReport.onLoaded(cost)
        }
        Looper.getMainLooper().queue.addIdleHandler({
            if (UpgradePatchRetry.getInstance(context).onPatchRetryLoad()) {
                SampleTinkerReport.onReportRetryPatch()
            }
            false
        })
    }

    override fun onLoadException(e: Throwable, errorCode: Int) {
        super.onLoadException(e, errorCode)
        SampleTinkerReport.onLoadException(e, errorCode)
    }

    override fun onLoadFileMd5Mismatch(file: File, fileType: Int) {
        super.onLoadFileMd5Mismatch(file, fileType)
        SampleTinkerReport.onLoadFileMisMatch(fileType)
    }

    /**
     * try to recover patch oat file
     *
     * @param file
     * @param fileType
     * @param isDirectory
     */
    override fun onLoadFileNotFound(file: File, fileType: Int, isDirectory: Boolean) {
        super.onLoadFileNotFound(file, fileType, isDirectory)
        SampleTinkerReport.onLoadFileNotFound(fileType)
    }

    override fun onLoadPackageCheckFail(patchFile: File, errorCode: Int) {
        super.onLoadPackageCheckFail(patchFile, errorCode)
        SampleTinkerReport.onLoadPackageCheckFailed(errorCode)
    }

    override fun onLoadPatchInfoCorrupted(oldVersion: String, newVersion: String, patchInfoFile: File) {
        super.onLoadPatchInfoCorrupted(oldVersion, newVersion, patchInfoFile)
        SampleTinkerReport.onLoadInfoCorrupted()
    }

    override fun onLoadInterpret(type: Int, e: Throwable) {
        super.onLoadInterpret(type, e)
        SampleTinkerReport.onLoadInterpretReport(type, e)
    }

    override fun onLoadPatchVersionChanged(oldVersion: String, newVersion: String, patchDirectoryFile: File, currentPatchName: String) {
        super.onLoadPatchVersionChanged(oldVersion, newVersion, patchDirectoryFile, currentPatchName)
    }
}