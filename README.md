# Tugas 2: Aplikasi Konversi Suhu

### Pembuat
- **Nama**: Ferdhyan Dwi Rangga Saputra
- **NPM**: 2210010171

---

## 1. Deskripsi Program
Aplikasi ini memungkinkan pengguna untuk:
- Memasukkan nilai suhu dalam suatu skala (Celsius, Fahrenheit, dll).
- Memilih jenis konversi suhu yang diinginkan.
- Menekan tombol **Konversi** untuk menampilkan hasil konversi ke skala suhu lain.

## 2. Komponen GUI
- **JFrame**: Window utama aplikasi.
- **JPanel**: Panel untuk menampung komponen.
- **JLabel**: Menampilkan label dan hasil konversi.
- **JTextField**: Input untuk memasukkan nilai suhu yang akan dikonversi.
- **JComboBox**: Dropdown untuk memilih skala suhu.
- **JButton**: Tombol untuk melakukan konversi suhu.
- **JRadioButton**: Tombol radio untuk memilih arah konversi suhu.

## 3. Logika Program
- Menggunakan rumus konversi suhu sesuai skala yang dipilih (misalnya Celsius ke Fahrenheit atau Celsius ke Kelvin).
- Validasi input untuk memastikan pengguna hanya memasukkan angka.

## 4. Events
Menggunakan **ActionListener** dan **KeyAdapter** untuk memudahkan interaksi pengguna:

### A. Tombol Konversi
Menampilkan hasil konversi suhu setelah memvalidasi input dan menjalankan rumus konversi yang sesuai.
```java
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
```
### B. Key Adapter untuk membatasi input hanya angka
```java
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
```
## 5. Variasi
Aplikasi ini memiliki variasi tambahan berikut:

### A. Konversi Suhu ke Skala Lain (Reamur, Kelvin, dsb)
Menambahkan konversi suhu ke skala lain seperti Reamur dan Kelvin dengan rumus yang sesuai.
```java
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
```
### B. ItemListener pada JRadioButton
Menggunakan **ItemListener** untuk memilih arah konversi suhu.
```java
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
```

### C. Konversi Otomatis
Implementasi konversi otomatis saat nilai input berubah, mempermudah pengguna melihat hasil konversi tanpa menekan tombol **Konversi**.
```java
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
```
## 6. Tampilan Aplikasi Saat di Run



## 7. Indikator Penilaian

| No  | Komponen          | Persentase |
| :-: | ------------------ | :--------: |
|  1  | Komponen GUI      |     10%    |
|  2  | Logika Program    |     20%    |
|  3  | Events            |     15%    |
|  4  | Kesesuaian UI     |     15%    |
|  5  | Memenuhi Variasi  |     40%    |
|     | **TOTAL**         |  **100%**  |

--- 
