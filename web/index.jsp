<%-- 
    Document   : index
    Created on : Nov 19, 2025, 9:30:17 AM
    Author     : zaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="style.css" rel="stylesheet" type="text/css" />
    <title>Informasi Gaji Karyawan</title>
</head>

<body bgcolor="#808080">

<%
    String menu =
        "<ul>"
        + "<li><b>Master Data</b></li>"
        + "<li><a href='Karyawan'>Karyawan</a></li>"
        + "<li><a href='Pekerjaan'>Pekerjaan</a></li>"
        + "<li><b>Transaksi</b></li>"
        + "<li><a href='Gaji'>Gaji</a></li>"
        + "<li><b>Laporan</b></li>"
        + "<li><a href='LaporanGaji'>Gaji</a></li>"
        + "<li><a href='LoginController'>Login</a></li>"
        + "</ul>";

    String topMenu =
        "<nav>"
        + " <ul>"
        + "   <li><a href='index.jsp'>Home</a></li>"
        + "   <li><a href='#'>Master Data</a>"
        + "       <ul>"
        + "         <li><a href='Karyawan'>Karyawan</a></li>"
        + "         <li><a href='Pekerjaan'>Pekerjaan</a></li>"
        + "       </ul>"
        + "   </li>"
        + "   <li><a href='#'>Transaksi</a>"
        + "       <ul>"
        + "         <li><a href='Gaji'>Gaji</a></li>"
        + "       </ul>"
        + "   </li>"
        + "   <li><a href='#'>Laporan</a>"
        + "       <ul>"
        + "         <li><a href='LaporanGaji'>Gaji</a></li>"
        + "       </ul>"
        + "   </li>"
        + "   <li><a href='LoginController'>Login</a></li>"
        + " </ul>"
        + "</nav>";

    String konten =
    "<div style='margin-top:20px;'>" +
    "<h1>Selamat Datang di Sistem Penjualan</h1>" +
    "<h2>Toko Dagang Zaki</h2>" +
    
    "<div style='margin:20px auto; width:80%; background:#f7f7f7; padding:20px; " +
    "border-radius:10px; box-shadow:0px 0px 10px rgba(0,0,0,0.1);'>" +
    
    "<p style='font-size:16px;'>belum ada barangnya ya sabar lagi dikirim dari china" +
    "</div>";
%>

<center>
<table width="80%" bgcolor="#eeeeee">
    <tr>
        <td colspan="2" align="center">
            <br>
            <h2 style="margin:0;">Informasi Gaji karyawan</h2>
            <h1 style="margin:0;">PT Sintory</h1>
            <h4 style="margin:0;">Jl. Surya Kencana No. 99 Pamulang, Tangerang Selatan, Banten</h4>
            <br>
        </td>
    </tr>

    <tr height="400">
        <!-- MENU KIRI -->
        <td width="200" align="center" valign="top" bgcolor="#eeeeee">
            <br>
            <div id="menu">
                <%=menu%>
            </div>
        </td>

        <!-- KONTEN -->
        <td align="center" valign="top" bgcolor="#ffffff">
            <%=topMenu%>
            <br>
            <%=konten%>
        </td>
    </tr>

    <tr>
        <td colspan="2" align="center" bgcolor="#eeeeff">
            <small>
                Copyright © 2017 PT Sintory<br>
                Jl. Surya Kencana No. 99 Pamulang, Tangerang Selatan, Banten
            </small>
        </td>
    </tr>
</table>
</center>

</body>
</html>