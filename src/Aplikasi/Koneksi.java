package Aplikasi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi {

    // Gunakan satu metode statis untuk membuat koneksi
    public static Connection KoneksiDb() {
        Connection koneksi = null; // Inisialisasi koneksi dengan null
        String JDBC_URL = "jdbc:mysql://localhost:3306/db_cafe"; // Tambahkan port 3306 secara eksplisit (standar)
        String USER = "root";
        String PASSWORD = "";

        try {
            // 1. Memuat Driver
            // Class.forName("com.mysql.cj.jdbc.Driver"); // Tidak wajib sejak JDBC 4.0, tetapi aman untuk dipertahankan
            
            // 2. Membuat Koneksi
            koneksi = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Koneksi ke database berhasil!"); // Pesan sukses di konsol
            
        } catch (SQLException error) {
            // Tangani error khusus SQL
            JOptionPane.showMessageDialog(null, "Koneksi ke database GAGAL! \nError: " + error.getMessage(), 
                                          "Error Koneksi", JOptionPane.ERROR_MESSAGE);
            error.printStackTrace(); // Cetak jejak kesalahan di konsol
        } 
        /* catch (ClassNotFoundException e) {
            // Tangani error jika driver tidak ditemukan (jar belum ditambahkan/salah nama driver)
            JOptionPane.showMessageDialog(null, "Driver database tidak ditemukan!", 
                                          "Error Driver", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } 
        */

        return koneksi;
    }
    
    // Anda dapat menghapus metode KoneksiDB() dan createStatement() yang tidak terpakai dari kode asli
    // karena hanya berisi "throw new UnsupportedOperationException".

    public static Object KoneksiDB() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}