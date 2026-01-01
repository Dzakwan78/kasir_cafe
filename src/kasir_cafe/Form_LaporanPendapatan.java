package kasir_cafe;
import Aplikasi.Koneksi;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;


public class Form_LaporanPendapatan extends javax.swing.JFrame {
    private DefaultTableModel model;

    
    public Form_LaporanPendapatan() {
        initComponents();
        Nonaktif();

        model = new DefaultTableModel();
        tabel_transaksi.setModel(model);

        model.addColumn("ID Transaksi");
        model.addColumn("Tanggal");
        model.addColumn("ID Pegawai");
        model.addColumn("ID Pelanggan");
        model.addColumn("ID Meja");
        model.addColumn("Nama Menu");
        model.addColumn("Total Harga");
        model.addColumn("Total Bayar");
        model.addColumn("Bayar");
        model.addColumn("Kembali");
    }
    
    public void Nonaktif() {
        jc_tanggal.setEnabled(false);
        jc_daritanggal.setEnabled(false);
        jc_daritanggal2.setEnabled(false);
        btn_lihat.setEnabled(false);
        btn_caritanggal.setEnabled(false);
    }
    
    public void TotalBiaya(){
        int jumlahBaris = tabel_transaksi.getRowCount();
        int totalBiaya = 0;
        int total;
        for(int i = 0; i < jumlahBaris; i++){
            total = Integer.parseInt(tabel_transaksi.getValueAt(i, 6).toString());
            totalBiaya = totalBiaya + total;
        }
        txt_pendapatan.setText(String.valueOf("RP."+totalBiaya+",-"));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lb_keluar = new javax.swing.JLabel();
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
        tabel_transaksi = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txt_pendapatan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lb_datamenu = new javax.swing.JLabel();
        lb_datameja = new javax.swing.JLabel();
        lb_laporantransaksi = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lb_logaktivitas = new javax.swing.JLabel();
        lb_tentang = new javax.swing.JLabel();
        lb_id = new javax.swing.JLabel();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1300, 700));

        jPanel1.setBackground(new java.awt.Color(255, 204, 51));

        lb_keluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/logout.png"))); // NOI18N
        lb_keluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_keluarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(871, Short.MAX_VALUE)
                .addComponent(lb_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lb_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

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

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Total Pendapatan");

        txt_pendapatan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(rb_daritanggal)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addGap(18, 18, 18)
                            .addComponent(txt_pendapatan))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_pendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 959, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("LAPORAN PENDAPATAN PENJUALAN");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("MANAGER");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/dashboard (1) (1).png"))); // NOI18N
        jLabel4.setText("Dashboard");

        lb_datamenu.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_datamenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/menu.png"))); // NOI18N
        lb_datamenu.setText("Data Menu");
        lb_datamenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_datamenuMouseClicked(evt);
            }
        });

        lb_datameja.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_datameja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/table (1).png"))); // NOI18N
        lb_datameja.setText("Data Meja");
        lb_datameja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_datamejaMouseClicked(evt);
            }
        });

        lb_laporantransaksi.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_laporantransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/report (1).png"))); // NOI18N
        lb_laporantransaksi.setText("Laporan Transaksi");
        lb_laporantransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_laporantransaksiMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/report (1).png"))); // NOI18N
        jLabel8.setText("Laporan Pendapatan");

        lb_logaktivitas.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_logaktivitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        lb_logaktivitas.setText("Log Aktivitas Pegawai");
        lb_logaktivitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_logaktivitasMouseClicked(evt);
            }
        });

        lb_tentang.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_tentang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/tentang (1).png"))); // NOI18N
        lb_tentang.setText("Tentang");
        lb_tentang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_tentangMouseClicked(evt);
            }
        });

        lb_id.setText("id");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(lb_datamenu)
                    .addComponent(lb_datameja)
                    .addComponent(lb_laporantransaksi)
                    .addComponent(jLabel8)
                    .addComponent(lb_logaktivitas)
                    .addComponent(lb_tentang))
                .addGap(118, 118, 118)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lb_id))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(lb_id))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(26, 26, 26)
                        .addComponent(lb_datamenu)
                        .addGap(33, 33, 33)
                        .addComponent(lb_datameja)
                        .addGap(26, 26, 26)
                        .addComponent(lb_laporantransaksi)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel8)
                        .addGap(32, 32, 32)
                        .addComponent(lb_logaktivitas)
                        .addGap(32, 32, 32)
                        .addComponent(lb_tentang)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        SimpleDateFormat sDate = new SimpleDateFormat("YYYY-MM-dd");
        String date = sDate.format(jc_tanggal.getDate());
        try {
            Connection c = Koneksi.KoneksiDb();
            String sql = "Select * from transaksi where tanggal = '"+date+"'";
            Statement stat = c.createStatement();
            ResultSet rs = stat.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10)   
                });
            }

            tabel_transaksi.setModel(model);
            TotalBiaya();
        } catch (Exception e) {
            System.out.println("Cari Data Error");
        }finally{
            jc_tanggal.setDate(null);
        }
    }//GEN-LAST:event_btn_lihatActionPerformed

    private void btn_caritanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_caritanggalActionPerformed
       model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        SimpleDateFormat sDate = new SimpleDateFormat("YYYY-MM-dd");
        String date1 = sDate.format(jc_daritanggal.getDate());
        String date2 = sDate.format(jc_daritanggal2.getDate());
        try {
            Connection c = Koneksi.KoneksiDb();
            String sql = "Select * from transaksi Where tanggal Between  '"+date1+"' And '"+date2+"'";
            Statement stat = c.createStatement();
            ResultSet rs = stat.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9)
                });
            }
            tabel_transaksi.setModel(model);
            TotalBiaya();
        } catch (Exception e) {
            System.out.println("Cari Data Error");
        }finally{
            jc_daritanggal.setDate(null);
            jc_daritanggal2.setDate(null);
        }
    }//GEN-LAST:event_btn_caritanggalActionPerformed

    private void lb_datamenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_datamenuMouseClicked
        new Form_Menu().show();
           this.dispose();
           
           
           try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * from pengguna where id_pegawai = '"+lb_id.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_id.getText().equals(r.getString("id_pegawai"))) {
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

            String sql = "Select * from pengguna where id_pegawai = '"+lb_id.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_id.getText().equals(r.getString("id_pegawai"))) {
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

            String sql = "Select * from pengguna where id_pegawai = '"+lb_id.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_id.getText().equals(r.getString("id_pegawai"))) {
                    Form_LaporanTransaksi.lb_id.setText(r.getString(5));
                }
            }

        }catch (Exception e) {
        }
    }//GEN-LAST:event_lb_laporantransaksiMouseClicked

    private void lb_logaktivitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_logaktivitasMouseClicked
        new Form_LogAktivitasPegawaiManager().show();
        this.dispose();
           
           
           try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * from pengguna where id_pegawai = '"+lb_id.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_id.getText().equals(r.getString("id_pegawai"))) {
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

            String sql = "Select * from pengguna where id_pegawai = '"+lb_id.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_id.getText().equals(r.getString("id_pegawai"))) {
                    Form_TentangManager.lb_id.setText(r.getString(5));
                }
            }

        }catch (Exception e) {
        }
    }//GEN-LAST:event_lb_tentangMouseClicked

    private void lb_keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_keluarMouseClicked
        new Form_DashboardManager().show();
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
            java.util.logging.Logger.getLogger(Form_LaporanPendapatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_LaporanPendapatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_LaporanPendapatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_LaporanPendapatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_LaporanPendapatan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_caritanggal;
    private javax.swing.JButton btn_lihat;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jc_daritanggal;
    private com.toedter.calendar.JDateChooser jc_daritanggal2;
    private com.toedter.calendar.JDateChooser jc_tanggal;
    private javax.swing.JLabel lb_datameja;
    private javax.swing.JLabel lb_datamenu;
    public static javax.swing.JLabel lb_id;
    private javax.swing.JLabel lb_keluar;
    private javax.swing.JLabel lb_laporantransaksi;
    private javax.swing.JLabel lb_logaktivitas;
    private javax.swing.JLabel lb_tentang;
    private javax.swing.JRadioButton rb_daritanggal;
    private javax.swing.JRadioButton rb_tanggal;
    private javax.swing.JTable tabel_transaksi;
    private javax.swing.JTextField txt_pendapatan;
    // End of variables declaration//GEN-END:variables
}
