package code.inter;

import code.symbols.Type;

public class For extends Stmt {
    Stmt declStmt;
    Expr boolExpr;
    Stmt modifyStmt;
    Stmt forBlockStmt;

    public For() {
        boolExpr = null;
        declStmt = null;
        modifyStmt = null;
    }

    public void init(Stmt s1, Expr x2, Stmt s3, Stmt s_block) {
        declStmt = s1;
        boolExpr = x2;
        modifyStmt = s3;
        forBlockStmt = s_block;
        if (boolExpr != Expr.Null && boolExpr.type != Type.Bool)
            boolExpr.error("boolean required in for-loop");
    }

    public void gen(int begin, int next) {
        this.breakNext = next;
        int forStartLabel = newlabel();
        int forEndLabel = newlabel();
        
    // begin: 
        declStmt.gen(0, 0);  // 0标号表示不存在跳转，直接按顺序执行指令
    
    // forStartLabel:
        emitlabel(forStartLabel);
        boolExpr.jumping(0, next);
        
        // 这里就是for循环的stmt-block
        forBlockStmt.gen(begin, forEndLabel);
        emitlabel(forEndLabel);
        
    // forEndLabel: 
        modifyStmt.gen(0, 0);
        emit("goto L" + forStartLabel);
        
    // next: 
    }

    public void display() {
        emit("stmt : for begin");
        declStmt.display();
        modifyStmt.display();
        forBlockStmt.display();
        emit("stmt : for end");
    }
}
