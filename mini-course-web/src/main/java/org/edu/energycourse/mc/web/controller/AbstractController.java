package org.edu.energycourse.mc.web.controller;

import org.edu.energycourse.mc.common.controller.BaseController;
import org.springframework.boot.web.servlet.error.ErrorController;

public abstract class AbstractController extends BaseController implements ErrorController
{
    private static final String ERROR_PATH = "/error";

    public String getErrorPath()
    {
        return ERROR_PATH;
    }
}
