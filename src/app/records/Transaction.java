package app.records;
import java.sql.Date;

public record Transaction(int id, double amount, Date date, int id_transaction_type, int id_atm, int id_wallet, int id_wallet_target) {
}
