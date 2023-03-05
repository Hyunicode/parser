package code.inter;

public class Stmt extends Node {

    public Stmt() {
    } // 允许空构造

    public static Stmt Null = new Stmt(); // 一个初始常量Null

    /*
     * generate the Three address code
     * begin是stmt开始之前的label，next是stmt之后的label
     * 每一个stmt都会被begin和next包裹在中间，比如：
     * begin:
     *     stmt.code
     * next:
     */
    public void gen(int begin, int next) {
        // do nothing in the virtual class
    }

    // stmt 的下一个三地址码的开始, 用于break的跳转
    int breakNext = 0;

    public static Stmt Enclosing = Stmt.Null; // used for break, continue stmts

    public void display() {
        // statement has nothing to display ?
    }
}
