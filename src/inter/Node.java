package code.inter;

import code.lexer.Lexer;

public class Node { //
    int lexline = 0; // code 在第几行开始

    Node() {
        lexline = Lexer.line;
    }

    void error(String s) {
        throw new Error("near line " + lexline + ": " + s);
    }

    private static int labels = 0; // 三地址码的计数器

    // 创建一个新的label
    public int newlabel() {
        return ++labels;
    }

    //
    public void emitlabel(int i) {
        System.out.println("L" + i + ": ");
    }

    //
    public void emit(String s) {
        System.out.println("\t" + s);  // 这里多一个缩进会好看一点
    }
}
