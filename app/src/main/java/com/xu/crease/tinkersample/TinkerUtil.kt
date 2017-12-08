package com.xu.crease.tinkersample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import android.os.StatFs
import com.tencent.tinker.lib.util.TinkerLog
import com.tencent.tinker.loader.shareutil.ShareConstants
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.PrintStream

/**
 * Copied from Tencent Samples
 *
 * @author Crease
 * @version 1.0
 */
internal object TinkerUtil {
    private const val TAG = "TinkerUtil"
    private const val XPOSED_BRIDGE = "de.robv.android.xposed.XposedBridge"

    const val ERROR_PATCH_GOOGLE_PLAY = 10027
    const val ERROR_PATCH_ROM_SPACE = 10028
    const val ERROR_PATCH_MEMORY_LIMIT = 10029
    const val ERROR_PATCH_CRASH_LIMIT = 10030
    const val ERROR_PATCH_CONDITION_NOT_SATISFIED = 10031

    const val PLATFORM = "platform"

    const val MIN_MEMORY_HEAP_SIZE = 45

    var background = false

    fun isGooglePlay(): Boolean = false

    fun checkForPatchRecover(roomSize: Long, maxMemory: Int): Int {
        return when {
            isGooglePlay() -> ERROR_PATCH_GOOGLE_PLAY
            maxMemory < MIN_MEMORY_HEAP_SIZE -> ERROR_PATCH_MEMORY_LIMIT

            else -> ShareConstants.ERROR_PATCH_OK
        }
    }

    fun isXposedExists(throwable: Throwable): Boolean {
        val stackTraces = throwable.stackTrace
        stackTraces.forEach {
            val clazzName = it.className
            if (null != clazzName && clazzName.contains(XPOSED_BRIDGE))
                return true
        }

        return false
    }

    fun checkRomSpaceEnough(limitSize: Long): Boolean {
        var allsize: Long
        var availableSize: Long

        try {
            val file = Environment.getDataDirectory()
            val sf = StatFs(file.path)

            availableSize = sf.availableBlocksLong * sf.blockSize
            allsize = sf.blockCountLong * sf.blockSize
        } catch (e: Exception) {
            allsize = 0L
            availableSize = 0L
        }

        return allsize != 0L && availableSize > limitSize
    }

    fun getExceptionCauseString(ex: Throwable?): String {

        if (null == ex) return ""

        val bos = ByteArrayOutputStream()
        val ps = PrintStream(bos)

        try {
            var throwable: Throwable = ex
            var throwableCause = throwable.cause
            while (throwableCause != null) {
                throwable = throwableCause
                throwableCause = throwable.cause
            }
            throwable.printStackTrace(ps)
            return toVisualString(bos.toString())
        } catch (ignored: Exception) {
            return ""
        } finally {
            try {
                bos.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
    }

    private fun toVisualString(src: String?): String {

        var currentFlag = false

        if (null == src) return ""

        val char = src.toCharArray()

        var goalIndex = 0
        char.forEachIndexed { index, c ->
            if (c.toInt() > 127) {
                goalIndex = index
                char[index] = 0.toChar()
                currentFlag = true
                return@forEachIndexed
            }
        }

        return if (currentFlag) {
            String(char, 0, goalIndex)
        } else {
            src
        }
    }

    class ScreenState(context: Context, onScreenOffInterface: IOnScreenOff?) {
        interface IOnScreenOff {
            fun onScreenOff()
        }

        init {
            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_SCREEN_OFF)

            context.registerReceiver(object : BroadcastReceiver() {

                override fun onReceive(context: Context, intent: Intent?) {
                    val action = if (intent == null) "" else intent.action
                    TinkerLog.i(TAG, "ScreenReceiver action [%s] ", action)
                    if (Intent.ACTION_SCREEN_OFF == action) {
                        onScreenOffInterface?.onScreenOff()
                    }
                    context.unregisterReceiver(this)
                }
            }, filter)
        }
    }
}