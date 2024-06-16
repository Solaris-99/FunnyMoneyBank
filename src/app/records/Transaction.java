package app.records;
import java.util.Date;

public record Transaction(int id, double amount, Date date, int id_wallet, int id_transaction_type) {
}
