package Finals.CritterCode.lang;

/**
 * * @author Kia
 */

import Finals.CritterCode.ast.*;
import java.util.*;

public class TestEverything {
    public static void main(String[] args) {

        String code =
            "critter Bibo:\n" +
            "    energy = 20\n" +
            "\n" +
            "    teach shout:\n" +
            "        say \"NYA~!!\"\n" +
            "\n" +
            "tell Bibo:\n" +
            "    energy = 10 - 2\n" +
            "\n" +
            "if Bibo.energy > 5:\n" +
            "    ask Bibo to shout";

        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.tokenize(code);

        Parser parser = new Parser();
        ProgramNode program = parser.parse(tokens);

        Interpreter interpreter = new Interpreter();
        String output = interpreter.run(program);

        System.out.println(output);
    }
}