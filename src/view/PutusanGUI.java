package view;

import controller.KnowledgeController;
import model.Putusan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

public class PutusanGUI extends JFrame {
    private KnowledgeController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotalData;
    private JLabel lblRataVonis;
    private JLabel lblRataDenda;
    private JLabel lblJenisTerbanyak;

    public PutusanGUI(KnowledgeController controller) {
        this.controller = controller;
        this.initComponents();
        this.refreshTable();
        this.updateStatistik();
    }

    private void initComponents() {
        this.setTitle("Knowledge Management System - Putusan Pengadilan (GUI)");
        this.setSize(1400, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));

        // HEADER
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setPreferredSize(new Dimension(0, 70));
        headerPanel.setLayout(new GridBagLayout());

        JLabel lblTitle = new JLabel("KNOWLEDGE MANAGEMENT SYSTEM - PUTUSAN PENGADILAN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        this.add(headerPanel, BorderLayout.NORTH);

        // CENTER PANEL
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 5));

        // TABLE
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

        // STATISTIK PANEL (BAWAH)
        JPanel statPanel = createStatistikPanel();
        this.add(statPanel, BorderLayout.SOUTH);
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

    private void refreshTable() {
        this.updateTable(this.controller.getSemuaPutusan());
    }

    private void updateTable(ArrayList<Putusan> daftar) {
        this.tableModel.setRowCount(0);
        for (Putusan p : daftar) {
            Object[] row = {
                    p.getNomorPerkara(), p.getPengadilan(), p.getTanggalPutusan(),
                    p.getNamaTerdakwa(), p.getUmurTerdakwa(), p.getJenisNarkotika(),
                    p.getBeratBarangBukti(), p.getPasalDilanggar(), p.getPeranTerdakwa(),
                    p.getVonisHukuman(), p.getVonisDenda(), p.getNamaHakim()
            };
            this.tableModel.addRow(row);
        }
    }

    
    private void updateStatistik() {
        // Akan diisi di commit selanjutnya
    }
}