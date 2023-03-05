package code.lexer;

public class Tag {
    public final static int //tag��ʶ����
            AND = 256, BASIC = 257, BREAK = 258, DO = 259, ELSE = 260,
            EQ = 261, FALSE = 262, GE = 263, ID = 264, IF = 265,
            INDEX = 266, LE = 267, MINUS = 268, NE = 269, NUM = 270,
            OR = 271, REAL = 272, TEMP = 273, TRUE = 274, WHILE = 275,
            FOR = 276;
}

/*
 * tag<------->token
 * 1 type相关的关键字
 * BASIC ----->  (int float char bool)
 * INDEX -----> []
 *
 * 2 stmt 相关的关键字
 * DO -----> do
 * WHILE -----> while
 * BREAK -----> break
 * IF -----> if
 * ELSE -----> else
 *
 * 3 bool 相关的关键字
 * AND ----->&&
 * OR -----> II
 *
 * 4 比较相关的关键字
 * EQ -----> ==
 * GE -----> >=
 * LE -----> <=
 * NE -----> !=
 *
 * 5 Bool类型
 * TRUE ----->true
 * FALSE -----> false
 *
 * 6 整数和浮点数
 * NUM -----> int、char
 * REAL -----> float
 *
 * 7 程序的变量
 * ID -----> 变量名
 *
 * 8
 * Minus -----> -  取反运算符
 * TEMP -----> ? * 三地址码的临时变量
 *
 * */
