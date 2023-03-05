package code.inter;

import code.symbols.Array;
import code.symbols.Type;
import code.lexer.Token;

public class Rel extends Logical {
    // Rel实现了: <  <=  ==  !=  >=  > 这6个运算符

    public Rel(Token tok, Expr x1, Expr x2) {
        super(tok, x1, x2);
    }

    public Type check(Type p1, Type p2) {
        // 两个变量类型必须一致，同时不能是数组类型
        if (p1 instanceof Array || p2 instanceof Array) return null;
        else if (p1 == p2) return Type.Bool;  // 强制转化为bool类型
        else return null;
    }

    public void jumping(int trueLabel, int falseLabel) {
        Expr lhs = expr1.reduce();
        Expr rhs = expr2.reduce();
        String testCondition = lhs.toString() + " " + op.toString() + " " + rhs.toString();
        this.emitjumps(testCondition, trueLabel, falseLabel);
    }
}
