package com.jinkun_innovation.pastureland.manager;

import com.jinkun_innovation.pastureland.bean.GrassBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guan on 2018/3/30.
 */

public class GrassManager {

    private static GrassManager mInstance;

    private List<GrassBean> mGrassBeanList;

    public List<GrassBean> getGrassBeanList() {

        if (mGrassBeanList == null) {

            mGrassBeanList = new ArrayList<GrassBean>();
        }

        return mGrassBeanList;
    }

    public void setGrassBeanList(List<GrassBean> grassBeanList) {

        mGrassBeanList = grassBeanList;

    }


    public static GrassManager getInstance() {
        if (mInstance == null) {
            synchronized (GrassManager.class) {
                if (mInstance == null) {
                    mInstance = new GrassManager();
                }
            }
        }

        return mInstance;
    }




}
