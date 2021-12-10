package com.uni.project.advices;

import com.uni.project.services.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = RestController.class)
public class MvcAdvice {
    @Autowired(required=true)
    private HttpServletRequest request;

    @ModelAttribute("isLogged")
    public Boolean isLogged() { return SessionManager.IsLogged(request); }

    @ModelAttribute("isAdmin")
    public Boolean isAdmin() { return SessionManager.IsAdmin(request); }
}
