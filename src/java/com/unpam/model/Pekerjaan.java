/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpam.model;
import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import com.unpam.view.PesanDialog;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author zaki
 */
public class Pekerjaan {

    private String kodePekerjaan, namaPekerjaan; 
    private int jumlahTugas; 
    private String pesan; 
    private Object[][] list; 
    private final Koneksi koneksi = new Koneksi();  
    private final PesanDialog pesanDialog = new PesanDialog();

    public String getKodePekerjaan() {  
        return kodePekerjaan; 
    } 
    public void setKodePekerjaan(String kodePekerjaan) {  
        this.kodePekerjaan = kodePekerjaan; 
    } 
    public String getNamaPekerjaan() {  
        return namaPekerjaan; 
    } 
    public void setNamaPekerjaan(String namaPekerjaan) {  
        this.namaPekerjaan = namaPekerjaan; 
    } 
    public int getJumlahTugas() { 
        return jumlahTugas; 
    } 
    public void setJumlahTugas(int jumlahTugas) {  
        this.jumlahTugas = jumlahTugas;
    }

    public String getPesan() {  
        return pesan; 
    } 

    public Object[][] getList() {  
        return list; 
    } 

    public void setList(Object[][] list) {  
        this.list = list; 
    } 

    public boolean simpan(){ 
        boolean adaKesalahan = false;  
        Connection connection; 
        
        if ((connection = koneksi.getConnection()) != null){ 
            int jumlahSimpan = 0; 
            boolean simpan = false;  
            Statement sta = null;  
            ResultSet rset = null; 
            String SQLStatemen = "";

            try { 
                simpan = true; 

                SQLStatemen = "insert into tbpekerjaan values("
                             + "'" + kodePekerjaan + "',"
                             + "'" + namaPekerjaan + "',"
                             + "'" + jumlahTugas + "')";

                sta = connection.createStatement();  
                jumlahSimpan = sta.executeUpdate(SQLStatemen);

                if (simpan) { 
                    if (jumlahSimpan < 1){  
                        adaKesalahan = true; 
                        pesan = "Gagal menyimpan data pekerjaan"; 
                    } 
                }

            } catch (SQLException ex){  
                adaKesalahan = true; 
                pesan = "Tidak dapat membuka tabel tbpekerjaan\n"
                        + ex + "\n" + SQLStatemen; 

            } finally {
                try { 
                    if (sta != null) sta.close(); 
                } catch (SQLException e) {}
                try { 
                    if (rset != null) rset.close(); 
                } catch (SQLException e) {}
                try { 
                    connection.close(); 
                } catch (SQLException e) {}
            }

        } else { 
            adaKesalahan = true; 
            pesan = "Tidak dapat melakukan koneksi ke server\n" 
                    + koneksi.getPesanKesalahan(); 
        } 
        
        return !adaKesalahan; 
    } 
    public boolean bacaData(int mulai, int jumlah) {
        boolean adaKesalahan = false;
        ArrayList<Object[]> temp = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rset = null;

        try {
            connection = koneksi.getConnection();

            String SQL = "SELECT kodepekerjaan, namapekerjaan, jumlahtugas "
                       + "FROM tbpekerjaan LIMIT ?,?";

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, mulai);
            preparedStatement.setInt(2, jumlah);

            rset = preparedStatement.executeQuery();

            // ===== INI JAWABAN PERTANYAAN KAMU =====
            while (rset.next()) {
                temp.add(new Object[]{
                    rset.getString(1), // kodepekerjaan
                    rset.getString(2), // namapekerjaan
                    rset.getInt(3)     // jumlahtugas
                });
            }

            list = temp.toArray(new Object[temp.size()][3]);
            // =====================================

        } catch (SQLException ex) {
            adaKesalahan = true;
            pesan = "Tidak dapat membaca data dari tbpekerjaan\n" + ex;
        } finally {
            try { if (rset != null) rset.close(); } catch (Exception e) {}
            try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }

        return !adaKesalahan;
    }

    public boolean hapus(String kode) {
        boolean error = false;
        try (Connection c = koneksi.getConnection()) {
            PreparedStatement ps = c.prepareStatement(
                "DELETE FROM tbpekerjaan WHERE kodepekerjaan=?");
            ps.setString(1, kode);
            ps.executeUpdate();
        } catch (SQLException e) {
            error = true;
            pesan = e.getMessage();
        }
        return !error;
    }

    public boolean baca(String kodePekerjaan) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}