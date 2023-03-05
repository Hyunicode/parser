package code.inter;

import code.lexer.Token;

public class And extends Logical {
    public And(Token tok, Expr x1, Expr x2) {
        super(tok, x1, x2);
    }

    public void jumping(int trueLabel, int falseLabel) {
        int tempFalseLabel = (falseLabel == 0) ? newlabel() : falseLabel;
        expr1.jumping(0, tempFalseLabel);
        expr2.jumping(trueLabel, tempFalseLabel);
        if (falseLabel == 0) emitlabel(tempFalseLabel);
    }

}
