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

    public void tampilkan() {
        System.out.printf("| %-22s | %-18s | %-10s | %-8s | %-5d bln | Rp%-11.0f |%n",
                nomorPerkara, namaTerdakwa, jenisNarkotika, peranTerdakwa, vonisHukuman, vonisDenda);
    }

    public void tampilkan(boolean detail) {
        if (detail) {
            System.out.println("==============================================================");
            System.out.println("                 DETAIL PUTUSAN PENGADILAN                    ");
            System.out.println("==============================================================");
            System.out.printf(" Nomor Perkara      : %s%n", nomorPerkara);
            System.out.printf(" Pengadilan         : %s%n", pengadilan);
            System.out.printf(" Tanggal Putusan    : %s%n", tanggalPutusan);
            System.out.printf(" Nama Terdakwa      : %s%n", namaTerdakwa);
            System.out.printf(" Umur Terdakwa      : %d tahun%n", umurTerdakwa);
            System.out.printf(" Jenis Narkotika    : %s%n", jenisNarkotika);
            System.out.printf(" Berat BB           : %.2f gram%n", beratBarangBukti);
            System.out.printf(" Pasal Dilanggar    : %s%n", pasalDilanggar);
            System.out.printf(" Peran Terdakwa     : %s%n", peranTerdakwa);
            System.out.printf(" Vonis Hukuman      : %d bulan%n", vonisHukuman);
            System.out.printf(" Vonis Denda        : Rp %.2f%n", vonisDenda);
            System.out.printf(" Nama Hakim         : %s%n", namaHakim);
            System.out.printf(" Kategori Hukuman   : %s%n", getKategoriHukuman());
            System.out.println("==============================================================");
        } else {
            tampilkan();
        }
    }

    public String getKategoriHukuman() {
        if (vonisHukuman < 60) return "Ringan";
        else if (vonisHukuman <= 120) return "Sedang";
        else return "Berat";
    }

    @Override
    public String toString() {
        return String.format("Putusan[%s | %s | %s | %s | %d bln | Rp%.0f]",
                nomorPerkara, namaTerdakwa, jenisNarkotika, peranTerdakwa,
                vonisHukuman, vonisDenda);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Putusan putusan = (Putusan) obj;
        return nomorPerkara != null && nomorPerkara.equalsIgnoreCase(putusan.nomorPerkara);
    }

    @Override
    public int hashCode() {
        return nomorPerkara != null ? nomorPerkara.toLowerCase().hashCode() : 0;
    }

    public String getNomorPerkara() { return nomorPerkara; }
    public String getPengadilan() { return pengadilan; }
    public String getTanggalPutusan() { return tanggalPutusan; }
    public String getNamaTerdakwa() { return namaTerdakwa; }
    public int getUmurTerdakwa() { return umurTerdakwa; }
    public String getJenisNarkotika() { return jenisNarkotika; }
    public double getBeratBarangBukti() { return beratBarangBukti; }
    public String getPasalDilanggar() { return pasalDilanggar; }
    public String getPeranTerdakwa() { return peranTerdakwa; }
    public int getVonisHukuman() { return vonisHukuman; }
    public double getVonisDenda() { return vonisDenda; }
    public String getNamaHakim() { return namaHakim; }
    public static int getJumlahDibuat() { return jumlahDibuat; }

