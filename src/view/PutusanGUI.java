package view;

import controller.KnowledgeController;
import model.Putusan;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class PutusanGUI extends JFrame {
    private KnowledgeController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    // ==================== FORM COMPONENTS ====================
    private JTextField txtNomor, txtNama, txtUmur, txtBerat, txtVonis, txtDenda, txtHakim, txtTahun;
    private JComboBox<String> cbJenis, cbPengadilan, cbPeran, cbPasal;

    // ==================== STAT LABELS ====================
    private JLabel lblTotalData, lblRataVonis, lblRataDenda, lblJenisTerbanyak;

    // ==================== DATA COMBOBOX ====================
    private final String[] JENIS_NARKOTIKA = {"Narkotika", "Uang Negara (Korupsi)", "N/A (Perdata)", "Pidana Militer", "Tindak Pidana Ringan"};
    private final String[] PENGADILAN = {"Pengadilan Negeri Surabaya", "Pengadilan Negeri Serui", "Pengadilan Tinggi Bandung", "Pengadilan Agama Waikabubak"};
    private final String[] PERAN = {"Karyawan Swasta", "Wiraswasta", "Belum Bekerja", "PNS", "Petani", "Sopir"};
    private final String[] PASAL = {"UU No. 35/2009", "UU Tipikor", "Hukum Perdata", "Hukum Islam", "HIR / RBg"};

    public PutusanGUI(KnowledgeController controller) {
        this.controller = controller;
        this.initComponents();
        this.refreshTable();
        this.updateStatistik();
    }

    private void initComponents() {
        this.setTitle("Knowledge Management System - Putusan Pengadilan (GUI)");
        this.setSize(1500, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));

        // ==================== HEADER ====================
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setPreferredSize(new Dimension(0, 70));
        headerPanel.setLayout(new GridBagLayout());

        JLabel lblTitle = new JLabel("KNOWLEDGE MANAGEMENT SYSTEM - PUTUSAN PENGADILAN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        this.add(headerPanel, BorderLayout.NORTH);

        // ==================== CENTER PANEL ====================
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 5));

        // ==================== TABLE ====================
        String[] columns = {"Nomor Perkara", "Pengadilan", "Tahun", "Nama Terdakwa", "Umur",
                "Jenis Kasus", "Berat BB (gr)", "Pasal Dilanggar", "Peran",
                "Vonis (bln)", "Denda (Rp)", "Hakim"};
        this.tableModel = new DefaultTableModel(columns, 0);
        this.table = new JTable(this.tableModel);
        this.table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        this.table.setRowHeight(30);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader header = this.table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(44, 62, 80));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 40));

        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        // ==================== EAST PANEL - FORM INPUT ====================
        JPanel formPanel = createFormPanel();
        this.add(formPanel, BorderLayout.EAST);

        // ==================== SOUTH PANEL - STATISTIK ====================
        JPanel statPanel = createStatistikPanel();
        this.add(statPanel, BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new CompoundBorder(
                new EmptyBorder(10, 5, 10, 10),
                new TitledBorder(new LineBorder(new Color(52, 152, 219), 2),
                        " Input Data Putusan ", TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 14), new Color(52, 152, 219))
        ));
        panel.setPreferredSize(new Dimension(400, 0));
        panel.setBackground(new Color(250, 250, 250));

        // ==================== INISIALISASI KOMPONEN ====================
        this.txtNomor = createTextField();
        this.cbPengadilan = new JComboBox<>(PENGADILAN);
        this.cbPengadilan.setEditable(true);
        this.txtTahun = createTextField();
        this.txtNama = createTextField();
        this.txtUmur = createTextField();
        this.cbJenis = new JComboBox<>(JENIS_NARKOTIKA);
        this.cbJenis.setEditable(true);
        this.txtBerat = createTextField();
        this.cbPasal = new JComboBox<>(PASAL);
        this.cbPasal.setEditable(true);
        this.cbPeran = new JComboBox<>(PERAN);
        this.cbPeran.setEditable(true);
        this.txtVonis = createTextField();
        this.txtDenda = createTextField();
        this.txtHakim = createTextField();

        // ==================== TAMBAHKAN KE PANEL ====================
        panel.add(createFormRow("Nomor Perkara:", this.txtNomor));
        panel.add(createFormRow("Pengadilan:", this.cbPengadilan));
        panel.add(createFormRow("Tahun Putusan:", this.txtTahun));
        panel.add(createFormRow("Nama Terdakwa:", this.txtNama));
        panel.add(createFormRow("Umur (thn):", this.txtUmur));
        panel.add(createFormRow("Jenis Kasus:", this.cbJenis));
        panel.add(createFormRow("Berat BB (gr):", this.txtBerat));
        panel.add(createFormRow("Pasal Dilanggar:", this.cbPasal));
        panel.add(createFormRow("Peran Terdakwa:", this.cbPeran));
        panel.add(createFormRow("Vonis (bulan):", this.txtVonis));
        panel.add(createFormRow("Denda (Rp):", this.txtDenda));
        panel.add(createFormRow("Nama Hakim:", this.txtHakim));

        // ==================== BUTTON PANEL ====================
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        btnPanel.setBackground(new Color(250, 250, 250));

        JPanel btnTambah = createCustomButton("Tambah", new Color(39, 174, 96), e -> tambahPutusan());
        JPanel btnUpdate = createCustomButton("Update", new Color(243, 156, 18), e -> updatePutusan());
        JPanel btnHapus = createCustomButton("Hapus", new Color(231, 76, 60), e -> hapusPutusan());
        JPanel btnClear = createCustomButton("Clear", new Color(127, 140, 141), e -> clearForm());

        btnPanel.add(btnTambah);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnHapus);
        btnPanel.add(btnClear);

        panel.add(Box.createVerticalStrut(10));
        panel.add(btnPanel);
        return panel;
    }

    private JPanel createCustomButton(String text, Color bgColor, Consumer<ActionEvent> action) {
        JPanel button = new JPanel();
        button.setLayout(new GridBagLayout());
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(bgColor.darker(), 2),
                new EmptyBorder(10, 18, 10, 18)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(110, 40));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(Color.WHITE);
        button.add(label);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                action.accept(null);
            }
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    private JPanel createFormRow(String label, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(5, 5));
        row.setBackground(new Color(250, 250, 250));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        row.setBorder(new EmptyBorder(3, 5, 3, 5));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(44, 62, 80));
        lbl.setPreferredSize(new Dimension(120, 25));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        if (field instanceof JTextField) {
            field.setPreferredSize(new Dimension(200, 28));
        }

        row.add(lbl, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(189, 195, 199)),
                new EmptyBorder(5, 8, 5, 8)
        ));
        return tf;
    }

    private JPanel createStatistikPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        panel.setBackground(new Color(44, 62, 80));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(0, 65));

        this.lblTotalData = createStatLabel("Total Data: 0");
        this.lblRataVonis = createStatLabel("Rata-rata Vonis: 0 bln");
        this.lblRataDenda = createStatLabel("Rata-rata Denda: Rp 0");
        this.lblJenisTerbanyak = createStatLabel("Jenis Terbanyak: -");

        panel.add(this.lblTotalData);
        panel.add(this.lblRataVonis);
        panel.add(this.lblRataDenda);
        panel.add(this.lblJenisTerbanyak);
        return panel;
    }

    private JLabel createStatLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    // ==================== CRUD METHODS ====================

    private void tambahPutusan() {
        try {
            if (txtNomor.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nomor Perkara wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (txtNama.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama Terdakwa wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] data = {
                    txtNomor.getText().trim(),
                    getComboValue(cbPengadilan),
                    txtTahun.getText().trim().isEmpty() ? "2024" : txtTahun.getText().trim(),
                    txtNama.getText().trim(),
                    txtUmur.getText().trim().isEmpty() ? "0" : txtUmur.getText().trim(),
                    getComboValue(cbJenis),
                    txtBerat.getText().trim().isEmpty() ? "0" : txtBerat.getText().trim(),
                    getComboValue(cbPasal),
                    getComboValue(cbPeran),
                    txtVonis.getText().trim().isEmpty() ? "0" : txtVonis.getText().trim(),
                    txtDenda.getText().trim().isEmpty() ? "0" : txtDenda.getText().trim(),
                    txtHakim.getText().trim().isEmpty() ? "Majelis Hakim" : txtHakim.getText().trim()
            };

            if (controller.tambahPutusan(data)) {
                JOptionPane.showMessageDialog(this, "Putusan berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                refreshTable();
                updateStatistik();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePutusan() {
        JOptionPane.showMessageDialog(this, "Fitur Update akan segera hadir!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void hapusPutusan() {
        JOptionPane.showMessageDialog(this, "Fitur Hapus akan segera hadir!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        txtNomor.setText("");
        cbPengadilan.setSelectedIndex(0);
        txtTahun.setText("");
        txtNama.setText("");
        txtUmur.setText("");
        cbJenis.setSelectedIndex(0);
        txtBerat.setText("");
        cbPasal.setSelectedIndex(0);
        cbPeran.setSelectedIndex(0);
        txtVonis.setText("");
        txtDenda.setText("");
        txtHakim.setText("");
        table.clearSelection();
    }

    private String getComboValue(JComboBox<String> combo) {
        Object selected = combo.getSelectedItem();
        return selected != null ? selected.toString() : "";
    }

    private void refreshTable() {
        updateTable(controller.getSemuaPutusan());
    }

    private void updateTable(ArrayList<Putusan> daftar) {
        tableModel.setRowCount(0);
        for (Putusan p : daftar) {
            Object[] row = {
                    p.getNomorPerkara(), p.getPengadilan(), p.getTanggalPutusan(),
                    p.getNamaTerdakwa(), p.getUmurTerdakwa(), p.getJenisNarkotika(),
                    p.getBeratBarangBukti(), p.getPasalDilanggar(), p.getPeranTerdakwa(),
                    p.getVonisHukuman(), p.getVonisDenda(), p.getNamaHakim()
            };
            tableModel.addRow(row);
        }
    }

    private void updateStatistik() {
        // Akan diisi di commit selanjutnya
    }
}

