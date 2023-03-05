package code.symbols;

import code.inter.Id;
import code.lexer.Token;

import java.util.Hashtable;

public class Env {  // 每一个block都用一个子集的Env, 记录decl的变量
    private Hashtable table;
    protected Env prev;  // 继承父节点的Env

    public Env(Env n) {
        table = new Hashtable();
        prev = n;
    }

    public void put(Token w, Id i) {
        table.put(w, i);
    }

    public Id get(Token w) {  // 自底向上逐层查询并获取该id
        for (Env e = this; e != null; e = e.prev) {
            Id found = (Id) (e.table.get(w));
            if (found != null) return found;
        }
        return null;
    }
}
