package org.edu.energycourse.mc.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublicController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicController.class);

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

    protected String getMyModulePath()
    {
        return null;
    }
}
