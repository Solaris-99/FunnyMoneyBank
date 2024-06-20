package app.helpers;

public enum Operation {
    WITHDRAW(1),
    DEPOSIT(2),
    TRANSFER(3);

    private final int id;

    Operation(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public static Operation fromId(int id) {
        for (Operation op : values()) {
            if (op.getId() == id) {
                return op;
            }
        }
        throw new IllegalArgumentException("Invalid ID: " + id);
    }

    @Override
    public String toString() {
        return switch (this.id) {
            case 1 -> "Retiro";
            case 2 -> "Depósito";
            case 3 -> "Transferencia";
            case 4 -> "Re-establecimiento";
            default -> name();
        };
    }

}
