package org.edu.timelycourse.mc.web.controller;

import org.edu.timelycourse.mc.beans.model.ContractModel;
import org.edu.timelycourse.mc.biz.security.JwtAuthenticationRequest;
import org.edu.timelycourse.mc.biz.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String showLogin()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof AnonymousAuthenticationToken ? getModulePage("public/login") : "forward:/";
    }

    @RequestMapping("/loginDialog")
    public String showLoginDialog(HttpServletRequest request, Model model)
    {
        return getModulePage("public/dialog/dialogLogin");
//        model.addAttribute(Constants.REDIRECT_URL_PARAMETER, request.getHeader("Referer"));
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return auth instanceof AnonymousAuthenticationToken ? getModulePage("logon") : getModulePage("index");
    }

    @Override
    protected String getMyModulePath()
    {
        return null;
    }

    @Override
    protected String getModuleName()
    {
        return "public";
    }
}
