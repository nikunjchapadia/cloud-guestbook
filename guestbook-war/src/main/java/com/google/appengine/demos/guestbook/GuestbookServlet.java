package com.google.appengine.demos.guestbook;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.Override;
import java.lang.System;
import java.util.Properties;
import java.io.IOException;

public class GuestbookServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        System.out.println("do get ....");
        if (request.getParameter("testing") == null) {
            response.setContentType("text/plain");
            response.getWriter().println("Hello this is testing servlet \n\n");
            Properties p = System.getProperties();
            p.list(response.getWriter());
        } else {
            UserService userService = UserServiceFactory.getUserService();
            User currentUser = null;
            if (userService != null) {
                currentUser = userService.getCurrentUser();
            }
            if (userService != null && currentUser != null) {
                response.setContentType("text/plain");
                response.getWriter().println("Hello, " + currentUser.getNickname());
            } else {
                response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
            }
        }
    }

}