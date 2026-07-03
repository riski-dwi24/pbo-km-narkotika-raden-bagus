package model;

/**
 * Kelas Putusan merepresentasikan data putusan pengadilan narkotika.
 */
public class Putusan {

    private String nomorPerkara;
    private String pengadilan;
    private String tanggalPutusan;
    private String namaTerdakwa;
    private int umurTerdakwa;
    private String jenisNarkotika;
    private double beratBarangBukti;
    private String pasalDilanggar;
    private String peranTerdakwa;
    private int vonisHukuman;
    private double vonisDenda;
    private String namaHakim;

    private static int jumlahDibuat = 0;

    public Putusan() {
        this.nomorPerkara = "";
        this.pengadilan = "";
        this.tanggalPutusan = "";
        this.namaTerdakwa = "";
        this.umurTerdakwa = 0;
        this.jenisNarkotika = "";
        this.beratBarangBukti = 0.0;
        this.pasalDilanggar = "";
        this.peranTerdakwa = "";
        this.vonisHukuman = 0;
        this.vonisDenda = 0.0;
        this.namaHakim = "";
        jumlahDibuat++;
    }

    public Putusan(String nomorPerkara, String pengadilan, String tanggalPutusan,
                   String namaTerdakwa, int umurTerdakwa, String jenisNarkotika,
                   double beratBarangBukti, String pasalDilanggar, String peranTerdakwa,
                   int vonisHukuman, double vonisDenda, String namaHakim) {
        setNomorPerkara(nomorPerkara);
        setPengadilan(pengadilan);
        setTanggalPutusan(tanggalPutusan);
        setNamaTerdakwa(namaTerdakwa);
        setUmurTerdakwa(umurTerdakwa);
        setJenisNarkotika(jenisNarkotika);
        setBeratBarangBukti(beratBarangBukti);
        setPasalDilanggar(pasalDilanggar);
        setPeranTerdakwa(peranTerdakwa);
        setVonisHukuman(vonisHukuman);
        setVonisDenda(vonisDenda);
        setNamaHakim(namaHakim);
        jumlahDibuat++;
    }
