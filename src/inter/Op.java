package code.inter;

import code.symbols.Type;
import code.lexer.Token;

public class Op extends Expr {
    // 该类只提供了reduce的一个功能，在每种情况下，reduce调用gen函数生成一个项，同时把该项存放在一个临时变量t中

    public Op(Token tok, Type p) {
        super(tok, p);
    }

    // 把整个表达式，规约到一个临时变量temp，同时返回这个临时变量
    public Expr reduce() {
        Expr x = gen();
        Temp t = new Temp(type);
        emit(t.toString() + " = " + x.toString());
        return t;
    }
}
