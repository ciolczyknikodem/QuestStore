package com.example.queststore.controllers.web;

import com.example.queststore.dao.UserDAO;
import com.sun.net.httpserver.HttpServer;
import com.example.queststore.data.sessions.SessionDAO;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {

    private UserDAO userDAO;
    private SessionDAO sessionDAO;

    public WebServer(UserDAO userDAO, SessionDAO sessionDAO) {
        this.userDAO = userDAO;
        this.sessionDAO = sessionDAO;
    }

    public void start() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(7000), 0);
        server.createContext("/register", new RegistrationHandler(userDAO));
        server.createContext("/login", new LoginHandler(userDAO, sessionDAO));
        server.createContext("/static", new StaticHandler());
        server.createContext("/admin", new AdminHandler());
        server.createContext("/mentor", new MentorHandler());
        server.setExecutor(null);
        server.start();
    }
}