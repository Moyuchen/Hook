package com.moyuchen.simpleapp;

import android.content.Context;

import com.moyuchen.hootutil.inter.IBean;

/**
 * @Author zhangyabo
 * @Date 2020-04-22
 * @Des
 **/
public class Bean implements IBean {

    private String name = "moyuchen";


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getStringValue(Context ctx) {
        return ctx.getResources().getString(R.string.app_name);
    }


}
