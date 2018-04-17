package org.edu.timelycourse.mc.web.controller;

import com.google.common.reflect.TypeToken;
import org.edu.timelycourse.mc.biz.enums.EBuiltInConfig;
import org.edu.timelycourse.mc.biz.model.SchoolProductModel;
import org.edu.timelycourse.mc.biz.model.SystemConfigModel;
import org.edu.timelycourse.mc.biz.model.SystemRoleModel;
import org.edu.timelycourse.mc.biz.model.UserModel;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/school")
public class SchoolController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolController.class);

    @RequestMapping("/info")
    public String showSchoolInfo (Model model, HttpServletRequest request)
    {
        model.addAttribute("school", fetchSchool(request));
        return getModulePage("schoolInfo");
    }

    @RequestMapping("/product")
    public String showSchoolProduct (Model model,
                                     @RequestParam(required = false, value = "id") Integer productId,
                                     HttpServletRequest request)
    {
        if (productId != null)
        {
            if (EntityUtils.isValidEntityId(productId))
            {
                model.addAttribute("product", fetchProduct(request, productId));
            }

            return getModulePage("pages/productListPage");
        }
        else
        {
            SystemConfigModel courseType = fetchConfigByName(request, EBuiltInConfig.C_COURSE_TYPE.name());
            if (courseType != null && courseType.getChildren() != null)
            {
                Map<SystemConfigModel, List<SchoolProductModel>> products = new HashMap<>();
                for (SystemConfigModel subType : courseType.getChildren())
                {
                    products.put(subType, fetchProductByType(request, subType.getId()));
                }

                model.addAttribute("products", products);
            }
        }

        return getModulePage("schoolProduct");
    }

    @RequestMapping("/product/dialog")
    public String showSchoolProductDialog (Model model,
                                           @RequestParam(required = false, value = "id")  Integer id,
                                           @RequestParam(required = false, value = "pid") Integer parentId,
                                           HttpServletRequest request)
    {
        if (EntityUtils.isValidEntityId(id))
        {
            model.addAttribute("product", fetchProduct(request, id));
        }

        if (EntityUtils.isValidEntityId(parentId))
        {
            model.addAttribute("parentId", parentId);
            model.addAttribute("parent", fetchProduct(request, parentId));
        }
        else
        {
            model.addAttribute("types", fetchConfigByName(request, EBuiltInConfig.C_COURSE_TYPE.name()));
        }

        return getModulePage("dialog/dialogSchoolProduct");
    }

    @RequestMapping("/member")
    public String showSchoolMember (Model model,
                                    @RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer numPerPage,
                                    @RequestParam(required = false) String userName,
                                    HttpServletRequest request)
    {
        UserModel criteria = new UserModel();
        criteria.setSchoolId(SecurityContextHelper.getSchoolIdFromPrincipal());
        criteria.setUserName(userName);

        model.addAttribute("pagingBean", fetchMembers(request, pageNum, numPerPage, criteria));
        model.addAttribute("search", criteria);
        return getModulePage("schoolMember");
    }

    @RequestMapping("/member/dialog")
    public String showSchoolMemberFormDialog (Model model,
                                              @RequestParam(required = false, value = "id")  Integer memberId,
                                              HttpServletRequest request)
    {
        if (EntityUtils.isValidEntityId(memberId))
        {
            model.addAttribute("member", fetchMemberById(request, memberId));
        }

        model.addAttribute("types", fetchProducts(request));
        model.addAttribute("grades", fetchConfigByName(request, EBuiltInConfig.C_GRADE.name()));
        model.addAttribute("subjects", fetchConfigByName(request, EBuiltInConfig.C_SUBJECT.name()));
        model.addAttribute("roles", fetchSystemRoles(request));

        return getModulePage("dialog/dialogSchoolMember");
    }

    @RequestMapping("/member/pwd")
    public String showSchoolMemberResetPasswordDialog (Model model,
                                                       @RequestParam(required = true, value = "id")  Integer memberId)
    {
        model.addAttribute("id", memberId);
        return getModulePage("dialog/dialogResetPassword");
    }


    @RequestMapping("/consult")
    public String showSchoolConsult ()
    {
        return getModulePage("schoolConsult");
    }

    protected String getMyModulePath()
    {
        return "school";
    }

}
