package code.inter;

import code.symbols.Type;
import code.lexer.Token;
import code.lexer.Num;
import code.lexer.Real;
import code.lexer.Word;

public class Constant extends Expr {

    public Constant(Token tok, Type p) {
        super(tok, p);
    }

    public Constant(int i) {
        super(new Num(i), Type.Int);
    }

    public Constant(float f) {
        super(new Real(f), Type.Float);
    }

    public static final Constant
            True = new Constant(Word.True, Type.Bool),
            False = new Constant(Word.False, Type.Bool);

    public void jumping(int trueLabel, int falseLabel) {
        if (this == True && trueLabel != 0) {
            emit("goto L" + trueLabel);
        } else if (this == False && falseLabel != 0) {
            emit("goto L" + falseLabel);
        }
    }
}

