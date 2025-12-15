package kasir_cafe;

import Aplikasi.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Pastikan menggunakan java.sql.Statement
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import static kasir_cafe.Form_DashboardKasir.lb_username;

public final class Form_Pelanggan extends javax.swing.JFrame {

    private final DefaultTableModel model;
    String id_pelanggan, nama_pelanggan, jenis_kelamin,  no_hp, alamat;

    public Form_Pelanggan() {
        initComponents();
        NonAktif();
        
        model = new DefaultTableModel();
        tabel_pelanggan.setModel(model);
        model.addColumn("ID Pelanggan");
        model.addColumn("Nama Pelanggan");
        model.addColumn("Jenis Kelamin");
        model.addColumn("No Handphone");
        model.addColumn("Alamat");

        GetData();
        IdOtomatis();
    }

  public void GetData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try (Connection conn = Koneksi.KoneksiDb();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery("SELECT * FROM pelanggan")) {
            
             while (rs.next()) {
                Object[] obj = new Object[6];
                obj[0] = rs.getString("id_pelanggan");
                obj[1] = rs.getString("nama_pelanggan");
                obj[2] = rs.getString("jenis_kelamin");
                obj[3] = rs.getString("no_hp");
                obj[4] = rs.getString("alamat");

                model.addRow(obj);
             }
        } catch (SQLException error) {
            // Menggunakan logger untuk mencatat kesalahan lebih detail, disamping JOptionPane
            Logger.getLogger(Form_Pelanggan.class.getName()).log(Level.SEVERE, null, error);
            JOptionPane.showMessageDialog(null, "Error mengambil data: " + error.getMessage());
        }
    }
    
    public void SelectData() {
        int i = tabel_pelanggan.getSelectedRow();
        if (i == -1) {
            return;
        }
        String jk = model.getValueAt(i, 2) != null ? model.getValueAt(i, 2).toString() : "";
        
        txt_idpelanggan.setText("" + model.getValueAt(i, 0));
        txt_namapelanggan.setText("" + model.getValueAt(i, 1));
        
        if ("L".equalsIgnoreCase(jk)) { 
            rb_lakilaki.setSelected(true);
            rb_perempuan.setSelected(false); 
        } else if ("P".equalsIgnoreCase(jk)) {
            rb_perempuan.setSelected(true);
            rb_lakilaki.setSelected(false); 
        }
        
        // Pastikan nilai Jabatan ada di ComboBox
        Object jabatanValue = model.getValueAt(i, 3);
        if (jabatanValue != null) {
        }

        txt_no_hp.setText("" + model.getValueAt(i, 3));
        txtarea_alamat.setText("" + model.getValueAt(i, 4));
        btn_hapus.setEnabled(true);
        btn_edit.setEnabled(true);
    }
    
    public void IdOtomatis() {
        try (Connection con = Koneksi.KoneksiDb();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id_pelanggan FROM pelanggan ORDER BY id_pelanggan DESC LIMIT 1")) {
            
            if (rs.next()) {
                String id = rs.getString("id_pelanggan").substring(2); // Ambil angka setelah "PG"
                int idInt = Integer.parseInt(id) + 1;
                String AN = String.valueOf(idInt);
                String nol = "";

                switch (AN.length()) {
                    case 1 -> nol = "00";
                    case 2 -> nol = "0";
                    case 3 -> nol = "";
                    default -> {
                    }
                }
                txt_idpelanggan.setText("PN" + nol + AN);
            } else {
                txt_idpelanggan.setText("PN001");
            }
        } catch (SQLException error) {
            Logger.getLogger(Form_Pelanggan.class.getName()).log(Level.SEVERE, null, error);
            JOptionPane.showMessageDialog(null, "Error membuat ID Otomatis: " + error.getMessage());
        } catch (NumberFormatException e) {
             Logger.getLogger(Form_Pelanggan.class.getName()).log(Level.SEVERE, null, e);
             JOptionPane.showMessageDialog(null, "Error format ID Pegawai: " + e.getMessage());
        }
    }
    
    public void LoadData() {
        id_pelanggan = txt_idpelanggan.getText();
        nama_pelanggan = txt_namapelanggan.getText();
        jenis_kelamin = null;
        if (rb_lakilaki.isSelected()) {
            jenis_kelamin = "L";
        } else if (rb_perempuan.isSelected()) {
            jenis_kelamin = "P";
        }
        no_hp = txt_no_hp.getText();
        alamat = txtarea_alamat.getText();
    }
    
    public void SimpanData() {
        LoadData();

        String sql = "INSERT INTO pelanggan (id_pelanggan, nama_pelanggan, jenis_kelamin, no_hp, alamat) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Koneksi.KoneksiDb();
             PreparedStatement p = con.prepareStatement(sql)) {

            p.setString(1, id_pelanggan);
            p.setString(2, nama_pelanggan);
            p.setString(3, jenis_kelamin);
            p.setString(4, no_hp);
            p.setString(5, alamat);
            p.executeUpdate();

            GetData();
            NonAktif();
            Kosongkan();

            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        } catch (SQLException ex) {
            Logger.getLogger(Form_Pelanggan.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error Simpan Data: " + ex.getMessage());
        }
    }
    
   
   public void UbahData() {
        LoadData();
        
         if (id_pelanggan.isEmpty() || nama_pelanggan.isEmpty() || jenis_kelamin == null || jenis_kelamin.isEmpty() || no_hp.isEmpty() || alamat.isEmpty() || "Tidak dipilih".equals(jenis_kelamin)) {
            JOptionPane.showMessageDialog(null, "Semua data harus diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE pelanggan SET nama_pelanggan = ?, jenis_kelamin = ?, no_hp = ?, alamat = ? WHERE id_pelanggan = ?";

        try (Connection con = Koneksi.KoneksiDb();
             PreparedStatement p = con.prepareStatement(sql)) {

            p.setString(1, nama_pelanggan);
            p.setString(2, jenis_kelamin);
            p.setString(3, no_hp);
            p.setString(4, alamat);
            p.setString(5, id_pelanggan); 
            
            if(p.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            } else {
                 JOptionPane.showMessageDialog(null, "Data Gagal Diubah atau ID tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            GetData(); 
            Kosongkan();
            Nonaktif();
        } catch (SQLException err) {
            Logger.getLogger(Form_Pelanggan.class.getName()).log(Level.SEVERE, "Error Ubah Data", err);
            JOptionPane.showMessageDialog(null, "Error Ubah Data: " + err.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void HapusData() {
        LoadData();

        int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data pelanggan " + id_pelanggan + "?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if (pesan == JOptionPane.OK_OPTION) {
            
            // **PENTING: Menggunakan PreparedStatement untuk DELETE data**
            String sql = "DELETE FROM pelanggan WHERE id_pelanggan = ?";
            
            try (Connection con = Koneksi.KoneksiDb();
                 PreparedStatement p = con.prepareStatement(sql)) {

                p.setString(1, id_pelanggan);
                p.executeUpdate();

                GetData();
                Kosongkan(); // Untuk Mengkosongkan form setelah hapus
                NonAktif(); // Untuk menonAktifkan form setelah hapus

                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            } catch (SQLException error) {
                 Logger.getLogger(Form_Pelanggan.class.getName()).log(Level.SEVERE, null, error);
                JOptionPane.showConfirmDialog(null, "Error Hapus Data: " + error.getMessage());
            }
        }
    }

    public void NonAktif() {
        txt_idpelanggan.setEnabled(false);
        txt_namapelanggan.setEnabled(false);
        rb_lakilaki.setEnabled(false);
        rb_perempuan.setEnabled(false);
        txt_no_hp.setEnabled(false);
        txtarea_alamat.setEnabled(false);
        btn_simpan.setEnabled(false);
        btn_edit.setEnabled(false);
        btn_hapus.setEnabled(false);
        btn_tambah.setEnabled(true); 
    }
    
    public void Aktif() {
        txt_idpelanggan.setEnabled(false); 
        txt_namapelanggan.setEnabled(true);
        rb_lakilaki.setEnabled(true);
        rb_perempuan.setEnabled(true);
        txt_no_hp.setEnabled(true);
        txtarea_alamat.setEnabled(true);
        btn_simpan.setEnabled(true);
        btn_tambah.setEnabled(false);
        txt_namapelanggan.requestFocus();
    }
  
    public void Kosongkan() {
        txt_namapelanggan.setText("");
        txt_idpelanggan.setText(""); 
        buttonGroup1.clearSelection(); 
        txt_no_hp.setText("");
        txtarea_alamat.setText("");
        IdOtomatis(); 
    }
    public void Cari() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        String keyword = "%" + txt_cari.getText() + "%";
        String sql = "SELECT * FROM pelanggan WHERE nama_pelanggan LIKE ?";

        try (Connection con = Koneksi.KoneksiDb();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, keyword);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] ob = new Object[5];
                    ob[0] = rs.getString(1);
                    ob[1] = rs.getString(2);
                    ob[2] = rs.getString(3);
                    ob[3] = rs.getString(4);
                    ob[4] = rs.getString(5);

                    model.addRow(ob);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(Form_Pelanggan.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Error Cari Data: " + e.getMessage());
        }
    }    
    
    
    
@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lb_keluar = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lb_catatantransaksi = new javax.swing.JLabel();
        lb_update = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lb_transaksi = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt_idpelanggan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_namapelanggan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        rb_lakilaki = new javax.swing.JRadioButton();
        rb_perempuan = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txt_no_hp = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtarea_alamat = new javax.swing.JTextArea();
        txt_cari = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_pelanggan = new javax.swing.JTable();
        btn_tambah = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        lb_tentang = new javax.swing.JLabel();
        lb_id = new javax.swing.JLabel();
        lb_jenis = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));

        jPanel2.setBackground(new java.awt.Color(255, 204, 51));
        jPanel2.setPreferredSize(new java.awt.Dimension(1368, 85));

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
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lb_keluar)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel3.setPreferredSize(new java.awt.Dimension(1138, 598));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 385, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("DATA PELANGGAN");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("KASIR");

        lb_catatantransaksi.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lb_catatantransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/report (1).png"))); // NOI18N
        lb_catatantransaksi.setText("Catatan Transaksi");
        lb_catatantransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_catatantransaksiMouseClicked(evt);
            }
        });

        lb_update.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lb_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/table (1).png"))); // NOI18N
        lb_update.setText("Update Status Meja");
        lb_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_updateMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        jLabel16.setText("Data Pelanggan");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/dashboard (1) (1).png"))); // NOI18N
        jLabel10.setText("Dashboard");

        lb_transaksi.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lb_transaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        lb_transaksi.setText("Transaksi");
        lb_transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_transaksiMouseClicked(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(1078, 541));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("ID Pelanggan");

        txt_idpelanggan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_idpelanggan.setPreferredSize(new java.awt.Dimension(250, 25));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setText("Nama Pelanggan");

        txt_namapelanggan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_namapelanggan.setPreferredSize(new java.awt.Dimension(250, 25));
        txt_namapelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_namapelangganKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setText("Jenis Kelamin");

        rb_lakilaki.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rb_lakilaki);
        rb_lakilaki.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rb_lakilaki.setText("Laki-laki");

        rb_perempuan.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rb_perempuan);
        rb_perempuan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rb_perempuan.setText("Perempuan");
        rb_perempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_perempuanActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setText("No Handphone");

        txt_no_hp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_no_hp.setPreferredSize(new java.awt.Dimension(250, 25));
        txt_no_hp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_no_hpActionPerformed(evt);
            }
        });
        txt_no_hp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_no_hpKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_no_hpKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("Alamat");

        txtarea_alamat.setColumns(20);
        txtarea_alamat.setRows(5);
        txtarea_alamat.setPreferredSize(new java.awt.Dimension(250, 50));
        jScrollPane1.setViewportView(txtarea_alamat);

        txt_cari.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_cari.setPreferredSize(new java.awt.Dimension(650, 30));
        txt_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cariKeyTyped(evt);
            }
        });

        tabel_pelanggan.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_pelanggan.setPreferredSize(new java.awt.Dimension(700, 385));
        tabel_pelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_pelangganMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_pelanggan);

        btn_tambah.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/add (2).png"))); // NOI18N
        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_simpan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/bookmark (1).png"))); // NOI18N
        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_edit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/edit (1).png"))); // NOI18N
        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_hapus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/hapus (1).png"))); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Search (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rb_lakilaki)
                        .addGap(38, 38, 38)
                        .addComponent(rb_perempuan))
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(txt_idpelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_namapelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_no_hp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(btn_tambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_edit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_hapus)))
                        .addGap(73, 73, 73))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_tambah)
                            .addComponent(btn_simpan)
                            .addComponent(btn_edit)
                            .addComponent(btn_hapus)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txt_idpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_namapelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rb_lakilaki)
                            .addComponent(rb_perempuan))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_no_hp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );

        lb_tentang.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_tentang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/tentang (1).png"))); // NOI18N
        lb_tentang.setText("Tentang");
        lb_tentang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_tentangMouseClicked(evt);
            }
        });

        lb_id.setForeground(new java.awt.Color(255, 255, 255));
        lb_id.setText("id");

        lb_jenis.setForeground(new java.awt.Color(255, 255, 255));
        lb_jenis.setText("jenis");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lb_jenis)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_id)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(lb_transaksi)
                    .addComponent(jLabel10)
                    .addComponent(jLabel16)
                    .addComponent(lb_update)
                    .addComponent(lb_catatantransaksi)
                    .addComponent(lb_tentang))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1040, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1007, Short.MAX_VALUE)
                        .addGap(85, 85, 85))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_id)
                        .addComponent(lb_jenis)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(lb_transaksi)
                        .addGap(18, 18, 18)
                        .addComponent(lb_catatantransaksi)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(lb_update)
                        .addGap(18, 18, 18)
                        .addComponent(lb_tentang))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(414, 414, 414))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void rb_perempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_perempuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_perempuanActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        SimpanData();
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
         UbahData();
         Aktif();
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
         HapusData();
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        Kosongkan();
        Aktif();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void tabel_pelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_pelangganMouseClicked
        SelectData();
    }//GEN-LAST:event_tabel_pelangganMouseClicked

    private void txt_namapelangganKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_namapelangganKeyTyped
        if(Character.isDigit(evt.getKeyChar())){
            evt.consume();
            JOptionPane.showMessageDialog(null, "pada Kolom nama hanya bisa dimasukan karakter huruf");
        } else {
        }
    }//GEN-LAST:event_txt_namapelangganKeyTyped

    private void txt_no_hpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_no_hpKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
        evt.consume();
        JOptionPane.showMessageDialog(null, "Pada Kolom no hanphone hanya bisa memasukan karater angka");
        }
    }//GEN-LAST:event_txt_no_hpKeyTyped

    private void txt_no_hpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_no_hpKeyReleased
        String input = txt_no_hp.getText();
        if(input.length()>13){
            JOptionPane.showMessageDialog(rootPane, "Jumlah input melebihi batas");
            txt_no_hp.setText("");
        }
    }//GEN-LAST:event_txt_no_hpKeyReleased

    private void txt_cariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyTyped
        Cari();
    }//GEN-LAST:event_txt_cariKeyTyped

    private void txt_no_hpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_no_hpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_no_hpActionPerformed

    private void lb_transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_transaksiMouseClicked
       new Form_Transaksi().show();
        this.dispose();
        
        try {
        Connection c = Koneksi.KoneksiDb();
        Statement s = c.createStatement();

        String sql = "Select * from pengguna Where id_pegawai = '"+lb_id.getText()+"'";
        ResultSet r = s.executeQuery(sql);

        if (r.next()) {
            if (lb_id.getText().equals(r.getString("username")) &&
                r.getString("jenis").equals("kasir")) {
                Form_Transaksi.lb_jabatan.setText(r.getString(4));
                Form_Transaksi.txt_idpegawai.setText(r.getString(5));
            }
        }
      }catch(Exception e){
        }
    }//GEN-LAST:event_lb_transaksiMouseClicked

    private void lb_catatantransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_catatantransaksiMouseClicked
        new Form_CatatanTransaksi().show();
        this.dispose();

        try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * From pengguna Where id_pegawai = '"+lb_id.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_id.getText().equals(r.getString("id_pegawai")) &&
                   r.getString("jenis").equals("Kasir")) {
                    Form_CatatanTransaksi.txt_jabatan.setText(r.getString(4));
                    Form_CatatanTransaksi.txt_idpegawai.setText(r.getString(5));
                }
            }

        }catch (Exception e) {
        }
    }//GEN-LAST:event_lb_catatantransaksiMouseClicked

    private void lb_updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_updateMouseClicked
        new Form_UpdateStatusMeja().show();
        this.dispose();

        try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * From pengguna Where id_pegawai = '"+lb_id.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_id.getText().equals(r.getString("username")) &&
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

            String sql = "Select * From pengguna Where id_pegawai = '"+lb_id.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_id.getText().equals(r.getString("id_pegawai")) &&
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Form_Pelanggan().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_catatantransaksi;
    public static javax.swing.JLabel lb_id;
    public static javax.swing.JLabel lb_jenis;
    private javax.swing.JLabel lb_keluar;
    private javax.swing.JLabel lb_tentang;
    private javax.swing.JLabel lb_transaksi;
    private javax.swing.JLabel lb_update;
    private javax.swing.JRadioButton rb_lakilaki;
    private javax.swing.JRadioButton rb_perempuan;
    private javax.swing.JTable tabel_pelanggan;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_idpelanggan;
    private javax.swing.JTextField txt_namapelanggan;
    private javax.swing.JTextField txt_no_hp;
    private javax.swing.JTextArea txtarea_alamat;
    // End of variables declaration//GEN-END:variables

    private void Nonaktif() {
         }
    private void selectData() {
         }
}
