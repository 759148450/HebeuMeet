package com.hebeu.meet.domain;

/**
 * @ProjectName: HebeuMeet
 * @Package: com.hebeu.meet.domain
 * @Description: java类作用描述
 * @Author: Yue Chunpeng
 * @CreateDate: 2019-05-19 18:23
 * @Remark: Remark
 * @Version: 1.0
 */
public class JSONResult {

    /**
     * 状态码
     * 0    为成功
     * 500  为失败
     */
    private int code;

    /**
     * 返回信息
     */
    private String msg;

    public void SUCCESS(){
        this.code = 0;
        this.msg = null;

    }
    public void SUCCESS(String msg){
        this.code = 0;
        this.msg = msg;
    }
    public void ERROR(){
        this.code = 500;
        this.msg = null;
    }
    public void ERROR(String msg){
        this.code = 500;
        this.msg = msg;
    }

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

    @Override
    public String toString() {
        return "JSONResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
