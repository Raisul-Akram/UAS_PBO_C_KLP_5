package model;

import java.util.Scanner;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VirtualAccountPayment implements Payment {

    @Override
    public boolean processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);
        
        // 1. Generate Nomor VA Acak (Simulasi Bank)
        // Angka '8800' adalah kode bank, sisanya random
        String vaNumber = "8800" + (new Random().nextInt(900000) + 100000);

        // --- TAMBAHAN BIAYA ADMIN (Rp 2000) ---
        double adminFee = 2000;
        double totalBayar = amount + adminFee;
        // ---------------------------------------
        
        System.out.println("\n--- Pembayaran Virtual Account ---");
        System.out.println("Nomor VA      : " + vaNumber); // Tunjukkan nomor ini saat demo
        
        // Update Tampilan Tagihan dengan Admin Fee
        System.out.println("Harga Tiket   : Rp " + (long)amount);
        System.out.println("Biaya Admin   : Rp " + (long)adminFee);
        System.out.println("TOTAL BAYAR   : Rp " + (long)totalBayar);
        
        System.out.println("(Ketik '0' untuk batal)");

        // 2. Loop Input Transfer
        while (true) {
            System.out.print("Masukkan nominal transfer: Rp ");
            try {
                double transfer = Double.parseDouble(scanner.nextLine());

                // 3. Fitur Batal
                if (transfer == 0) {
                    System.out.println("Pembayaran dibatalkan.");
                    return false;
                }

                // 4. Validasi Transfer (Cek terhadap TOTAL BAYAR)
                if (transfer >= totalBayar) {
                    // Cetak Struk dengan Nomor VA
                    printReceipt("VIRTUAL ACCOUNT", amount, adminFee, totalBayar, transfer, vaNumber);
                    return true;
                } else {
                    System.out.println("Nominal transfer salah (Harus Rp " + (long)totalBayar + ").");
                    System.out.print("Coba lagi? (y/n): ");
                    if (!scanner.nextLine().equalsIgnoreCase("y")) return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input salah.");
            }
        }
    }

    @Override
    public String getPaymentType() {
        return "Virtual Account";
    }

    private void printReceipt(String method, double ticketPrice, double adminFee, double total, double paid, String vaNum) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("\n=========================================");
        System.out.println("          BUKTI PEMBAYARAN LUNAS        ");
        System.out.println("===========================================");
        System.out.printf("%-15s: %s\n", "Tanggal", date);
        System.out.printf("%-15s: %s\n", "Metode", method);
        System.out.printf("%-15s: %s\n", "No. Ref", vaNum); // Tampilkan No VA di struk
        System.out.println("-------------------------------------------");
        System.out.printf("%-15s: Rp %d\n", "Harga Tiket", (long)ticketPrice);
        System.out.printf("%-15s: Rp %d\n", "Biaya Admin", (long)adminFee);
        System.out.println("-------------------------------------------");
        System.out.printf("%-15s: Rp %d\n", "TOTAL TAGIHAN", (long)total);
        System.out.printf("%-15s: Rp %d\n", "Total Bayar", (long)paid);
        System.out.println("===========================================");
        System.out.println("        Terima Kasih atas Kunjungan Anda");
        
        // 5. Barcode Tiket Bioskop (Sesuai requestmu)
        System.out.println("\n           TIKET VALIDATED");
        System.out.println("║█║▌║█║▌│║▌█║▌║│█║▌║█║▌║║█║▌║█║▌│║▌█║▌║│█║▌");
        System.out.println("║█║▌║█║▌│║▌█║▌║│█║▌║█║▌║║█║▌║█║▌│║▌█║▌║│█║▌");
        System.out.println("===========================================\n");
    }
}
