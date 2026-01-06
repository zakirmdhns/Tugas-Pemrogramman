/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpam.model;
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException;  
import com.unpam.view.PesanDialog;
import java.util.ArrayList;

/**
 *
 * @author zaki
 */
public class Karyawan {
    private String ktp, nama, password;  
    private int ruang; 
    private String pesan;  
    private Object[][] list; 
    private final Koneksi koneksi = new Koneksi(); 
    private final PesanDialog pesanDialog = new PesanDialog();

    public String getKtp() {  
        return ktp; 
    } 
    public void setKtp(String ktp) { 
        this.ktp = ktp; 
    } 
    public String getNama() {  
        return nama; 
    } 
    public void setNama(String nama) {  
        this.nama = nama; 
    } 
    public int getRuang() {  
        return ruang; 
    } 
    public void setRuang(int ruang) {  
        this.ruang = ruang; 
    } 
    public String getPesan() {  
        return pesan; 
    } 
    public String getPassword() { 
        return password; 
    } 
    public void setPassword(String password) {  
        this.password = password; 
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

        if ((connection = koneksi.getConnection()) !=  null){ 
            int jumlahSimpan = 0; 
            boolean simpan = false;  
            String SQLStatemen = ""; 
            PreparedStatement preparedStatement = null;  
            ResultSet rset = null; 

            try { 
                simpan = true; 
                SQLStatemen = "insert into tbkaryawan(ktp, nama, ruang, password) values (?,?,?,?)"; 
                preparedStatement = connection.prepareStatement(SQLStatemen);  
                preparedStatement.setString(1, ktp);  
                preparedStatement.setString(2, nama);  
                preparedStatement.setInt(3, ruang);  
                preparedStatement.setString(4, password); 
                jumlahSimpan = preparedStatement.executeUpdate(); 

                if (simpan) { 
                    if (jumlahSimpan < 1){ 
                        adaKesalahan = true; 
                        pesan = "Gagal menyimpan data karyawan"; 
                    } 
                } 
            } catch (SQLException ex){  
                adaKesalahan = true; 
                pesan = "Tidak dapat tbkaryawan\n" + ex + "\n" + SQLStatemen; 
            } finally {
                try {
                    if (preparedStatement != null) preparedStatement.close();
                } catch (SQLException ex) {
                    // abaikan atau set pesan jika perlu
                }
                try {
                    if (rset != null) rset.close();
                } catch (SQLException ex) {
                    // abaikan atau set pesan jika perlu
                }
                try {
                    if (connection != null) connection.close();
                } catch (SQLException ex) {
                    // abaikan atau set pesan jika perlu
                }
            }
        } else { 
            adaKesalahan = true; 
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan(); 
        } 
        return !adaKesalahan; 
    } 
    public boolean bacaData(int mulai, int jumlah) {
        boolean error = false;
        ArrayList<Object[]> temp = new ArrayList<>();

        try (Connection c = koneksi.getConnection()) {
            String sql = "SELECT ktp, nama FROM tbkaryawan LIMIT ?,?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, mulai);
            ps.setInt(2, jumlah);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                temp.add(new Object[]{ rs.getString(1), rs.getString(2) });
            }
            list = temp.toArray(new Object[temp.size()][2]);
        } catch (SQLException e) {
            error = true;
            pesan = e.getMessage();
        }
        return !error;
    }

    public boolean baca(String ktp) {
    boolean adaKesalahan = false;
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        connection = koneksi.getConnection();
        String sql = "SELECT ktp, nama, ruang, password FROM tbkaryawan WHERE ktp=?";
        ps = connection.prepareStatement(sql);
        ps.setString(1, ktp);
        rs = ps.executeQuery();

        if (rs.next()) {
            this.ktp = rs.getString("ktp");
            this.nama = rs.getString("nama");
            this.ruang = rs.getInt("ruang");
            this.password = rs.getString("password");
        } else {
            adaKesalahan = true;
            pesan = "KTP tidak ditemukan";
        }
    } catch (SQLException e) {
        adaKesalahan = true;
        pesan = e.getMessage();
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (connection != null) connection.close(); } catch (Exception e) {}
    }

    return !adaKesalahan;
} 


    public boolean hapus(String ktp) {
        boolean adaKesalahan = false;
        Connection conn = koneksi.getConnection();

        try {
            String sql = "DELETE FROM tbkaryawan WHERE ktp=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ktp);
            ps.executeUpdate();
        } catch (SQLException e) {
            adaKesalahan = true;
            pesan = e.getMessage();
        }
        return !adaKesalahan;
    }

}