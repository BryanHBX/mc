package org.edu.timelycourse.mc.web.controller;

import org.edu.timelycourse.mc.beans.model.*;
import org.edu.timelycourse.mc.common.controller.BaseController;
import org.edu.timelycourse.mc.web.rpc.RestServiceCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AbstractController extends BaseController implements ErrorController
{
    private static final String ERROR_PATH = "/error";
    private static final String MODULE_PATH = "modules";

    @Autowired
    protected RestServiceCaller restServiceCaller;

    @Override
    protected String getModulePath()
    {
        return getMyModulePath() != null ? String.format("%s/%s", MODULE_PATH, getMyModulePath()) : MODULE_PATH;
    }

    @Override
    public String getErrorPath()
    {
        return ERROR_PATH;
    }

    @ModelAttribute("student")
    protected StudentModel getStudentModel () { return new StudentModel(); }

    protected abstract String getMyModulePath();

    protected abstract String getModuleName ();
}
