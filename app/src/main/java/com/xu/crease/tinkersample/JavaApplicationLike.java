package com.xu.crease.tinkersample;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tinkerpatch.sdk.TinkerPatch;


@SuppressWarnings("unused")
@DefaultLifeCycle(application = "tinker.sample.android.app.JavaApplication", flags = ShareConstants
        .TINKER_ENABLE_ALL, loadVerifyFlag = false)
public class JavaApplicationLike extends DefaultApplicationLike {
    public JavaApplicationLike(
            Application application,
            int tinkerFlags,
            boolean tinkerLoadVerifyFlag,
            long applicationStartElapsedTime,
            long applicationStartMillisTime,
            Intent tinkerResultIntent) {
        super(
                application,
                tinkerFlags,
                tinkerLoadVerifyFlag,
                applicationStartElapsedTime,
                applicationStartMillisTime,
                tinkerResultIntent
        );
    }

    @Override
    public void onBaseContextAttached(Context context) {
        super.onBaseContextAttached(context);

        TinkerManager.INSTANCE.setApplicationLike(this);

        TinkerManager.INSTANCE.initFastCrashProtect();
        TinkerManager.INSTANCE.setUpgradeRetryEnable(true);

        TinkerManager.INSTANCE.installTinker(this);
        Tinker.with(getApplication());

    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化TinkerPatch
        TinkerPatch.init(this)
                .reflectPatchLibrary()
                .setPatchRollbackOnScreenOff(true)
                .setPatchRestartOnSrceenOff(true)
                .setFetchPatchIntervalByHours(6);

        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks
                                                           callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }
}
