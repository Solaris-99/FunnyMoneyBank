package app.records;

import java.sql.Date;

public record AtmTransaction(int id, int id_employee, int id_atm, double amount, Date date) {
}
