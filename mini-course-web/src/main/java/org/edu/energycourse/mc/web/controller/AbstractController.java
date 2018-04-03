package org.edu.energycourse.mc.web.controller;

import org.edu.energycourse.mc.common.controller.BaseController;
import org.springframework.boot.web.servlet.error.ErrorController;

import java.util.Optional;

public abstract class AbstractController extends BaseController implements ErrorController
{
    private static final String ERROR_PATH = "/error";
    private static String MODULE_PATH = "modules";

    public String getErrorPath()
    {
        return ERROR_PATH;
    }

    protected String getModulePath()
    {
        return getMyModulePath() != null ? String.format("%s/%s", MODULE_PATH, getMyModulePath()) : MODULE_PATH;
    }

    protected abstract String getMyModulePath();
}
