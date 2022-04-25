package encryptdecrypt;

public enum Mode {
    enc(1),
    dec(-1);

    private final int value;

    Mode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
