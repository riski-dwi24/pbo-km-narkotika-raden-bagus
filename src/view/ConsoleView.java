package view;

import controller.KnowledgeController;
import model.Putusan;
import util.InputHandler;

import java.util.Scanner;

public class ConsoleView {
    private Scanner scanner;
    private KnowledgeController controller;

    public ConsoleView(KnowledgeController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public int tampilkanMenu() {
        System.out.println();
        System.out.println("==============================================================");
        System.out.println("      KNOWLEDGE MANAGEMENT SYSTEM - PUTUSAN PENGADILAN NARKOTIKA");
        System.out.println("                    MAHKAMAH AGUNG REPUBLIK INDONESIA");
        System.out.println("==============================================================");
        System.out.println("  [1] Tambah Putusan Baru");
        System.out.println("  [2] Tampilkan Semua Putusan");
        System.out.println("  [3] Cari Putusan");
        System.out.println("  [4] Filter Putusan");
        System.out.println("  [5] Urutkan Data (Sorting)");
        System.out.println("  [6] Lihat Laporan Statistik");
        System.out.println("  [7] Lihat Detail Spesifik Putusan");
        System.out.println("  [8] Hapus Data Putusan");
        System.out.println("  [9] Update Data Putusan");
        System.out.println("  [10] Export Data ke File (.txt)");
        System.out.println("  [0] Keluar Aplikasi");
        System.out.println("==============================================================");
        System.out.println("  Total Data: " + this.controller.getTotalData() + " putusan");
        return InputHandler.validasiPilihan("   Pilih Menu (0-10): ", 0, 10, this.scanner);
    }

    public void tampilkanHeader(String judul) {
        System.out.println();
        System.out.println("=== " + judul + " ===");
    }

    public void tampilkanPesan(String pesan) {
        System.out.println();
        System.out.println("  " + pesan);
    }

    public void tungguEnter() {
        System.out.print("\n  [Tekan ENTER untuk melanjutkan...]");
        this.scanner.nextLine();
    }

    public void close() {
        this.scanner.close();
    }
    private void tampilkanTabelHeader() {
        System.out.println("+------------------------+--------------------+------------+----------+-----------+-----------------+");
        System.out.println("| NOMOR PERKARA          | NAMA TERDAKWA      | JENIS NARK | PERAN    | VONIS     | DENDA (Rp)      |");
        System.out.println("+------------------------+--------------------+------------+----------+-----------+-----------------+");
    }

    private void tampilkanTabelFooter(int total) {
        System.out.println("+------------------------+--------------------+------------+----------+-----------+-----------------+");
        System.out.printf("| Total Data: %-86d |%n", total);
        System.out.println("+------------------------+--------------------+------------+----------+-----------+-----------------+");
    }
}
