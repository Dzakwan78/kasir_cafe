package kasir_cafe;

import Aplikasi.Koneksi;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;


public final class Form_Pengguna extends javax.swing.JFrame {
    private final DefaultTableModel model;
    private final DefaultTableModel model2;
    String username, password, email, jenis, id_pegawai, created_at, update_at, active, nama_pegawai;

    public Form_Pengguna() {
        initComponents();
        Nonaktif();
        
        model = new DefaultTableModel();
        tabel_pengguna.setModel(model);
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("Email");
        model.addColumn("Jabatan");
        model.addColumn("ID Pegawai");
        model.addColumn("Active");

        model2 = new DefaultTableModel();
        tabel_pegawai.setModel(model2);
        model2.addColumn("ID Pegawai");
        model2.addColumn("Nama Pegawai");
        
        tabel_pegawai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                SelectDataPegawai();
            }
        });
        
        // Membuat tabel merespons klik mouse (Implementasi untuk SelectDataPengguna)
        tabel_pengguna.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                SelectDataPengguna();
            }
        });

        GetDataPengguna();
        GetDataPegawai();
}
    
public void GetDataPengguna() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try (Connection con = Koneksi.KoneksiDb();
             Statement stat = con.createStatement();
             ResultSet rs = stat.executeQuery("SELECT username, password, email, jenis, id_pegawai, active FROM pengguna")) {

            while (rs.next()) {
                Object[] obj = new Object[6];
                obj[0] = rs.getString("username");
                obj[1] = rs.getString("password");
                obj[2] = rs.getString("email");
                obj[3] = rs.getString("jenis");
                obj[4] = rs.getString("id_pegawai");
                obj[5] = rs.getString("active");

                model.addRow(obj);
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data pengguna: " + error.getMessage());
        }
    }
  
 public void GetDataPegawai() {
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        try (Connection con = Koneksi.KoneksiDb();
             Statement stat = con.createStatement();
             ResultSet rs = stat.executeQuery("SELECT id_pegawai, nama_pegawai FROM pegawai")) {

            while (rs.next()) {
                Object[] obj = new Object[2];
                obj[0] = rs.getString("id_pegawai");
                obj[1] = rs.getString("nama_pegawai");

                model2.addRow(obj);
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data pegawai: " + error.getMessage());
        }
    }
 public void SelectDataPengguna() {
        int i = tabel_pengguna.getSelectedRow();
        if (i == -1) {
            return;
        }
        // Mengisi field input dari baris yang dipilih
        txt_username.setText(model.getValueAt(i, 0).toString());
        txt_password.setText(model.getValueAt(i, 1).toString());
        txt_email.setText(model.getValueAt(i, 2).toString());
        cb_jabatan.setSelectedItem(model.getValueAt(i, 3).toString());
        txt_idpegawai.setText(model.getValueAt(i, 4).toString());
        cb_active.setSelectedItem(model.getValueAt(i, 5).toString());
        
        btn_hapus.setEnabled(true);
        btn_edit.setEnabled(true);
        btn_simpan.setEnabled(false); 
        btn_tambah.setEnabled(true);
        Aktif(); 
        
    }
 
public void SelectDataPegawai() {
        int i = tabel_pegawai.getSelectedRow();
        if (i == -1) {
            return;
        }
        txt_idpegawai.setText(model2.getValueAt(i, 0).toString());
    }

public void LoadData() {
        username = txt_username.getText();
        password = txt_password.getText();
        email = txt_email.getText();
        jenis = (String)cb_jabatan.getSelectedItem();
        id_pegawai = txt_idpegawai.getText();
        active = (String)cb_active.getSelectedItem();
    }

public void EditData() {
    LoadData(); 
    if (username.isEmpty() || password.isEmpty() || email.isEmpty() || id_pegawai.isEmpty() || "Tidak dipilih".equals(jenis) || "Tidak dipilih".equals(active)) {
        JOptionPane.showMessageDialog(null, "Semua data pengguna harus diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Untuk Menampilkan dialog konfirmasi sebelum update
    int pesan = JOptionPane.showConfirmDialog(null, 
        "Anda yakin ingin mengubah data pengguna **" + username + "**?", 
        "Konfirmasi Perubahan Data", 
        JOptionPane.OK_CANCEL_OPTION);

    // Untuk mengecek apakah pengguna menekan tombol OK (Ya/Setuju)
    if (pesan == JOptionPane.OK_OPTION) {
        
        // Untuk UPDATE tabel 'pengguna'
        String sql = "UPDATE pengguna SET username = ?, password = ?, email = ?, jenis = ?, active = ?, update_at = NOW() WHERE id_pegawai = ?";
        
        try (Connection con = Koneksi.KoneksiDb();
              PreparedStatement p = con.prepareStatement(sql)) {
            
            // Mengisi PreparedStatement
            p.setString(1, username);
            p.setString(2, password);
            p.setString(3, email);
            p.setString(4, jenis);
            p.setString(5, active);
            p.setString(6, id_pegawai); 
            
            int rowsAffected = p.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data Pengguna **" + id_pegawai + "** Berhasil Diubah!");
            } else {
                JOptionPane.showMessageDialog(null, "Data Pengguna Gagal Diubah. Username mungkin tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException err) {
            Logger.getLogger(Form_Pengguna.class.getName()).log(Level.SEVERE, "Error Ubah Data Pengguna", err); 
            JOptionPane.showMessageDialog(null, "Error Ubah Data Pengguna: " + err.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            GetDataPengguna(); 
            Kosongkan();
            Nonaktif();
            btn_tambah.setEnabled(true);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Perubahan data dibatalkan.", "Dibatalkan", JOptionPane.INFORMATION_MESSAGE);
        Kosongkan();
        Nonaktif();
        btn_tambah.setEnabled(true);
    }
}

public void HapusData() {
        LoadData(); 
        
        if (username.isEmpty()) {
             JOptionPane.showMessageDialog(null, "Silakan pilih data pengguna yang ingin dihapus.");
             return;
        }
        
        int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data pengguna " + username + " ?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        
        if (pesan == JOptionPane.OK_OPTION) {
            String sql = "DELETE FROM pengguna WHERE username = ?";
            try (Connection c = Koneksi.KoneksiDb();
                 PreparedStatement p = c.prepareStatement(sql)) {
                
                p.setString(1, username);
                int rowsAffected = p.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan atau gagal dihapus.");
                }
            } catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + error.getMessage());
            } finally {
                GetDataPengguna();
                Kosongkan();
                Nonaktif();
                btn_tambah.setEnabled(true);
            }
        }
    }

public void Nonaktif() {
        txt_username.setEnabled(false);
        txt_password.setEnabled(false);
        txt_email.setEnabled(false);
        cb_jabatan.setEnabled(false);
        txt_idpegawai.setEnabled(false); 
        cb_active.setEnabled(false);
        btn_simpan.setEnabled(false);
        btn_edit.setEnabled(false);
        btn_hapus.setEnabled(false);
        btn_tambah.setEnabled(true);
    }

    public void Aktif() {
        txt_username.setEnabled(true);
        txt_password.setEnabled(true);
        txt_email.setEnabled(true);
        cb_jabatan.setEnabled(true);
        cb_active.setEnabled(true);
        btn_simpan.setEnabled(true);
        btn_tambah.setEnabled(false);
        btn_edit.setEnabled(true);
        txt_username.requestFocus();
    }
    
    public void Kosongkan() {
        txt_username.setText("");
        txt_password.setText("");
        txt_email.setText("");
        cb_jabatan.setSelectedItem("Tidak dipilih");
        txt_idpegawai.setText("");
        cb_active.setSelectedItem("Tidak dipilih");
    }
    
    public void CariPengguna() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try (Connection con = Koneksi.KoneksiDb();
             PreparedStatement pst = con.prepareStatement("SELECT username, password, email, jenis, id_pegawai, active FROM pengguna WHERE username LIKE ?")) {
            
            pst.setString(1, "%" + txt_caripengguna.getText() + "%");
            
            try (ResultSet rs = pst.executeQuery()) {
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
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mencari pengguna: " + e.getMessage());
        }
    }
    
    public void CariPegawai() {
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        try (Connection con = Koneksi.KoneksiDb();
             PreparedStatement pst = con.prepareStatement("SELECT id_pegawai, nama_pegawai FROM pegawai WHERE nama_pegawai LIKE ?")) {
            
            pst.setString(1, "%" + txt_caripegawai.getText() + "%");
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Object[] ob = new Object[2];
                    ob[0] = rs.getString(1);
                    ob[1] = rs.getString(2);
                    
                    model2.addRow(ob);
                }
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mencari pegawai: " + e.getMessage());
        }
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lb_keluar = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_password = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cb_jabatan = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txt_idpegawai = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cb_active = new javax.swing.JComboBox<>();
        txt_caripegawai = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_pegawai = new javax.swing.JTable();
        txt_caripengguna = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_pengguna = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lb_datapegawai = new javax.swing.JLabel();
        lb_datapengguna = new javax.swing.JLabel();
        lb_logaktivitas = new javax.swing.JLabel();
        lb_tentang = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 600));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_keluar)
                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lb_keluar)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(1337, 790));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Username");

        txt_username.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Password");

        txt_password.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("E Mail");

        txt_email.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Jabatan");

        cb_jabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak dipilih", "admin", "kasir", "manager" }));
        cb_jabatan.setPreferredSize(new java.awt.Dimension(72, 25));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("ID Pegawai");

        txt_idpegawai.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Active");

        cb_active.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak dipilih", "1", "0" }));
        cb_active.setPreferredSize(new java.awt.Dimension(72, 25));

        txt_caripegawai.setPreferredSize(new java.awt.Dimension(64, 25));
        txt_caripegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_caripegawaiKeyPressed(evt);
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
        jScrollPane1.setViewportView(tabel_pegawai);

        txt_caripengguna.setPreferredSize(new java.awt.Dimension(70, 25));
        txt_caripengguna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_caripenggunaKeyPressed(evt);
            }
        });

        tabel_pengguna.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tabel_pengguna);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Search (1).png"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Search (1).png"))); // NOI18N

        btn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/add (2).png"))); // NOI18N
        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/bookmark (1).png"))); // NOI18N
        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/edit (1).png"))); // NOI18N
        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/hapus (1).png"))); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)
                        .addComponent(jLabel5)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3)
                        .addComponent(txt_idpegawai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cb_jabatan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_email, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                        .addComponent(txt_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cb_active, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(txt_caripegawai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_tambah)
                        .addGap(18, 18, 18)
                        .addComponent(btn_simpan)
                        .addGap(18, 18, 18)
                        .addComponent(btn_edit)
                        .addGap(18, 18, 18)
                        .addComponent(btn_hapus)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txt_caripengguna, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(38, 38, 38))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_caripegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel9))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_caripengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jLabel10)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_idpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_active, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_tambah)
                        .addComponent(btn_simpan))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_hapus)
                        .addComponent(btn_edit)))
                .addContainerGap(293, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("DATA PENGGUNA");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("ADMIN");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/dashboard (1) (1).png"))); // NOI18N
        jLabel12.setText("Dashboard");

        lb_datapegawai.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_datapegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        lb_datapegawai.setText("Data Pegawai");
        lb_datapegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_datapegawaiMouseClicked(evt);
            }
        });

        lb_datapengguna.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_datapengguna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        lb_datapengguna.setText("Data Pengguna");
        lb_datapengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_datapenggunaMouseClicked(evt);
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

        lb_tentang.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_tentang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/tentang (1).png"))); // NOI18N
        lb_tentang.setText("Tentang");
        lb_tentang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_tentangMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_datapegawai)
                    .addComponent(jLabel12)
                    .addComponent(lb_datapengguna)
                    .addComponent(lb_logaktivitas)
                    .addComponent(lb_tentang)
                    .addComponent(jLabel11))
                .addGap(86, 86, 86)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 974, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 774, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(lb_datapegawai)
                        .addGap(18, 18, 18)
                        .addComponent(lb_datapengguna)
                        .addGap(18, 18, 18)
                        .addComponent(lb_logaktivitas)
                        .addGap(18, 18, 18)
                        .addComponent(lb_tentang))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 660, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        Kosongkan();
        Aktif();
        txt_username.requestFocus(); 
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        LoadData(); // Muat semua data dari field ke variabel global

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || id_pegawai.isEmpty() || jenis.equals("Tidak dipilih")) {
            JOptionPane.showMessageDialog(null, "Semua kolom wajib diisi!");
            return;
        }

        String sql = "INSERT INTO pengguna (username, password, email, jenis, id_pegawai, active, created_at, update_at) VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())";
        
        try (Connection c = Koneksi.KoneksiDb();
             PreparedStatement p = c.prepareStatement(sql)) {
            
            p.setString(1, username);
            p.setString(2, password);
            p.setString(3, email);
            p.setString(4, jenis);
            p.setString(5, id_pegawai);
            p.setString(6, active);
            
            p.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan data: " + e.getMessage());
        } finally {
            GetDataPengguna();
            Kosongkan();
            Nonaktif();
        }      
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        HapusData();
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void txt_caripegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_caripegawaiKeyPressed
        CariPengguna();
    }//GEN-LAST:event_txt_caripegawaiKeyPressed

    private void txt_caripenggunaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_caripenggunaKeyPressed
        CariPegawai();
    }//GEN-LAST:event_txt_caripenggunaKeyPressed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        EditData();
    }//GEN-LAST:event_btn_editActionPerformed

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

    private void lb_keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_keluarMouseClicked
        new Form_DashboardAdmin().show();
        this.dispose();
    }//GEN-LAST:event_lb_keluarMouseClicked

 
    public static void main(String args[]) {
      
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, (UnsupportedLookAndFeelException) ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Form_Pengguna().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox<String> cb_active;
    private javax.swing.JComboBox<String> cb_jabatan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_datapegawai;
    private javax.swing.JLabel lb_datapengguna;
    private javax.swing.JLabel lb_keluar;
    private javax.swing.JLabel lb_logaktivitas;
    private javax.swing.JLabel lb_tentang;
    private javax.swing.JTable tabel_pegawai;
    private javax.swing.JTable tabel_pengguna;
    private javax.swing.JTextField txt_caripegawai;
    private javax.swing.JTextField txt_caripengguna;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_idpegawai;
    private javax.swing.JTextField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables

   
}
