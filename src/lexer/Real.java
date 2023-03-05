package code.lexer;

public class Real extends Token {
    public final float value;  // 浮点数常量的大小

    public Real(float v) {
        super(Tag.REAL);
        value = v;
    }

    public String toString() {
        return "" + value;
    }

}
