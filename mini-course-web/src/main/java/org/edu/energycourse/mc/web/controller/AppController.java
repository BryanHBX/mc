package org.edu.energycourse.mc.web.controller;

import org.edu.energycourse.mc.common.constants.Constants;
import org.edu.energycourse.mc.common.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController extends BaseController implements ErrorController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    private static final String ERROR_PATH = "/error";

    @RequestMapping("/")
    public String home()
    {
        return getModulePage("index");
    }

    @RequestMapping("/login")
    public String showLoginPage(HttpServletRequest request, Model model)
    {
        return getModulePage("index");
//        model.addAttribute(Constants.REDIRECT_URL_PARAMETER, request.getHeader("Referer"));
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return auth instanceof AnonymousAuthenticationToken ? getModulePage("logon") : getModulePage("index");
    }

    protected String getModulePath()
    {
        return "modules";
    }

    public String getErrorPath()
    {
        return ERROR_PATH;
    }
}
