package code.inter;

import code.lexer.Token;

public class Or extends Logical {

    public Or(Token tok, Expr x1, Expr x2) {
        super(tok, x1, x2);
    }

    public void jumping(int trueLabel, int falseLabel) {
        int tempTrueLabel = (trueLabel == 0) ? newlabel() : trueLabel;
        expr1.jumping(tempTrueLabel, 0);
        expr2.jumping(tempTrueLabel, falseLabel);
        if (trueLabel == 0) emitlabel(tempTrueLabel);
    }
}
