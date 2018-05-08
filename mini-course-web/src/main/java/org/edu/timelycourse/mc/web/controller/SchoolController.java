package org.edu.timelycourse.mc.web.controller;

import org.edu.timelycourse.mc.beans.enums.EBuiltInConfig;
import org.edu.timelycourse.mc.beans.model.UserModel;
import org.edu.timelycourse.mc.biz.utils.SecurityContextHelper;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/school")
public class SchoolController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolController.class);

    @RequestMapping("/info")
    public String showSchoolInfo (Model model, HttpServletRequest request)
    {
        model.addAttribute("school", restServiceCaller.findSchool(request));
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
                model.addAttribute("product", restServiceCaller.findProductById(request, productId));
            }

            return getModulePage("pages/productListPage");
        }
        else
        {
            model.addAttribute("products", restServiceCaller.getAllProducts(request));

            /*
            SystemConfigModel courseType = findConfigByName(request, EBuiltInConfig.C_COURSE_TYPE.name());
            if (courseType != null && courseType.getChildren() != null)
            {
                Map<SystemConfigModel, List<SchoolProductModel>> products = new HashMap<>();
                for (SystemConfigModel subType : courseType.getChildren())
                {
                    products.put(subType, findProductByType(request, subType.getId()));
                }

                model.addAttribute("products", products);
            }
            */
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
            model.addAttribute("product", restServiceCaller.findProductById(request, id));
        }

        if (EntityUtils.isValidEntityId(parentId))
        {
            model.addAttribute("parentId", parentId);
            model.addAttribute("parent", restServiceCaller.findProductById(request, parentId));
        }
        else
        {
            model.addAttribute("types", restServiceCaller.findConfigByName(request, EBuiltInConfig.C_COURSE_TYPE.name()));
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

        model.addAttribute("pagingBean", restServiceCaller.getAllMembers(request, pageNum, numPerPage));
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
            model.addAttribute("member", restServiceCaller.findMemberById(request, memberId));
        }

        model.addAttribute("types", restServiceCaller.getAllProducts(request));
        model.addAttribute("grades", restServiceCaller.findConfigByName(request, EBuiltInConfig.C_GRADE.name()));
        model.addAttribute("subjects", restServiceCaller.findConfigByName(request, EBuiltInConfig.C_SUBJECT.name()));
        model.addAttribute("roles", restServiceCaller.getAllSystemRoles(request));

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

    @Override
    protected String getModuleName()
    {
        return "school";
    }

    protected String getMyModulePath()
    {
        return "school";
    }

}
