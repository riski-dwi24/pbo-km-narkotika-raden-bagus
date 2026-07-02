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
