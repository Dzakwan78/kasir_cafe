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


public final class Form_Meja extends javax.swing.JFrame {
    private final DefaultTableModel model;
    String id_meja, no_meja, jenis_meja,jml_kursi, status, id_pegawai;

    public Form_Meja() {
        initComponents();
        Nonaktif();
        
        model = new DefaultTableModel();
        tabel_meja.setModel(model);
        model.addColumn("ID Meja");
        model.addColumn("No Meja");
        model.addColumn("Kategori");
        model.addColumn("Jumlah Kursi");
        model.addColumn("Status");
        
        GetData();
        IdOtomatis();
    }

    public void GetData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        // **PERHATIAN:** Hindari memanggil KoneksiDb() berulang kali. Simpan hasilnya di variabel Connection.
        try(Connection con = Koneksi.KoneksiDb()){ // Gunakan try-with-resources untuk mejatup Connection secara otomatis (direkomendasikan)
            if (con == null) return; // Keluar jika koneksi gagal

            Statement stat = con.createStatement(); // TIDAK perlu casting (Statement) di sini
            String sql = "Select id_meja, no_meja, jenis_meja, jml_kursi, status, id_pegawai From meja"; // Sesuaikan urutan kolom

            try(ResultSet rs = stat.executeQuery(sql)){
                while(rs.next()){
                    Object[] obj = new Object[6];
                    obj[0] = rs.getString("id_meja");
                    obj[1] = rs.getString("no_meja");
                    obj[2] = rs.getString("jenis_meja");
                    obj[3] = rs.getString("jml_kursi");
                    obj[4] = rs.getString("status");
                    obj[5] = rs.getString("id_pegawai");
                    
                    model.addRow(obj);
                }
            } // ResultSet akan ditutup secara otomatis
            
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void SelectData(){
        int i = tabel_meja.getSelectedRow();
        if (i== -1)
        {
            return;
        }txt_idmeja.setText(""+model.getValueAt(i, 0));
        txt_nomeja.setText(""+model.getValueAt(i, 1));
        cb_kategori.setSelectedItem(""+model.getValueAt(i, 2));
        txt_jumlahkursi.setText(""+model.getValueAt(i, 3));
        cb_status.setSelectedItem(""+model.getValueAt(i, 4));
        btn_hapus.setEnabled(true);
        btn_edit.setEnabled(true);                
    }
    
    public void IdOtomatis(){
        // **PENYESUAIAN:** Menghapus casting ke com.mysql.cj.jdbc.Statement
        try(Connection con = Koneksi.KoneksiDb()){
            if (con == null) return; 

            Statement st = con.createStatement();
            String sql = "Select id_meja From meja order by id_meja desc";
            
            try(ResultSet rs = st.executeQuery(sql)){
                if(rs.next()){
                    String id = rs.getString("id_meja").substring(2); // Asumsi id_meja: MN001, ambil "001"
                    int no = Integer.parseInt(id) + 1;
                    String AN = String.valueOf(no);
                    String nol = "";
                    
                    if(AN.length()==1)
                    {nol="00";}
                    else if(AN.length()==2)
                    {nol="0";}
                    else if(AN.length()==3)
                    {nol="";}
                        txt_idmeja.setText("MJ"+nol+AN);      
                }else{
                    txt_idmeja.setText("MJ001");
                }
            }
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void LoadData(){
    id_meja = txt_idmeja.getText();
    no_meja = txt_nomeja.getText();
    jenis_meja = (String)cb_kategori.getSelectedItem();
    
    // Pastikan jml_kursi dan status hanya berisi angka
    if (txt_jumlahkursi.getText().isEmpty() || cb_status.getSelectedItem()==null){
        jml_kursi = "0"; // Defaultkan ke 0 jika kosong
        status = "0";
    } else {
        jml_kursi = txt_jumlahkursi.getText();
        status = (String)cb_status.getSelectedItem();
    }

    id_pegawai = lb_id.getText();
}
    
    public void SimpanData(){
    LoadData();
    
    try(Connection con = Koneksi.KoneksiDb()){
        if (con == null) return; 
        
        String sql = "INSERT INTO meja (id_meja, no_meja, jenis_meja, jml_kursi, status, id_pegawai) VALUES (?, ?, ?, ?, ?, ?)";
        
        try(PreparedStatement p = con.prepareStatement(sql)){
             p.setString(1, id_meja);
             p.setString(2, no_meja);
             p.setString(3, jenis_meja);
             
             // **Perubahan Inti: Konversi ke Integer**
             // Menggunakan Integer.parseInt() dan PreparedStatement.setInt()
             p.setInt(4, Integer.parseInt(jml_kursi)); // Konversi String jml_kursi menjadi Integer
             p.setInt(5, Integer.parseInt(status));  // Konversi String status menjadi Integer
             
             p.setString(6, id_pegawai);
             
             p.executeUpdate();
        }
        
        GetData();
        Nonaktif();
            
        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
    }catch(NumberFormatException e){
        // Menangkap kesalahan jika Harga atau Stok bukan angka
        JOptionPane.showMessageDialog(null, "Harga dan Stok harus berupa angka yang valid.", "Input Error", JOptionPane.ERROR_MESSAGE);
        Logger.getLogger(Form_Meja.class.getName()).log(Level.WARNING, "Format Angka Salah", e);
    } catch(SQLException ex){
        Logger.getLogger(Form_Meja.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + ex.getMessage());
    }         
}
    
    public void UbahData(){
    LoadData();
    
    try(Connection con = Koneksi.KoneksiDb()){
        if (con == null) return; 
        
        String sql = "UPDATE meja SET no_meja=?, jenis_meja=?, jml_kursi=?, status=?, id_pegawai=? WHERE id_meja=?";
        
        try(PreparedStatement p = con.prepareStatement(sql)){
            p.setString(1, no_meja);
            p.setString(2, jenis_meja);
            
            // **Perubahan Inti: Konversi ke Integer**
            // Menggunakan Integer.parseInt() dan PreparedStatement.setInt()
            p.setInt(3, Integer.parseInt(jml_kursi)); // Konversi String jml_kursi menjadi Integer
            p.setInt(4, Integer.parseInt(status));  // Konversi String status menjadi Integer
            
            p.setString(5, id_pegawai);
            p.setString(6, id_meja);
            
            p.executeUpdate ();
        }
        
        GetData();
        Kosongkan();
        
        JOptionPane.showMessageDialog(null, "Data Berhasil Dirubah");   
    }catch(NumberFormatException e){
        // Menangkap kesalahan jika Harga atau Stok bukan angka
        JOptionPane.showMessageDialog(null, "Harga dan Stok harus berupa angka yang valid.", "Input Error", JOptionPane.ERROR_MESSAGE);
        Logger.getLogger(Form_Meja.class.getName()).log(Level.WARNING, "Format Angka Salah", e);
    } catch(SQLException err){
        JOptionPane.showMessageDialog(null, err.getMessage());
    }
}
    
    public void HapusData(){
        LoadData();
        
        int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data meja " + id_meja + "?","Konfirmasi Hapus",JOptionPane.OK_CANCEL_OPTION);
        if(pesan == JOptionPane.OK_OPTION){
            // **PENYESUAIAN:** Menghapus casting dan menggunakan PreparedStatement
            try(Connection con = Koneksi.KoneksiDb()){
                if (con == null) return; 
                
                String sql = "DELETE From meja Where id_meja=?";
                try(PreparedStatement p = con.prepareStatement(sql)){
                    p.setString(1, id_meja);
                    p.executeUpdate();
                }
                
                GetData ();
                
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            }catch(SQLException error){
                JOptionPane.showMessageDialog(null, error.getMessage());
            }
        }
    }
        
        public void Nonaktif(){
            txt_idmeja.setEnabled(false);
            txt_nomeja.setEnabled(false); 
            cb_kategori.setEnabled(false);
            txt_jumlahkursi.setEnabled(false);
            cb_status.setEnabled(false);
            btn_simpan.setEnabled(false);
            btn_edit.setEnabled(false);
            btn_hapus.setEnabled(false);
        }
        public void Aktif(){
            txt_idmeja.setEnabled(true);
            txt_nomeja.setEnabled(true);
            cb_kategori.setEnabled(true);
            // Ubah dari setEditable(true) menjadi setEnabled(true)
            txt_jumlahkursi.setEnabled(true); // <-- PASTIKAN INI TRUE
            cb_status.setEnabled(true);
            btn_simpan.setEnabled(true);
            btn_tambah.setEnabled(false);
            txt_nomeja.requestFocus();
        }
        
        public void Kosongkan(){
            txt_nomeja.setText("");
            cb_kategori.setSelectedItem("Tidak dipilih");
            txt_jumlahkursi.setText("");
            cb_status.setSelectedItem("Tidak dipilih");
        }
        
        public void Cari(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try(Connection con = Koneksi.KoneksiDb()){
            if (con == null) return; 

            Statement st = con.createStatement();
            // Perhatian: SQL Anda memiliki spasi di `nama meja`, seharusnya `no_meja`? Saya asumsikan `no_meja`.
            String sql = "Select * From meja Where no_meja like '%" + txt_cari.getText() + "%'";
            
            try(ResultSet rs = st.executeQuery(sql)){
                while(rs.next()){
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
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_idmeja = new javax.swing.JTextField();
        txt_nomeja = new javax.swing.JTextField();
        cb_kategori = new javax.swing.JComboBox<>();
        txt_jumlahkursi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        txt_cari = new javax.swing.JTextField();
        lb_cari = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_meja = new javax.swing.JTable();
        lb_id = new javax.swing.JLabel();
        cb_status = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        lb_keluar = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lb_datameja = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lb_laporantransaksi = new javax.swing.JLabel();
        lb_laporanpendapatan = new javax.swing.JLabel();
        lb_logaktivitas = new javax.swing.JLabel();
        lb_tentang = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1036, 482));

        txt_idmeja.setPreferredSize(new java.awt.Dimension(300, 25));
        txt_idmeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idmejaActionPerformed(evt);
            }
        });

        txt_nomeja.setPreferredSize(new java.awt.Dimension(500, 25));
        txt_nomeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nomejaActionPerformed(evt);
            }
        });
        txt_nomeja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nomejaKeyTyped(evt);
            }
        });

        cb_kategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak dipilih", "single", "double", "family", "exclusif" }));
        cb_kategori.setPreferredSize(new java.awt.Dimension(300, 25));

        txt_jumlahkursi.setPreferredSize(new java.awt.Dimension(500, 25));
        txt_jumlahkursi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_jumlahkursiKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("ID Meja        :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("No Meja       :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Kategori       :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Jumlah Kursi :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText(" Status         :");

        btn_tambah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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

        txt_cari.setPreferredSize(new java.awt.Dimension(650, 30));
        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });

        lb_cari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Search (1).png"))); // NOI18N

        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 200));

        tabel_meja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Meja", "No Meja", "Kategori", "Jumlah Kursi", "Status"
            }
        ));
        tabel_meja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_mejaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_meja);

        lb_id.setForeground(new java.awt.Color(255, 255, 255));
        lb_id.setText("id");

        cb_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak dipilih", "1", "0" }));
        cb_status.setPreferredSize(new java.awt.Dimension(300, 25));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(50, 50, 50)))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_nomeja, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(txt_idmeja, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cb_kategori, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cb_status, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_jumlahkursi, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(lb_id))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_tambah)
                                .addGap(34, 34, 34)
                                .addComponent(btn_simpan)
                                .addGap(39, 39, 39)
                                .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(btn_hapus))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lb_cari))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_id)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_idmeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nomeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_jumlahkursi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cb_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah)
                    .addComponent(btn_simpan)
                    .addComponent(btn_edit)
                    .addComponent(btn_hapus))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_cari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 204, 102));
        jPanel2.setPreferredSize(new java.awt.Dimension(1036, 85));

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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_keluar)
                .addGap(59, 59, 59))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lb_keluar)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/dashboard (1) (1).png"))); // NOI18N
        jLabel10.setText("Dashboard");

        lb_datameja.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_datameja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/menu.png"))); // NOI18N
        lb_datameja.setText("Data Menu");
        lb_datameja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_datamejaMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/table (1).png"))); // NOI18N
        jLabel12.setText("Data Meja");

        lb_laporantransaksi.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_laporantransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/report (1).png"))); // NOI18N
        lb_laporantransaksi.setText("Laporan Transaksi");
        lb_laporantransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_laporantransaksiMouseClicked(evt);
            }
        });

        lb_laporanpendapatan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_laporanpendapatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        lb_laporanpendapatan.setText("Laporan Pendapatan");
        lb_laporanpendapatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_laporanpendapatanMouseClicked(evt);
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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("MANAGER");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(lb_tentang)
                    .addComponent(lb_logaktivitas)
                    .addComponent(lb_laporanpendapatan)
                    .addComponent(lb_laporantransaksi)
                    .addComponent(jLabel12)
                    .addComponent(lb_datameja)
                    .addComponent(jLabel10))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addGap(66, 66, 66)
                .addComponent(jLabel10)
                .addGap(26, 26, 26)
                .addComponent(lb_datameja)
                .addGap(27, 27, 27)
                .addComponent(jLabel12)
                .addGap(27, 27, 27)
                .addComponent(lb_laporantransaksi)
                .addGap(18, 18, 18)
                .addComponent(lb_laporanpendapatan)
                .addGap(18, 18, 18)
                .addComponent(lb_logaktivitas)
                .addGap(18, 18, 18)
                .addComponent(lb_tentang)
                .addContainerGap(379, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("DATA MEJA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 931, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 931, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idmejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idmejaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idmejaActionPerformed

    private void txt_nomejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nomejaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nomejaActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        SimpanData();
        Kosongkan();
        IdOtomatis();
        Nonaktif();
        btn_tambah.setEnabled(true);
        btn_simpan.setEnabled(false);
        btn_edit.setEnabled(false);
        btn_hapus.setEnabled(false);
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
       IdOtomatis();
       Kosongkan();
       Aktif();
       txt_nomeja.requestFocus();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        UbahData();
        Kosongkan();
        Nonaktif();
        btn_tambah.setEnabled(true);
        btn_simpan.setEnabled(false);
        btn_edit.setEnabled(false);
        btn_hapus.setEnabled(false);
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        HapusData();
        Kosongkan();
        IdOtomatis();
        Nonaktif();
        btn_tambah.setEnabled(true);
        btn_simpan.setEnabled(false);
        btn_edit.setEnabled(false);
        btn_hapus.setEnabled(false);
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void tabel_mejaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_mejaMouseClicked
        SelectData();
        Aktif();
        txt_idmeja.setEnabled(false);
        btn_simpan.setEnabled(false);
    }//GEN-LAST:event_tabel_mejaMouseClicked

    private void txt_nomejaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nomejaKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Pada kolom no meja hanya bisa memasukan karakter angka");
        }
    }//GEN-LAST:event_txt_nomejaKeyTyped

    private void txt_jumlahkursiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahkursiKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Pada kolom jumlah kursi hanya bisa memasukan karakter angka");
        }
    }//GEN-LAST:event_txt_jumlahkursiKeyTyped

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        Cari();
    }//GEN-LAST:event_txt_cariActionPerformed

    private void lb_datamejaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_datamejaMouseClicked
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

    private void lb_laporanpendapatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_laporanpendapatanMouseClicked
        new Form_LaporanPendapatan().show();
        this.dispose();
           
           
           try {
            Connection c = Koneksi.KoneksiDb();
            Statement s = c.createStatement();

            String sql = "Select * from pengguna where id_pegawai = '"+lb_id.getText()+"'";
            ResultSet r = s.executeQuery(sql);

            if(r.next()) {
                if(lb_id.getText().equals(r.getString("id_pegawai"))) {
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

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Form_Meja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Meja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Meja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Meja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Meja().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox<String> cb_kategori;
    private javax.swing.JComboBox<String> cb_status;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_cari;
    private javax.swing.JLabel lb_datameja;
    public static javax.swing.JLabel lb_id;
    private javax.swing.JLabel lb_keluar;
    private javax.swing.JLabel lb_laporanpendapatan;
    private javax.swing.JLabel lb_laporantransaksi;
    private javax.swing.JLabel lb_logaktivitas;
    private javax.swing.JLabel lb_tentang;
    private javax.swing.JTable tabel_meja;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_idmeja;
    private javax.swing.JTextField txt_jumlahkursi;
    private javax.swing.JTextField txt_nomeja;
    // End of variables declaration//GEN-END:variables
}
