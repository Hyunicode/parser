package code.inter;

import code.symbols.Type;

public class If extends Stmt {

    Expr expr;
    Stmt stmt;

    public If(Expr x, Stmt s) { // if(E) Stmt
        expr = x;
        stmt = s;
        if (expr.type != Type.Bool) expr.error("boolean required in if");
    }

    public void gen(int begin, int next) {
        expr.jumping(0, next);
        int stmtLabel = newlabel();  // 进入一个新的stmt，应该创建一个label
        stmt.gen(stmtLabel, next);
    }

    public void display() {
        emit("stmt : if begin");
        stmt.display();
        emit("stmt : if end");
    }
}
