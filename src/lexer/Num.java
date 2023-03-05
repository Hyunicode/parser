package code.lexer;

public class Num extends Token {
    public final int value;  // 常量的大小

    public Num(int v) {
        super(Tag.NUM);
        value = v;
    }

    public String toString() {
        return "" + value;
    }
}
