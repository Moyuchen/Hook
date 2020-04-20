package com.moyuchen.hook.hookStartActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.util.Log;

/**
 * @Author zhangyabo
 * @Date 2020-04-20
 * @Des
 **/
public class EvilInstrumentation extends Instrumentation {
    private static final String TAG = "EvilInstrumentation";

    // 替身Activity的包名, 也就是我们自己的包名
    String packageName = getClass().getPackage().getName();

    // ActivityThread中原始的对象, 保存起来
    Instrumentation mBase;

    public EvilInstrumentation(Instrumentation base) {
        mBase = base;
    }

    public Activity newActivity(ClassLoader cl, String className,
                                Intent intent)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        Log.i(TAG, "newActivity: ");

        // 把替身恢复成真身
//        Intent rawIntent = intent.getParcelableExtra(HookHelper.EXTRA_TARGET_INTENT);
//        if(rawIntent == null) {
//            return mBase.newActivity(cl, className, intent);
//        }
//        String newClassName = rawIntent.getComponent().getClassName();
//        return mBase.newActivity(cl, newClassName, rawIntent);

        return mBase.newActivity(cl, className, intent);
    }
}
