package view;

import controller.KnowledgeController;
import model.Putusan;
import model.StatistikPutusan;
import util.InputHandler;

import java.util.ArrayList;
import java.util.Iterator;
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

    public String[] inputFormPutusan() {
        this.tampilkanHeader("INPUT DATA PUTUSAN BARU");
        String[] data = new String[]{
                InputHandler.validasiStringWajib("  Nomor Perkara      : ", this.scanner),
                InputHandler.validasiStringWajib("  Nama Pengadilan    : ", this.scanner),
                InputHandler.validasiStringWajib("  Tanggal Putusan    : ", this.scanner),
                InputHandler.validasiStringWajib("  Nama Terdakwa      : ", this.scanner),
                String.valueOf(InputHandler.validasiUmur("  Umur Terdakwa (Thn): ", this.scanner)),
                InputHandler.validasiStringWajib("  Jenis Narkotika    : ", this.scanner),
                String.valueOf(InputHandler.validasiDouble("  Berat Barang Bukti : ", this.scanner)),
                InputHandler.validasiStringWajib("  Pasal Dilanggar    : ", this.scanner),
                InputHandler.validasiStringWajib("  Peran Terdakwa     : ", this.scanner),
                String.valueOf(InputHandler.validasiIntPositif("  Vonis Hukuman (Bln): ", this.scanner)),
                String.valueOf(InputHandler.validasiDouble("  Vonis Denda (Rp)   : ", this.scanner)),
                InputHandler.validasiStringWajib("  Nama Hakim Ketua   : ", this.scanner)
        };
        return data;
    }

    public void tampilkanDaftarPutusan(ArrayList<Putusan> daftar) {
        this.tampilkanHeader("DAFTAR SELURUH PUTUSAN");
        if (daftar.isEmpty()) {
            System.out.println("  Tidak ada data putusan dalam sistem.");
        } else {
            this.tampilkanTabelHeader();
            for (Putusan p : daftar) {
                p.tampilkan();
            }
            this.tampilkanTabelFooter(daftar.size());
        }
    }

    public void tampilkanStatistik(StatistikPutusan statistik) {
        this.tampilkanHeader("LAPORAN STATISTIK");
        if (statistik == null) {
            System.out.println("  Tidak ada data untuk ditampilkan.");
        } else {
            statistik.tampilkanLaporan();
        }
    }

    public void tampilkanDetail(Putusan p) {
        if (p == null) {
            System.out.println("  Data tidak ditemukan!");
        } else {
            p.tampilkan(true);
        }
    }

    public void tampilkanPesan(String pesan) {
        System.out.println();
        System.out.println("  " + pesan);
    }

    public void tampilkanHeader(String judul) {
        System.out.println();
        System.out.println("=== " + judul + " ===");
    }

    public void menuCari() {
        this.tampilkanHeader("CARI DATA PUTUSAN");
        System.out.println("  [1] Cari berdasarkan Nomor Perkara");
        System.out.println("  [2] Cari berdasarkan Nama Terdakwa");
        int pil = InputHandler.validasiPilihan("  Pilih tipe pencarian (1-2): ", 1, 2, this.scanner);
        ArrayList<Putusan> hasil;
        String keyword;
        if (pil == 1) {
            keyword = InputHandler.validasiStringWajib("  Masukkan Nomor Perkara: ", this.scanner);
            hasil = this.controller.cariPutusan(keyword, "nomor");
        } else {
            keyword = InputHandler.validasiStringWajib("  Masukkan Nama Terdakwa: ", this.scanner);
            hasil = this.controller.cariPutusan(keyword, "nama");
        }
        this.tampilkanHeader("HASIL PENCARIAN");
        if (hasil.isEmpty()) {
            System.out.println("  Data tidak ditemukan.");
        } else {
            this.tampilkanTabelHeader();
            for (Putusan p : hasil) {
                p.tampilkan();
            }
            this.tampilkanTabelFooter(hasil.size());
        }
    }

    public void menuFilter() {
        this.tampilkanHeader("FILTER DATA PUTUSAN");
        System.out.println("  [1] Berdasarkan Jenis Narkotika");
        System.out.println("  [2] Berdasarkan Nama Pengadilan");
        System.out.println("  [3] Berdasarkan Peran Terdakwa");
        System.out.println("  [4] Berdasarkan Rentang Hukuman Vonis");
        int pil = InputHandler.validasiPilihan("  Pilih tipe filter (1-4): ", 1, 4, this.scanner);
        ArrayList<Putusan> hasil;
        String kw;
        if (pil == 1) {
            kw = InputHandler.validasiStringWajib("  Masukkan Jenis Narkotika: ", this.scanner);
            hasil = this.controller.filterPutusan(kw, "jenis");
        } else if (pil == 2) {
            kw = InputHandler.validasiStringWajib("  Masukkan Nama Pengadilan: ", this.scanner);
            hasil = this.controller.filterPutusan(kw, "pengadilan");
        } else if (pil == 3) {
            kw = InputHandler.validasiStringWajib("  Masukkan Peran Terdakwa: ", this.scanner);
            hasil = this.controller.filterPutusan(kw, "peran");
        } else {
            int min = InputHandler.validasiIntPositif("  Vonis Minimal (Bulan): ", this.scanner);
            int max = InputHandler.validasiIntPositif("  Vonis Maksimal (Bulan): ", this.scanner);
            hasil = this.controller.filterPutusanRentang(min, max);
        }
        this.tampilkanHeader("HASIL FILTER DATA");
        if (hasil.isEmpty()) {
            System.out.println("  Tidak ada data yang sesuai dengan kriteria filter.");
        } else {
            this.tampilkanTabelHeader();
            for (Putusan p : hasil) {
                p.tampilkan();
            }
            this.tampilkanTabelFooter(hasil.size());
        }
    }

    public void menuSort() {
        this.tampilkanHeader("URUTKAN DATA (SORTING)");
        System.out.println("  [1] Urutkan Berdasarkan Lamanya Vonis Hukuman (Terendah -> Tertinggi)");
        System.out.println("  [2] Urutkan Berdasarkan Nominal Denda (Tertinggi -> Terendah)");
        System.out.println("  [3] Urutkan Berdasarkan Abjad Nama Terdakwa (A - Z)");
        int pil = InputHandler.validasiPilihan("  Pilih Kriteria Urut (1-3): ", 1, 3, this.scanner);
        ArrayList<Putusan> hasil;
        String kriteria;
        if (pil == 1) {
            hasil = this.controller.sortByVonis(this.controller.getSemuaPutusan());
            kriteria = "Vonis Hukuman";
        } else if (pil == 2) {
            hasil = this.controller.sortByDenda(this.controller.getSemuaPutusan());
            kriteria = "Nominal Denda";
        } else {
            hasil = this.controller.sortByNama(this.controller.getSemuaPutusan());
            kriteria = "Nama Terdakwa";
        }
        this.tampilkanHeader("HASIL DATA DIURUTKAN BERDASARKAN " + kriteria.toUpperCase());
        this.tampilkanTabelHeader();
        for (Putusan p : hasil) {
            p.tampilkan();
        }
        this.tampilkanTabelFooter(hasil.size());
    }

    public void menuHapus() {
        this.tampilkanHeader("PENGHAPUSAN DATA PUTUSAN");
        String nomor = InputHandler.validasiStringWajib("  Masukkan Nomor Perkara yang akan dihapus: ", this.scanner);
        ArrayList<Putusan> hasil = this.controller.cariPutusan(nomor, "nomor");
        if (hasil.isEmpty()) {
            this.tampilkanPesan("Putusan dengan nomor perkara tersebut tidak ditemukan.");
        } else {
            System.out.println("  Data yang akan dihapus:");
            this.tampilkanTabelHeader();
            ((Putusan)hasil.get(0)).tampilkan();
            this.tampilkanTabelFooter(1);
            if (InputHandler.konfirmasi("  Apakah Anda yakin ingin menghapus data ini?", this.scanner)) {
                if (this.controller.hapusPutusan(nomor)) {
                    this.tampilkanPesan("Data putusan sukses dihapus dari repositori!");
                } else {
                    this.tampilkanPesan("Gagal menghapus data.");
                }
            } else {
                this.tampilkanPesan("Proses penghapusan dibatalkan.");
            }
        }
    }

    public void menuUpdate() {
        this.tampilkanHeader("UPDATE DATA PUTUSAN");
        String nomor = InputHandler.validasiStringWajib("  Masukkan Nomor Perkara yang akan diupdate: ", this.scanner);
        ArrayList<Putusan> hasil = this.controller.cariPutusan(nomor, "nomor");
        if (hasil.isEmpty()) {
            this.tampilkanPesan("Putusan dengan nomor perkara tersebut tidak ditemukan.");
        } else {
            Putusan lama = (Putusan)hasil.get(0);
            Putusan baru = new Putusan();
            baru.setNomorPerkara(lama.getNomorPerkara());
            System.out.println("  Masukkan data baru (Kosongkan/Tekan ENTER jika tidak ingin diubah):");
            System.out.print("  Nama Pengadilan [" + lama.getPengadilan() + "]: ");
            String input = this.scanner.nextLine().trim();
            baru.setPengadilan(input.isEmpty() ? lama.getPengadilan() : input);
            System.out.print("  Tanggal Putusan [" + lama.getTanggalPutusan() + "]: ");
            input = this.scanner.nextLine().trim();
            baru.setTanggalPutusan(input.isEmpty() ? lama.getTanggalPutusan() : input);
            System.out.print("  Nama Terdakwa [" + lama.getNamaTerdakwa() + "]: ");
            input = this.scanner.nextLine().trim();
            baru.setNamaTerdakwa(input.isEmpty() ? lama.getNamaTerdakwa() : input);
            System.out.print("  Umur Terdakwa [" + lama.getUmurTerdakwa() + "]: ");
            input = this.scanner.nextLine().trim();
            baru.setUmurTerdakwa(input.isEmpty() ? lama.getUmurTerdakwa() : Integer.parseInt(input));
            System.out.print("  Jenis Narkotika [" + lama.getJenisNarkotika() + "]: ");
            input = this.scanner.nextLine().trim();
            baru.setJenisNarkotika(input.isEmpty() ? lama.getJenisNarkotika() : input);
            System.out.print("  Berat Barang Bukti [" + lama.getBeratBarangBukti() + "]: ");
            input = this.scanner.nextLine().trim();
            baru.setBeratBarangBukti(input.isEmpty() ? lama.getBeratBarangBukti() : Double.parseDouble(input));
            System.out.print("  Pasal Dilanggar [" + lama.getPasalDilanggar() + "]: ");
            input = this.scanner.nextLine().trim();
            baru.setPasalDilanggar(input.isEmpty() ? lama.getPasalDilanggar() : input);
            System.out.print("  Peran Terdakwa [" + lama.getPeranTerdakwa() + "]: ");
            input = this.scanner.nextLine().trim();
            baru.setPeranTerdakwa(input.isEmpty() ? lama.getPeranTerdakwa() : input);
            System.out.print("  Vonis Hukuman (Bulan) [" + lama.getVonisHukuman() + "]: ");
            input = this.scanner.nextLine().trim();
            baru.setVonisHukuman(input.isEmpty() ? lama.getVonisHukuman() : Integer.parseInt(input));
            System.out.print("  Vonis Denda [" + lama.getVonisDenda() + "]: ");
            input = this.scanner.nextLine().trim();
            baru.setVonisDenda(input.isEmpty() ? lama.getVonisDenda() : Double.parseDouble(input));
            System.out.print("  Nama Hakim [" + lama.getNamaHakim() + "]: ");
            input = this.scanner.nextLine().trim();
            baru.setNamaHakim(input.isEmpty() ? lama.getNamaHakim() : input);
            if (InputHandler.konfirmasi("  Simpan perubahan?", this.scanner)) {
                if (this.controller.updatePutusan(nomor, baru)) {
                    this.tampilkanPesan("Putusan berhasil diupdate!");
                } else {
                    this.tampilkanPesan("Gagal mengupdate putusan.");
                }
            } else {
                this.tampilkanPesan("Update dibatalkan.");
            }
        }
    }

    public void menuLihatDetail() {
        this.tampilkanHeader("LIHAT DETAIL PUTUSAN");
        String nomor = InputHandler.validasiStringWajib("  Masukkan Nomor Perkara: ", this.scanner);
        ArrayList<Putusan> hasil = this.controller.cariPutusan(nomor, "nomor");
        if (hasil.isEmpty()) {
            this.tampilkanPesan("Putusan tidak ditemukan.");
        } else {
            this.tampilkanDetail((Putusan)hasil.get(0));
        }
    }

    public void menuExport() {
        this.tampilkanHeader("EXPORT DATA KE FILE");
        String filename = InputHandler.validasiStringWajib("  Nama file (contoh: laporan.txt): ", this.scanner);
        this.controller.exportToFile(filename);
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