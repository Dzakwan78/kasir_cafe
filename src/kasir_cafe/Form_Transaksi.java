package kasir_cafe;

import Aplikasi.Koneksi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


public class Form_Transaksi extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Form_Transaksi.class.getName());
    java.util.Date tglsekarang = new java.util.Date();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private String tanggal = format.format(tglsekarang);
    private DefaultTableModel model; // Model Tabel Menu
    private DefaultTableModel model2; // Model Tabel Keranjang

    public Form_Transaksi() {
        initComponents();
        Nonaktif(); 

        // Model untuk Daftar Menu yang tersedia
        model = new DefaultTableModel();
        tabel_menu.setModel(model);
        model.addColumn("ID Menu");
        model.addColumn("Nama Menu");
        model.addColumn("Kategori"); // Tetap kategori jika di DB kolom ke-3 adalah jenis/kategori
        model.addColumn("Harga");
        model.addColumn("Stok");

        // Model untuk Keranjang Belanja (Detail Transaksi sementara)
        model2 = new DefaultTableModel();
        tabel_dettransaksi.setModel(model2);
        model2.addColumn("ID Transaksi");
        model2.addColumn("ID Menu");
        model2.addColumn("Nama Menu"); // Kolom Baru: Indeks 2
        model2.addColumn("Harga");     // Indeks 3
        model2.addColumn("Jumlah");    // Indeks 4
        model2.addColumn("Total");     // Indeks 5
        
        SetJam(); 
        Utama();  
        GetDataMenu(); 
        
        txt_tanggal.setText(tanggal);      
        txt_totalbayar.setText("0");       
        txt_bayar.setText("0");            
        txt_kembali.setText("0");

        // Pengaturan awal komponen
        txt_jumlah.setEnabled(false);      
        txt_bayar.setEnabled(false);
        txt_namamenu.setEnabled(false);
        btn_tambah2.setEnabled(false);     
        btn_kurang.setEnabled(false);      
        btn_proses.setEnabled(false);      
        btn_cetak.setEnabled(false);       
        btn_carimeja.setEnabled(false);    
        btn_caripelanggan.setEnabled(false); 
        txt_carimenu.setEnabled(false);    
    } 
    
    public void GetDataMenu() {
        model.setRowCount(0); // Cara lebih bersih menghapus data tabel

        try {
            Connection con = Koneksi.KoneksiDb();
            Statement stat = con.createStatement();
            // Query mengambil data dari tabel 'menu'
            String sql = "SELECT * FROM menu";
            ResultSet rs = stat.executeQuery(sql);

            while(rs.next()){
                Object obj[] = new Object[5];
                obj[0] = rs.getString("id_menu");
                obj[1] = rs.getString("nama_menu"); // Memastikan menggunakan nama_menu
                obj[2] = rs.getString("jenis");     // Jika di DB nama kolomnya 'jenis'
                obj[3] = rs.getString("harga");
                obj[4] = rs.getString("stok");

                model.addRow(obj);
            }
            rs.close();
            stat.close();
        } catch(SQLException error) {
            JOptionPane.showMessageDialog(null, "Gagal ambil data menu: " + error.getMessage());
        }
    }
    
    public void SelectDataMenu() {
        int i = tabel_menu.getSelectedRow();
        if (i == -1) {
            return;
        }
        // Ambil data dari tabel berdasarkan baris yang diklik
        txt_idmenu.setText("" + model.getValueAt(i, 0));
        txt_namamenu.setText("" + model.getValueAt(i, 1));
        txt_harga.setText("" + model.getValueAt(i, 3)); 
        txt_jumlah.setEnabled(true);
        btn_tambah2.setEnabled(true);
        txt_jumlah.requestFocus();
    }

    public void GetDetaDetTransaksi() {
        int rowMenu = tabel_menu.getSelectedRow();
        if (rowMenu == -1) {
            JOptionPane.showMessageDialog(null, "Pilih menu terlebih dahulu di tabel menu!");
            return;
        }

        // Ambil nama menu dari tabel menu (kolom indeks 1)
        String namaMenu = tabel_menu.getValueAt(rowMenu, 1).toString();

        model2.addRow(new Object[] {
            txt_idtransaksi.getText(),
            txt_idmenu.getText(),
            namaMenu,           // Nama Menu tampil di kolom indeks 2 keranjang
            txt_harga.getText(),
            txt_jumlah.getText(),
            txt_hargatotal.getText()
        });
    }
    
    public void IdOtomatis() {
        try {
            Statement st = Koneksi.KoneksiDb().createStatement();
            String sql = "SELECT id_transaksi FROM transaksi ORDER BY id_transaksi DESC LIMIT 1";
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                String id_str = rs.getString("id_transaksi").substring(2);
                int AN_int = (Integer.parseInt(id_str)) + 1;
                String AN_str = String.valueOf(AN_int);
                String nol = "";

                if (AN_str.length() == 1) {
                    nol = "00";
                } else if (AN_str.length() == 2) {
                    nol = "0";
                } 
                txt_idtransaksi.setText("TR" + nol + AN_str);
            } else {
                txt_idtransaksi.setText("TR001");
            }
            rs.close();
            st.close();
        } catch(SQLException error) {
            JOptionPane.showMessageDialog(null, "Error ID Otomatis: " + error.getMessage());
        }
    }
    
    public void Nonaktif() {
        txt_idtransaksi.setEditable(false);
        txt_tanggal.setEnabled(false);
        txt_jam.setEnabled(false);
        txt_idpegawai.setEnabled(false);
        txt_idmenu.setEnabled(false);
        txt_harga.setEnabled(false);
        txt_hargatotal.setEnabled(false);
        txt_totalbayar.setEnabled(false);
        txt_kembali.setEnabled(false);
        txt_idpelanggan.setEnabled(false);
        txt_idmeja.setEnabled(false);
    }
    
    public void Cari() {
        model.setRowCount(0);
        
        try {
            Connection con = Koneksi.KoneksiDb();
            Statement st = con.createStatement();
            // Pencarian berdasarkan ID atau Nama Menu
            String sql = "SELECT * FROM menu WHERE id_menu LIKE '%" + txt_carimenu.getText() + "%'" +
                         " OR nama_menu LIKE '%" + txt_carimenu.getText() + "%'";
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                Object ob[] = new Object[5];
                ob[0] = rs.getString("id_menu");
                ob[1] = rs.getString("nama_menu"); // Perbaikan: Menggunakan nama kolom string agar aman
                ob[2] = rs.getString("jenis");
                ob[3] = rs.getString("harga");
                ob[4] = rs.getString("stok");
                model.addRow(ob);
            }
        } catch(SQLException e) {
            System.out.println("Error Cari: " + e);
        }
    }
    
    public void SetJam() {
        ActionListener taskPerform = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                int jam = cal.get(java.util.Calendar.HOUR_OF_DAY);
                int menit = cal.get(java.util.Calendar.MINUTE);
                int detik = cal.get(java.util.Calendar.SECOND);
                txt_jam.setText(String.format("%02d:%02d:%02d", jam, menit, detik));
            }
        };
        new Timer(1000, taskPerform).start();
    }
    
    public void TotalBiaya() { 
        int jumlahBaris = tabel_dettransaksi.getRowCount();
        int totalBiaya = 0; 
        
        for (int i = 0; i < jumlahBaris; i++) {
            // Sesuai indeks model2: Harga(3), Jumlah(4)
            int harga = Integer.parseInt(tabel_dettransaksi.getValueAt(i, 3).toString());
            int jumlah = Integer.parseInt(tabel_dettransaksi.getValueAt(i, 4).toString());
            totalBiaya += (harga * jumlah);
        }
        txt_totalbayar.setText(String.valueOf(totalBiaya));
    }

    public void TambahTransaksi() {
        if(txt_idmenu.getText().equals("") || txt_jumlah.getText().equals("")) {
             JOptionPane.showMessageDialog(null, "Pilih Menu dan Isi Jumlah!");
        } else {
            try {
                int jumlah = Integer.parseInt(txt_jumlah.getText());
                int harga = Integer.parseInt(txt_harga.getText());
                int total = jumlah * harga;
                
                txt_hargatotal.setText(String.valueOf(total));
               
                GetDetaDetTransaksi();
                TotalBiaya();
                Kosongkan2();
                btn_proses.setEnabled(true);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Masukkan jumlah angka yang valid!");
            }
        }
    }
    
    public void Kosong() {
        model2.setRowCount(0);
    }

    public void Utama() {
        txt_idpelanggan.setText("");
        txt_carimenu.setText("");
        txt_idmenu.setText("");
        txt_idmeja.setText("");
        txt_harga.setText("");
        txt_jumlah.setText("");
        txt_hargatotal.setText("");
        IdOtomatis();
    }
    
    public void Kosongkan() {
        txt_carimenu.setText("");
        txt_totalbayar.setText("0");
        txt_bayar.setText("0");
        txt_kembali.setText("0");
    }

    public void Kosongkan2() {
        txt_idmenu.setText("");
        txt_harga.setText("");
        txt_jumlah.setText("");
        txt_hargatotal.setText("");
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lb_keluar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_idtransaksi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_tanggal = new javax.swing.JTextField();
        txt_jam = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_idpegawai = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_idpelanggan = new javax.swing.JTextField();
        txt_carimenu = new javax.swing.JTextField();
        btn_caripelanggan = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txt_idmeja = new javax.swing.JTextField();
        btn_carimeja = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_menu = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_dettransaksi = new javax.swing.JTable();
        txt_idmenu = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_harga = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_jumlah = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_hargatotal = new javax.swing.JTextField();
        btn_tambah2 = new javax.swing.JButton();
        btn_kurang = new javax.swing.JButton();
        btn_proses = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_totalbayar = new javax.swing.JTextField();
        txt_bayar = new javax.swing.JTextField();
        txt_kembali = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        btn_cetak = new javax.swing.JButton();
        txt_namamenu = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lb_catatantransaksi = new javax.swing.JLabel();
        lb_datapelanggan = new javax.swing.JLabel();
        lb_update = new javax.swing.JLabel();
        lb_tentang = new javax.swing.JLabel();
        lb_jabatan = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(1300, 700));

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
                .addGap(64, 64, 64))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lb_keluar)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("ID Transaksi");

        txt_idtransaksi.setPreferredSize(new java.awt.Dimension(100, 28));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Tanggal / Jam");

        txt_tanggal.setPreferredSize(new java.awt.Dimension(100, 28));

        txt_jam.setMinimumSize(new java.awt.Dimension(100, 28));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("ID Pegawai");

        txt_idpegawai.setPreferredSize(new java.awt.Dimension(100, 28));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("ID Pelanggan :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Pencarian Menu :");

        txt_idpelanggan.setMinimumSize(new java.awt.Dimension(64, 28));
        txt_idpelanggan.setPreferredSize(new java.awt.Dimension(64, 28));
        txt_idpelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idpelangganActionPerformed(evt);
            }
        });

        txt_carimenu.setPreferredSize(new java.awt.Dimension(70, 28));

        btn_caripelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Search (1).png"))); // NOI18N
        btn_caripelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_caripelangganActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("ID Meja :");

        txt_idmeja.setPreferredSize(new java.awt.Dimension(64, 28));

        btn_carimeja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Search (1).png"))); // NOI18N
        btn_carimeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_carimejaActionPerformed(evt);
            }
        });

        tabel_menu.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_menuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_menu);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("ID Menu :");

        tabel_dettransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tabel_dettransaksi);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Harga :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Jumlah :");

        txt_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahActionPerformed(evt);
            }
        });

        jLabel11.setText("Harga Total :");

        btn_tambah2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/add (2).png"))); // NOI18N
        btn_tambah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambah2ActionPerformed(evt);
            }
        });

        btn_kurang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Min (1).png"))); // NOI18N
        btn_kurang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kurangActionPerformed(evt);
            }
        });

        btn_proses.setText("Proses");
        btn_proses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prosesActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Total Bayar :");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Bayar :");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Kembali :");

        txt_totalbayar.setPreferredSize(new java.awt.Dimension(77, 28));

        txt_bayar.setPreferredSize(new java.awt.Dimension(77, 28));
        txt_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bayarActionPerformed(evt);
            }
        });

        txt_kembali.setPreferredSize(new java.awt.Dimension(77, 28));

        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_cetak.setText("Cetak");
        btn_cetak.setPreferredSize(new java.awt.Dimension(73, 23));
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
            }
        });

        jLabel18.setText("Nama Menu :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 34, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(txt_idtransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_jam, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_idpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(95, 95, 95)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(jLabel5)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_tambah)
                            .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_hargatotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txt_jumlah, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_harga, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_idmenu, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_proses)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(btn_tambah2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_kurang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(64, 64, 64)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_namamenu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_totalbayar, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(txt_bayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_kembali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_carimenu, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_idpelanggan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_caripelanggan)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_idmeja, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_carimeja))))
                .addGap(34, 34, 34))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(txt_idpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_caripelanggan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_carimenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn_carimeja)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_idmeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_tambah2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_idmenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8))
                                    .addComponent(btn_kurang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_proses))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txt_totalbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(txt_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_idtransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_hargatotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(txt_namamenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txt_jam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_idpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_tambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(230, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("TRANSAKSI");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("KASIR");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/dashboard (1) (1).png"))); // NOI18N
        jLabel16.setText("Dashboard");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/report (1).png"))); // NOI18N
        jLabel17.setText("Transaksi");

        lb_catatantransaksi.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_catatantransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/menu.png"))); // NOI18N
        lb_catatantransaksi.setText("Catatan Transaksi");
        lb_catatantransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_catatantransaksiMouseClicked(evt);
            }
        });

        lb_datapelanggan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_datapelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/teamwork (1).png"))); // NOI18N
        lb_datapelanggan.setText("Data Pelanggan");
        lb_datapelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_datapelangganMouseClicked(evt);
            }
        });

        lb_update.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lb_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/table (1).png"))); // NOI18N
        lb_update.setText("Update Status Meja");
        lb_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_updateMouseClicked(evt);
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

        lb_jabatan.setText("Jabatan");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(298, 298, 298)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addComponent(lb_jabatan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(lb_catatantransaksi)
                    .addComponent(lb_datapelanggan)
                    .addComponent(lb_update)
                    .addComponent(lb_tentang)
                    .addComponent(jLabel15))
                .addGap(67, 67, 67)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addComponent(lb_jabatan))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(lb_catatantransaksi)
                        .addGap(18, 18, 18)
                        .addComponent(lb_datapelanggan)
                        .addGap(18, 18, 18)
                        .addComponent(lb_update)
                        .addGap(18, 18, 18)
                        .addComponent(lb_tentang))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(167, 167, 167))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idpelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idpelangganActionPerformed
       
    }//GEN-LAST:event_txt_idpelangganActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        txt_jumlah.setEnabled(true);
        txt_bayar.setEnabled(true);
        btn_tambah2.setEnabled(true);
        btn_kurang.setEnabled(true);
        btn_proses.setEnabled(true);
        btn_cetak.setEnabled(true);
        btn_carimeja.setEnabled(true);
        btn_caripelanggan.setEnabled(true);
        txt_carimenu.setEnabled(true);
        
        Kosong();
        GetDataMenu();
        Kosongkan();
        Kosongkan2();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_caripelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_caripelangganActionPerformed
       Daftar_Pelanggan a =  new Daftar_Pelanggan();
       a.setVisible(true);
    }//GEN-LAST:event_btn_caripelangganActionPerformed

    private void btn_carimejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_carimejaActionPerformed
       Daftar_Meja a = new Daftar_Meja();
       a.setVisible(true);
    }//GEN-LAST:event_btn_carimejaActionPerformed

    private void tabel_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_menuMouseClicked
       SelectDataMenu();
       
       txt_jumlah.requestFocus();
    }//GEN-LAST:event_tabel_menuMouseClicked

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed
        int totharga = 0;
        int jumlah, harga;
        jumlah = Integer.parseInt(txt_jumlah.getText());
        harga = Integer.parseInt(txt_harga.getText());
        totharga = (jumlah * harga);
        
        txt_hargatotal.setText(String.valueOf(totharga));
    }//GEN-LAST:event_txt_jumlahActionPerformed

    private void txt_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bayarActionPerformed
       int bayar, total, kembalian;
       total = Integer.parseInt(txt_totalbayar.getText());
       bayar = Integer.parseInt(txt_bayar.getText());

       if (total > bayar) {
        // Logika JOptionPane (dianggap dihapus)
       } else {
            kembalian = bayar - total;
            txt_kembali.setText(String.valueOf(kembalian));
       }
    }//GEN-LAST:event_txt_bayarActionPerformed

    private void btn_tambah2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambah2ActionPerformed
       TambahTransaksi();
    }//GEN-LAST:event_btn_tambah2ActionPerformed

    private void btn_kurangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kurangActionPerformed
       DefaultTableModel model = (DefaultTableModel)tabel_dettransaksi.getModel();
       int row = tabel_dettransaksi.getSelectedRow();
       model.removeRow(row);
       TotalBiaya();
       txt_bayar.setText("0");
       txt_kembali.setText("0");
    }//GEN-LAST:event_btn_kurangActionPerformed

    private void btn_prosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prosesActionPerformed
       DefaultTableModel model = (DefaultTableModel) tabel_dettransaksi.getModel();

        // Ambil data dari TextField
        String notrans     = txt_idtransaksi.getText();
        String tanggal     = txt_tanggal.getText();
        String idpegawai   = txt_idpegawai.getText();
        String idpel       = txt_idpelanggan.getText();
        String meja        = txt_idmeja.getText();

        // Ambil Nama Menu (Jika ini ringkasan, pastikan txt_namamenu tidak kosong)
        String menuSummary = txt_namamenu.getText().isEmpty() ? "-" : txt_namamenu.getText();

        // Validasi Angka: Jika kosong atau bukan angka, beri nilai 0 agar database tidak error
        // Menggunakan replace untuk membersihkan karakter non-angka (seperti titik ribuan atau Rp)
        String subtotal   = txt_hargatotal.getText().replaceAll("[^0-9]", "");
        String totalbayar  = txt_totalbayar.getText().replaceAll("[^0-9]", "");
        String bayar       = txt_bayar.getText().replaceAll("[^0-9]", "");
        String kembali     = txt_kembali.getText().replaceAll("[^0-9]", "");

        // Jika hasil replace menyebabkan string kosong, set ke "0"
        if (subtotal.isEmpty()) subtotal = "0";
        if (totalbayar.isEmpty()) totalbayar = "0";
        if (bayar.isEmpty()) bayar = "0";
        if (kembali.isEmpty()) kembali = "0";

        try {
            // BLOK 1: INSERT DATA KE TABEL 'transaksi' (Master)
            Connection c = Koneksi.KoneksiDb();
            // Pastikan urutan kolom di database sesuai: id, tgl, id_peg, id_pel, meja, menu, sub, total, bayar, kembali
            String sqlMaster = "INSERT INTO transaksi VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement p1 = c.prepareStatement(sqlMaster);

            p1.setString(1, notrans);
            p1.setString(2, tanggal);
            p1.setString(3, idpegawai);
            p1.setString(4, idpel);
            p1.setString(5, meja);
            p1.setString(6, menuSummary);
            p1.setString(7, subtotal);
            p1.setString(8, totalbayar);
            p1.setString(9, bayar);
            p1.setString(10, kembali);

            p1.executeUpdate();
            p1.close();
            System.out.println("Data Master Transaksi Berhasil Disimpan");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Gagal simpan transaksi: " + ex.getMessage());
            Logger.getLogger(Form_Transaksi.class.getName()).log(Level.SEVERE, "Error Blok 1", ex);
        }

        try {
            // BLOK 2: INSERT DATA KE TABEL 'detail_transaksi' (Looping dari Tabel GUI)
            Connection c = Koneksi.KoneksiDb();
            int baris = tabel_dettransaksi.getRowCount();

            String sqlDetail = "INSERT INTO detail_transaksi (id_transaksi, id_menu, nama_menu, harga, jumlah, total_harga) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement p2 = c.prepareStatement(sqlDetail);

            for (int i = 0; i < baris; i++) {
                p2.setString(1, notrans);
                // Mengambil data dari kolom tabel (Pastikan indeks kolom 1, 2, 3, 4, 5 sesuai dengan posisi di JTable Anda)
                p2.setObject(2, tabel_dettransaksi.getValueAt(i, 1)); // ID Menu
                p2.setObject(3, tabel_dettransaksi.getValueAt(i, 2)); // Nama Menu
                p2.setObject(4, tabel_dettransaksi.getValueAt(i, 3)); // Harga Satuan
                p2.setObject(5, tabel_dettransaksi.getValueAt(i, 4)); // Jumlah Beli
                p2.setObject(6, tabel_dettransaksi.getValueAt(i, 5)); // Subtotal per Item

                p2.executeUpdate();
            }
            p2.close();
            JOptionPane.showMessageDialog(null, "Transaksi Berhasil Diproses!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Gagal simpan detail: " + ex.getMessage());
            Logger.getLogger(Form_Transaksi.class.getName()).log(Level.SEVERE, "Error Blok 2", ex);
        }

        // --- AKHIR DARI PROSES DATABASE ---

        // Reset Form dan Komponen UI
        Kosongkan();
        Utama();
        Kosong();
        IdOtomatis();  
        GetDataMenu();

        // Pengaturan Status Komponen
        txt_jumlah.setEnabled(false);
        txt_bayar.setEnabled(false);
        btn_tambah2.setEnabled(false);
        btn_kurang.setEnabled(false);
        btn_proses.setEnabled(false);
        btn_carimeja.setEnabled(false);
        btn_caripelanggan.setEnabled(false);
        txt_carimenu.setEnabled(false);
    }//GEN-LAST:event_btn_prosesActionPerformed

    private void lb_catatantransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_catatantransaksiMouseClicked
       new Form_CatatanTransaksi().show();
       this.dispose();
       
       try {
        Connection c = Koneksi.KoneksiDb();
        Statement s = c.createStatement();

        String sql = "Select * from pengguna Where id_pegawai = '"+txt_idpegawai.getText()+"'";
        ResultSet r = s.executeQuery(sql);

        if (r.next()) {
            if (txt_idpegawai.getText().equals(r.getString("id_pegawai")) &&
                r.getString("jenis").equals("kasir")) {
                Form_CatatanTransaksi.txt_jabatan.setText(r.getString(4));
                Form_CatatanTransaksi.txt_idpegawai.setText(r.getString(5));
            }
        }
      }catch(Exception e){
        }                            
    }//GEN-LAST:event_lb_catatantransaksiMouseClicked

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

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
         String nota = JOptionPane.showInputDialog("Masukan ID Transaksi");
    Connection con = null;
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String DB = "jdbc:mysql://localhost/db_cafe";
        String user = "root";
        String pass = "";

        con = DriverManager.getConnection(DB, user, pass);
        Statement stat = con.createStatement();

        try {
            String report = ("src\\kasir_cafe\\nota_kasir.jrxml");
            HashMap hash = new HashMap();
            hash.put("kode", nota);
            JasperReport jasperReport = JasperCompileManager.compileReport(report);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, con);
            JasperViewer.viewReport(jasperPrint, false);
        }catch (Exception ex) {
            Logger.getLogger(Form_Transaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }catch (Exception e) {

    }
    }//GEN-LAST:event_btn_cetakActionPerformed

   
    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(() -> new Form_Transaksi().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_carimeja;
    private javax.swing.JButton btn_caripelanggan;
    private javax.swing.JButton btn_cetak;
    private javax.swing.JButton btn_kurang;
    private javax.swing.JButton btn_proses;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_tambah2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_catatantransaksi;
    private javax.swing.JLabel lb_datapelanggan;
    public static javax.swing.JLabel lb_jabatan;
    private javax.swing.JLabel lb_keluar;
    private javax.swing.JLabel lb_tentang;
    private javax.swing.JLabel lb_update;
    private javax.swing.JTable tabel_dettransaksi;
    private javax.swing.JTable tabel_menu;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_carimenu;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_hargatotal;
    public static javax.swing.JTextField txt_idmeja;
    private javax.swing.JTextField txt_idmenu;
    public static javax.swing.JTextField txt_idpegawai;
    public static javax.swing.JTextField txt_idpelanggan;
    private javax.swing.JTextField txt_idtransaksi;
    private javax.swing.JTextField txt_jam;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_kembali;
    private javax.swing.JTextField txt_namamenu;
    private javax.swing.JTextField txt_tanggal;
    private javax.swing.JTextField txt_totalbayar;
    // End of variables declaration//GEN-END:variables
}
