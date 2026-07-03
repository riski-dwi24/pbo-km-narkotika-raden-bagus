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