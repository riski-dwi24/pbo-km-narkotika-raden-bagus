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
