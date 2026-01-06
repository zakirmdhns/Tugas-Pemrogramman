package com.unpam.controller;

import com.unpam.model.Pekerjaan;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "PekerjaanController", urlPatterns = {"/PekerjaanController"})
public class PekerjaanController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // ===== CEK LOGIN =====
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }
        
        if (request.getParameter("kodepekerjaan") == null) {
            request.getRequestDispatcher("pekerjaan.jsp").forward(request, response);
            return;
        }


        Pekerjaan pekerjaan = new Pekerjaan();

        String tombol = request.getParameter("tombol");
        String kodePekerjaan = request.getParameter("kodePekerjaan");
        String namaPekerjaan = request.getParameter("namaPekerjaan");
        String jumlahTugas = request.getParameter("jumlahTugas");
        String mulaiParam = request.getParameter("mulai");
        String jumlahParam = request.getParameter("jumlah");
        String kodeDipilih = request.getParameter("kodePekerjaanDipilih");

        if (tombol == null) tombol = "";
        if (kodePekerjaan == null) kodePekerjaan = "";
        if (namaPekerjaan == null) namaPekerjaan = "";
        if (jumlahTugas == null) jumlahTugas = "2";
        if (kodeDipilih == null) kodeDipilih = "";

        int mulai = 0, jumlah = 10;
        try { mulai = Integer.parseInt(mulaiParam); } catch (Exception e) {}
        try { jumlah = Integer.parseInt(jumlahParam); } catch (Exception e) {}

        String keterangan = "<br>";

        // ===== PROSES TOMBOL =====
        if (tombol.equals("Simpan")) {
            pekerjaan.setKodePekerjaan(kodePekerjaan);
            pekerjaan.setNamaPekerjaan(namaPekerjaan);
            pekerjaan.setJumlahTugas(Integer.parseInt(jumlahTugas));

            if (pekerjaan.simpan()) {
                keterangan = "Data berhasil disimpan";
                kodePekerjaan = namaPekerjaan = "";
                jumlahTugas = "2";
            } else {
                keterangan = pekerjaan.getPesan();
            }

        } else if (tombol.equals("Hapus")) {
            if (pekerjaan.hapus(kodePekerjaan)) {
                keterangan = "Data berhasil dihapus";
                kodePekerjaan = namaPekerjaan = "";
                jumlahTugas = "2";
            } else {
                keterangan = pekerjaan.getPesan();
            }

        } else if (tombol.equals("Cari")) {
            if (pekerjaan.baca(kodePekerjaan)) {
                namaPekerjaan = pekerjaan.getNamaPekerjaan();
                jumlahTugas = String.valueOf(pekerjaan.getJumlahTugas());
            } else {
                keterangan = pekerjaan.getPesan();
            }

        } else if (tombol.equals("Pilih")) {
            if (pekerjaan.baca(kodeDipilih)) {
                kodePekerjaan = pekerjaan.getKodePekerjaan();
                namaPekerjaan = pekerjaan.getNamaPekerjaan();
                jumlahTugas = String.valueOf(pekerjaan.getJumlahTugas());
            }
        }

        // ===== TAMPIL DATA =====
        String kontenLihat = "";
        if (tombol.equals("Lihat") || tombol.equals("Berikutnya") ||
            tombol.equals("Sebelumnya") || tombol.equals("Tampilkan")) {

            if (tombol.equals("Sebelumnya")) {
                mulai -= jumlah;
                if (mulai < 0) mulai = 0;
            }
            if (tombol.equals("Berikutnya")) {
                mulai += jumlah;
            }

            pekerjaan.bacaData(mulai, jumlah);
            Object[][] data = pekerjaan.getList();

            kontenLihat += "<tr><td colspan='2'><table border='1'>";
            for (int i = 0; i < data.length; i++) {
                kontenLihat += "<tr>";
                kontenLihat += "<td><input type='radio' name='kodePekerjaanDipilih' value='" + data[i][0] + "'></td>";
                kontenLihat += "<td>" + data[i][0] + "</td>";
                kontenLihat += "<td>" + data[i][1] + "</td>";
                kontenLihat += "<td>" + data[i][2] + "</td>";
                kontenLihat += "</tr>";
            }
            kontenLihat += "</table></td></tr>";
        }

        // ===== FORM =====
        String konten =
            "<h2>Master Data Pekerjaan</h2>" +
            "<form method='post'>" +
            "<table>" +

            "<tr><td>Kode</td><td>" +
            "<input type='text' name='kodePekerjaan' value='" + kodePekerjaan + "'>" +
            "<input type='submit' name='tombol' value='Cari'></td></tr>" +

            "<tr><td>Nama</td><td>" +
            "<input type='text' name='namaPekerjaan' value='" + namaPekerjaan + "'></td></tr>" +

            "<tr><td>Jumlah Tugas</td><td><select name='jumlahTugas'>";

        for (int i = 2; i <= 6; i++) {
            if (String.valueOf(i).equals(jumlahTugas))
                konten += "<option selected>" + i + "</option>";
            else
                konten += "<option>" + i + "</option>";
        }

        konten +=
            "</select></td></tr>" +
            "<tr><td colspan='2'>" + keterangan + "</td></tr>" +

            "<tr><td colspan='2' align='center'>" +
            "<input type='submit' name='tombol' value='Simpan'> " +
            "<input type='submit' name='tombol' value='Hapus'> " +
            "<input type='submit' name='tombol' value='Lihat'>" +
            "</td></tr>" +

            kontenLihat +
            "</table></form>";

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