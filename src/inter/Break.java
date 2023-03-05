package code.inter;

public class Break extends Stmt {
    Stmt stmt;

    public Break() {
        // 在parser文件中, do-while/while/for三个语句，都会提前把自身节点放到Enclosing里面
        if (Enclosing == Null) error("unenclosed break");
        stmt = Enclosing;
    }

    public void gen(int begin, int next) {
        emit("\tgoto L" + stmt.breakNext);
    }

    public void display() {
        emit(" break ");
    }
}
