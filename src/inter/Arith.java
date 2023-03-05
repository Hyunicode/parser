package code.inter;

import code.symbols.Type;
import code.lexer.Token;

public class Arith extends Op {
    // Arith类实现了双目运算符，包括+和*
    public Expr expr1, expr2;

    /* tok是双目运算符 */
    public Arith(Token tok, Expr x1, Expr x2) {
        super(tok, null); // this.type = null
        expr1 = x1;
        expr2 = x2;
        type = Type.max(expr1.type, expr2.type); // this.type=max(type1,type2)合并类型
        if (type == null) error("type error");
    }

    public Expr gen() {
        return new Arith(op, expr1.reduce(), expr2.reduce());
    }

    public String toString() {
        return expr1.toString() + " " + op.toString() + " " + expr2.toString();
    }
}
