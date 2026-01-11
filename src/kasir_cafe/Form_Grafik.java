package kasir_cafe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.FlowLayout;

// JFreeChart Library
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

public class Form_Grafik extends javax.swing.JFrame {
    
   private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Form_Grafik.class.getName());
   private ChartPanel chartPanel;
   private JPanel mainPanel; // Panel penampung utama
    
    public Form_Grafik() {
        initComponents();
        customInit();
        setTitle("Laporan Grafik Penjualan Cafe");
        setLocationRelativeTo(null);

        // Set tanggal default (Hari ini)
        tgl_awal.setDate(new java.util.Date());
        tgl_akhir.setDate(new java.util.Date());
        
        // Mengatur format tampilan pada JDateChooser agar mudah dibaca
        tgl_awal.setDateFormatString("dd MMM yyyy");
        tgl_akhir.setDateFormatString("dd MMM yyyy");
    }
    
    
    private void customInit() {
        // Menggunakan BorderLayout agar grafik fleksibel di tengah
        getContentPane().setLayout(new BorderLayout());

        // Membuat panel penampung kontrol di bagian atas (NORTH)
        JPanel panelKontrol = new JPanel();
        panelKontrol.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelKontrol.setBackground(new Color(245, 245, 245));

        // Memberikan ukuran lebar yang pasti agar tanggal terlihat jelas
        tgl_awal.setPreferredSize(new Dimension(180, 30));
        tgl_akhir.setPreferredSize(new Dimension(180, 30));

        panelKontrol.add(new JLabel("Periode:"));
        panelKontrol.add(tgl_awal);
        panelKontrol.add(new JLabel("s/d"));
        panelKontrol.add(tgl_akhir);
        panelKontrol.add(btn_proses);
        panelKontrol.add(btn_keluar);

        getContentPane().add(panelKontrol, BorderLayout.NORTH);
    }

    private void updateChart() {
        if (tgl_awal.getDate() == null || tgl_akhir.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Silahkan pilih rentang tanggal!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String t1 = sdf.format(tgl_awal.getDate());
        String t2 = sdf.format(tgl_akhir.getDate());

        DefaultCategoryDataset dataset = createDataset(t1, t2);

        if (dataset.getColumnCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data penjualan pada periode ini.");
        }

        JFreeChart chart = createChart(dataset);
        setCustomChart(chart);

        if (chartPanel != null) {
            getContentPane().remove(chartPanel);
        }

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(950, 500));
        
        getContentPane().add(chartPanel, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    private DefaultCategoryDataset createDataset(String tanggal1, String tanggal2) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String url = "jdbc:mysql://localhost:3306/db_cafe";
        String user = "root";
        String pass = "";

        String sql = "SELECT nama_menu, SUM(jumlah) as total FROM produk_terlaris " +
                     "WHERE waktu BETWEEN ? AND ? GROUP BY nama_menu ORDER BY total DESC";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tanggal1 + " 00:00:00");
            pstmt.setString(2, tanggal2 + " 23:59:59");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dataset.addValue(rs.getDouble("total"), "Jumlah Terjual", rs.getString("nama_menu"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Database: " + e.getMessage());
        }
        return dataset;
    }

    private JFreeChart createChart(DefaultCategoryDataset dataset) {
        return ChartFactory.createBarChart(
                "Grafik Penjualan Produk Terlaris",
                "Nama Menu",
                "Unit Terjual",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
    }

    private void setCustomChart(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        
        TextTitle title = new TextTitle(chart.getTitle().getText(), new Font("SansSerif", Font.BOLD, 18));
        chart.setTitle(title);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(34, 139, 34)); // Warna Hijau Segar
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_proses = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        tgl_awal = new com.toedter.calendar.JDateChooser();
        tgl_akhir = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 500));

        btn_proses.setText("Proses");
        btn_proses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prosesActionPerformed(evt);
            }
        });

        btn_keluar.setText("Keluar");
        btn_keluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_keluarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_proses)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_keluar)))
                .addContainerGap(432, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 442, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_proses)
                    .addComponent(btn_keluar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_prosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prosesActionPerformed
       updateChart();
    }//GEN-LAST:event_btn_prosesActionPerformed

    private void btn_keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_keluarMouseClicked
        new Form_LaporanPendapatan().show();
        this.dispose();
    }//GEN-LAST:event_btn_keluarMouseClicked

   
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {}

        java.awt.EventQueue.invokeLater(() -> {
            new Form_Grafik().setVisible(true);
        });
        
        java.awt.EventQueue.invokeLater(() -> new Form_Grafik().setVisible(true));
    }

    

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_proses;
    private com.toedter.calendar.JDateChooser tgl_akhir;
    private com.toedter.calendar.JDateChooser tgl_awal;
    // End of variables declaration//GEN-END:variables
}
