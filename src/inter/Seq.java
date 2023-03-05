package code.inter;

public class Seq extends Stmt {
    // 多个stmt序列

    Stmt stmt1;
    Stmt stmt2;

    public Seq(Stmt s1, Stmt s2) {
        stmt1 = s1;
        stmt2 = s2;
    }

    public void gen(int begin, int next) {
        if (stmt1 != Stmt.Null && stmt2 != Stmt.Null) {
            int middleLable = newlabel();
            stmt1.gen(begin, middleLable);
            emitlabel(middleLable);
            stmt2.gen(middleLable, next);
        } else {
            if (stmt1 != Stmt.Null) stmt1.gen(begin, next);
            if (stmt2 != Stmt.Null) stmt2.gen(begin, next);
        }
    }

    public void display() {
        if (stmt1 != Null) stmt1.display();
        if (stmt2 != Null) stmt2.display();
    }
}

