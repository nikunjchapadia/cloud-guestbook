package com.google.appengine.demos.guestbook;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class SignGuestbookServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        String guestbookName = req.getParameter("guestbookName");
        Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
        String content = req.getParameter("content");
        Date date = new Date();
        Entity greeting = new Entity("Greeting", guestbookKey);
        greeting.setProperty("user", user);
        greeting.setProperty("date", date);
        greeting.setProperty("content", content);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(greeting);

        resp.sendRedirect("/guestbook.jsp?guestbookName=" + guestbookName);
    }
}