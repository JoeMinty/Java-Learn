package com.moons.paramcheck.requestParam;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class ResponseVO {
    //return new ResponseVO(HttpStatus.OK.value(), "bodyPost", null);
    private Integer returnCode;
    private String  returnMessage;
    private List<String> errorMessages;


    public ResponseVO() {
    }

    public ResponseVO(String returnMessage, List<String> errorMessages) {
        this.returnMessage = returnMessage;
        this.errorMessages = errorMessages;
    }

    public ResponseVO(Integer returnCode, String returnMessage, List<String> errorMessages) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.errorMessages = errorMessages;
    }


    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}
