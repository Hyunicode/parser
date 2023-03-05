package code.inter;

import code.symbols.Type;

public class While extends Stmt {

    Expr expr;
    Stmt stmt;

    public While() {
        expr = null;
        stmt = null;
    }

    public void init(Expr x, Stmt s) {
        expr = x;
        stmt = s;
        if (expr.type != Type.Bool) expr.error("boolean required in while");
    }

    public void gen(int begin, int next) {
    // label begin:
        this.breakNext = next;  // 保留编号next, 用于break的跳转
        expr.jumping(0, next);
        int whileBlockLabel = newlabel();
        emitlabel(whileBlockLabel);
        stmt.gen(whileBlockLabel, begin);  // 注意: begin是子stmt的next-label
        emit("goto L" + begin);
    // label next:
    }

    public void display() {
        emit("stmt : while begin");
        stmt.display();
        emit("stmt : while end");
    }
}
