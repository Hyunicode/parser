package code.inter;

import code.symbols.Type;
import code.lexer.Word;

public class Id extends Expr {
    // id就是一个标识符，一个标识符代表一个地址

    public int offset; // id 在symbol表里面的相对地址，用于查找变量

    public Id(Word id, Type p, int b) {
        super(id, p);
        offset = b;
    }
}
