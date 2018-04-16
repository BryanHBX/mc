package org.edu.timelycourse.mc.web.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorResponseData implements Serializable
{
    private static final String DEFAULT_CALLBACK_TYPE = "closeCurrentNavTab";

    private String statusCode;
    private String message;
    private String navTabId;
    private String rel;
    private String callbackType;
    private String forwardUrl;
    private String confirmMsg;

    public static ErrorResponseData create (int statusCode, String message)
    {
        ErrorResponseData responseData = new ErrorResponseData();
        responseData.setStatusCode(String.valueOf(statusCode));
        responseData.setMessage(message);
        responseData.setCallbackType(DEFAULT_CALLBACK_TYPE);
        return responseData;
    }
}
