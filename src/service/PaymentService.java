package service;

import model.Payment;

/**
 * Service untuk pembayaran.
 * @author Kelompok 5
 */

public class PaymentService {
    public boolean process(Payment payment, double amount) {
        return payment.processPayment(amount);
    }
}
