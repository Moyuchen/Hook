package com.moyuchen.hook.hookAssets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import dalvik.system.DexClassLoader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.moyuchen.hook.R;
import com.moyuchen.hootutil.inter.IBean;

import java.io.File;
import java.lang.reflect.Method;

public class HookStringActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();
    private AssetManager mAssetManager = null;
    private Resources mResources = null;
    private Resources.Theme theme = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_string);

        String[] requests = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                || !(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            //没有权限，申请权限
            ActivityCompat.requestPermissions(this, requests, 0);
        } else {
            //拥有权限
        }


        TextView tvName = findViewById(R.id.tvname);

//        File fileStreamPath = getFileStreamPath("simpleapp-debug.apk");
//        if (fileStreamPath.exists()) {
//        }

        String path = "/storage/emulated/0/files/simpleapp-debug.apk";
        File file = new File(path);
        if (file.exists()) {
            System.out.println("simpleapp-debug.apk 存在");
        }

        File dex = getDir("dex", 0);
        DexClassLoader dexClassLoader = new DexClassLoader(path, dex.getAbsolutePath(), null, getClassLoader());

        findViewById(R.id.load).setOnClickListener(l -> {
            try {
                loadResource(path);
                Class<?> aClass = dexClassLoader.loadClass("com.moyuchen.simpleapp.Bean");

                Object newInstance = aClass.newInstance();
                IBean bean = (IBean) newInstance;

                // ------- 带参数调用 start---------
//                bean.setName("加载插件案例");
//                tvName.setText(bean.getName());
                //---------带参数调用end


                // ---------  反射调用 start-------
//                Method getName = aClass.getMethod("getName");
//                getName.setAccessible(true);
//                String name = (String) getName.invoke(newInstance);
//                tvName.setText(name);
                // --------- 反射调用 end ---------

                //---------   反射调用Asset或Resource中的String值
                String stringValue = bean.getStringValue(HookStringActivity.this);
                tvName.setText(stringValue);

            } catch (Exception e) {
//                e.printStackTrace();
                Log.i(TAG, "onCreate:e: " + e.getMessage());
            }
        });
    }

    private void loadResource(String path) {

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);
            mAssetManager = assetManager;
        } catch (Exception e) {
//            e.printStackTrace();
            Log.i(TAG, "loadResource: e:" + e.getMessage());
        }

        mResources = new Resources(mAssetManager, super.getResources().getDisplayMetrics(), super.getResources().getConfiguration());
        theme = mResources.newTheme();
        theme.setTo(super.getTheme());
    }

    @Override
    public AssetManager getAssets() {
        if (null != mAssetManager) {
            return mAssetManager;
        }
        return super.getAssets();
    }

    @Override
    public Resources getResources() {
        if (null != mResources) {
            return mResources;
        }
        return super.getResources();
    }

    @Override
    public Resources.Theme getTheme() {
        if (null != theme) {
            return theme;
        }
        return super.getTheme();
    }
}
