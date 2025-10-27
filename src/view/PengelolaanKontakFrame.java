/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.KontakController;
import java.io.*;
import model.Kontak;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PengelolaanKontakFrame extends javax.swing.JFrame {

    private DefaultTableModel model;
    private KontakController controller;
    
    public PengelolaanKontakFrame() {
        initComponents();
        controller = new KontakController();
        model = new DefaultTableModel(new String[]{"No", "Nama", "Nomor Telepon", "Kategori"}, 0);
        tblKontak.setModel(model);
        loadContacts();
    }
    
    private void loadContacts() {
        try {
        model.setRowCount(0);
        List<Kontak> contacts = controller.getAllContacts();
        int rowNumber = 1;
        for (Kontak contact : contacts) {
        model.addRow(new Object[]{
        rowNumber++,
        contact.getNama(),
        contact.getNomorTelepon(),
        contact.getKategori()
        });
        }
        } catch (SQLException e) {
        showError(e.getMessage());
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error",
        JOptionPane.ERROR_MESSAGE);
    }

    private void addContact() {
        String nama = txtNama.getText().trim();
        String nomorTelepon = txtNomorTelepon.getText().trim();
        String kategori = (String) cmbKategori.getSelectedItem();
        if (!validatePhoneNumber(nomorTelepon)) {
        return; // Validasi nomor telepon gagal
        }
        try {
        if (controller.isDuplicatePhoneNumber(nomorTelepon, null)) {
        JOptionPane.showMessageDialog(this, "Kontak nomor telepon ini sudah ada.", "Kesalahan", JOptionPane.WARNING_MESSAGE);
        return;
        }
        controller.addContact(nama, nomorTelepon, kategori);
        loadContacts();
        JOptionPane.showMessageDialog(this, "Kontak berhasil ditambahkan!");
        clearInputFields();
        } catch (SQLException ex) {
        showError("Gagal menambahkan kontak: " + ex.getMessage());
        }
    }
    
    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nomor telepon tidak boleh kosong.");
        return false;
        }
        if (!phoneNumber.matches("\\d+")) { // Hanya angka
        JOptionPane.showMessageDialog(this, "Nomor telepon hanya boleh berisi angka.");
        return false;
        }
        if (phoneNumber.length() < 8 || phoneNumber.length() > 15) { //Panjang 8-15
        JOptionPane.showMessageDialog(this, "Nomor telepon harus memiliki panjang antara 8 hingga 15 karakter.");
        return false;
        }
        return true;
    }
    
    private void clearInputFields() {
        txtNama.setText("");
        txtNomorTelepon.setText("");
        cmbKategori.setSelectedIndex(0);
    }
    
    private void editContact() {
        int selectedRow = tblKontak.getSelectedRow();
        if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih kontak yang ingin diperbarui.", "Kesalahan", JOptionPane.WARNING_MESSAGE);
        return;
        }
        int id = (int) model.getValueAt(selectedRow, 0);
        String nama = txtNama.getText().trim();
        String nomorTelepon = txtNomorTelepon.getText().trim();
        String kategori = (String) cmbKategori.getSelectedItem();
        if (!validatePhoneNumber(nomorTelepon)) {
        return;
        }
        try {
        if (controller.isDuplicatePhoneNumber(nomorTelepon, id)) {
        JOptionPane.showMessageDialog(this, "Kontak nomor telepon ini sudah ada.", "Kesalahan", JOptionPane.WARNING_MESSAGE);
        return;
        }
        controller.updateContact(id, nama, nomorTelepon, kategori);
        loadContacts();
        JOptionPane.showMessageDialog(this, "Kontak berhasil diperbarui!");
        clearInputFields();
        } catch (SQLException ex) {
        showError("Gagal memperbarui kontak: " + ex.getMessage());
        }
    }
    
    private void populateInputFields(int selectedRow) {
        // Ambil data dari JTable
        String nama = model.getValueAt(selectedRow, 1).toString();
        String nomorTelepon = model.getValueAt(selectedRow, 2).toString();
        String kategori = model.getValueAt(selectedRow, 3).toString();
        // Set data ke komponen input
        txtNama.setText(nama);
        txtNomorTelepon.setText(nomorTelepon);
        cmbKategori.setSelectedItem(kategori);
    }
    
    private void deleteContact() {
        int selectedRow = tblKontak.getSelectedRow();
        if (selectedRow != -1) {
        int id = (int) model.getValueAt(selectedRow, 0);
        try {
        controller.deleteContact(id);
        loadContacts();
        JOptionPane.showMessageDialog(this, "Kontak berhasil dihapus!");
        clearInputFields();
        } catch (SQLException e) {
                showError(e.getMessage());
            }
        }
    }
    
    private void searchContact() {
        String keyword = txtPencarian.getText().trim();
        if (!keyword.isEmpty()) {
        try {
        List<Kontak> contacts = controller.searchContacts(keyword);
        model.setRowCount(0); // Bersihkan tabel
        for (Kontak contact : contacts) {
        model.addRow(new Object[]{
        contact.getId(),
        contact.getNama(),
        contact.getNomorTelepon(),
        contact.getKategori()
        });
        }
        if (contacts.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Tidak ada kontak ditemukan.");
        }
        } catch (SQLException ex) {
        showError(ex.getMessage());
        }
        } else {
        loadContacts();
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblJudul = new javax.swing.JLabel();
        lblKontak = new javax.swing.JLabel();
        lblTelepon = new javax.swing.JLabel();
        lblKategori = new javax.swing.JLabel();
        lblPencarian = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtNomorTelepon = new javax.swing.JTextField();
        txtPencarian = new javax.swing.JTextField();
        cmbKategori = new javax.swing.JComboBox<>();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKontak = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblJudul.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblJudul.setText("APLIKASI PENGELOLAAN KONTAK");
        jPanel2.add(lblJudul);

        lblKontak.setText("Nama Kontak");

        lblTelepon.setText("Nomor Telepon");

        lblKategori.setText("Kategori");

        lblPencarian.setText("Pencarian");

        txtPencarian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPencarianKeyTyped(evt);
            }
        });

        cmbKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Keluarga", "Teman", "Kantor" }));

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnExport.setText("Export");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnImport.setText("Import");
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        tblKontak.setModel(new javax.swing.table.DefaultTableModel(
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
        tblKontak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKontakMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKontak);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblKontak)
                                        .addComponent(lblTelepon)
                                        .addComponent(lblKategori))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNomorTelepon)
                                        .addComponent(cmbKategori, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblPencarian)
                                    .addGap(51, 51, 51)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(btnTambah)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnEdit)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnHapus))
                                        .addComponent(txtPencarian))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnExport)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnImport)))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblKontak)
                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(lblTelepon))
                            .addComponent(txtNomorTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKategori))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnEdit)
                    .addComponent(btnHapus))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPencarian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImport)
                    .addComponent(btnExport))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImportActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        addContact();        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editContact(); // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void tblKontakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKontakMouseClicked
        int selectedRow = tblKontak.getSelectedRow();
        if (selectedRow != -1) {
        populateInputFields(selectedRow);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tblKontakMouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        deleteContact();        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusActionPerformed

    private void txtPencarianKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPencarianKeyTyped
        searchContact();        // TODO add your handling code here:
    }//GEN-LAST:event_txtPencarianKeyTyped

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
            java.util.logging.Logger.getLogger(PengelolaanKontakFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PengelolaanKontakFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PengelolaanKontakFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PengelolaanKontakFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PengelolaanKontakFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbKategori;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblKategori;
    private javax.swing.JLabel lblKontak;
    private javax.swing.JLabel lblPencarian;
    private javax.swing.JLabel lblTelepon;
    private javax.swing.JTable tblKontak;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNomorTelepon;
    private javax.swing.JTextField txtPencarian;
    // End of variables declaration//GEN-END:variables
}
