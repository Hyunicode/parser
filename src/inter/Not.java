package code.inter;

import code.lexer.Token;

public class Not extends Logical { // !x

    public Not(Token tok, Expr x2) {
        super(tok, x2, x2);
    }

    public void jumping(int trueLabel, int falseLabel) {
        expr2.jumping(falseLabel, trueLabel);
        // 因为not的原因，需要调转一下true和false要跳转的label
    }

    public String toString() {
        return op.toString() + " " + expr2.toString();
    }
}
