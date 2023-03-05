package code.inter;

import code.symbols.Array;
import code.symbols.Type;

public class SetElem extends Stmt { // a[i] = x

    public Id array;  // 数组
    public Expr index; // 下标 
    public Expr expr;  // 右侧计算表达式

    public SetElem(Access x, Expr y) {
        array = x.array;
        index = x.index;
        expr = y;
        // 在构造SetElem对象的时候进行类型检查
        if (check(x.type, expr.type) == null) error("type error");
    }

    public Type check(Type p1, Type p2) {
        // 数组类型不能直接作为赋值对象，也不能作为右值
        if (p1 instanceof Array || p2 instanceof Array) return null;
        else if (p1 == p2) return p2;
        else if (Type.numeric(p1) && Type.numeric(p2)) return p2;
        else return null;
    }

    public void gen(int begin, int next) {
        String s1 = index.reduce().toString();
        String s2 = expr.reduce().toString();
        emit(array.toString() + "[" + s1 + "]" + " = " + s2);
    }

    public void display() {
        emit(" assignment ");
    }
}
