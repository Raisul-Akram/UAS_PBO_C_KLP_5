package model;

public class QRISPayment implements Payment {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Pembayaran QRIS berhasil.");
        return true;
    }
