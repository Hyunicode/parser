package code.inter;

import code.symbols.Type;

public class Set extends Stmt {
    //  assignment stmt like x=expr

    public Id id;  // 左侧变量x
    public Expr expr;  // 右侧计算表达式expr

    public Set(Id i, Expr x) {
        id = i;
        expr = x;
        if (check(id.type, expr.type) == null) error("type error");
    }

    public Type check(Type p1, Type p2) {  // 赋值操作需要类型检查
        // 只有当两个都是bool 、 或者两个都是数量类型的时候，才赋值
        if (Type.numeric(p1) && Type.numeric(p2)) return p2;
        else if (p1 == Type.Bool && p2 == Type.Bool) return p2;
        else return null;
    }

    public void gen(int begin, int next) {
        emit(id.toString() + " = " + expr.gen().toString());
    }

    public void display() {
        emit(" assignment ");
    }
}