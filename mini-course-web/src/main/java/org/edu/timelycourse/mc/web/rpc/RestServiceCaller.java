package org.edu.timelycourse.mc.web.rpc;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import org.edu.timelycourse.mc.beans.dto.ContractArrangementDTO;
import org.edu.timelycourse.mc.beans.dto.ContractDTO;
import org.edu.timelycourse.mc.beans.dto.ContractInvoiceStatDTO;
import org.edu.timelycourse.mc.beans.dto.StudentDTO;
import org.edu.timelycourse.mc.beans.entity.ResponseData;
import org.edu.timelycourse.mc.beans.model.*;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.common.constants.Constants;
import org.edu.timelycourse.mc.common.utils.ParameterizedTypeReferenceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by x36zhao on 2018/5/8.
 */
@Component
public final class RestServiceCaller
{
    @Value("${api.host}")
    protected String API_HOST;

    @Value("${jwt.header}")
    private String TOKEN_HEADER;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Send rest request to fetch response data
     *
     * @param request
     * @param contextPath
     * @param typeToken
     * @param <T>
     * @return
     */
    private <T> ResponseData<T> remoteCall (HttpServletRequest request, String contextPath, TypeToken<T> typeToken)
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

    private String getRequestPath (String contextPath)
    {
        return String.format("%s/%s", API_HOST, contextPath);
    }

    public UserModel findMemberById (HttpServletRequest request, Integer memberId)
    {
        return remoteCall(request,"member/" + memberId, new TypeToken<UserModel>() {}).getData();
    }

    public SchoolModel findSchool(HttpServletRequest request)
    {
        return remoteCall(request,"school/" + SecurityContextHelper.getSchoolIdFromPrincipal(), new TypeToken<SchoolModel>() {}).getData();
    }

    public SchoolProductModel findProductById (HttpServletRequest request, Integer productId)
    {
        return remoteCall(request,"product/" + productId, new TypeToken<SchoolProductModel>() {}).getData();
    }

    public PagingBean<UserModel> getAllMembers (HttpServletRequest request,
                                                Integer pageNum,
                                                Integer pageSize)
    {
        return remoteCall(request, String.format("member?pageNum=%d&pageSize=%d&%s",
                pageNum != null ? pageNum : 1, pageSize != null ? pageSize : Constants.DEFAULT_PAGE_SIZE,
                request.getQueryString()), new TypeToken<PagingBean<UserModel>>() {}).getData();
    }

    public PagingBean<StudentDTO> findStudentsByPage (HttpServletRequest request,
                                                      Integer pageNum,
                                                      Integer pageSize)
    {
        return remoteCall(request, String.format("student?pageNum=%d&pageSize=%d&%s",
                pageNum != null ? pageNum : 1, pageSize != null ? pageSize : Constants.DEFAULT_PAGE_SIZE,
                request.getQueryString()), new TypeToken<PagingBean<StudentDTO>>() {}).getData();
    }

    public ContractModel fetchContract (HttpServletRequest request, Integer contractId)
    {
        return remoteCall(request,"contract/" + contractId, new TypeToken<ContractModel>() {}).getData();
    }

    public PagingBean<ContractDTO> findContractsByPage (HttpServletRequest request,
                                                           Integer pageNum,
                                                           Integer pageSize)
    {
        return remoteCall(request, String.format("contract?pageNum=%d&pageSize=%d&%s",
                pageNum != null ? pageNum : 1, pageSize != null ? pageSize : Constants.DEFAULT_PAGE_SIZE,
                request.getQueryString()), new TypeToken<PagingBean<ContractDTO>>() {}).getData();
    }

    public ContractInvoiceStatDTO findInvoicesByPage (HttpServletRequest request,
                                                      Integer pageNum,
                                                      Integer pageSize)
    {
        return remoteCall(request, String.format("invoice?pageNum=%d&pageSize=%d&%s",
                pageNum != null ? pageNum : 1, pageSize != null ? pageSize : Constants.DEFAULT_PAGE_SIZE,
                request.getQueryString()), new TypeToken<ContractInvoiceStatDTO>() {}).getData();
    }

    public ContractDTO findContractById (HttpServletRequest request, Integer contractId)
    {
        return remoteCall(request,"contract/" + contractId, new TypeToken<ContractDTO>() {}).getData();
    }

    public List<SchoolProductModel> getAllProducts (HttpServletRequest request)
    {
        return remoteCall(request,"product", new TypeToken<List<SchoolProductModel>>() {}).getData();
    }

    public List<SchoolProductModel> findProductByType (HttpServletRequest request, Integer productType)
    {
        return remoteCall(request,"product?productType=" + productType, new TypeToken<List<SchoolProductModel>>() {}).getData();
    }

    public SystemConfigModel findConfigByName(HttpServletRequest request, String configName)
    {
        return remoteCall(request,"system/config?configName=" + configName, new TypeToken<SystemConfigModel>() {}).getData();
    }

    public List<SystemRoleModel> getAllSystemRoles(HttpServletRequest request)
    {
        return remoteCall(request,"system/role", new TypeToken<List<SystemRoleModel>>() {}).getData();
    }

    public SystemConfigModel findConfigById (HttpServletRequest request, Integer configId)
    {
        return remoteCall(request,"system/config/" + configId, new TypeToken<SystemConfigModel>() {}).getData();
    }

    public List<SystemConfigModel> getAllSystemConfigs(HttpServletRequest request)
    {
        return remoteCall(request,"system/config", new TypeToken<List<SystemConfigModel>>() {}).getData();
    }

    public List<ContractArrangementDTO> findArrangementByContract (HttpServletRequest request, Integer contractId)
    {
        return remoteCall(request, String.format("contract/%d/arrangement", contractId),
                new TypeToken<List<ContractArrangementDTO>>() {}).getData();
    }
}
