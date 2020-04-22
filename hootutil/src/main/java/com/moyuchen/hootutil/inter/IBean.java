package com.moyuchen.hootutil.inter;

import android.content.Context;

/**
 * @Author zhangyabo
 * @Date 2020-04-22
 * @Des
 **/
public interface IBean {
    String getName();
    void setName(String name);

    String getStringValue(Context ctx);
}
