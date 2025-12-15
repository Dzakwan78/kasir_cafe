package kasir_cafe;

import Aplikasi.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Form_Pegawai extends javax.swing.JFrame {

    private final DefaultTableModel model;
    String id_pegawai, nama_pegawai, jenis_kelamin, jenis, no_hp, alamat;

    public Form_Pegawai() {
        initComponents();
        NonAktif();
        
        model = new DefaultTableModel();
        tabel_pegawai.setModel(model);
        model.addColumn("ID Pegawai");
        model.addColumn("Nama Pegawai");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Jabatan");
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
             ResultSet rs = stat.executeQuery("SELECT * FROM pegawai")) {
            
             while (rs.next()) {
                Object[] obj = new Object[6];
                obj[0] = rs.getString("id_pegawai");
                obj[1] = rs.getString("nama_pegawai");
                obj[2] = rs.getString("jenis_kelamin");
                obj[3] = rs.getString("jenis");
                obj[4] = rs.getString("no_hp");
                obj[5] = rs.getString("alamat");

                model.addRow(obj);
             }
        } catch (SQLException error) {
            Logger.getLogger(Form_Pegawai.class.getName()).log(Level.SEVERE, null, error);
            JOptionPane.showMessageDialog(null, "Error mengambil data: " + error.getMessage());
        }
    }
    
    public void SelectData() {
        int i = tabel_pegawai.getSelectedRow();
        if (i == -1) {
            return;
        }
 
        String jk = model.getValueAt(i, 2) != null ? model.getValueAt(i, 2).toString() : "";
        
        txt_idpegawai.setText("" + model.getValueAt(i, 0));
        txt_namapegawai.setText("" + model.getValueAt(i, 1));
        
        if ("L".equalsIgnoreCase(jk)) { 
            rb_lakilaki.setSelected(true);
            rb_perempuan.setSelected(false); 
        } else if ("P".equalsIgnoreCase(jk)) {
            rb_perempuan.setSelected(true);
            rb_lakilaki.setSelected(false); 
        }
        
        
        Object jabatanValue = model.getValueAt(i, 3);
        if (jabatanValue != null) {
            cb_jabatan.setSelectedItem(jabatanValue);
        }

        txt_no_hp.setText("" + model.getValueAt(i, 4));
        txtarea_alamat.setText("" + model.getValueAt(i, 5));
        btn_hapus.setEnabled(true);
        btn_edit.setEnabled(true);
    }
    
    public void IdOtomatis() {
        try (Connection con = Koneksi.KoneksiDb();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id_pegawai FROM pegawai ORDER BY id_pegawai DESC LIMIT 1")) {
            
            if (rs.next()) {
                String id = rs.getString("id_pegawai").substring(2); // Ambil angka setelah "PG"
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
                txt_idpegawai.setText("PG" + nol + AN);
            } else {
                txt_idpegawai.setText("PG001");
            }
        } catch (SQLException error) {
            Logger.getLogger(Form_Pegawai.class.getName()).log(Level.SEVERE, null, error);
            JOptionPane.showMessageDialog(null, "Error membuat ID Otomatis: " + error.getMessage());
        } catch (NumberFormatException e) {
             Logger.getLogger(Form_Pegawai.class.getName()).log(Level.SEVERE, null, e);
             JOptionPane.showMessageDialog(null, "Error format ID Pegawai: " + e.getMessage());
        }
    }
    
    public void LoadData() {
        id_pegawai = txt_idpegawai.getText();
        nama_pegawai = txt_namapegawai.getText();
        jenis_kelamin = null;
        if (rb_lakilaki.isSelected()) {
            jenis_kelamin = "L";
        } else if (rb_perempuan.isSelected()) {
            jenis_kelamin = "P";
        }

        jenis = (String)cb_jabatan.getSelectedItem();
        no_hp = txt_no_hp.getText();
        alamat = txtarea_alamat.getText();
    }
    
    public void SimpanData() {
        LoadData();

        String sql = "INSERT INTO pegawai (id_pegawai, nama_pegawai, jenis_kelamin, jenis, no_hp, alamat) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Koneksi.KoneksiDb();
             PreparedStatement p = con.prepareStatement(sql)) {

            p.setString(1, id_pegawai);
            p.setString(2, nama_pegawai);
            p.setString(3, jenis_kelamin);
            p.setString(4, jenis);
            p.setString(5, no_hp);
            p.setString(6, alamat);
            
            p.executeUpdate();

            GetData();
            NonAktif();
            Kosongkan(); 

            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        } catch (SQLException ex) {
            Logger.getLogger(Form_Pegawai.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error Simpan Data: " + ex.getMessage());
        }
    }
    
   
   public void UbahData() {
        LoadData();
        
         if (id_pegawai.isEmpty() || nama_pegawai.isEmpty() || jenis_kelamin == null || jenis_kelamin.isEmpty() || no_hp.isEmpty() || alamat.isEmpty() || "Tidak dipilih".equals(jenis)) {
            JOptionPane.showMessageDialog(null, "Semua data harus diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE pegawai SET nama_pegawai = ?, jenis_kelamin = ?, jenis = ?, no_hp = ?, alamat = ? WHERE id_pegawai = ?";

        try (Connection con = Koneksi.KoneksiDb();
             PreparedStatement p = con.prepareStatement(sql)) {

            p.setString(1, nama_pegawai);
            p.setString(2, jenis_kelamin);
            p.setString(3, jenis);
            p.setString(4, no_hp);
            p.setString(5, alamat);
            p.setString(6, id_pegawai); 
            
            if(p.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            } else {
                 JOptionPane.showMessageDialog(null, "Data Gagal Diubah atau ID tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            GetData(); 
            Kosongkan();
            Nonaktif();
        } catch (SQLException err) {
            Logger.getLogger(Form_Pegawai.class.getName()).log(Level.SEVERE, "Error Ubah Data", err);
            JOptionPane.showMessageDialog(null, "Error Ubah Data: " + err.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void HapusData() {
        LoadData();

        int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data pegawai " + id_pegawai + "?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if (pesan == JOptionPane.OK_OPTION) {
           
            String sql = "DELETE FROM pegawai WHERE id_pegawai = ?";
            
            try (Connection con = Koneksi.KoneksiDb();
                 PreparedStatement p = con.prepareStatement(sql)) {

                p.setString(1, id_pegawai);
                p.executeUpdate();

                GetData();
                Kosongkan(); // Untuk mengkosongkan form setelah hapus
                NonAktif(); // Untuk menonaktifkan form setelah hapus

                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            } catch (SQLException error) {
                 Logger.getLogger(Form_Pegawai.class.getName()).log(Level.SEVERE, null, error);
                JOptionPane.showConfirmDialog(null, "Error Hapus Data: " + error.getMessage());
            }
        }
    }

    public void NonAktif() {
        txt_idpegawai.setEnabled(false);
        txt_namapegawai.setEnabled(false);
        rb_lakilaki.setEnabled(false);
        rb_perempuan.setEnabled(false);
        cb_jabatan.setEnabled(false);
        txt_no_hp.setEnabled(false);
        txtarea_alamat.setEnabled(false);
        btn_simpan.setEnabled(false);
        btn_edit.setEnabled(false);
        btn_hapus.setEnabled(false);
        btn_tambah.setEnabled(true); // Aktifkan tombol tambah
        // Aktifkan tombol batal jika ada
    }
    
    public void Aktif() {
        txt_idpegawai.setEnabled(false); 
        txt_namapegawai.setEnabled(true);
        rb_lakilaki.setEnabled(true);
        rb_perempuan.setEnabled(true);
        cb_jabatan.setEnabled(true);
        txt_no_hp.setEnabled(true);
        txtarea_alamat.setEnabled(true);
        btn_simpan.setEnabled(true);
        btn_tambah.setEnabled(false);
        txt_namapegawai.requestFocus();
    }
  
    public void Kosongkan() {
        txt_namapegawai.setText("");
        txt_idpegawai.setText(""); 
        buttonGroup1.clearSelection(); 
        cb_jabatan.setSelectedItem("Tidak Dipilih");
        txt_no_hp.setText("");
        txtarea_alamat.setText("");
        IdOtomatis(); 
    }
    public void Cari() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        String keyword = "%" + txt_cari.getText() + "%";
        String sql = "SELECT * FROM pegawai WHERE nama_pegawai LIKE ?";

        // Menggunakan PreparedStatement untuk pencarian agar lebih aman
        try (Connection con = Koneksi.KoneksiDb();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, keyword);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] ob = new Object[6];
                    ob[0] = rs.getString(1);
                    ob[1] = rs.getString(2);
                    ob[2] = rs.getString(3);
                    ob[3] = rs.getString(4);
                    ob[4] = rs.getString(5);
                    ob[5] = rs.getString(6);

                    model.addRow(ob);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(Form_Pegawai.class.getName()).log(Level.SEVERE, null, e);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lb_datapengguna = new javax.swing.JLabel();
        lb_tentang = new javax.swing.JLabel();
        lb_logaktivitas = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lb_datapegawai = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt_idpegawai = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_namapegawai = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        rb_lakilaki = new javax.swing.JRadioButton();
        rb_perempuan = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        cb_jabatan = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txt_no_hp = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtarea_alamat = new javax.swing.JTextArea();
        txt_cari = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_pegawai = new javax.swing.JTable();
        btn_tambah = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

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
                .addContainerGap(1051, Short.MAX_VALUE)
                .addComponent(lb_keluar)
                .addGap(38, 38, 38))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lb_keluar)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("DATA PEGAWAI");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("ADMIN");

        lb_datapengguna.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_datapengguna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        lb_datapengguna.setText("Data Pengguna");
        lb_datapengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_datapenggunaMouseClicked(evt);
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

        lb_logaktivitas.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_logaktivitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        lb_logaktivitas.setText("Log Aktivitas Pegawai");
        lb_logaktivitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_logaktivitasMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/dashboard (1) (1).png"))); // NOI18N
        jLabel10.setText("Dashboard");

        lb_datapegawai.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_datapegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        lb_datapegawai.setText("Data Pegawai");
        lb_datapegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_datapegawaiMouseClicked(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(1078, 541));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("ID Pegawai");

        txt_idpegawai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_idpegawai.setPreferredSize(new java.awt.Dimension(250, 25));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setText("Nama Pegawai");

        txt_namapegawai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_namapegawai.setPreferredSize(new java.awt.Dimension(250, 25));
        txt_namapegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_namapegawaiKeyTyped(evt);
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

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setText("Jabatan");

        cb_jabatan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cb_jabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak dipilih", "Admin", "Manager", "Kasir" }));
        cb_jabatan.setPreferredSize(new java.awt.Dimension(250, 25));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setText("No Handphone");

        txt_no_hp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_no_hp.setPreferredSize(new java.awt.Dimension(250, 25));
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

        tabel_pegawai.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_pegawai.setPreferredSize(new java.awt.Dimension(700, 385));
        tabel_pegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_pegawaiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_pegawai);

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(cb_jabatan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rb_lakilaki)
                        .addGap(38, 38, 38)
                        .addComponent(rb_perempuan))
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(txt_idpegawai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_namapegawai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_no_hp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addGap(44, 44, 44))
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
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1040, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txt_idpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_namapegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rb_lakilaki)
                            .addComponent(rb_perempuan))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_no_hp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_tambah)
                        .addComponent(btn_simpan)
                        .addComponent(btn_edit)
                        .addComponent(btn_hapus)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(127, 127, 127)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1114, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_logaktivitas)
                            .addComponent(lb_tentang)
                            .addComponent(lb_datapengguna)
                            .addComponent(jLabel10)
                            .addComponent(lb_datapegawai))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(lb_datapegawai)
                        .addGap(18, 18, 18)
                        .addComponent(lb_datapengguna)
                        .addGap(18, 18, 18)
                        .addComponent(lb_logaktivitas)
                        .addGap(18, 18, 18)
                        .addComponent(lb_tentang)))
                .addGap(782, 782, 782))
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

    private void tabel_pegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_pegawaiMouseClicked
        SelectData();
    }//GEN-LAST:event_tabel_pegawaiMouseClicked

    private void txt_namapegawaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_namapegawaiKeyTyped
        if(Character.isDigit(evt.getKeyChar())){
            evt.consume();
            JOptionPane.showMessageDialog(null, "pada Kolom nama hanya bisa dimasukan karakter huruf");
        } else {
        }
    }//GEN-LAST:event_txt_namapegawaiKeyTyped

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

    private void lb_keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_keluarMouseClicked
       new Form_DashboardAdmin().show();
        this.dispose();
    }//GEN-LAST:event_lb_keluarMouseClicked

    private void lb_datapegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_datapegawaiMouseClicked
        new Form_Pegawai().show();
        this.dispose();
    }//GEN-LAST:event_lb_datapegawaiMouseClicked

    private void lb_datapenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_datapenggunaMouseClicked
        new Form_Pengguna().show();
        this.dispose();
    }//GEN-LAST:event_lb_datapenggunaMouseClicked

    private void lb_logaktivitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_logaktivitasMouseClicked
       new Form_LogAktivitasPegawaiAdmin().show();
       this.dispose();
    }//GEN-LAST:event_lb_logaktivitasMouseClicked

    private void lb_tentangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_tentangMouseClicked
        new Form_TentangAdmin().show();
        this.dispose();
    }//GEN-LAST:event_lb_tentangMouseClicked

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Form_Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Form_Pegawai().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cb_jabatan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_datapegawai;
    private javax.swing.JLabel lb_datapengguna;
    private javax.swing.JLabel lb_keluar;
    private javax.swing.JLabel lb_logaktivitas;
    private javax.swing.JLabel lb_tentang;
    private javax.swing.JRadioButton rb_lakilaki;
    private javax.swing.JRadioButton rb_perempuan;
    private javax.swing.JTable tabel_pegawai;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_idpegawai;
    private javax.swing.JTextField txt_namapegawai;
    private javax.swing.JTextField txt_no_hp;
    private javax.swing.JTextArea txtarea_alamat;
    // End of variables declaration//GEN-END:variables

    private void Nonaktif() {
         }
    private void selectData() {
         }
}
