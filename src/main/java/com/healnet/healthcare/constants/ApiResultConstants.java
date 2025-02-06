package com.healnet.healthcare.constants;

import com.healnet.healthcare.dto.ApiResult;


public class ApiResultConstants {

    public static final ApiResult SUCCESS = new ApiResult(ApiStatus.SUCCESS,"Request Processed Successfully");
    public static final ApiResult FAILURE = new ApiResult(ApiStatus.FAILURE,"failed to Process the request");
    public static final ApiResult INTERNAL_SERVER_ERROR = new ApiResult(ApiStatus.FAILURE,"Internal Server error");

    private ApiResultConstants(){}

}
