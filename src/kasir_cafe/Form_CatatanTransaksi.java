package kasir_cafe;

import Aplikasi.Koneksi;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Form_CatatanTransaksi extends javax.swing.JFrame {
    private DefaultTableModel model;

    
    public Form_CatatanTransaksi() {
        initComponents();
        
        // Pengaturan Tanggal Sistem
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        txt_tanggal.setText(s.format(date));
        
        this.setLocationRelativeTo(null);
       
        model = new DefaultTableModel();
        tabel_transaksi.setModel(model);
        model.addColumn("ID Transaksi");
        model.addColumn("Tanggal");
        model.addColumn("ID Konsumen");
        model.addColumn("ID Meja");
        model.addColumn("Nama Menu"); 
        model.addColumn("Total");
        model.addColumn("Bayar");
        model.addColumn("Kembali");
    }
    
    public void LihatTransaksi() {
        // Membersihkan data tabel sebelum memuat ulang
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "SELECT * FROM transaksi WHERE id_pegawai LIKE '%" + txt_idpegawai.getText() + "%'";
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                // Membuat array objek dengan jumlah 8 sesuai jumlah kolom model
                Object[] o = new Object[8];
                o[0] = r.getString("id_transaksi");
                o[1] = r.getString("tanggal");
                o[2] = r.getString("id_pelanggan");
                o[3] = r.getString("id_meja");
                o[4] = r.getString("nama_menu"); 
                o[5] = r.getString("total");
                o[6] = r.getString("bayar");
                o[7] = r.getString("kembali");

                model.addRow(o);
            }
            r.close();
            s.close();
        } catch (Exception ex) {
            Logger.getLogger(Form_CatatanTransaksi.class.getName()).log(Level.SEVERE, null, ex);
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal memuat data: " + ex.getMessage());
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lb_keluar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txt_tanggal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_idpegawai = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_jabatan = new javax.swing.JTextField();
        btn_lihattransaksi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_transaksi = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lb_transaksi = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lb_datapelanggan = new javax.swing.JLabel();
        lb_update = new javax.swing.JLabel();
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
                .addContainerGap(957, Short.MAX_VALUE)
                .addComponent(lb_keluar)
                .addGap(167, 167, 167))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lb_keluar)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("KASIR");

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Tanggal");

        txt_tanggal.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("ID Pegawai");

        txt_idpegawai.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Jabatan");

        txt_jabatan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_jabatan.setEnabled(false);

        btn_lihattransaksi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_lihattransaksi.setText("Lihat Transaksi");
        btn_lihattransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lihattransaksiActionPerformed(evt);
            }
        });

        tabel_transaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabel_transaksi);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 882, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txt_idpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txt_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btn_lihattransaksi)))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txt_idpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txt_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_lihattransaksi))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("CATATAN TRANSAKSI");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/dashboard (1) (1).png"))); // NOI18N
        jLabel4.setText("Dashboard");

        lb_transaksi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_transaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/menu.png"))); // NOI18N
        lb_transaksi.setText("Transaksi");
        lb_transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_transaksiMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/report (1).png"))); // NOI18N
        jLabel2.setText("Catatan Transaksi");

        lb_datapelanggan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_datapelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        lb_datapelanggan.setText("Data Pelanggan");
        lb_datapelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_datapelangganMouseClicked(evt);
            }
        });

        lb_update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/table (1).png"))); // NOI18N
        lb_update.setText("Update Status Meja");
        lb_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_updateMouseClicked(evt);
            }
        });

        lb_tentang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_transaksi)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(lb_datapelanggan)
                            .addComponent(lb_update)
                            .addComponent(lb_tentang))
                        .addGap(93, 93, 93)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(6, 6, 6)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1)))
                .addGap(39, 39, 39)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(31, 31, 31)
                        .addComponent(lb_transaksi)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2)
                        .addGap(30, 30, 30)
                        .addComponent(lb_datapelanggan)
                        .addGap(31, 31, 31)
                        .addComponent(lb_update)
                        .addGap(26, 26, 26)
                        .addComponent(lb_tentang))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_lihattransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lihattransaksiActionPerformed
        LihatTransaksi();
    }//GEN-LAST:event_btn_lihattransaksiActionPerformed

    private void lb_transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_transaksiMouseClicked
        new Form_Transaksi().show();
        this.dispose();
        
        try {
        Connection c = Koneksi.KoneksiDb();
        Statement s = c.createStatement();

        String sql = "Select * from pengguna Where id_pegawai = '"+txt_idpegawai.getText()+"'";
        ResultSet r = s.executeQuery(sql);

        if (r.next()) {
            if (txt_idpegawai.getText().equals(r.getString("username")) &&
                r.getString("jenis").equals("kasir")) {
                Form_Transaksi.lb_jabatan.setText(r.getString(4));
                Form_Transaksi.txt_idpegawai.setText(r.getString(5));
            }
        }
      }catch(Exception e){
        }
    }//GEN-LAST:event_lb_transaksiMouseClicked

    private void lb_datapelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_datapelangganMouseClicked
        new Form_Pelanggan().show();
        this.dispose();

        try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * From pengguna Where id_pegawai = '"+txt_idpegawai.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(txt_idpegawai.getText().equals(r.getString("username")) &&
                   r.getString("jenis").equals("Kasir")) {
                    Form_Pelanggan.lb_jenis.setText(r.getString(4));
                    Form_Pelanggan.lb_id.setText(r.getString(5));
                }
            }

        }catch (Exception e) {
        }
    }//GEN-LAST:event_lb_datapelangganMouseClicked

    private void lb_updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_updateMouseClicked
        new Form_UpdateStatusMeja().show();
        this.dispose();

        try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * From pengguna Where id_pegawai = '"+txt_idpegawai.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(txt_idpegawai.getText().equals(r.getString("username")) &&
                   r.getString("jenis").equals("Kasir")) {
                    Form_UpdateStatusMeja.lb_jenis.setText(r.getString(4));
                    Form_UpdateStatusMeja.lb_id.setText(r.getString(5));
                }
            }

        }catch (Exception e) {
        }
    }//GEN-LAST:event_lb_updateMouseClicked

    private void lb_tentangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_tentangMouseClicked
        new Form_TentangKasir().show();
        this.dispose();

        try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * From pengguna Where id_pegawai = '"+txt_idpegawai.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(txt_idpegawai.getText().equals(r.getString("id_pegawai")) &&
                   r.getString("jenis").equals("Kasir")) {
                    Form_TentangKasir.lb_jenis.setText(r.getString(4));
                    Form_TentangKasir.lb_id.setText(r.getString(5));
                }
            }

        }catch (Exception e) {
        }
    }//GEN-LAST:event_lb_tentangMouseClicked

    private void lb_keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_keluarMouseClicked
        new Form_DashboardKasir().show();
        this.dispose();
    }//GEN-LAST:event_lb_keluarMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form_CatatanTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_CatatanTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_CatatanTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_CatatanTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_CatatanTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_lihattransaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_datapelanggan;
    private javax.swing.JLabel lb_keluar;
    private javax.swing.JLabel lb_tentang;
    private javax.swing.JLabel lb_transaksi;
    private javax.swing.JLabel lb_update;
    private javax.swing.JTable tabel_transaksi;
    public static javax.swing.JTextField txt_idpegawai;
    public static javax.swing.JTextField txt_jabatan;
    private javax.swing.JTextField txt_tanggal;
    // End of variables declaration//GEN-END:variables
}
