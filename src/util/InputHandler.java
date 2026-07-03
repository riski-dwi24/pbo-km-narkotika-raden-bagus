package util;

import java.util.Scanner;

public class InputHandler {
    public static int validasiInt(String prompt, Scanner sc) {
        while(true) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException var3) {
                System.out.println("  Error: Input harus berupa angka bulat. Silakan coba lagi.");
            }
        }
    }

    public static int validasiIntPositif(String prompt, Scanner sc) {
        while(true) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine().trim();
                int nilai = Integer.parseInt(input);
                if (nilai >= 0) {
                    return nilai;
                }

                System.out.println("  Error: Nilai tidak boleh negatif. Silakan coba lagi.");
            } catch (NumberFormatException var4) {
                System.out.println("  Error: Input harus berupa angka bulat. Silakan coba lagi.");
            }
        }
    }

    public static int validasiUmur(String prompt, Scanner sc) {
        while(true) {
            int umur = validasiInt(prompt, sc);
            if (umur >= 0 && umur <= 150) {
                return umur;
            }

            System.out.println("  Error: Umur harus antara 0-150 tahun.");
        }
    }

    public static double validasiDouble(String prompt, Scanner sc) {
        while(true) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine().trim().replace(",", ".");
                return Double.parseDouble(input);
            } catch (NumberFormatException var3) {
                System.out.println("  Error: Input harus berupa angka desimal. Silakan coba lagi.");
            }
        }
    }


    public static double validasiDoublePositif(String prompt, Scanner sc) {
        while(true) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine().trim().replace(",", ".");
                double nilai = Double.parseDouble(input);
                if (nilai >= (double)0.0F) {
                    return nilai;
                }

                System.out.println("  Error: Nilai tidak boleh negatif. Silakan coba lagi.");
            } catch (NumberFormatException var5) {
                System.out.println("  Error: Input harus berupa angka desimal. Silakan coba lagi.");
            }
        }
    }

    public static String validasiString(String prompt, Scanner sc) {
        while(true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("  Error: Input tidak boleh kosong. Silakan coba lagi.");
        }
    }

    public static boolean konfirmasi(String prompt, Scanner sc) {
        while(true) {
            System.out.print(prompt + " (Y/N): ");
            String input = sc.nextLine().trim().toUpperCase();
            if (!input.equals("Y") && !input.equals("YA")) {
                if (!input.equals("N") && !input.equals("TIDAK")) {
                    System.out.println("  Error: Masukkan Y atau N.");
                    continue;
                }

                return false;
            }

            return true;
        }
    }
}

// Validasi rentang angka
public static int validasiRentang(String prompt, int min, int max, Scanner sc) {
    while (true) {
        int nilai = validasiInt(prompt, sc);

        if (nilai >= min && nilai <= max) {
            return nilai;
        }

        System.out.println("  Error: Nilai harus antara " + min + " dan " + max + ".");
    }
}

// Validasi panjang minimal string
public static String validasiPanjangString(String prompt, int min, Scanner sc) {
    while (true) {
        String input = validasiString(prompt, sc);

        if (input.length() >= min) {
            return input;
        }

        System.out.println("  Error: Minimal " + min + " karakter.");
    }
}

// Validasi pilihan menu
public static int validasiMenu(String prompt, int jumlahMenu, Scanner sc) {
    while (true) {
        int pilihan = validasiInt(prompt, sc);

        if (pilihan >= 1 && pilihan <= jumlahMenu) {
            return pilihan;
        }

        System.out.println("  Error: Pilihan menu tidak tersedia.");
    }
}