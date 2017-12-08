package com.xu.crease.tinkersample

import android.content.Context
import android.content.Intent
import com.tencent.tinker.lib.reporter.DefaultPatchReporter
import com.tencent.tinker.loader.shareutil.SharePatchInfo
import java.io.File

/**
 * Copied from Tencent Samples
 *
 * @author Crease
 * @version 1.0
 */
internal class SamplePatchReporter(context : Context) : DefaultPatchReporter(context){

    override fun onPatchServiceStart(intent: Intent) {
        super.onPatchServiceStart(intent)
        SampleTinkerReport.onApplyPatchServiceStart()
    }

    override fun onPatchDexOptFail(patchFile: File, dexFiles: List<File>, t: Throwable) {
        super.onPatchDexOptFail(patchFile, dexFiles, t)
        SampleTinkerReport.onApplyDexOptFail(t)
    }

    override fun onPatchException(patchFile: File, e: Throwable) {
        super.onPatchException(patchFile, e)
        SampleTinkerReport.onApplyCrash(e)
    }

    override fun onPatchInfoCorrupted(patchFile: File?, oldVersion: String, newVersion: String) {
        super.onPatchInfoCorrupted(patchFile, oldVersion, newVersion)
        SampleTinkerReport.onApplyInfoCorrupted()
    }

    override fun onPatchPackageCheckFail(patchFile: File, errorCode: Int) {
        super.onPatchPackageCheckFail(patchFile, errorCode)
        SampleTinkerReport.onApplyPackageCheckFail(errorCode)
    }

    override fun onPatchResult(patchFile: File, success: Boolean, cost: Long) {
        super.onPatchResult(patchFile, success, cost)
        SampleTinkerReport.onApplied(cost, success)
    }

    override fun onPatchTypeExtractFail(patchFile: File, extractTo: File, filename: String, fileType: Int) {
        super.onPatchTypeExtractFail(patchFile, extractTo, filename, fileType)
        SampleTinkerReport.onApplyExtractFail(fileType)
    }

    override fun onPatchVersionCheckFail(patchFile: File, oldPatchInfo: SharePatchInfo?, patchFileVersion: String) {
        super.onPatchVersionCheckFail(patchFile, oldPatchInfo, patchFileVersion)
        SampleTinkerReport.onApplyVersionCheckFail()
    }
}