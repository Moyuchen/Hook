package com.moyuchen.hook.loadOtherApk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import dalvik.system.DexClassLoader;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.moyuchen.hook.R;
import com.moyuchen.hootutil.inter.IBean;

import java.io.File;
import java.lang.reflect.Method;

public class LoadOtherApkActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_other_apk);
        String[] requests = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                || !(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            //没有权限，申请权限
            ActivityCompat.requestPermissions(this,requests,0);
        }else {
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
                Class<?> aClass = dexClassLoader.loadClass("com.moyuchen.simpleapp.Bean");

                Object newInstance = aClass.newInstance();
                IBean bean = (IBean) newInstance;

                // ------- 带参数调用 start---------
//                bean.setName("加载插件案例");
//                tvName.setText(bean.getName());
                //---------带参数调用end


                // ---------  反射调用 start-------
                Method getName = aClass.getMethod("getName");
                getName.setAccessible(true);
                String name = (String) getName.invoke(newInstance);
                tvName.setText(name);
                // --------- 反射调用 end ---------

            } catch (Exception e) {
//                e.printStackTrace();
                Log.i(TAG, "onCreate:e: " + e.getMessage());
            }
        });
    }


}
