package com.xiaowu.crowd.util;

//统一整个项目中Ajax请求返回的结果（也可以用于分布式架构中各个模块间调用时统一返回的类型）

public class ResultEntity<T> {

    private static final String SUCCESS = "SUCCESS";

    private static final String FAILED = "FAILED";

//    用来封装当前请求处理的结果是成功还是失败
    private String result;

//    请求处理失败时返回的错误消息
    private String message;

//    要返回的数据
    private T data;

    public ResultEntity(){}

//    返回成功不需要数据的
    public static <Type> ResultEntity<Type> successWithoutData(){
        return new ResultEntity<Type>(SUCCESS,null,null);
    }

//    返回成功需要带数据的
    public static <Type> ResultEntity<Type> successWithData(Type data){
        return new ResultEntity<Type>(SUCCESS,null,data);
    }

//    返回请求失败的消息
    public static <Type> ResultEntity<Type> failed(String message){
        return new ResultEntity<Type>(FAILED,message,null);
    }



    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
