package com.jinkun_innovation.pastureland.bean;

import java.util.List;

/**
 * Created by Guan on 2018/5/14.
 */

public class GetToolList {


    /**
     * msg : 设备工具获取成功
     * code : success
     * toolList : [{"deviceCount":"1","createTime":"2018-05-14 10:57:25","ranchId":"13","deviceFunction":"老吴裕泰","id":"19","deviceName":"割草机"},{"deviceCount":"2","createTime":"2018-05-14 10:59:27","ranchId":"13","deviceFunction":"维护牧场","id":"20","deviceName":"播种机"},{"deviceCount":"2","createTime":"2018-05-14 10:59:50","ranchId":"13","deviceFunction":"维护牧场","id":"21","deviceName":"播种机"},{"deviceCount":"2","createTime":"2018-05-14 11:00:52","ranchId":"13","deviceFunction":"维护牧场","id":"22","deviceName":"播种机"},{"deviceCount":"123","createTime":"2018-05-14 11:04:01","ranchId":"13","deviceFunction":"11111","id":"23","deviceName":"测试111"},{"deviceCount":"123","createTime":"2018-05-14 11:04:39","ranchId":"13","deviceFunction":"11111","id":"24","deviceName":"测试111"},{"deviceCount":"123","createTime":"2018-05-14 11:05:36","ranchId":"13","deviceFunction":"11111","id":"25","deviceName":"测试111"},{"deviceCount":"123","createTime":"2018-05-14 11:07:57","ranchId":"13","deviceFunction":"11111","id":"26","deviceName":"测试111"},{"deviceCount":"1","createTime":"2018-05-14 11:12:17","ranchId":"13","deviceFunction":"驴蹄子","id":"27","deviceName":"割草机"},{"deviceCount":"1","createTime":"2018-05-14 15:20:03","ranchId":"13","deviceFunction":"罗一笑","id":"29","deviceName":"割草机"},{"deviceCount":"1","createTime":"2018-05-14 15:43:13","ranchId":"13","deviceFunction":"哦咯XP","id":"30","deviceName":"割草机"},{"deviceCount":"1","createTime":"2018-05-14 17:19:19","ranchId":"13","deviceFunction":"KKK","id":"31","deviceName":"割草机"}]
     */

    private String msg;
    private String code;
    private List<ToolListBean> toolList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ToolListBean> getToolList() {
        return toolList;
    }

    public void setToolList(List<ToolListBean> toolList) {
        this.toolList = toolList;
    }

    public static class ToolListBean {
        /**
         * deviceCount : 1
         * createTime : 2018-05-14 10:57:25
         * ranchId : 13
         * deviceFunction : 老吴裕泰
         * id : 19
         * deviceName : 割草机
         */

        private String deviceCount;
        private String createTime;
        private String ranchId;
        private String deviceFunction;
        private String id;
        private String deviceName;

        public String getDeviceCount() {
            return deviceCount;
        }

        public void setDeviceCount(String deviceCount) {
            this.deviceCount = deviceCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getRanchId() {
            return ranchId;
        }

        public void setRanchId(String ranchId) {
            this.ranchId = ranchId;
        }

        public String getDeviceFunction() {
            return deviceFunction;
        }

        public void setDeviceFunction(String deviceFunction) {
            this.deviceFunction = deviceFunction;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }
    }
}
