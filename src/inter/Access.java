package code.inter;

import code.symbols.Type;
import code.lexer.Tag;
import code.lexer.Word;

public class Access extends Op {
    // access = array[index_1][index_2]...[index_n], 直接指向数组里面的元素
    public Id array;  //
    public Expr index;  //

    public Access(Id a, Expr i, Type p) {
        super(new Word("[]", Tag.INDEX), p);
        array = a;
        index = i;
    }

    public Expr gen() {
        return new Access(array, index.reduce(), type);
    }

    public void jumping(int trueLabel, int falseLabel) {
        emitjumps(this.reduce().toString(), trueLabel, falseLabel);
    }

    public String toString() {
        return array.toString() + "[" + index.toString() + "]";
    }
}
