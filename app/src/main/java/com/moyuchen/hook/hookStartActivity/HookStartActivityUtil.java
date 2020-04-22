package com.moyuchen.hook.hookStartActivity;

import android.app.Instrumentation;

import com.moyuchen.hootutil.util.HookObjectUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * @Author zhangyabo
 * @Date 2020-04-19
 * @Des
 **/
public class HookStartActivityUtil {
//    private String TAG = getClass().getSimpleName();
//    public static void startActivity() throws Exception{
//
////获取ActivityManager对象
//        Class<?> mAM = Class.forName("android.app.ActivityManager");
////        获取IActivityManagerSingleton变量
//        Field mIActivityManagerSingleton = mAM.getDeclaredField("IActivityManagerSingleton");
//        //设置权限
//        mIActivityManagerSingleton.setAccessible(true);
//        Object o = mIActivityManagerSingleton.get(null);
//
//        Class<?> mSingleton = Class.forName("android.util.Singleton");
//        Field mInstance = mSingleton.getDeclaredField("mInstance");
//        mInstance.setAccessible(true);
//        Object object = mInstance.get(mIActivityManagerSingleton);
//
//
//        //通过反射，获取IActivityManager对象
//        Class<?> mIAM = Class.forName("android.app.IAcitivityManager");
//        Proxy.newProxyInstance(HookStartActivityUtil.class.getClassLoader(), new Class[]{mIAM}, new MyInvocationHandler(object));
//
//
//    }
//
//
//    public  static class MyInvocationHandler implements InvocationHandler {
//        private Object mObject;
//
//        public MyInvocationHandler(Object args) {
//            this.mObject = args;
//        }
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            Log.i("HookUtil", "invoke: ");
//            return method.invoke(mObject, args);
//        }
//    }

    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    /**
     * Hook AMS
     * 主要完成的操作是  "把真正要启动的Activity临时替换为在AndroidManifest.xml中声明的替身Activity",进而骗过AMS
     */
    public static void hookAMN() throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, NoSuchFieldException {

        //获取AMN的gDefault单例gDefault，gDefault是final静态的
        Object gDefault = HookObjectUtil.getStaticFieldObject("android.app.ActivityManagerNative", "gDefault");

        // gDefault是一个 android.util.Singleton<T>对象; 我们取出这个单例里面的mInstance字段
        Object mInstance = HookObjectUtil.getFieldObject("android.util.Singleton", gDefault, "mInstance");

        // 创建一个这个对象的代理对象MockClass1, 然后替换这个字段, 让我们的代理对象帮忙干活
        Class<?> classB2Interface = Class.forName("android.app.IActivityManager");
        Object proxy = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[] { classB2Interface },
                new MockClass(mInstance));

        //把gDefault的mInstance字段，修改为proxy
        HookObjectUtil.setFieldObject("android.util.Singleton", gDefault, "mInstance", proxy);
    }

    public static void attachContext() throws Exception{
        // 先获取到当前的ActivityThread对象
        Object currentActivityThread = HookObjectUtil.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread");

        // 拿到原始的 mInstrumentation字段
        Instrumentation mInstrumentation = (Instrumentation) HookObjectUtil.getFieldObject(currentActivityThread, "mInstrumentation");

        // 创建代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);

        // 偷梁换柱
        HookObjectUtil.setFieldObject(currentActivityThread, "mInstrumentation", evilInstrumentation);
    }



}
