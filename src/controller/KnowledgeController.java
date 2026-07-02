package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import model.KnowledgeRepository;
import model.Putusan;
import model.StatistikPutusan;
import util.Sortable;

public class KnowledgeController implements Sortable {
    private KnowledgeRepository repository = new KnowledgeRepository();

    public boolean tambahPutusan(String[] data) {
        try {
            if (data != null && data.length >= 12) {
                if (data[0] != null && !data[0].trim().isEmpty()) {
                    if (data[3] != null && !data[3].trim().isEmpty()) {
                        int umur = Integer.parseInt(data[4]);
                        if (umur >= 0 && umur <= 150) {
                            double berat = Double.parseDouble(data[6]);
                            if (berat < (double) 0.0F) {
                                System.out.println("Error: Berat barang bukti tidak boleh negatif!");
                                return false;
                            } else {
                                int vonis = Integer.parseInt(data[9]);
                                if (vonis < 0) {
                                    System.out.println("Error: Vonis hukuman tidak boleh negatif!");
                                    return false;
                                } else {
                                    double denda = Double.parseDouble(data[10]);
                                    if (denda < (double) 0.0F) {
                                        System.out.println("Error: Vonis denda tidak boleh negatif!");
                                        return false;
                                    } else {
                                        Putusan p = new Putusan(data[0], data[1], data[2], data[3], umur, data[5], berat, data[7], data[8], vonis, denda, data[11]);
                                        this.repository.simpan(p);
                                        return true;
                                    }
                                }
                            }
                        } else {
                            System.out.println("Error: Umur harus antara 0-150 tahun!");
                            return false;
                        }
                    } else {
                        System.out.println("Error: Nama terdakwa wajib diisi!");
                        return false;
                    }
                } else {
                    System.out.println("Error: Nomor perkara wajib diisi!");
                    return false;
                }
            } else {
                System.out.println("Error: Data tidak lengkap!");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Format angka tidak valid! " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error tidak terduga: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Putusan> getSemuaPutusan() {
        return this.repository.getDaftarPutusan();
    }

    public ArrayList<Putusan> cariPutusan(String keyword, String tipe) {
        if (tipe.equalsIgnoreCase("nomor")) {
            ArrayList<Putusan> hasil = new ArrayList();
            Putusan p = this.repository.cariByNomor(keyword);
            if (p != null) {
                hasil.add(p);
            }

            return hasil;
        } else {
            return tipe.equalsIgnoreCase("nama") ? this.repository.cariByNama(keyword) : new ArrayList();
        }
    }

    public ArrayList<Putusan> filterPutusan(String keyword, String tipe) {
        switch (tipe.toLowerCase()) {
            case "jenis" -> {
                return this.repository.filterByJenisNarkotika(keyword);
            }
            case "pengadilan" -> {
                return this.repository.filterByPengadilan(keyword);
            }
            case "peran" -> {
                return this.repository.filterByPeran(keyword);
            }
            default -> {
                return new ArrayList();
            }
        }
    }

    public ArrayList<Putusan> filterPutusanRentang(int min, int max) {
        return this.repository.filterByRentangVonis(min, max);
    }

    public boolean hapusPutusan(String nomor) {
        return this.repository.hapus(nomor);
    }

    public boolean updatePutusan(String nomor, Putusan baru) {
        return this.repository.update(nomor, baru);
    }

    public StatistikPutusan getStatistik() {
        return new StatistikPutusan(this.repository.getDaftarPutusan());
    }

    public int getTotalData() {
        return this.repository.getTotalData();
    }

    public ArrayList<Putusan> sortByVonis(ArrayList<Putusan> daftar) {
        ArrayList<Putusan> hasil = new ArrayList(daftar);
        hasil.sort(Comparator.comparingInt(Putusan::getVonisHukuman));
        return hasil;
    }
    public ArrayList<Putusan> sortByDenda(ArrayList<Putusan> daftar) {
        ArrayList<Putusan> hasil = new ArrayList(daftar);
        hasil.sort(Comparator.comparingDouble(Putusan::getVonisDenda).reversed());
        return hasil;
    }

    public ArrayList<Putusan> sortByNama(ArrayList<Putusan> daftar) {
        ArrayList<Putusan> hasil = new ArrayList(daftar);
        hasil.sort(Comparator.comparing(Putusan::getNamaTerdakwa));
        return hasil;
    }

    public void exportToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("LAPORAN PUTUSAN PENGADILAN NARKOTIKA");
            writer.println("=====================================");
            writer.println("Total Data: " + this.repository.getTotalData());
            writer.println("Tanggal Export: " + String.valueOf(LocalDateTime.now()));
            writer.println();

            for(Putusan p : this.repository.getDaftarPutusan()) {
                writer.println(p.toString());
            }

            writer.println();
            writer.println("--- STATISTIK ---");
            StatistikPutusan stat = this.getStatistik();
            writer.println("Total Putusan: " + stat.getTotalPutusan());
            Object[] var10002 = new Object[]{stat.getRataRataVonis()};
            writer.println("Rata-rata Vonis: " + String.format("%.2f", var10002) + " bulan");
            var10002 = new Object[]{stat.getRataRataDenda()};
            writer.println("Rata-rata Denda: Rp " + String.format("%.2f", var10002));
            writer.println("Jenis Terbanyak: " + stat.getJenisNarkotikaTerbanyak());
            System.out.println("Data berhasil diekspor ke: " + filename);
        } catch (IOException e) {
            System.out.println("Gagal mengekspor: " + e.getMessage());
        }

    }

    public void inisialisasiDataSampel() {
        String[][] data = new String[][]{{"1/Pid.Sus/2024/PN Sby", "Pengadilan Negri Surabaya", "2024", "Ricky Noviyanto Bin Sugeng Hariyono", "35", "Narkoba jenis shabu", "1.06", "pasal 114 ayat (1) UU RI NO.35 Tahun 2009 tntng nrkotika", "Bandar", "92", "1000000000", "Dr.Nurnaningsih Amriana,S,H., M,H"}, {"3/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "I Putu Gede Dhani Gita Candra", "31", "Shabu (Metamfetamina)", "2.38", "Pasal 114 ayat (1) UU No. 35 Tahun 2009 tentang Narkotika", "Pengedar", "72", "1000000000", "Cokia Ana Pontia O., S.H., M.H."}, {"6/Pdt.G/2025/PA.Botg", "Pengadilan Agama Bontang", "2025", "Istri", "31", "Perceraian", "0.0", "Hukum Perkawinan Islam", "Penggugat", "0", "0", "Nor Hasanuddin, Lc., M.A."}, {"7/Pdt.G/2025/PA.Wkb", "Pengadilan Agama Waikabubak", "2025", "Nawal Faisal Binti Amir Faisal", "24", "Perceraian", "0.0", "Hukum Perkawinan Islam", "Penggugat", "0", "0", "Farida Latif, S.H.I."}, {"8/Pdt.P/2025/PA.Wkb", "Pengadilan Agama Waikabubak", "2025", "Muhammad Hidayat bin Usman", "38", "Perwalian", "0.0", "Hukum Islam", "Pemohon", "0", "0", "Farida Latif, S.H.I."}, {"9/Pid.Sus/2025/PN Sru", "Pengadilan Negri Serui", "2025", "Ghito Rolies Jems Tanawani Alias Boncu", "35", "Ganja", "39.1", "Pasal 111 ayat (1) UU No. 35 Tahun 2009 tentang Narkotika", "Memiliki dan menyimpan ganja untuk dikonsumsi sendiri", "72", "1000000000", "Maizar Arthur hehanussa, S.H"}, {"10/Pid.Sus/2024/PN Sby", "Pengadilan Negri Surabaya", "2024", "M. Ilham Wahyudi Bin Ilyas", "29", "Sabu (Metamfetamina)", "1.626", "Pasal 114 ayat (1) UU No. 35 Tahun 2009 tentang Narkotika", "Pengedar", "108", "1500000000", "Budi Winarti, S.H., M.H."}, {"11/Pid.Sus/2024/PN.Sby", "Pengadilan Negri Surabaya", "2024", "Gatut Setyo Utomo Bin Suratno", "23", "Sabu (Metamfetamina)", "0.146", "Pasal 114 ayat (1) UU No. 35 Tahun 2009 tentang Narkotika", "pengedar", "84", "1000000000", "Rudito Surotomo, S.H., M.H."}, {"13/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Mochamad Yahya Bin Hadi", "33", "Sabu (Metamfetamina)", "2.24", "Pasal 114 ayat (1) UU No. 35 Tahun 2009 tentang Narkotika", "Pengedar", "72", "1000000000", "Silfi Yanti Zulfia, S.H., M.H."}, {"14/PID.SUS-TPK/2025/PT BDG", "Pengadilan Tinggi Bandung", "2025", "Windu Tresna Wulandhani, S.E., M.P.", "45", "Korupsi", "0.0", "Pasal 3 jo. Pasal 18 UU Nomor 31 Tahun 1999 tentang Pemberantasan Tindak Pidana", "korupsi", "36", "50000000", "H. Baktar Jubri Nasution, S.H., M.H."}, {"15/Pdt.P/2025/PN Sru", "Pengadilan Negeri Serui", "2025", "Nikolaus Wiku", "58", "Permohonan perwalian (voluntair)", "0.0", "Hukum Perdata", "Pemohon", "0", "0", "Rofik Budiantoro, S.H."}, {"20/Pdt.G/2024/PN Tjg", "Pengadilan Negeri Tanjung", "2024", "Murjani", "64", "Perdata Gugatan (Sengketa Hak Milik Tanah)", "0.0", "Hukum Perdata", "Penggugat", "0", "0", "Rimang K. Rizal, S.H"}, {"21/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Wahyu Ade Pratama Firmansyah", "20", "Sabu (Metamfetamina)", "0.463", "asal 114 ayat (1) Undang-Undang Nomor 35 Tahun 2009 tentang Narkotika", "Pengedar", "72", "1000000000", "Rudito Surotomo, S.H., M.H."}, {"25/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Moch Mansjur Bin Rupi", "28", "pil ekstasi (MDMA)", "15.0", "Pasal 114 ayat (2) Undang-Undang Nomor 35 Tahun 2009 tentang Narkotika", "Pengedar", "120", "1000000000", "Silfi Yanti Zulfia, S.H., M.H."}, {"27/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "MOCH. FAHRI Bin JINAB", "52", "Shabu (Metamfetamina)", "2.10", "asal 114 ayat (1) UU No. 35 Tahun 2009 tentang Narkotika", "Pengedar", "84", "1000000000", "Silfi Yanti Zulfia, S.H., M.H"}, {"28/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Kuswantoro Bin Tohir", "26", "Shabu (Metamfetamina)", "106.88", "Pasal 114 ayat (2) jo. Pasal 132 ayat (1) Undang-Undang Nomor 35 Tahun 2009 tentang Narkotika", "Pengedar", "120", "1000000000", "Silfi Yanti Zulfia, S.H., M.H."}, {"29/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "AKHMAD RIYANTO Bin ABDUL AZIS", "31", "shabu (Metamfetamina)", "903.7", "Pasal 114 ayat (2) jo. Pasal 132 ayat (1) Undang-Undang Nomor 35 Tahun 2009 tentang Narkotika", "Pengedar", "204", "2000000000", "Alex Adam Faisal, S.H."}, {"30/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Abdul Bakir Bin Asnawi", "49", "shabu (Metamfetamina)", "20.88", "Pasal 114 ayat (2) jo. Pasal 132 ayat (1) Undang-Undang Nomor 35 Tahun 2009 tentang Narkotika", "Pengedar", "108", "1000000000", "Suparno, S.H., M.H."}, {"31/Pid.Sus/2024/PN.Sby", "Pengadilan Negeri Surabaya", "2024", "MUCHAMAD UMAR FAROQ", "34", "shabu (Metamfetamina)", "903.7", "Pasal 114 ayat (2) jo. Pasal 132 ayat (1) Undang-Undang Nomor 35 Tahun 2009 tentang Narkotika", "Pengedar", "204", "2000000000", "Heru Hanindyo, S.H., M.H., LL.M."}, {"32/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "ALFIN IRFIAN Bin SISWO", "25", "shabu (Metamfetamina)", "0.956", "Pasal 112 ayat (1) Undang-Undang Nomor 35 Tahun 2009 tentang Narkotika", "Pengedar", "60", "800000000", "Dr. Nurnaningsih Amriani, S.H., M.H."}, {"33/Pdt.G/2025/PN Sel", "Pengadilan Negeri Selong", "2025", "Nasrudin Alias Amaq Janah", "62", "Perdata Gugatan", "0.0", "Pasal 271 - 272 RV (Reglement op de Rechtsvordering)", "Penggugat", "0", "0", "da Bagus Oka Saputra M., S.H., M.Hum."}, {"34/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Abdul Gefur Bin Asmar", "37", "shabu (Metamfetamina)", "0.836", "Pasal 112 ayat (1) Undang-Undang RI Nomor 35 Tahun 2009 tentang Narkotika.", "Pengedar", "60", "800000000", "Mochammad Djoenaidie, S.H., M.H."}, {"36/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Alias Tover Tukan Alias Itong", "33", "shabu (Metamfetamina)", "1.63", "Pasal 114 Ayat (1) Undang-Undang RI Nomor 35 Tahun 2009 tentang Narkotika", "Pengedar", "72", "1000000000", "Moch Taufik Tatas Prihyantono, S.H."}, {"39/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Moch. Riadi Bin. Moch. Ridwan", "35", "shabu (Metamfetamina)", "0.229", "Pasal 114 Ayat (1) Undang-Undang RI Nomor 35 Tahun 2009 tentang Narkotika.", "Pengedar", "72", "1000000000", "Erintuah Damanik, S.H., M.H."}, {"45/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "YOGA HARDONO", "46", "shabu (Metamfetamina)", "1.055", "Pasal 114 ayat (2) Jo. Pasal 132 ayat (1) Undang-Undang RI Nomor 35 Tahun 2009 tentang Narkotika.", "Pengedar", "144", "2000000000", "Antyo Harri Susetyo, S.H."}, {"47/Pid.Sus/2025/PN Pmk", "Pengadilan Negeri Pamekasan", "2025", "ABDUL HARIS BIN MIFTAHUL ARIFIN", "28", "shabu (Metamfetamina)", "1.964", "Pasal 112 ayat (1) Undang-Undang RI Nomor 35 Tahun 2009 tentang Narkotika", "Pengedar", "60", "800000000", "Muhammad Arief Fatony, S.H., M.H."}, {"49/Pdt.G/2025/PTA.MTR", "Pengadilan Tinggi Agama Mataram", "2025", "Dyas Fitra Aljosha Bin Muksan", "33", "Cerai Gugat", "0.0", "UU No. 7/1989", "Pembanding", "0", "0", "Drs. H. Suwarto, M.H."}, {"50/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "GUNTUR PAMUNGKAS", "27", "shabu (Metamfetamina)", "2.089", "Pasal 114 ayat (1) Undang-Undang RI Nomor 35 Tahun 2009 tentang Narkotika", "Pengedar", "72", "1200000000", "Sudar, S.H., M.Hum."}, {"54/Pdt.G/2025/PA.NGJ", "Pengadilan Agama Nganjuk", "2025", "Suami", "51", "Perceraian", "0.0", "UU No. 7/1989", "Pemohon", "0", "0", "Drs. Malem Puteh, S.H., M.H."}, {"55/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Ilham Reza Izzuddin Alias Reza Bin Eko Suhartono", "25", "Ganja (Narkotika Golongan I dalam bentuk tanaman)", "9.197", "Pasal 114 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menjual dan menerima Narkotika Golongan I (menjadi perantara dalam jual beli)", "78", "1000000000", "Antyo Harry Susetyo, S.H."}, {"58/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Kasmanto Bin Slamet", "38", "Sabu-sabu (Metamfetamina) Golongan I bukan tanaman", "0.079", "Pasal 112 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Memiliki dan menguasai Narkotika Golongan I bukan tanaman", "48", "1000000000", "Saifudin Zuhri, S.H., M.Hum."}, {"60/Pid.C/2025/PN Gpr", "Pengadilan Negeri Kabupaten Kediri", "2025", "Andri Bin Jayadi", "43", "Menjual minuman keras tanpa izin (bukan narkotika)", "0.0", "Pasal 25 ayat (1) huruf b jo Pasal 41 huruf e Jo Pasal 50 ayat (1) Perda Nomor 6 Tahun 2017", "Menjual minuman keras tanpa izin", "0", "5000", "Rofi Heryanto, S.H."}, {"61/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Fausen Alias Kacong Bin Sonhaji", "23", "Sabu-sabu (Metamfetamina) Golongan I bukan tanaman", "0.551", "Pasal 112 Ayat (1) UU No. 35 Tahun 2009 tentang Narkotika", "Memiliki, menyimpan, menguasai Narkotika Golongan I", "78", "800000000", "Taufan Mandala, S.H., M.Hum."}, {"61/Pdt.G.S/2025/PN Pwd", "Pengadilan Negeri Purwodadi", "2025", "PT. BPR Bank Purwa Artha (Perseroda)", "0", "Gugatan Sederhana", "0.0", "HIR / RBg", "Penggugat", "0", "0", "Horas El Cairo Purba, S.H., M.H."}, {"62/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Muhammad Rohman Bin Lukdin", "45", "Sabu-sabu (Metamfetamina) Golongan I bukan tanaman", "0.069", "Pasal 112 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menguasai Narkotika Golongan I bukan tanaman", "66", "900000000", "Dr. Nurnaningsih Amriani, S.H., M.H."}, {"63/Pdt.G/2025/PA.Ntn", "Pengadilan Agama Natuna", "2025", "PENGGUGAT (Nama dilindungi)", "54", "Cerai Gugat (Perceraian)", "0.0", "UU Perkawinan", "Penggugat", "0", "0", "Sardianto, S.H.I., M.H.I."}, {"64/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "M. Abdul Sakur Bin M. Hariri dan Musaffa Bin Umar", "31", "Ekstasi (MDMA) Golongan I bukan tanaman", "1.57", "Pasal 114 Ayat (1) jo Pasal 132 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Permufakatan jahat menawarkan untuk dijual Narkotika Golongan I", "60", "1000000000", "Widiarso, S.H., M.H."}, {"64/Pdt.G/2025/PA.Ntn", "Pengadilan Agama Natuna", "2025", "PENGGUGAT (Nama dilindungi)", "49", "Cerai Gugat (Perceraian)", "0.0", "UU Perkawinan", "Penggugat", "0", "0", "Sardianto, S.H.I., M.H.I."}, {"65/Pid.C/2025/PN Gpr", "Pengadilan Negeri Kabupaten Kediri", "2025", "Muh Rifai Bin Samsul", "30", "Menjual minuman keras tanpa izin (bukan narkotika)", "0.0", "Perda Kab Kediri No.04 Tahun 1962 Pasal 2 Jo 17", "Menjual minuman keras tanpa izin", "0", "5000", "Rofi Heryanto, S.H."}, {"65/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Musaffa Bin Umar", "36", "Ekstasi (MDMA) Golongan I bukan tanaman", "7.96", "Pasal 114 Ayat (2) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menjadi perantara dalam jual beli Narkotika Golongan I", "84", "1000000000", "Ferdinand Marcus Leander, S.H., M.H."}, {"66/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Herianto Alias Heri Bin Sukri (alm)", "40", "Sabu-sabu (Metamfetamina) Golongan I bukan tanaman", "3.14", "Pasal 114 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menjual dan membeli Narkotika Golongan I", "72", "1000000000", "Toniwidjaya Hansberd Hilly, S.H."}, {"67/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Lanang Firdaus Ingpranata Buana bin Raden Monmowangsa", "20", "Sinte/tembakau gorilla (MDMB-4en PINACA) Golongan I", "47.0", "Pasal 114 Ayat (2) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menjual beli Narkotika Golongan I bukan tanaman beratnya >5 gram", "120", "1000000000", "Hj. Halima Umaternate, S.H., M.H."}, {"70/Pdt.G/2025/PA.Ntn", "Pengadilan Agama Natuna", "2025", "PENGGUGAT (Nama dilindungi)", "20", "Cerai Gugat (Perceraian)", "0.0", "UU Perkawinan", "Penggugat", "0", "0", "Sardianto, S.H.I., M.H.I."}, {"75/Pdt.G/2025/PTA.JK", "Pengadilan Tinggi Agama Jakarta", "2025", "PEMBANDING", "59", "Gugatan Cerai (Banding)", "0.0", "UU Perkawinan", "Pembanding", "0", "0", "Drs. H. Suwarto, M.H."}, {"79/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Moch. Nopel Bin Binakram (ALM)", "42", "Sabu-sabu (Metamfetamina) Golongan I", "0.513", "Pasal 114 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menawarkan untuk dijual Narkotika Golongan I", "72", "1000000000", "Djuanto, S.H., M.H."}, {"80/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Moch. Dahlan Bin Rasit (Alm)", "45", "Sabu-sabu (Metamfetamina) Golongan I bukan tanaman", "5.018", "Pasal 114 Ayat (2) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menawarkan untuk dijual Narkotika Golongan I >5 gram", "78", "1000000000", "Heru Hanindyo, S.H., M.H., LL.M."}, {"81/Pdt.G/2025/MS.Skm", "Mahkamah Syar'iyah Suka Makmue", "2025", "XX (Nama dilindungi)", "24", "Gugatan Perceraian, Hak Asuh Anak serta Nafkah Anak", "0.0", "UU Perkawinan", "Penggugat", "0", "0", "Achmad Sofyan Aji Sudrajad, S.H., M.H."}, {"82-K/PM.I-02/AD/IX/2021", "Pengadilan Militer I-02 Medan", "2021", "Trivosa Silalahi", "34", "Pidana Militer", "0.0", "Pasal 87 Ayat (1) ke-2 jo Ayat (2) jo Pasal 88 Ayat (1) ke-1 KUHPM", "Baurjahril Minperslog (Serka)", "12", "10000", "Muhammad Rizal, S.H., M.H."}, {"84/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Husnul Yaqin als Asmad Bin Zahrah", "41", "Sabu-sabu (Metamfetamina) Golongan I bukan tanaman", "0.639", "Pasal 114 Ayat (1) UU No. 35 Tahun 2009 tentang Narkotika", "Membeli dan menjual Narkotika Golongan I", "84", "1000000000", "Dr. Nurnaningsih Amriani, S.H., M.H."}, {"86/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Moch. Oesman Bin Mat Sui", "48", "Sabu-sabu (Metamfetamina) Golongan I", "0.868", "Pasal 114 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Membeli dan menjual Narkotika Golongan I", "84", "1000000000", "I Dewa Gede Suarditha, S.H., M.H."}, {"87/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Yuli Subagyo Bin Irsad (Alm)", "46", "Sabu-sabu (Metamfetamina) Golongan I", "1.681", "Pasal 114 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menawarkan untuk dijual Narkotika Golongan I", "72", "1000000000", "Ni Putu Sri Indayani, S.H., M.H."}, {"88/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Ahmad Faishol als Faishol Bin Shobari", "29", "Ganja dan Sabu-sabu (Golongan I)", "147.382", "Pasal 114 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menawarkan untuk dijual, menjual, membeli Narkotika Golongan I", "144", "2000000000", "Ferdinand Marcus Leander, S.H., M.H."}, {"91/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Ketot Samiawan Alias Wawa Bin Ponimin (Alm)", "34", "Sabu-sabu (Metamfetamina) Golongan I", "4.003", "Pasal 114 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menjual Narkotika Golongan I", "90", "1000000000", "Silfi Yanti Zulfia, S.H., M.H."}, {"91/PID.SUS/2025/PT BJM", "Pengadilan Tinggi Banjarmasin", "2025", "Rahmatullah Bin Mappaita", "41", "Membawa senjata tajam tanpa izin (badik)", "0.0", "Pasal 2 Ayat (1) Undang-Undang Darurat RI Nomor 12 Tahun 1951", "Membawa senjata tajam tanpa izin", "18", "5000", "Andi Astara, S.H., M.H."}, {"94/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Fikri Ramadanu Bin Marduki", "21", "Sabu-sabu (Metamfetamina) Golongan I", "0.200", "Pasal 114 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Membeli dan menjadi perantara dalam jual beli Narkotika Golongan I", "66", "1000000000", "Dr. Nurnaningsih Amriani, S.H., M.H."}, {"95/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Rasmat Als. Lahmat Bin Mat Radji", "58", "Sabu-sabu (Metamfetamina) Golongan I", "0.772", "Pasal 114 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menjual dan membeli Narkotika Golongan I", "84", "1000000000", "Alex Adam Faisal, S.H."}, {"96/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Achmad Junaidi Bin Achmad Nur dan Fajar Apriyanto Bin Sutrisno", "35", "Sabu-sabu (Metamfetamina) Golongan I", "2.536", "Pasal 114 Ayat (1) jo Pasal 132 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Permufakatan jahat menawarkan untuk dijual Narkotika Golongan I", "84", "1000000000", "Ni Putu Sri Indayani, S.H., M.H."}, {"97/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Dimas Agus Setiawan Bin Dadik Masturiawan", "21", "Sabu-sabu (Metamfetamina) Golongan I", "0.259", "Pasal 114 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menjual Narkotika Golongan I", "72", "1000000000", "Ferdinand Marcus Leander, S.H., M.H."}, {"98/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Moch. Saiful Bin Moch. Hustadi", "37", "Sabu-sabu (Metamfetamina) Golongan I", "0.469", "Pasal 114 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Menjadi perantara dalam jual beli Narkotika Golongan I", "0", "1000000000", "Mangapul, S.H., M.H."}, {"103/Pid.Sus/2024/PN Sby", "Pengadilan Negeri Surabaya", "2024", "Hadi Yatno Alias Jon Bin Seli dan Achmad Fauzi Bin Andrawi", "27", "Sabu-sabu (Metamfetamina) Golongan I", "1.27", "Pasal 114 Ayat (1) jo Pasal 132 Ayat (1) UU RI No. 35 Tahun 2009 tentang Narkotika", "Permufakatan jahat menerima, menjual dan menjadi perantara dalam jual beli Narkotika Golongan I", "78", "1500000000", "Heru Hanindyo, S.H., M.H., LL.M."}};

        for(String[] row : data) {
            try {
                Putusan p = new Putusan(row[0], row[1], row[2], row[3], Integer.parseInt(row[4]), row[5], Double.parseDouble(row[6]), row[7], row[8], Integer.parseInt(row[9]), Double.parseDouble(row[10]), row[11]);
                this.repository.simpan(p);
            } catch (Exception e) {
                System.out.println("Error loading sample " + row[0] + ": " + e.getMessage());
            }
        }

    }
}

