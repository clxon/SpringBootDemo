package me.lemon.boot.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorPageController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("error")
    public String handleError(HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        switch (status.intValue()) {
            case 401:
                return "/401";
            case 403:
                return "/403";
            case 404:
                return "/404";
            default:
                return "/500";
        }
    }
}
