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
}