package com.jinkun_innovation.pastureland.manager;

import com.jinkun_innovation.pastureland.bean.ToolBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guan on 2018/3/30.
 */

public class ToolsManager {

    private static ToolsManager mInstance;

    private List<ToolBean> mToolBeanList;

    public List<ToolBean> getToolBeanList() {

        if (mToolBeanList == null) {

            mToolBeanList = new ArrayList<ToolBean>();
        }

        return mToolBeanList;
    }

    public void setToolBeanList(List<ToolBean> mToolBeanList) {

        mToolBeanList = mToolBeanList;

    }


    public static ToolsManager getInstance() {
        if (mInstance == null) {
            synchronized (ToolsManager.class) {
                if (mInstance == null) {
                    mInstance = new ToolsManager();
                }
            }
        }

        return mInstance;
    }




}
