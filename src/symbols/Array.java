package code.symbols;

import code.lexer.Tag;

public class Array extends Type {
    // 声明方式 int[sz] a;
    // 其中of可能是basic基本类型，也可能是数组array
    // of 是数组的类型
    // sz 是数组的大小
    public Type of;
    public int size;

    public Array(int sz, Type p) {
        super("[]", Tag.INDEX, sz * p.width);
        size = sz;
        of = p;
    }

    public String toString() {
        return "[" + size + "]" + of.toString();
    }
}
