package com.unpam.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "LogoutController", urlPatterns = {"/LogoutController"})
public class LogoutController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        response.sendRedirect("LoginController");
    }

    @Override protected void doGet(HttpServletRequest r, HttpServletResponse s)
            throws ServletException, IOException { processRequest(r,s); }
    @Override protected void doPost(HttpServletRequest r, HttpServletResponse s)
            throws ServletException, IOException { processRequest(r,s); }
}