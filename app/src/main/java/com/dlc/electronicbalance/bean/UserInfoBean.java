package com.dlc.electronicbalance.bean;

/**
 * Created by YoungeTao on 2017/8/19
 * QQ 2276559259.
 * gmail youngetao@gmail.com
 */

public class UserInfoBean {

    /**
     * code : 1
     * msg : 管理员获取成功
     * data : {"id":"2","account":"56001","name":"刘德法","password":"123","card":"","status":"1","op_type":"1","token":"8YvLNGD9cMjYQijSkIdG7seKJLpuCxB9"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * account : 56001
         * name : 刘德法
         * password : 123
         * card :
         * status : 1
         * op_type : 1
         * token : 8YvLNGD9cMjYQijSkIdG7seKJLpuCxB9
         */

        private String id;
        private String account;
        private String name;
        private String password;
        private String card;
        private String status;
        private String op_type;
        private String token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOp_type() {
            return op_type;
        }

        public void setOp_type(String op_type) {
            this.op_type = op_type;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
