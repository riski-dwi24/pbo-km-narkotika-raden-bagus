package model;

/**
 * Kelas abstrak dasar untuk laporan (Inheritance).
 * Wajib di-extend oleh class laporan konkret.
 */
abstract class LaporanDasar {

    /**
     * Method abstrak untuk menampilkan laporan.
     * Harus diimplementasikan oleh subclass.
     */
    public abstract void tampilkanLaporan();

    /**
     * Method konkret untuk header laporan.
     */
    public void tampilkanHeader() {
        System.out.println("==============================================================");
    }
}