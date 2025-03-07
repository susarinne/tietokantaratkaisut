package converter;

public enum AikidoRank {

    // Kyu ranks from lowest to highest
    KYU_6(1),
    KYU_5(2),
    KYU_4(3),
    KYU_3(4),
    KYU_2(5),
    KYU_1(6),

    // Dan ranks (black belt ranks) from lowest to highest
    DAN_1(7),
    DAN_2(8),
    DAN_3(9),
    DAN_4(10);

    private final int value;

    AikidoRank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
