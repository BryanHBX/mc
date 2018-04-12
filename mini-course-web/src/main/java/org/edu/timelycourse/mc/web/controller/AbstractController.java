package org.edu.timelycourse.mc.web.controller;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import org.edu.timelycourse.mc.biz.model.SystemConfigModel;
import org.edu.timelycourse.mc.biz.model.SystemRoleModel;
import org.edu.timelycourse.mc.biz.model.UserModel;
import org.edu.timelycourse.mc.common.controller.BaseController;
import org.edu.timelycourse.mc.common.entity.ResponseData;
import org.edu.timelycourse.mc.common.reflect.ParameterizedTypeReferenceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public abstract class AbstractController extends BaseController implements ErrorController
{
    @Value("${api.host}")
    protected String API_HOST;

    @Autowired
    private RestTemplate restTemplate;

    private static final String ERROR_PATH = "/error";
    private static String MODULE_PATH = "modules";

    public String getErrorPath()
    {
        return ERROR_PATH;
    }

    protected <T> ResponseData<T> remoteCall (String contextPath, TypeToken<T> typeToken)
    {
        ResponseEntity<ResponseData<T>> result = restTemplate.exchange(
                getRequestPath(contextPath),
                HttpMethod.GET,
                null,
                ParameterizedTypeReferenceBuilder.fromTypeToken(
                        new TypeToken<ResponseData<T>>() {}
                        .where(new TypeParameter<T>() {}, typeToken)
                )
        );

        return result.getBody();
    }

    protected UserModel fetchMemberById (Integer memberId)
    {
        return remoteCall("member/" + memberId, new TypeToken<UserModel>() {}).getData();
    }

    protected SystemConfigModel fetchConfigByName (String configName)
    {
        return remoteCall("system/config?configName=" + configName, new TypeToken<SystemConfigModel>() {}).getData();
    }

    protected List<SystemRoleModel> fetchSystemRoles ()
    {
        return remoteCall("system/role", new TypeToken<List<SystemRoleModel>>() {}).getData();
    }

    protected SystemConfigModel fetchConfigById (Integer configId)
    {
        return remoteCall("system/config/" + configId, new TypeToken<SystemConfigModel>() {}).getData();
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
