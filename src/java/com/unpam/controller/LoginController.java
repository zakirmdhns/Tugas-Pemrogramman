package com.unpam.controller;

import com.unpam.model.Enkripsi;
import com.unpam.model.Karyawan;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("LOGIN") != null) {
            response.sendRedirect("KaryawanController");
            return;
        }

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        String form =
            "<form method='post'>" +
            "<table>" +
            "<tr><td>User ID</td><td><input name='userId'></td></tr>" +
            "<tr><td>Password</td><td><input type='password' name='password'></td></tr>" +
            "<tr><td colspan='2' align='center'><input type='submit' value='Login'></td></tr>" +
            "</table></form>";

        String pesan = "";

        if (userId != null) {
            Karyawan k = new Karyawan();
            Enkripsi e = new Enkripsi();

            if (k.baca(userId)) {
                try {
                    if (e.hashMD5(password).equals(k.getPassword())) {
                        session = request.getSession(true);
                        session.setAttribute("LOGIN", true);
                        session.setAttribute("USER_NAME", k.getNama());
                        response.sendRedirect("KaryawanController");
                        return;
                    }
                } catch (Exception ex) {}
            }
            pesan = "<br><b style='color:red'>Login gagal</b>";
        }

        new MainForm().tampilkan(form + pesan, request, response);
    }

    @Override protected void doGet(HttpServletRequest r, HttpServletResponse s)
            throws ServletException, IOException { processRequest(r,s); }
    @Override protected void doPost(HttpServletRequest r, HttpServletResponse s)
            throws ServletException, IOException { processRequest(r,s); }
}