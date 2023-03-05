package code.inter;

import code.symbols.Type;

public class Else extends Stmt {
    // Else包括了: if (expr) stmt else stmt
    // If 只有: if (expr) stmt

    Expr expr;
    Stmt stmt1, stmt2;

    public Else(Expr x, Stmt s1, Stmt s2) {
        expr = x;
        stmt1 = s1;
        stmt2 = s2;
        if (expr.type != Type.Bool) expr.error("boolean required in if");
    }

    public void gen(int begin, int next) {
        int ifBlockLabel = newlabel();
        int elseBlockLabel = newlabel();
        expr.jumping(0, elseBlockLabel); // true就按顺序执行即可
        emitlabel(ifBlockLabel);
        stmt1.gen(ifBlockLabel, next);
        emit("goto L" + next);  // 执行完一个if语句块，应该跳过下一个else语句块
        emitlabel(elseBlockLabel);
        stmt2.gen(elseBlockLabel, next);
    }

    public void display() {
        emit("stmt : else begin");
        emit("if true");
        stmt1.display();
        emit("else");
        stmt2.display();
        emit("stmt : else end");
    }
}
