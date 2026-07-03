# KMS Putusan Pengadilan Narkotika

## Deskripsi Proyek
Aplikasi Knowledge Management System (KMS) untuk mengelola data putusan pengadilan narkotika berbasis Java MVC.  
Mendukung dua mode tampilan:
- Console (Terminal) – interaksi berbasis teks.
- GUI (Swing) – antarmuka grafis untuk kemudahan akses.

Fitur utama:
- CRUD data putusan (Tambah, Lihat, Cari, Hapus, Update)
- Filter & Pencarian (berdasarkan nomor, nama, jenis narkotika, pengadilan, peran)
- Statistik otomatis (total, rata-rata vonis, rata-rata denda, jenis narkotika terbanyak)
- Sorting data (vonis, denda, nama)
- Export data ke file `.txt`
- Bonus: Sorting, Export Statistik

---

## Anggota Kelompok
| Nama       | NIM             | Peran                          | Branch               |
|------      |-----------------|--------------------------------|----------------------|
| Abyan      | 202310370311477 | View Developer (Console + GUI) | `feature/view`       |
| Risky      | 202510370110106 | Controller Engineer            | `feature/controller` |
| Firman     | 202110370311136 | Model Engineer                 | `feature/model`      |

---

## Cara Kompilasi
Pastikan Java JDK 11 dan Maven terinstal.

# Clone repository
git clone https://github.com/riski-dwi24/pbo-km-narkotika-Barokah.git
cd pbo-km-narkotika-Barokah

# Compile dengan Maven
mvn clean compile



#Jalankan Aplikasi Menggunakan IDE (IntelliJ/Eclipse)
Buka project di IDE
Buka file Main.java (di root folder src/)
Klik kanan → pilih Run 'Main.main()'
Saat muncul prompt di console, ketik 1 untuk Console atau 2 untuk GUI


#Struktur Proyek 
-controller/KnowledgeController.java → Controller (jembatan Model-View)
-model/Putusan.java → Entity/data class
-model/KnowledgeRepository.java → CRUD & penyimpanan data
-model/StatistikPutusan.java → Perhitungan statistik
-model/LaporanDasar.java → Abstract class untuk laporan
-util/InputHandler.java → Validasi input & exception handling
-util/Sortable.java → Interface untuk sorting
-view/ConsoleView.java → Tampilan console (terminal)
-view/PutusanGUI.java → Tampilan GUI (Swing)
-view/Main.java → Entry point aplikasi


#Video Demo
https://www.youtube.com/watch?v=UW-Lh9xKBAQ

