package code.inter;

import code.symbols.Type;
import code.lexer.Word;

public class Temp extends Expr {
    // 三地址码的临时变量
    private static int count = 0;  // 内部计数器
    int number = 0;  // 该临时变量的编号

    public Temp(Type p) {
        super(Word.temp, p);
        number = ++count;
    }

    public String toString() {
        return "t" + number;
    }
}
