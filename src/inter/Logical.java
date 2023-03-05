package code.inter;

import code.symbols.Type;
import code.lexer.Token;

public class Logical extends Expr {
    // 为Or、And、Not类提供了一些常见的功能

    public Expr expr1, expr2;  // 逻辑表达式的左右运算式

    Logical(Token tok, Expr x1, Expr x2) {
        super(tok, null);
        expr1 = x1;
        expr2 = x2;
        type = check(expr1.type, expr2.type);
        if (type == null) error("type error");
    }

    public Type check(Type p1, Type p2) {  // 只允许布尔类型
        if (p1 == Type.Bool && p2 == Type.Bool) return Type.Bool;
        else return null;
    }

    // 通过label的跳转，实现 temp = this.value 的过程
    public Expr gen() {
        int setFalseLabel = newlabel();  // 在中间放一个label
        int endLabel = newlabel();    // code最后的label

        Temp temp = new Temp(type);
        this.jumping(0, setFalseLabel);
        emit(temp.toString() + " = true");
        emit("goto L" + endLabel);
        emitlabel(setFalseLabel);
        emit(temp.toString() + " = false");
        emitlabel(endLabel);

        return temp;
    }
}
