package code.lexer;

public class Word extends Token {
    public String lexeme = ""; // 字符串word专有的词素

    public Word(String s, int tag) {
        super(tag);
        lexeme = s;
    } // 构造Word, tag是这个字符串Word的类型标识

    public String toString() {
        return lexeme;
    }

    public static final Word //����decaf�������������ʵ��������ʹ�õ���Щ����
            and = new Word("&&", Tag.AND), or = new Word("||", Tag.OR),
            eq = new Word("==", Tag.EQ), ne = new Word("!=", Tag.NE),
            le = new Word("<=", Tag.LE), ge = new Word(">=", Tag.GE),
            minus = new Word("minus", Tag.MINUS),
            True = new Word("true", Tag.TRUE),
            False = new Word("false", Tag.FALSE),
            temp = new Word("t", Tag.TEMP);
}