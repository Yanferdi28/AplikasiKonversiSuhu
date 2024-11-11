/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JOptionPane;
/**
 *
 * @author Fetra
 */
public class KonversiSuhuFrame extends javax.swing.JFrame {
    
    // Flag untuk mengecek apakah konversi otomatis telah diaktifkan
    private boolean isConversionEnabled = false;

    /**
     * Constructor untuk membuat form KonversiSuhuFrame.
     */
    public KonversiSuhuFrame() {
        initComponents();
        setupRadioButtonListeners(); // Atur listener untuk perubahan pada radio buttons
        setupButtonActionListener(); // Atur listener untuk tombol konversi
        setupInputValidation(); // Atur validasi input pada JTextField untuk hanya menerima angka
    }
    
    // Method untuk menambahkan listener pada setiap radio button agar memperbarui ComboBox sesuai pilihan
    private void setupRadioButtonListeners() {
        // Listener untuk rbCelcius
        rbCelcius.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update ComboBox dengan opsi konversi tujuan dari Celcius
                updateComboBox(new String[] {"Fahrenheit", "Kelvin", "Reamur"});
            }
        });

        // Listener untuk rbFahrenheit
        rbFahrenheit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update ComboBox dengan opsi konversi tujuan dari Fahrenheit
                updateComboBox(new String[] {"Celcius", "Kelvin", "Reamur"});
            }
        });

        // Listener untuk rbReamur
        rbReamur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update ComboBox dengan opsi konversi tujuan dari Reamur
                updateComboBox(new String[] {"Celcius", "Fahrenheit", "Kelvin"});
            }
        });

        // Listener untuk rbKelvin
        rbKelvin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update ComboBox dengan opsi konversi tujuan dari Kelvin
                updateComboBox(new String[] {"Celcius", "Fahrenheit", "Reamur"});
            }
        });
    }
    
    // Method untuk menambahkan listener pada tombol Konversi
    private void setupButtonActionListener() {
        btnKonversi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isConversionEnabled = true; // Aktifkan flag konversi otomatis
                performConversion(); // Lakukan konversi pertama kali saat tombol ditekan
                setupAutoConversionListener(); // Atur listener untuk input otomatis setelah konversi diaktifkan
            }
        });
    }
    
    // Method untuk mengaktifkan konversi otomatis setiap kali input di JTextField berubah
    private void setupAutoConversionListener() {
        txtSuhu.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (isConversionEnabled) {
                    performConversion(); // Lakukan konversi otomatis saat input berubah
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (isConversionEnabled) {
                    performConversion(); // Lakukan konversi otomatis saat input dihapus
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (isConversionEnabled) {
                    performConversion(); // Lakukan konversi otomatis untuk perubahan format
                }
            }
        });
    }
    
    // Method utama untuk melakukan konversi suhu
    private void performConversion() {
        try {
            // Cek apakah input kosong, jika ya, reset hasil
            if (txtSuhu.getText().isEmpty()) {
                lblHasil.setText("Hasil: ");
                return;
            }

            // Ambil nilai suhu input dan tujuan konversi
            double suhuInput = Double.parseDouble(txtSuhu.getText());
            String tujuanKonversi = cbKonversi.getSelectedItem().toString();
            double hasilKonversi = 0.0;

            // Lakukan konversi berdasarkan satuan asal yang dipilih
            if (rbCelcius.isSelected()) {
                hasilKonversi = konversiDariCelcius(suhuInput, tujuanKonversi);
            } else if (rbFahrenheit.isSelected()) {
                hasilKonversi = konversiDariFahrenheit(suhuInput, tujuanKonversi);
            } else if (rbReamur.isSelected()) {
                hasilKonversi = konversiDariReamur(suhuInput, tujuanKonversi);
            } else if (rbKelvin.isSelected()) {
                hasilKonversi = konversiDariKelvin(suhuInput, tujuanKonversi);
            }

            // Tampilkan hasil konversi di label
            lblHasil.setText("Hasil: " + hasilKonversi + " " + tujuanKonversi);
        } catch (NumberFormatException ex) {
            // Tampilkan pesan error jika input tidak valid
            JOptionPane.showMessageDialog(this, "Input suhu tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method untuk konversi dari Celcius ke satuan lain
    private double konversiDariCelcius(double suhu, String tujuan) {
        switch (tujuan) {
            case "Fahrenheit":
                return suhu * 9 / 5 + 32;
            case "Kelvin":
                return suhu + 273.15;
            case "Reamur":
                return suhu * 4 / 5;
            default:
                return suhu;
        }
    }

    // Method untuk konversi dari Fahrenheit ke satuan lain
    private double konversiDariFahrenheit(double suhu, String tujuan) {
        switch (tujuan) {
            case "Celcius":
                return (suhu - 32) * 5 / 9;
            case "Kelvin":
                return (suhu - 32) * 5 / 9 + 273.15;
            case "Reamur":
                return (suhu - 32) * 4 / 9;
            default:
                return suhu;
        }
    }

    // Method untuk konversi dari Reamur ke satuan lain
    private double konversiDariReamur(double suhu, String tujuan) {
        switch (tujuan) {
            case "Celcius":
                return suhu * 5 / 4;
            case "Fahrenheit":
                return suhu * 9 / 4 + 32;
            case "Kelvin":
                return suhu * 5 / 4 + 273.15;
            default:
                return suhu;
        }
    }

    // Method untuk konversi dari Kelvin ke satuan lain
    private double konversiDariKelvin(double suhu, String tujuan) {
        switch (tujuan) {
            case "Celcius":
                return suhu - 273.15;
            case "Fahrenheit":
                return (suhu - 273.15) * 9 / 5 + 32;
            case "Reamur":
                return (suhu - 273.15) * 4 / 5;
            default:
                return suhu;
        }
    }
    
    // Method untuk menambahkan validasi input hanya menerima angka
    private void setupInputValidation() {
        txtSuhu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Periksa apakah karakter bukan angka, bukan backspace, dan bukan delete
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                    
                    // Tampilkan pesan peringatan jika input tidak valid
                    JOptionPane.showMessageDialog(null, "Hanya angka yang diperbolehkan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    
    // Method untuk memperbarui ComboBox dengan opsi konversi tujuan
    private void updateComboBox(String[] options) {
        cbKonversi.removeAllItems(); // Hapus semua item yang ada
        for (String option : options) {
            cbKonversi.addItem(option); // Tambahkan item baru sesuai array options
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        rbCelcius = new javax.swing.JRadioButton();
        rbFahrenheit = new javax.swing.JRadioButton();
        rbReamur = new javax.swing.JRadioButton();
        rbKelvin = new javax.swing.JRadioButton();
        cbKonversi = new javax.swing.JComboBox<>();
        btnKonversi = new javax.swing.JButton();
        lblHasil = new javax.swing.JLabel();
        txtSuhu = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Konversi Suhu");

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Aplikasi Konversi Suhu");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Masukkan Suhu");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Pilih Tujuan Konversi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        buttonGroup1.add(rbCelcius);
        rbCelcius.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbCelcius.setForeground(new java.awt.Color(255, 255, 255));
        rbCelcius.setText("Celcius");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jPanel1.add(rbCelcius, gridBagConstraints);

        buttonGroup1.add(rbFahrenheit);
        rbFahrenheit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbFahrenheit.setForeground(new java.awt.Color(255, 255, 255));
        rbFahrenheit.setText("Fahrenheit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jPanel1.add(rbFahrenheit, gridBagConstraints);

        buttonGroup1.add(rbReamur);
        rbReamur.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbReamur.setForeground(new java.awt.Color(255, 255, 255));
        rbReamur.setText("Reamur");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jPanel1.add(rbReamur, gridBagConstraints);

        buttonGroup1.add(rbKelvin);
        rbKelvin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbKelvin.setForeground(new java.awt.Color(255, 255, 255));
        rbKelvin.setText("Kelvin");
        rbKelvin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbKelvinActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jPanel1.add(rbKelvin, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 4, 3, 4);
        jPanel1.add(cbKonversi, gridBagConstraints);

        btnKonversi.setBackground(new java.awt.Color(153, 153, 153));
        btnKonversi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnKonversi.setForeground(new java.awt.Color(255, 255, 255));
        btnKonversi.setText("Konversi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jPanel1.add(btnKonversi, gridBagConstraints);

        lblHasil.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHasil.setForeground(new java.awt.Color(255, 255, 255));
        lblHasil.setText("Hasil :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jPanel1.add(lblHasil, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 4, 3, 4);
        jPanel1.add(txtSuhu, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbKelvinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbKelvinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbKelvinActionPerformed

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
            java.util.logging.Logger.getLogger(KonversiSuhuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KonversiSuhuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KonversiSuhuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KonversiSuhuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KonversiSuhuFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKonversi;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbKonversi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblHasil;
    private javax.swing.JRadioButton rbCelcius;
    private javax.swing.JRadioButton rbFahrenheit;
    private javax.swing.JRadioButton rbKelvin;
    private javax.swing.JRadioButton rbReamur;
    private javax.swing.JTextField txtSuhu;
    // End of variables declaration//GEN-END:variables
}
