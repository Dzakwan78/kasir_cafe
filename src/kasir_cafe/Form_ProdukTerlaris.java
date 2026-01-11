package kasir_cafe;
import Aplikasi.Koneksi;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;


public class Form_ProdukTerlaris extends javax.swing.JFrame {
    private DefaultTableModel model;

    
    public Form_ProdukTerlaris() {
        initComponents();
        Nonaktif();

        model = new DefaultTableModel();
        tabel_produkterlaris.setModel(model);

        model.addColumn("Nama Menu");
        model.addColumn("Jumlah");
    }
    
    public void Nonaktif() {
        jc_tanggal.setEnabled(false);
        jc_daritanggal.setEnabled(false);
        jc_daritanggal2.setEnabled(false);
        btn_lihat.setEnabled(false);
        btn_caritanggal.setEnabled(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        rb_tanggal = new javax.swing.JRadioButton();
        jc_tanggal = new com.toedter.calendar.JDateChooser();
        btn_lihat = new javax.swing.JButton();
        rb_daritanggal = new javax.swing.JRadioButton();
        jc_daritanggal = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jc_daritanggal2 = new com.toedter.calendar.JDateChooser();
        btn_caritanggal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_produkterlaris = new javax.swing.JTable();
        btn_keluar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lb_id = new javax.swing.JLabel();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1300, 700));

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(1138, 598));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(1078, 541));

        buttonGroup1.add(rb_tanggal);
        rb_tanggal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rb_tanggal.setText("Tanggal");
        rb_tanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_tanggalActionPerformed(evt);
            }
        });

        btn_lihat.setText("Lihat");
        btn_lihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lihatActionPerformed(evt);
            }
        });

        buttonGroup1.add(rb_daritanggal);
        rb_daritanggal.setText("Dari Tanggal");
        rb_daritanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_daritanggalActionPerformed(evt);
            }
        });

        jLabel11.setText("s/d");

        btn_caritanggal.setText("Lihat");
        btn_caritanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_caritanggalActionPerformed(evt);
            }
        });

        tabel_produkterlaris.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tabel_produkterlaris);

        btn_keluar.setText("Keluar");
        btn_keluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_keluarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_keluar)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(rb_tanggal)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jc_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btn_lihat)))
                            .addGap(44, 44, 44)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jc_daritanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel11)
                                    .addGap(33, 33, 33)
                                    .addComponent(jc_daritanggal2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(28, 28, 28)
                                    .addComponent(btn_caritanggal))
                                .addComponent(rb_daritanggal)))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rb_daritanggal, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rb_tanggal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jc_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_lihat)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel11)
                                .addComponent(jc_daritanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jc_daritanggal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btn_caritanggal)
                        .addGap(1, 1, 1)))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 959, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("PRODUK TERLARIS");

        lb_id.setText("id");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1023, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lb_id)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lb_id)))
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rb_tanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_tanggalActionPerformed
        jc_tanggal.setEnabled(true);
        jc_daritanggal.setEnabled(false);
        jc_daritanggal2.setEnabled(false);
        btn_lihat.setEnabled(true);
        btn_caritanggal.setEnabled(true);
    }//GEN-LAST:event_rb_tanggalActionPerformed

    private void rb_daritanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_daritanggalActionPerformed
        jc_tanggal.setEnabled(false);
        jc_daritanggal.setEnabled(true);
        jc_daritanggal2.setEnabled(true);
        btn_lihat.setEnabled(false);
        btn_caritanggal.setEnabled(true);
    }//GEN-LAST:event_rb_daritanggalActionPerformed

    private void btn_lihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lihatActionPerformed
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd"); // Gunakan yyyy kecil untuk tahun
        String date = sDate.format(jc_tanggal.getDate());

        try {
            Connection c = Koneksi.KoneksiDb();

            // Perbaikan pada Query SQL: Menjumlahkan 'jumlah' dan mengelompokkan berdasarkan 'nama_menu'
            String sql = "SELECT nama_menu, SUM(jumlah) AS total_jumlah " +
                         "FROM produk_terlaris " +
                         "WHERE waktu LIKE '"+date+"%' " + 
                         "GROUP BY nama_menu";

            Statement stat = c.createStatement();
            ResultSet rs = stat.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nama_menu"),
                    rs.getString("total_jumlah")
                });
            }

            tabel_produkterlaris.setModel(model);

        } catch (Exception e) {
            System.out.println("Cari Data Error: " + e.getMessage());
        } finally {
            jc_tanggal.setDate(null);
        }
    }//GEN-LAST:event_btn_lihatActionPerformed

    private void btn_caritanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_caritanggalActionPerformed
       if (jc_daritanggal.getDate() == null || jc_daritanggal2.getDate() == null) {
        javax.swing.JOptionPane.showMessageDialog(null, "Pilih rentang tanggal terlebih dahulu!");
        return;
    }

    model.setRowCount(0); // Cara lebih efisien menghapus data tabel

    SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd"); // yyyy kecil
    String date1 = sDate.format(jc_daritanggal.getDate());
    String date2 = sDate.format(jc_daritanggal2.getDate());

    try {
        Connection c = Koneksi.KoneksiDb();
        // Query disesuaikan untuk menjumlahkan data (SUM) dalam rentang tanggal
        String sql = "SELECT nama_menu, SUM(jumlah) AS total_jumlah " +
                     "FROM produk_terlaris " +
                     "WHERE waktu BETWEEN '" + date1 + " 00:00:00' AND '" + date2 + " 23:59:59' " +
                     "GROUP BY nama_menu " +
                     "ORDER BY total_jumlah DESC"; // Urutkan dari yang terbanyak

        Statement stat = c.createStatement();
        ResultSet rs = stat.executeQuery(sql);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("nama_menu"),
                rs.getString("total_jumlah")
            });
        }
    } catch (Exception e) {
        System.out.println("Cari Data Error: " + e.getMessage());
    }
    }//GEN-LAST:event_btn_caritanggalActionPerformed

    private void btn_keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_keluarMouseClicked
        new Form_Transaksi().show();
        this.dispose();
    }//GEN-LAST:event_btn_keluarMouseClicked

    
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
            java.util.logging.Logger.getLogger(Form_ProdukTerlaris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_ProdukTerlaris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_ProdukTerlaris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_ProdukTerlaris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_ProdukTerlaris().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_caritanggal;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_lihat;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jc_daritanggal;
    private com.toedter.calendar.JDateChooser jc_daritanggal2;
    private com.toedter.calendar.JDateChooser jc_tanggal;
    public static javax.swing.JLabel lb_id;
    private javax.swing.JRadioButton rb_daritanggal;
    private javax.swing.JRadioButton rb_tanggal;
    private javax.swing.JTable tabel_produkterlaris;
    // End of variables declaration//GEN-END:variables
}
