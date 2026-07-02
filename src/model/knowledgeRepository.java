package model;

import java.util.ArrayList;
import java.util.Comparator;

public class KnowledgeRepository {

    private ArrayList<Putusan> daftarPutusan;

    public KnowledgeRepository() {
        this.daftarPutusan = new ArrayList<>();
    }

    public void simpan(Putusan p) {
        if (p == null) {
            throw new IllegalArgumentException("Putusan tidak boleh null!");
        }
        for (Putusan existing : daftarPutusan) {
            if (existing.getNomorPerkara().equalsIgnoreCase(p.getNomorPerkara())) {
                throw new IllegalArgumentException("Nomor perkara sudah ada: " + p.getNomorPerkara());
            }
        }
        daftarPutusan.add(p);
    }

    public Putusan cariByNomor(String nomor) {
        if (nomor == null) return null;
        for (Putusan p : daftarPutusan) {
            if (p.getNomorPerkara() != null && p.getNomorPerkara().equalsIgnoreCase(nomor.trim())) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Putusan> cariByNama(String nama) {
        ArrayList<Putusan> hasil = new ArrayList<>();
        if (nama == null) return hasil;
        String keyword = nama.toLowerCase().trim();
        for (Putusan p : daftarPutusan) {
            if (p.getNamaTerdakwa() != null && p.getNamaTerdakwa().toLowerCase().contains(keyword)) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public ArrayList<Putusan> filterByJenisNarkotika(String jenis) {
        ArrayList<Putusan> hasil = new ArrayList<>();
        if (jenis == null) return hasil;
        String keyword = jenis.toLowerCase().trim();
        for (Putusan p : daftarPutusan) {
            if (p.getJenisNarkotika() != null && p.getJenisNarkotika().toLowerCase().contains(keyword)) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public ArrayList<Putusan> filterByPengadilan(String pengadilan) {
        ArrayList<Putusan> hasil = new ArrayList<>();
        if (pengadilan == null) return hasil;
        String keyword = pengadilan.toLowerCase().trim();
        for (Putusan p : daftarPutusan) {
            if (p.getPengadilan() != null && p.getPengadilan().toLowerCase().contains(keyword)) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public ArrayList<Putusan> filterByPeran(String peran) {
        ArrayList<Putusan> hasil = new ArrayList<>();
        if (peran == null) return hasil;
        String keyword = peran.toLowerCase().trim();
        for (Putusan p : daftarPutusan) {
            if (p.getPeranTerdakwa() != null && p.getPeranTerdakwa().toLowerCase().contains(keyword)) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public ArrayList<Putusan> filterByRentangVonis(int minVonis, int maxVonis) {
        ArrayList<Putusan> hasil = new ArrayList<>();
        for (Putusan p : daftarPutusan) {
            int vonis = p.getVonisHukuman();
            if (vonis >= minVonis && vonis <= maxVonis) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public boolean hapus(String nomor) {
        Putusan target = cariByNomor(nomor);
        if (target != null) {
            daftarPutusan.remove(target);
            return true;
        }
        return false;
    }

    public boolean update(String nomor, Putusan putusanBaru) {
        Putusan target = cariByNomor(nomor);
        if (target != null && putusanBaru != null) {
            int index = daftarPutusan.indexOf(target);
            daftarPutusan.set(index, putusanBaru);
            return true;
        }
        return false;
    }

    public ArrayList<Putusan> getDaftarPutusan() {
        return new ArrayList<>(daftarPutusan);
    }

    public int getTotalData() {
        return daftarPutusan.size();
    }

    public ArrayList<Putusan> sortByVonisAsc() {
        ArrayList<Putusan> sorted = new ArrayList<>(daftarPutusan);
        sorted.sort(Comparator.comparingInt(Putusan::getVonisHukuman));
        return sorted;
    }

    public ArrayList<Putusan> sortByDendaDesc() {
        ArrayList<Putusan> sorted = new ArrayList<>(daftarPutusan);
        sorted.sort(Comparator.comparingDouble(Putusan::getVonisDenda).reversed());
        return sorted;
    }

    public ArrayList<Putusan> sortByNamaAsc() {
        ArrayList<Putusan> sorted = new ArrayList<>(daftarPutusan);
        sorted.sort(Comparator.comparing(Putusan::getNamaTerdakwa));
        return sorted;
    }
}