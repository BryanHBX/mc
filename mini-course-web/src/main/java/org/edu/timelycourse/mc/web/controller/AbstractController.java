package org.edu.timelycourse.mc.web.controller;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import org.edu.timelycourse.mc.beans.dto.ContractDTO;
import org.edu.timelycourse.mc.beans.dto.InvoiceDTO;
import org.edu.timelycourse.mc.beans.dto.InvoiceStatDTO;
import org.edu.timelycourse.mc.beans.dto.StudentDTO;
import org.edu.timelycourse.mc.beans.model.*;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.edu.timelycourse.mc.common.constants.Constants;
import org.edu.timelycourse.mc.common.controller.BaseController;
import org.edu.timelycourse.mc.beans.entity.ResponseData;
import org.edu.timelycourse.mc.common.utils.ParameterizedTypeReferenceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @Override
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

    protected UserModel findMemberById (HttpServletRequest request, Integer memberId)
    {
        return remoteCall(request,"member/" + memberId, new TypeToken<UserModel>() {}).getData();
    }

    protected SchoolModel findSchool(HttpServletRequest request)
    {
        // TODO: Debug usage only
        Integer schoolId = 1;
        return remoteCall(request,"school/" + schoolId, new TypeToken<SchoolModel>() {}).getData();
    }

    protected SchoolProductModel findProductById (HttpServletRequest request, Integer productId)
    {
        return remoteCall(request,"product/" + productId, new TypeToken<SchoolProductModel>() {}).getData();
    }

    protected PagingBean<UserModel> getAllMembers (HttpServletRequest request,
                                                  Integer pageNum,
                                                  Integer pageSize)
    {
        return remoteCall(request, String.format("member?pageNum=%d&pageSize=%d&%s",
                pageNum != null ? pageNum : 1, pageSize != null ? pageSize : Constants.DEFAULT_PAGE_SIZE,
                request.getQueryString()), new TypeToken<PagingBean<UserModel>>() {}).getData();
    }

    protected PagingBean<StudentDTO> findStudentsByPage (HttpServletRequest request,
                                                        Integer pageNum,
                                                        Integer pageSize)
    {
        return remoteCall(request, String.format("student?pageNum=%d&pageSize=%d&%s",
                pageNum != null ? pageNum : 1, pageSize != null ? pageSize : Constants.DEFAULT_PAGE_SIZE,
                request.getQueryString()), new TypeToken<PagingBean<StudentDTO>>() {}).getData();
    }

    protected ContractModel fetchContract (HttpServletRequest request, Integer contractId)
    {
        return remoteCall(request,"contract/" + contractId, new TypeToken<ContractModel>() {}).getData();
    }

    protected PagingBean<ContractDTO> findContractsByPage (HttpServletRequest request,
                                                          Integer pageNum,
                                                          Integer pageSize)
    {
        return remoteCall(request, String.format("contract?pageNum=%d&pageSize=%d&%s",
                pageNum != null ? pageNum : 1, pageSize != null ? pageSize : Constants.DEFAULT_PAGE_SIZE,
                request.getQueryString()), new TypeToken<PagingBean<ContractDTO>>() {}).getData();
    }

    protected InvoiceStatDTO findInvoicesByPage (HttpServletRequest request,
                                                             Integer pageNum,
                                                             Integer pageSize)
    {
        return remoteCall(request, String.format("invoice?pageNum=%d&pageSize=%d&%s",
                pageNum != null ? pageNum : 1, pageSize != null ? pageSize : Constants.DEFAULT_PAGE_SIZE,
                request.getQueryString()), new TypeToken<InvoiceStatDTO>() {}).getData();
    }

    protected ContractDTO findContractById (HttpServletRequest request, Integer contractId)
    {
        return remoteCall(request,"contract/" + contractId, new TypeToken<ContractDTO>() {}).getData();
    }

    protected List<SchoolProductModel> getAllProducts (HttpServletRequest request)
    {
        return remoteCall(request,"product", new TypeToken<List<SchoolProductModel>>() {}).getData();
    }

    protected List<SchoolProductModel> findProductByType (HttpServletRequest request, Integer productType)
    {
        return remoteCall(request,"product?productType=" + productType, new TypeToken<List<SchoolProductModel>>() {}).getData();
    }

    protected SystemConfigModel findConfigByName(HttpServletRequest request, String configName)
    {
        return remoteCall(request,"system/config?configName=" + configName, new TypeToken<SystemConfigModel>() {}).getData();
    }

    protected List<SystemRoleModel> getAllSystemRoles(HttpServletRequest request)
    {
        return remoteCall(request,"system/role", new TypeToken<List<SystemRoleModel>>() {}).getData();
    }

    protected SystemConfigModel findConfigById (HttpServletRequest request, Integer configId)
    {
        return remoteCall(request,"system/config/" + configId, new TypeToken<SystemConfigModel>() {}).getData();
    }

    protected List<SystemConfigModel> getAllSystemConfigs(HttpServletRequest request)
    {
        return remoteCall(request,"system/config", new TypeToken<List<SystemConfigModel>>() {}).getData();
    }

    @Override
    protected String getModulePath()
    {
        return getMyModulePath() != null ? String.format("%s/%s", MODULE_PATH, getMyModulePath()) : MODULE_PATH;
    }

    @ModelAttribute("student")
    protected StudentModel getStudentModel () { return new StudentModel(); }

    protected abstract String getMyModulePath();

    protected abstract String getModuleName ();

    private String getRequestPath (String contextPath)
    {
        return String.format("%s/%s", API_HOST, contextPath);
    }
}
