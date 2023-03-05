package code.lexer;

import code.symbols.Type;

import java.io.IOException;
import java.util.Hashtable;

/*
 * reads characters from the input and groups them into "Token" object
 * */
public class Lexer {
    public static int line = 1; // 当前行号
    char peek = ' '; //向前看一个字符
    Hashtable words = new Hashtable();

    void reserve(Word w) {
        words.put(w.lexeme, w);
    } // Hashtable put(key, value)

    public Lexer() { // 注册关键字
        // 基本关键字
        reserve(new Word("if", Tag.IF));
        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("do", Tag.DO));
        reserve(new Word("break", Tag.BREAK));
        reserve(new Word("for", Tag.FOR));
        // 布尔类型
        reserve(Word.True);
        reserve(Word.False);
        // Basic type
        reserve(Type.Int);
        reserve(Type.Char);
        reserve(Type.Bool);
        reserve(Type.Float);
    }

    void readch() throws IOException { // 读入字符
        peek = (char) System.in.read();
    }

    boolean readch(char c) throws IOException { //读入同时判断字符
        readch();
        if (peek != c) return false;
        peek = ' ';
        return true;
    }

    public Token scan() throws IOException {
        for (; ; readch()) { //循环读入，直到读到token的一个字符
            if (peek == ' ' || peek == '\t' || peek == '\r') continue;
            else if (peek == '\n') line = line + 1; // 遇到换行符就行号+1
            else break; // 读到token则退出
        }
        switch (peek) { // 根据字符判断是不是操作符
            case '&':
                if (readch('&')) return Word.and;
                else return new Token('&'); // 逻辑与
            case '|':
                if (readch('|')) return Word.or;
                else return new Token('|');  // 逻辑或
            case '=':
                if (readch('=')) return Word.eq;
                else return new Token('='); // 逻辑相等
            case '!':
                if (readch('=')) return Word.ne;
                else return new Token('!'); // 逻辑不等
            case '<':
                if (readch('=')) return Word.le;
                else return new Token('<'); // 小于/小于等于
            case '>':
                if (readch('=')) return Word.ge;
                else return new Token('>'); // 大于/大于等于
        }

        if (Character.isDigit(peek)) { // 判断是不是数字
            int v = 0;
            do {
                v = 10 * v + Character.digit(peek, 10);
                readch(); //Character.digit(char ch, int radix)
            } while (Character.isDigit(peek));
            if (peek != '.') return new Num(v); //返回数字
            float x = v;
            float d = 10;
            for (; ; ) {
                readch();
                if (!Character.isDigit(peek)) break;
                x = x + Character.digit(peek, 10) / d;
                d = d * 10;
            }
            return new Real(x); // 返回浮点数
        }

        if (Character.isLetter(peek)) { // 判断是否是字母
            StringBuffer b = new StringBuffer(); // 构建变量的字符串构造器
            do {
                b.append(peek);
                readch();
            } while (Character.isLetterOrDigit(peek));
            String s = b.toString();
            Word w = (Word) words.get(s); // 从符号表中获取变量。
            if (w != null) return w; // 如果是已经存在符号表中的变量，则直接返回。
            w = new Word(s, Tag.ID); //  否则创建新的变量，存入符号表。
            words.put(s, w);
            return w;
        }

        Token tok = new Token(peek);
        peek = ' ';
        return tok;
    }
}
