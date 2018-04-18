package com.jinkun_innovation.pastureland.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Guan on 2018/3/26.
 */

public class MuqunSum {


    /**
     * msg : 获取牲畜类型和数量成功
     * code : success
     * typeMap : {"1":235,"2":3,"3":6,"4":8,"7":1}
     */

    private String msg;
    private String code;
    private TypeMapBean typeMap;

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

    public TypeMapBean getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(TypeMapBean typeMap) {
        this.typeMap = typeMap;
    }

    public static class TypeMapBean {
        /**
         * 1 : 235
         * 2 : 3
         * 3 : 6
         * 4 : 8
         * 7 : 1
         */

        @SerializedName("1")
        private int _$1;
        @SerializedName("2")
        private int _$2;
        @SerializedName("3")
        private int _$3;
        @SerializedName("4")
        private int _$4;
        @SerializedName("7")
        private int _$7;

        public int get_$1() {
            return _$1;
        }

        public void set_$1(int _$1) {
            this._$1 = _$1;
        }

        public int get_$2() {
            return _$2;
        }

        public void set_$2(int _$2) {
            this._$2 = _$2;
        }

        public int get_$3() {
            return _$3;
        }

        public void set_$3(int _$3) {
            this._$3 = _$3;
        }

        public int get_$4() {
            return _$4;
        }

        public void set_$4(int _$4) {
            this._$4 = _$4;
        }

        public int get_$7() {
            return _$7;
        }

        public void set_$7(int _$7) {
            this._$7 = _$7;
        }
    }
}
