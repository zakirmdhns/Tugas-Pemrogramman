package com.unpam.view;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MainForm {

    public void tampilkan(String konten, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);

        String menu =
            "<br><b>Master Data</b><br>" +
            "<a href='KaryawanController'>Karyawan</a><br>" +
            "<a href='PekerjaanController'>Pekerjaan</a><br>" +
            "<b>Transaksi</b><br>" +
            "<a href='GajiController'>Gaji</a><br>" +
            "<b>Laporan</b><br>" +
            "<a href='LaporanGajiController'>Gaji</a><br>" +
            "<a href='LogoutController'>Logout</a><br><br>";

        String topMenu =
            "<nav><ul>" +
            "<li><a href='./'>Home</a></li>" +
            "<li><a href='#'>Master Data</a><ul>" +
            "<li><a href='KaryawanController'>Karyawan</a></li>" +
            "<li><a href='PekerjaanController'>Pekerjaan</a></li>" +
            "</ul></li>" +
            "<li><a href='#'>Transaksi</a><ul>" +
            "<li><a href='GajiController'>Gaji</a></li>" +
            "</ul></li>" +
            "<li><a href='#'>Laporan</a><ul>" +
            "<li><a href='LaporanGajiController'>Gaji</a></li>" +
            "</ul></li>" +
            "<li><a href='LogoutController'>Logout</a></li>" +
            "</ul></nav>";

        if (!session.isNew()) {
            if (session.getAttribute("menu") != null)
                menu = session.getAttribute("menu").toString();

            if (session.getAttribute("topMenu") != null)
                topMenu = session.getAttribute("topMenu").toString();
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<link href='style.css' rel='stylesheet'>");
            out.println("<title>Informasi Gaji Karyawan</title>");
            out.println("</head><body bgcolor='#808080'>");

            out.println("<center><table width='80%' bgcolor='#eeeeee'>");

            out.println("<tr><td colspan='2' align='center'>");
            out.println("<h2>Informasi Gaji Karyawan</h2>");
            out.println("<h1>PT Sintory</h1>");
            out.println("<h4>Jl. Surya Kencana No. 99 Pamulang</h4>");
            out.println("</td></tr>");

            out.println("<tr height='400'>");
            out.println("<td width='20%' valign='top' bgcolor='#eeeeff'>");
            out.println("<b>Menu</b>" + menu);
            out.println("</td>");

            out.println("<td valign='top' bgcolor='#ffffff'>");
            out.println(topMenu + "<br>" + konten);
            out.println("</td></tr>");

            out.println("<tr><td colspan='2' align='center' bgcolor='#eeeeff'>");
            out.println("<small>Copyright &copy; 2017 PT Sintory</small>");
            out.println("</td></tr>");

            out.println("</table></center></body></html>");
        }
    }
}