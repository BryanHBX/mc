package org.edu.timelycourse.mc.web.controller;

import com.google.common.reflect.TypeToken;
import org.edu.timelycourse.mc.biz.enums.EBuiltInConfig;
import org.edu.timelycourse.mc.biz.model.SchoolProductModel;
import org.edu.timelycourse.mc.biz.model.SystemConfigModel;
import org.edu.timelycourse.mc.biz.model.SystemRoleModel;
import org.edu.timelycourse.mc.biz.model.UserModel;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String showSchoolInfo ()
    {
        return getModulePage("schoolInfo");
    }

    @RequestMapping("/product")
    public String showSchoolProduct (
            Model model, @RequestParam(required = false, value = "id") Integer productId)
    {
        if (productId != null)
        {
            if (EntityUtils.isValidEntityId(productId))
            {
                model.addAttribute("product", fetchProduct(productId));
            }

            return getModulePage("pages/productListPage");
        }
        else
        {
            SystemConfigModel courseType = fetchConfigByName(EBuiltInConfig.C_COURSE_TYPE.name());
            if (courseType != null && courseType.getChildren() != null)
            {
                Map<SystemConfigModel, List<SchoolProductModel>> products = new HashMap<>();
                for (SystemConfigModel subType : courseType.getChildren())
                {
                    products.put(subType, fetchProductByType(subType.getId()));
                }

                model.addAttribute("products", products);
            }
        }

        return getModulePage("schoolProduct");
    }

    @RequestMapping("/product/dialog")
    public String showSchoolProductDialog (
            Model model,
            @RequestParam(required = false, value = "id")  Integer id,
            @RequestParam(required = false, value = "pid") Integer parentId)
    {
        if (EntityUtils.isValidEntityId(id))
        {
            model.addAttribute("product", fetchProduct(id));
        }

        if (EntityUtils.isValidEntityId(parentId))
        {
            model.addAttribute("parentId", parentId);
            model.addAttribute("parent", fetchProduct(parentId));
        }
        else
        {
            model.addAttribute("types", fetchConfigByName(EBuiltInConfig.C_COURSE_TYPE.name()));
        }

        return getModulePage("dialog/dialogSchoolProduct");
    }

    @RequestMapping("/member")
    public String showSchoolMember ()
    {
        return getModulePage("schoolMember");
    }

    @RequestMapping("/member/dialog")
    public String showSchoolMemberDialog (
            Model model, @RequestParam(required = false, value = "id")  Integer memberId)
    {
        if (EntityUtils.isValidEntityId(memberId))
        {
            model.addAttribute("member", fetchMemberById(memberId));
        }

        model.addAttribute("grades", fetchConfigByName(EBuiltInConfig.C_GRADE.name()));
        model.addAttribute("subjects", fetchConfigByName(EBuiltInConfig.C_SUBJECT.name()));
        model.addAttribute("roles", fetchSystemRoles());

        return getModulePage("dialog/dialogSchoolMember");
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
