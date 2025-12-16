package kasir_cafe;

import Aplikasi.Koneksi;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.security.MessageDigest;


public class Form_Login extends javax.swing.JFrame { 
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Form_Login.class.getName());
    
    String username, password; // Variabel untuk menyimpan username dan password (Baris 11)
  
    public Form_Login() {
        initComponents();
    }
    
    
    public void updateactive(){
        // Mengambil teks dari TextField dengan nama 'txt_username'
        String user = txt_username.getText();
        
        // Menyiapkan nilai status 'active' yang akan di-update
        String active = "1"; // Status '1' berarti aktif/login

        try {
            // Mendapatkan koneksi ke database
            Connection con = Koneksi.KoneksiDb();
            
            // Query SQL untuk mengupdate status 'active' menjadi '1' 
            // pada tabel 'pengguna' untuk username tertentu
            String sql = "Update pengguna Set active = ? Where username = ?";
            
            // Menyiapkan statement SQL
            PreparedStatement p = con.prepareStatement(sql);
            
            // Mengisi placeholder (?) pada query
            p.setString(1, active); // Mengatur kolom 'active' menjadi nilai "1" (aktif)
            p.setString(2, user);   // Mengatur kolom 'username' sesuai dengan user yang didapat
            p.executeUpdate();
            p.close();

        // Menangkap exception jika terjadi kesalahan dalam proses database
        } catch (Exception e) {
            // ... Sisa isi blok catch (tidak terlihat di gambar) ...
        }
}
     public static String passwordHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(password.getBytes());
            byte[] rbt = md.digest();
            StringBuilder sb = new StringBuilder();

        for (byte b : rbt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }   catch (Exception e) {
        return null;
    }
}  
    
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        btn_login = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo1.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("LOGIN");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel3.setText("USERNAME");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, -1, -1));

        txt_username.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel1.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel4.setText("PASSWORD");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 350, -1, -1));

        txt_password.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel1.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, -1, -1));

        btn_login.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        btn_login.setText("LOGIN");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });
        jPanel1.add(btn_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, -1, -1));

        btn_cancel.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        btn_cancel.setText("CANCEL");
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });
        jPanel1.add(btn_cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, -1, -1));

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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
      String rawPassword = new String(txt_password.getPassword());
    
    String hashedPassword = passwordHash(rawPassword); 
   
    if (hashedPassword == null) {
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat memproses password.");
        return; 
    }

    try {
        Connection c = Koneksi.KoneksiDb();
        Statement s = c.createStatement();
        
        String sql = "Select * From pengguna Where username='" + txt_username.getText()
                   + "' And password='" + hashedPassword + "'";
        
        ResultSet r = s.executeQuery(sql);

        // Memeriksa apakah ada hasil (pengguna ditemukan)
        if(r.next()) {  
            
            if(r.getString("jenis").equals("admin")) { // Cukup cek jenis saja
                Form_DashboardAdmin a = new Form_DashboardAdmin();
                Form_DashboardAdmin.lb_username.setText(r.getString(1));
                Form_DashboardAdmin.lb_jenis.setText(r.getString(4));
                Form_DashboardAdmin.lb_idpegawai.setText(r.getString(5));
                a.setVisible(true);
                
            } else if (r.getString("jenis").equals("kasir")) {
                Form_DashboardKasir a = new Form_DashboardKasir();
                Form_DashboardKasir.lb_username.setText(r.getString(1));
                Form_DashboardKasir.lb_jenis.setText(r.getString(4));
                Form_DashboardKasir.lb_idpegawai.setText(r.getString(5));
                a.setVisible(true);
                
            } else if (r.getString("jenis").equals("manager")) {
                Form_DashboardManager a = new Form_DashboardManager();
                Form_DashboardManager.lb_username.setText(r.getString(1));
                Form_DashboardManager.lb_jenis.setText(r.getString(4));
                Form_DashboardManager.lb_idpegawai.setText(r.getString(5));
                a.setVisible(true);
            }
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Periksa kemballi username dan password Anda!");
            txt_password.requestFocus();
        }
    } catch(Exception e) {
        System.out.println("Gagal: " + e.getMessage());
    }
    
    updateactive();
    }//GEN-LAST:event_btn_loginActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_cancelActionPerformed

    public static void main(String args[]) {
        

        java.awt.EventQueue.invokeLater(() -> new Form_Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_login;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
