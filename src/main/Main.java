package code.main;

import code.lexer.Lexer;
import code.parser.Parser;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input-right.txt"));
		Lexer lex = new Lexer();
		Parser parser = new Parser(lex);
		parser.program();
		System.out.print("\n");
	}

}
