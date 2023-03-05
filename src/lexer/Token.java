package code.lexer;

public class Token {
    // Token类只能表示： [0,255]之间的字符，比如: &、|、* 、^等等运算符
    // 它的子类有: Num/Real/Word

    public final int tag; // token的标识值

    public Token(int t) {
        tag = t;
    }

    public String toString() {
        assert (tag >= 0 && tag <= 255);
        return "" + (char) tag;
    } //
}

/*
 *  token is the operator of Number/Real/Char/Bool
 *  Word is variable or operator or Bool
 *  Num is const int/char Number
 *  Real is const float number
 * */
