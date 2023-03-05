package code.parser;

import code.inter.*;
import code.lexer.*;
import code.symbols.Array;
import code.symbols.Env;
import code.symbols.Type;

import java.io.IOException;

public class Parser {

    private Lexer lex;    // lexical analyzer for this parser
    private Token look;   // lookahead token
    Env top = null;       // current or top symbol table
    int used = 0;         // storage used for declarations


    public Parser(Lexer l) throws IOException {
        lex = l;
        move();
    }

    void move() throws IOException { // next token
        look = lex.scan();
    } //next token

    void error(String s) {
        throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) throws IOException { // 匹配一个正确字符并读入新的token
        if (look.tag == t) move(); //
        else error("syntax error");
    }

    public void program() throws IOException {  // program -> block
        // build the syntax tree
        Stmt s = block();
        // display the syntax tree, only display the stmts, without expr
        // s.display();
        int CodeBegin = s.newlabel();
        int CodeEnd = s.newlabel();
        s.emitlabel(CodeBegin);
        s.gen(CodeBegin, CodeEnd);
        s.emitlabel(CodeEnd);
    }

    Stmt block() throws IOException {  // block -> { decls stmts }
        match('{');
        Env savedEnv = top;
        top = new Env(top); //  入栈, top就是栈顶
        decls();
        Stmt s = stmts();
        match('}');
        top = savedEnv; // currentEnv出栈, top重新变成savedEnv
        return s;
    }

    void decls() throws IOException { // decls ->decls decl | epslon
        while (look.tag == Tag.BASIC) {   // decl -> type ID ;
            Type p = type();
            Token tok = look;  // 此时look就是变量名
            match(Tag.ID);  // 变量名 match ID
            match(';');
            Id id = new Id((Word) tok, p, used); // 声明变量
            if (top.get(tok) != null) {
                error("duplicated declare varible");
            }
            top.put(tok, id); //  存入
            used = used + p.width;   // 计算变量存放的相对地址
        }
    }

    Type type() throws IOException {
        // Type是word的子类，word是Token的子类，这里强行把look转成Type
        Type p = (Type) look;            // expect look.tag == Tag.BASIC
        match(Tag.BASIC);
        // lookahead(1) 预测下一个token
        if (look.tag != '[') return p; // T -> basic
        else return dims(p);            // return array type
    }

    Type dims(Type p) throws IOException { // array -> array[num]
        match('[');
        Token tok = look;
        match(Tag.NUM);
        match(']');
        if (look.tag == '[') // 判断是不是多维数组
            p = dims(p);  // 把type p传到递归低端，然后递归回来的时候，就得到了 int[]/float[]/... 类型的数组变量
        return new Array(((Num) tok).value, p);
    }

    Stmt stmts() throws IOException { //stmts -> stmts stmt | epslon
        if (look.tag == '}') return Stmt.Null;
        else return new Seq(stmt(), stmts());
    }

    Stmt stmt() throws IOException {
        Expr x, x1, x2;
        Stmt s, s1, s2;
        Stmt savedStmt;         // save enclosing loop for breaks

        switch (look.tag) {

            case ';': // stmt-> ;
                move();
                return Stmt.Null;

            case Tag.IF: // stmt -> if (bool) stmt | if (bool) stmt else stmt
                match(Tag.IF);
                match('(');
                x = bool();
                match(')');
                s1 = stmt();
                if (look.tag != Tag.ELSE) return new If(x, s1);
                match(Tag.ELSE);
                s2 = stmt();
                return new Else(x, s1, s2);

            case Tag.WHILE: //stmt->while(bool) stmt
                While whilenode = new While();
                savedStmt = Stmt.Enclosing;
                Stmt.Enclosing = whilenode;
                match(Tag.WHILE);
                match('(');
                x = bool();
                match(')');
                s1 = stmt();
                whilenode.init(x, s1);
                Stmt.Enclosing = savedStmt;  // reset Stmt.Enclosing
                return whilenode;

            case Tag.FOR:
                For forNode = new For();
                savedStmt = Stmt.Enclosing;
                Stmt.Enclosing = forNode;
                match(Tag.FOR);
                match('(');
                s1 = stmt();
                x1 = optexpr();
                match(';');
                s2 = assign(false);
                match(')');
                s = stmt();
                forNode.init(s1, x1, s2, s);
                Stmt.Enclosing = savedStmt;
                return forNode;

            case Tag.DO:
                Do donode = new Do();
                savedStmt = Stmt.Enclosing;
                Stmt.Enclosing = donode;
                match(Tag.DO);
                s1 = stmt();
                match(Tag.WHILE);
                match('(');
                x = bool();
                match(')');
                match(';');
                donode.init(s1, x);
                Stmt.Enclosing = savedStmt;  // reset Stmt.Enclosing
                return donode;

            case Tag.BREAK:
                match(Tag.BREAK);
                match(';');
                return new Break();

            case '{':
                return block();

            default:
                return assign(true);
        }
    }

    /**
     * 匹配赋值语句
     */
    Stmt assign(boolean needSemiColon) throws IOException {
        Stmt stmt;
        Token t = look;
        match(Tag.ID);
        Id id = top.get(t);
        if (id == null) error(t.toString() + " undeclared");

        if (look.tag == '=') {       // S -> id = E ;
            move();
            stmt = new Set(id, bool());
        } else {                        // S -> L = E ;
            Access x = offset(id);
            match('=');
            stmt = new SetElem(x, bool());
        }
        if (needSemiColon) {
            match(';');
        }

        return stmt;
    }

    Expr optexpr() throws IOException {
        if (look.tag == ';' || look.tag == ')') return Expr.Null;
        return bool();
    }

    /**
     * OR优先级最低
     * 获取运算式 -> (equality 或则 equality OR equality）
     */
    Expr bool() throws IOException {
        Expr x = join();
        while (look.tag == Tag.OR) {
            Token tok = look;
            move();
            x = new Or(tok, x, join());
        }
        return x;
    }

    /**
     * AND优先级高与OR
     * 获取运算式 -> (equality 或则 equality And equality）
     */
    Expr join() throws IOException {
        Expr x = equality();
        while (look.tag == Tag.AND) {
            Token tok = look;
            move();
            x = new And(tok, x, equality());
        }
        return x;
    }

    /**
     * 获取运算式 -> (rel 或则 rel==rel 或 rel != rel）
     */
    Expr equality() throws IOException {
        Expr x = rel();
        while (look.tag == Tag.EQ || look.tag == Tag.NE) {
            Token tok = look;
            move();
            x = new Rel(tok, x, rel());
        }
        return x;
    }

    /**
     * 获取运算式 -> (expr 或则 expr<expr 或 expr <= expr 或者 expr > expr 或则 expr >= expr）
     */
    Expr rel() throws IOException {
        Expr x = expr();
        switch (look.tag) {
            case '<':
            case Tag.LE:
            case Tag.GE:
            case '>':
                Token tok = look;
                move();
                return new Rel(tok, x, expr());
            default:
                return x;
        }
    }

    /**
     * 后加减
     * 获取运算式 -> (term 或则 term+term 或则 term - term）
     */
    Expr expr() throws IOException {
        Expr x = term();
        while (look.tag == '+' || look.tag == '-') {
            Token tok = look;
            move();
            x = new Arith(tok, x, term());
        }
        return x;
    }

    /**
     * 先乘除
     * 获取运算式 -> (unary 或则 unary*unary 或则 unary / unary）
     */
    Expr term() throws IOException {
        Expr x = unary();
        while (look.tag == '*' || look.tag == '/') {
            Token tok = look;
            move();
            x = new Arith(tok, x, unary());
        }
        return x;
    }

    /**
     * 匹配单目运算符号
     */
    Expr unary() throws IOException {
        if (look.tag == '-') {
            move();
            return new Unary(Word.minus, unary());
        } else if (look.tag == '!') {
            Token tok = look;
            move();
            return new Not(tok, unary());
        } else return factor();
    }

    /**
     * 返回一个因子（表达式/数字/浮点数/布尔类型/变量）
     */
    Expr factor() throws IOException {
        Expr x = null;
        switch (look.tag) {
            case '(':
                move();
                x = bool();
                match(')');
                return x;
            case Tag.NUM:
                x = new Constant(look, Type.Int);
                move();
                return x;
            case Tag.REAL:
                x = new Constant(look, Type.Float);
                move();
                return x;
            case Tag.TRUE:
                x = Constant.True;
                move();
                return x;
            case Tag.FALSE:
                x = Constant.False;
                move();
                return x;

            case Tag.ID:
                // 取到变量类型
                String s = look.toString();
                // 从符号表中获取变量。
                Id id = top.get(look);
                if (id == null) error(look.toString() + " undeclared");
                // 往前看一个token
                move();
                // 判断当前的变量是否是数组
                if (look.tag != '[') return id;
                    // 如果是则进入offset函数。
                else return offset(id);

            default:
                error("syntax error");
                return x;
        }
    }

    Access offset(Id a) throws IOException {   // I -> [E] | [E] I
        Expr i;
        Expr w;
        Expr t1, t2;
        Expr loc;  // inherit id

        Type type = a.type;
        match('[');
        i = bool();
        match(']');     // first index, I -> [ E ]
        // int* -> int
        type = ((Array) type).of;
        w = new Constant(type.width);
        t1 = new Arith(new Token('*'), i, w);
        loc = t1;
        // 多维数组
        while (look.tag == '[') {      // multi-dimensional I -> [ E ] I
            match('[');
            i = bool();
            match(']');
            // ... -> int** -> int* -> int
            type = ((Array) type).of;
            w = new Constant(type.width);
            t1 = new Arith(new Token('*'), i, w);
            t2 = new Arith(new Token('+'), loc, t1);
            loc = t2;
        }

        return new Access(a, loc, type);
    }
}
