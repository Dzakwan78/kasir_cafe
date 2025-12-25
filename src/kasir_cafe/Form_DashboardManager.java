package kasir_cafe;

import Aplikasi.Koneksi;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.Timer;
import javax.swing.JFrame;
import kasir_cafe.Form_Login;

public class Form_DashboardManager extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Form_DashboardManager.class.getName());
    java.util.Date tglSekarang = new java.util.Date(); 
    
    // Inisialisasi SimpleDateFormat dan String tanggal
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
    private String tanggal = format.format(tglSekarang);
     
    public Form_DashboardManager() {
        initComponents();
        lb_tanggal.setText(tanggal); 
        
        setJam();
    }

    public final void setJam(){
    
    ActionListener taskPerform = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            
            String nol_jam = "", nol_menit = "", nol_detik = "";

            // Mendapatkan waktu saat ini
            java.util.Date dateTime = new java.util.Date();

            // Mendapatkan nilai jam, menit, dan detik
            int nilai_jam = dateTime.getHours();
            int nilai_menit = dateTime.getMinutes();
            int nilai_detik = dateTime.getSeconds();

            // Menambahkan "0" jika nilai <= 9 (misal: 8 menjadi 08)
            if(nilai_jam <= 9) nol_jam = "0";
            if(nilai_menit <= 9) nol_menit = "0";
            if(nilai_detik <= 9) nol_detik = "0";

            // Menggabungkan semua string untuk membentuk format jam (HH:MM:SS)
            String jam = nol_jam + Integer.toString(nilai_jam);
            String menit = nol_menit + Integer.toString(nilai_menit);
            String detik = nol_detik + Integer.toString(nilai_detik);

            lb_jam.setText(jam + ":" + menit + ":" + detik + " ");
        }
    };
    new Timer(1000, taskPerform).start();
}
    
    public void updateactive() {
    // Mengambil teks dari JLabel/Field dengan nama 'lb_username'
    String user = lb_username.getText();
    
    // Menyiapkan nilai status 'active' yang akan di-update
    String active = "0"; // Status '0' kemungkinan berarti non-aktif/logout
    
    try {
        // Mendapatkan koneksi ke database
        Connection con = Koneksi.KoneksiDb();
        
        // Query SQL untuk mengupdate status 'active' 
        // pada tabel 'pengguna' untuk username tertentu
        String sql = "Update pengguna Set active = ? Where username = ?";
        
        // Menyiapkan statement SQL
        PreparedStatement p = con.prepareStatement(sql);
        
        // Mengisi placeholder (?) pada query
        p.setString(1, active); // Mengatur kolom 'active' menjadi nilai "0" (non-aktif)
        p.setString(2, user);   // Mengatur kolom 'username' sesuai dengan user yang didapat

        // Mengeksekusi update
        p.executeUpdate();

        // Menutup statement
        p.close();

    
    // Menangkap exception jika terjadi kesalahan dalam proses database
    } catch (Exception e) {
        
    }
}
 
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lb_keluar = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lb_username = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lb_idpegawai = new javax.swing.JLabel();
        lb_tanggal = new javax.swing.JLabel();
        lb_jam = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lb_datamenu = new javax.swing.JLabel();
        lb_datameja = new javax.swing.JLabel();
        lb_laporantransaksi = new javax.swing.JLabel();
        lb_laporanpendapatan = new javax.swing.JLabel();
        lb_jenis = new javax.swing.JLabel();
        lb_logaktivitas = new javax.swing.JLabel();
        lb_tentang = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1300, 700));

        jPanel2.setBackground(new java.awt.Color(255, 204, 51));

        lb_keluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/logout.png"))); // NOI18N
        lb_keluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_keluarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_keluar)
                .addGap(454, 454, 454))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lb_keluar)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Serif", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("SELAMAT DATANG ");

        lb_username.setFont(new java.awt.Font("Segoe UI Emoji", 1, 36)); // NOI18N
        lb_username.setForeground(new java.awt.Color(51, 51, 51));
        lb_username.setText("USERNAME");

        jLabel3.setBackground(new java.awt.Color(153, 255, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("ID Anda");

        lb_idpegawai.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        lb_idpegawai.setForeground(new java.awt.Color(51, 51, 51));
        lb_idpegawai.setText("ID");

        lb_tanggal.setBackground(new java.awt.Color(255, 255, 255));
        lb_tanggal.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        lb_tanggal.setForeground(new java.awt.Color(51, 51, 51));
        lb_tanggal.setText("TANGGAL");

        lb_jam.setBackground(new java.awt.Color(51, 51, 51));
        lb_jam.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lb_jam.setForeground(new java.awt.Color(51, 51, 51));
        lb_jam.setText("JAM");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(jLabel1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(347, 347, 347)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_username)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(lb_idpegawai)))))
                .addContainerGap(623, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_jam)
                    .addComponent(lb_tanggal))
                .addGap(469, 469, 469))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addComponent(lb_username)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lb_idpegawai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addComponent(lb_jam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_tanggal)
                .addGap(100, 100, 100))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("DASHBOARD");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("MANAGER");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/dashboard (1) (1).png"))); // NOI18N
        jLabel10.setText("Dashboard");

        lb_datamenu.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lb_datamenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/menu.png"))); // NOI18N
        lb_datamenu.setText("Data Menu");
        lb_datamenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_datamenuMouseClicked(evt);
            }
        });

        lb_datameja.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lb_datameja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/table (1).png"))); // NOI18N
        lb_datameja.setText("Data Meja");
        lb_datameja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_datamejaMouseClicked(evt);
            }
        });

        lb_laporantransaksi.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lb_laporantransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        lb_laporantransaksi.setText("Laporan Transaksi");
        lb_laporantransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_laporantransaksiMouseClicked(evt);
            }
        });

        lb_laporanpendapatan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lb_laporanpendapatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/report (1).png"))); // NOI18N
        lb_laporanpendapatan.setText("Laporan Pendapatan");
        lb_laporanpendapatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_laporanpendapatanMouseClicked(evt);
            }
        });

        lb_jenis.setBackground(new java.awt.Color(102, 204, 255));
        lb_jenis.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        lb_jenis.setForeground(new java.awt.Color(204, 255, 255));
        lb_jenis.setText("JABATAN");

        lb_logaktivitas.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lb_logaktivitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        lb_logaktivitas.setText("Log Aktifitas Pegawai");
        lb_logaktivitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_logaktivitasMouseClicked(evt);
            }
        });

        lb_tentang.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lb_tentang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/tentang (1).png"))); // NOI18N
        lb_tentang.setText("Tentang");
        lb_tentang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_tentangMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(lb_datamenu)
                    .addComponent(lb_datameja)
                    .addComponent(lb_laporantransaksi)
                    .addComponent(lb_laporanpendapatan)
                    .addComponent(lb_logaktivitas)
                    .addComponent(lb_tentang))
                .addGap(69, 69, 69)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(335, 335, 335)
                .addComponent(jLabel7)
                .addGap(59, 59, 59)
                .addComponent(lb_jenis)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel8)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(lb_jenis))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(lb_datamenu)
                        .addGap(18, 18, 18)
                        .addComponent(lb_datameja)
                        .addGap(18, 18, 18)
                        .addComponent(lb_laporantransaksi)
                        .addGap(18, 18, 18)
                        .addComponent(lb_laporanpendapatan)
                        .addGap(18, 18, 18)
                        .addComponent(lb_logaktivitas)
                        .addGap(18, 18, 18)
                        .addComponent(lb_tentang)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lb_keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_keluarMouseClicked
        new Form_Login().show();
        this.dispose();
        
        
        updateacive();
    }//GEN-LAST:event_lb_keluarMouseClicked

    private void lb_datamenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_datamenuMouseClicked
           new Form_Menu().show();
           this.dispose();
           
           
           try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * from pengguna where id_pegawai = '"+lb_idpegawai.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_idpegawai.getText().equals(r.getString("id_pegawai"))) {
                    Form_Menu.lb_id.setText(r.getString(5));
                }
            }

        }catch (Exception e) {
        }
 
    }//GEN-LAST:event_lb_datamenuMouseClicked

    private void lb_datamejaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_datamejaMouseClicked
        new Form_Meja().show();
           this.dispose();
           
           
           try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * from pengguna where id_pegawai = '"+lb_idpegawai.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_idpegawai.getText().equals(r.getString("id_pegawai"))) {
                    Form_Meja.lb_id.setText(r.getString(5));
                }
            }

        }catch (Exception e) {
        }
    }//GEN-LAST:event_lb_datamejaMouseClicked

    private void lb_laporantransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_laporantransaksiMouseClicked
        new Form_LaporanTransaksi().show();
           this.dispose();
           
           
           try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * from pengguna where id_pegawai = '"+lb_idpegawai.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_idpegawai.getText().equals(r.getString("id_pegawai"))) {
                    Form_LaporanTransaksi.lb_id.setText(r.getString(5));
                }
            }

        }catch (Exception e) {
        }
    }//GEN-LAST:event_lb_laporantransaksiMouseClicked

    private void lb_laporanpendapatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_laporanpendapatanMouseClicked
        new Form_LaporanPendapatan().show();
           this.dispose();
           
           
           try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * from pengguna where id_pegawai = '"+lb_idpegawai.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_idpegawai.getText().equals(r.getString("id_pegawai"))) {
                    Form_LaporanPendapatan.lb_id.setText(r.getString(5));
                }
            }

        }catch (Exception e) {
        }
    }//GEN-LAST:event_lb_laporanpendapatanMouseClicked

    private void lb_logaktivitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_logaktivitasMouseClicked
       new Form_LogAktivitasPegawaiManager().show();
           this.dispose();
           
           
           try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * from pengguna where id_pegawai = '"+lb_idpegawai.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_idpegawai.getText().equals(r.getString("id_pegawai"))) {
                    Form_LogAktivitasPegawaiManager.lb_id.setText(r.getString(5));
                }
            }

        }catch (Exception e) {
        }
    }//GEN-LAST:event_lb_logaktivitasMouseClicked

    private void lb_tentangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_tentangMouseClicked
        new Form_TentangManager().show();
           this.dispose();
           
           
           try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * from pengguna where id_pegawai = '"+lb_idpegawai.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_idpegawai.getText().equals(r.getString("id_pegawai"))) {
                    Form_TentangManager.lb_id.setText(r.getString(5));
                }
            }

        }catch (Exception e) {
        }
    }//GEN-LAST:event_lb_tentangMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> new Form_DashboardManager().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lb_datameja;
    private javax.swing.JLabel lb_datamenu;
    public static javax.swing.JLabel lb_idpegawai;
    private javax.swing.JLabel lb_jam;
    public static javax.swing.JLabel lb_jenis;
    private javax.swing.JLabel lb_keluar;
    private javax.swing.JLabel lb_laporanpendapatan;
    private javax.swing.JLabel lb_laporantransaksi;
    private javax.swing.JLabel lb_logaktivitas;
    private javax.swing.JLabel lb_tanggal;
    private javax.swing.JLabel lb_tentang;
    public static javax.swing.JLabel lb_username;
    // End of variables declaration//GEN-END:variables

    private void updateacive() {
   }
}
