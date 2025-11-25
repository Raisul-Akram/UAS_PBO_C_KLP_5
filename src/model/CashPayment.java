package model;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CashPayment implements Payment {

    // Method utama untuk memproses pembayaran tunai
    @Override
    public boolean processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);

        // 1. Tampilkan Judul dan Total Tagihan
        System.out.println("\n--- Pembayaran Tunai ---");
        System.out.println("Total Tagihan: Rp " + (long)amount);
        System.out.println("(Ketik '0' jika ingin membatalkan)");
        
        // 2. Loop (Perulangan) agar user bisa coba lagi jika uang kurang
        while (true) {
            System.out.print("Masukkan jumlah uang tunai: Rp ");
            try {
                // Baca input uang dari user
                double cashGiven = Double.parseDouble(scanner.nextLine());

                // 3. Cek apakah user ingin batal (Input 0)
                if (cashGiven == 0) {
                    System.out.println("Pembayaran dibatalkan.");
                    return false; // Kembali ke menu utama
                }

                // 4. Cek apakah uang cukup
                if (cashGiven >= amount) {
                    // Hitung kembalian
                    double change = cashGiven - amount;
                    
                    // 5. Cetak Struk Bukti Pembayaran (Lengkap dengan Barcode)
                    printReceipt("TUNAI", amount, cashGiven, change);
                    
                    return true; // Transaksi Sukses
                } else {
                    // Jika uang kurang, beri info dan tanya mau coba lagi?
                    System.out.println("Uang kurang Rp " + (long)(amount - cashGiven));
                    System.out.print("Coba lagi? (y/n): ");
                    
                    // Jika jawab 'n', batalkan transaksi
                    if (!scanner.nextLine().equalsIgnoreCase("y")) return false;
                }
            } catch (NumberFormatException e) {
                // Tangani jika user input huruf bukan angka
                System.out.println("Input salah. Harap masukkan angka.");
            }
        }
    }
