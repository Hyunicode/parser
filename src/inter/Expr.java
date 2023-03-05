package code.inter;

import code.symbols.Type;
import code.lexer.Token;

public class Expr extends Node { // 计算表达式, 只保存运算符和返回值类型

    public Token op;  // 运算符
    public Type type; // 运算结果的类型
    public static Expr Null = new Expr(new Token(0), new Type("", 0, 0));

    // 构造函数：
    // - Token : 表达式的运算符，不能为空
    // - Type : 表达式返回的类型
    Expr(Token tok, Type p) {
        op = tok;
        type = p;
    }

    public Expr gen() { // gen的作用是返回表达式
        return this;
    }

    public Expr reduce() {  // reduce提供了一个调用gen的统一接口，并把表达式存放到一个临时变量t中
        return this;
    }

    /*
     * 如果expr为真，跳转到truelabel，否则跳转到falseLabel
     */
    public void jumping(int trueLabel, int falseLabel) {
        emitjumps(toString(), trueLabel, falseLabel);
    }

    public void emitjumps(String test, int trueLabel, int falseLabel) {
        if (trueLabel != 0 && falseLabel != 0) {
            emit("if " + test + " goto L" + trueLabel);
            emit("goto L" + falseLabel);
        } else if (trueLabel != 0) {
            emit("if " + test + " goto L" + trueLabel);
        } else if (falseLabel != 0) {
            emit("ifFalse " + test + " goto L" + falseLabel);
        } else {
            // do nothing, because skip this code.
        }
    }

    public String toString() {
        return op.toString();
    }

}
