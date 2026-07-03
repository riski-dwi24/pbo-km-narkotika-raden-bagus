package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatistikPutusan extends LaporanDasar {

    private int totalPutusan;
    private double rataRataVonis;
    private double rataRataDenda;
    private String jenisNarkotikaTerbanyak;
    private String[] distribusiPeran;
    private ArrayList<Putusan> daftarPutusan;

    public StatistikPutusan(ArrayList<Putusan> daftarPutusan) {
        this.daftarPutusan = daftarPutusan != null ? daftarPutusan : new ArrayList<>();
        this.totalPutusan = this.daftarPutusan.size();
        this.distribusiPeran = new String[0];
        hitungSemua();
    }

    public void hitungSemua() {
        this.totalPutusan = daftarPutusan.size();
        if (daftarPutusan.isEmpty()) {
            this.rataRataVonis = 0;
            this.rataRataDenda = 0;
            this.jenisNarkotikaTerbanyak = "Tidak ada data";
            this.distribusiPeran = new String[0];
            return;
        }

        double totalVonis = 0;
        double totalDenda = 0;

        HashMap<String, Integer> mapJenis = new HashMap<>();
        HashMap<String, Integer> mapPeran = new HashMap<>();

        for (Putusan p : daftarPutusan) {
            totalVonis += p.getVonisHukuman();
            totalDenda += p.getVonisDenda();

            String jenis = p.getJenisNarkotika();
            if (jenis != null && !jenis.trim().isEmpty()) {
                mapJenis.put(jenis, mapJenis.getOrDefault(jenis, 0) + 1);
            }

            String peran = p.getPeranTerdakwa();
            if (peran != null && !peran.trim().isEmpty()) {
                mapPeran.put(peran, mapPeran.getOrDefault(peran, 0) + 1);
            }
        }

        this.rataRataVonis = totalVonis / totalPutusan;
        this.rataRataDenda = totalDenda / totalPutusan;

        String maxJenis = "Tidak ada data";
        int maxCountJenis = -1;
        for (Map.Entry<String, Integer> entry : mapJenis.entrySet()) {
            if (entry.getValue() > maxCountJenis) {
                maxCountJenis = entry.getValue();
                maxJenis = entry.getKey();
            }
        }
        this.jenisNarkotikaTerbanyak = maxJenis + " (" + maxCountJenis + " kasus)";

        ArrayList<String> listPeran = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mapPeran.entrySet()) {
            listPeran.add(entry.getKey() + ": " + entry.getValue() + " terdakwa");
        }
        this.distribusiPeran = listPeran.toArray(new String[0]);
    }