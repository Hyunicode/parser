package code.inter;

import code.symbols.Type;

public class Do extends Stmt {

    Expr expr;
    Stmt stmt;

    public Do() {
        expr = null;
        stmt = null;
    }

    public void init(Stmt s, Expr x) {
        expr = x;
        stmt = s;
        if (expr.type != Type.Bool) expr.error("boolean required in do");
    }

    public void gen(int begin, int next) {
    // begin:
        this.breakNext = next;
        int doExprLabel = newlabel();  // 该label用于expr
        stmt.gen(begin, doExprLabel);
        emitlabel(doExprLabel);
        expr.jumping(begin, 0);
    // next:
    }

    public void display() {
        emit("stmt : do begin");
        stmt.display();
        //expr.jumping(b,0);
        emit("stmt : do end");
    }
}