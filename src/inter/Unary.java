package code.inter;

import code.symbols.Type;
import code.lexer.Token;

public class Unary extends Op { // -x, 注意:!x是not

    public Expr expr;

    public Unary(Token tok, Expr x) {
        super(tok, null);
        expr = x;
        type = Type.max(Type.Int, expr.type);  // 转化为更高级的类型
        if (type == null) error("type error");
    }

    public Expr gen() {
        return new Unary(op, expr.reduce());
    }

    public String toString() {
        return op.toString() + " " + expr.toString();
    }
}

