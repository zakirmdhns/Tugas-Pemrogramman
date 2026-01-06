/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java
 */
package com.unpam.controller;

import com.unpam.model.Gaji;
import com.unpam.model.Karyawan;
import com.unpam.model.Pekerjaan;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "GajiController", urlPatterns = {"/GajiController"})
public class GajiController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN") == null) {
            response.sendRedirect("LoginController");
            return;
        }

        Karyawan karyawan = new Karyawan();
        Pekerjaan pekerjaan = new Pekerjaan();
        Gaji gaji = new Gaji();
        String userName = "";

        String tombol = request.getParameter("tombol");
        String tombolKaryawan = request.getParameter("tombolKaryawan");
        String ktp = request.getParameter("ktp");
        String namaKaryawan = request.getParameter("namaKaryawan");
        String ruang = request.getParameter("ruang");
        String mulaiParameter = request.getParameter("mulai");
        String jumlahParameter = request.getParameter("jumlah");
        String ktpDipilih = request.getParameter("ktpDipilih");
        String tombolPekerjaan = request.getParameter("tombolPekerjaan");
        String kodePekerjaan = request.getParameter("kodePekerjaan");
        String namaPekerjaan = request.getParameter("namaPekerjaan");
        String jumlahTugas = request.getParameter("jumlahTugas");
        String kodePekerjaanDipilih = request.getParameter("kodePekerjaanDipilih");
        String gajibersih = request.getParameter("gajibersih");
        String gajikotor = request.getParameter("gajikotor");
        String tunjangan = request.getParameter("tunjangan");

        if (tombol == null) tombol = "";
        if (tombolKaryawan == null) tombolKaryawan = "";
        if (ktp == null) ktp = "";
        if (namaKaryawan == null) namaKaryawan = "";
        if (ruang == null) ruang = "";
        if (ktpDipilih == null) ktpDipilih = "";
        if (tombolPekerjaan == null) tombolPekerjaan = "";
        if (kodePekerjaan == null) kodePekerjaan = "";
        if (namaPekerjaan == null) namaPekerjaan = "";
        if (jumlahTugas == null) jumlahTugas = "";
        if (kodePekerjaanDipilih == null) kodePekerjaanDipilih = "";
        if (gajibersih == null) gajibersih = "";
        if (gajikotor == null) gajikotor = "";
        if (tunjangan == null) tunjangan = "";

        int mulai = 0, jumlah = 10;

        try { mulai = Integer.parseInt(mulaiParameter); } catch (Exception e) {}
        try { jumlah = Integer.parseInt(jumlahParameter); } catch (Exception e) {}

        String keterangan = "<br>";

        try {
            userName = session.getAttribute("userName").toString();
        } catch (Exception e) {}

        if (!(userName == null || userName.equals(""))) {

            if (tombolKaryawan.equals("Cari")) {
                if (!ktp.equals("") && karyawan.baca(ktp)) {
                    namaKaryawan = karyawan.getNama();
                    ruang = Integer.toString(karyawan.getRuang());
                } else {
                    keterangan = "KTP " + ktp + " tidak ada";
                }
            }

            if (tombolPekerjaan.equals("Cari")) {
                if (!kodePekerjaan.equals("") && pekerjaan.baca(kodePekerjaan)) {
                    namaPekerjaan = pekerjaan.getNamaPekerjaan();
                    jumlahTugas = Integer.toString(pekerjaan.getJumlahTugas());
                } else {
                    keterangan = "Kode pekerjaan tidak ada";
                }
            }

            if (tombol.equals("Simpan")) {
                if (!ktp.equals("") && !kodePekerjaan.equals("")) {
                    gaji.setKtp(ktp);
                    gaji.setListGaji(new Object[][]{
                        {kodePekerjaan, gajibersih, gajikotor, tunjangan}
                    });
                    if (gaji.simpan()) {
                        keterangan = "Sudah disimpan";
                    } else {
                        keterangan = "Gagal menyimpan:\n" + gaji.getPesan();
                    }
                } else {
                    keterangan = "KTP dan kode pekerjaan tidak boleh kosong";
                }
            }

            if (tombol.equals("Hapus")) {
                if (!ktp.equals("") && !kodePekerjaan.equals("")) {
                    if (gaji.hapus(ktp, kodePekerjaan)) {
                        keterangan = "Sudah dihapus";
                    } else {
                        keterangan = "Gagal menghapus:\n" + gaji.getPesan();
                    }
                } else {
                    keterangan = "KTP dan kode pekerjaan tidak boleh kosong";
                }
            }
        }

        String konten = "<h2>Input Gaji Karyawan</h2>";
        konten += "<form action='GajiController' method='post'>";
        konten += "<table>";

        konten += "<tr><td>KTP</td><td><input name='ktp' value='"+ktp+"'></td></tr>";
        konten += "<tr><td>Nama</td><td><input readonly value='"+namaKaryawan+"'></td></tr>";
        konten += "<tr><td>Ruang</td><td><input readonly value='"+ruang+"'></td></tr>";
        konten += "<tr><td>Gaji Bersih</td><td><input name='gajibersih' value='"+gajibersih+"'></td></tr>";
        konten += "<tr><td>Gaji Kotor</td><td><input name='gajikotor' value='"+gajikotor+"'></td></tr>";
        konten += "<tr><td>Tunjangan</td><td><input name='tunjangan' value='"+tunjangan+"'></td></tr>";

        konten += "<tr><td colspan='2'>";
        konten += "<input type='submit' name='tombol' value='Simpan'>";
        konten += "<input type='submit' name='tombol' value='Hapus'>";
        konten += "</td></tr>";

        if (!keterangan.equals("<br>")) {
            konten += "<tr><td colspan='2'><b>"+keterangan+"</b></td></tr>";
        }

        konten += "</table></form>";

        new MainForm().tampilkan(konten, request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}