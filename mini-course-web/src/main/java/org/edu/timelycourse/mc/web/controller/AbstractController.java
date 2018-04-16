package org.edu.timelycourse.mc.web.controller;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import org.edu.timelycourse.mc.biz.model.*;
import org.edu.timelycourse.mc.biz.paging.PagingBean;
import org.edu.timelycourse.mc.common.constants.Constants;
import org.edu.timelycourse.mc.common.controller.BaseController;
import org.edu.timelycourse.mc.common.entity.ResponseData;
import org.edu.timelycourse.mc.common.exception.AuthenticationException;
import org.edu.timelycourse.mc.common.reflect.ParameterizedTypeReferenceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class AbstractController extends BaseController implements ErrorController
{
    private static final String ERROR_PATH = "/error";
    private static final String MODULE_PATH = "modules";

    @Value("${api.host}")
    protected String API_HOST;

    @Value("${jwt.header}")
    private String TOKEN_HEADER;

    @Autowired
    private RestTemplate restTemplate;

    public String getErrorPath()
    {
        return ERROR_PATH;
    }

    protected <T> ResponseData<T> remoteCall (HttpServletRequest request, String contextPath, TypeToken<T> typeToken)
    {
        HttpHeaders requestHeaders = new HttpHeaders();
        String tokenHeader = request.getHeader(TOKEN_HEADER);
        if (tokenHeader != null)
        {
            requestHeaders.add(TOKEN_HEADER, tokenHeader);
        }

        ResponseEntity<ResponseData<T>> result = restTemplate.exchange(
                getRequestPath(contextPath),
                HttpMethod.GET,
                new HttpEntity<String>(null, requestHeaders),
                ParameterizedTypeReferenceBuilder.fromTypeToken(
                        new TypeToken<ResponseData<T>>() {}
                                .where(new TypeParameter<T>() {}, typeToken)
                )
        );

        return result.getBody();

    }

    protected UserModel fetchMemberById (HttpServletRequest request, Integer memberId)
    {
        return remoteCall(request,"member/" + memberId, new TypeToken<UserModel>() {}).getData();
    }

    protected SchoolModel fetchSchool (HttpServletRequest request)
    {
        // TODO: Debug usage only
        Integer schoolId = 1;
        return remoteCall(request,"school/" + schoolId, new TypeToken<SchoolModel>() {}).getData();
    }

    protected SchoolProductModel fetchProduct (HttpServletRequest request, Integer productId)
    {
        return remoteCall(request,"product/" + productId, new TypeToken<SchoolProductModel>() {}).getData();
    }

    protected PagingBean<UserModel> fetchMembers (HttpServletRequest request, Integer pageNum, Integer pageSize, final UserModel model)
    {
        return remoteCall(request, String.format("member?pageNum=%d&pageSize=%d&%s",
                pageNum != null ? pageNum : 1, pageSize != null ? pageSize : Constants.DEFAULT_PAGE_SIZE,
                model.getUrlParams()),
                new TypeToken<PagingBean<UserModel>>() {}).getData();
    }

    protected List<SchoolProductModel> fetchProducts (HttpServletRequest request)
    {
        return remoteCall(request,"product", new TypeToken<List<SchoolProductModel>>() {}).getData();
    }

    protected List<SchoolProductModel> fetchProductByType (HttpServletRequest request, Integer productType)
    {
        return remoteCall(request,"product?productType=" + productType, new TypeToken<List<SchoolProductModel>>() {}).getData();
    }

    protected SystemConfigModel fetchConfigByName (HttpServletRequest request, String configName)
    {
        return remoteCall(request,"system/config?configName=" + configName, new TypeToken<SystemConfigModel>() {}).getData();
    }

    protected List<SystemRoleModel> fetchSystemRoles (HttpServletRequest request)
    {
        return remoteCall(request,"system/role", new TypeToken<List<SystemRoleModel>>() {}).getData();
    }

    protected SystemConfigModel fetchConfigById (HttpServletRequest request, Integer configId)
    {
        return remoteCall(request,"system/config/" + configId, new TypeToken<SystemConfigModel>() {}).getData();
    }

    protected List<SystemConfigModel> fetchConfigs (HttpServletRequest request)
    {
        return remoteCall(request,"system/config", new TypeToken<List<SystemConfigModel>>() {}).getData();
    }

    protected String getModulePath()
    {
        return getMyModulePath() != null ? String.format("%s/%s", MODULE_PATH, getMyModulePath()) : MODULE_PATH;
    }

    protected abstract String getMyModulePath();

    private String getRequestPath (String contextPath)
    {
        return String.format("%s/%s", API_HOST, contextPath);
    }
}
