package com.moons.paramcheck.requestParam;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class ResponseVOAL {
    private Integer returnCode;
    private String  returnMessage;
    private JSONObject errorMessages;

    public ResponseVOAL() {
    }

    public ResponseVOAL(Integer returnCode, String returnMessage, JSONObject errorMessages) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
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

    public JSONObject getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(JSONObject errorMessages) {
        this.errorMessages = errorMessages;
    }
}
