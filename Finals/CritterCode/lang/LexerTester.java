package Finals.CritterCode.lang;

/**
 * @author Kia
 */

import java.util.*;

public class LexerTester {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        
        String code =
        "critter Bibble:\n" +
        "    energy = 20\n" +
        "\n" +
        "    teach Pamuyayaw:\n" +
        "        say \"AYOKO NA~!!\"\n" +
        "\n" +
        "if Bibo.energy > 0:\n" +
        "    ask Bibo to shout\n";

        List<Token> tokens = lexer.tokenize(code);

        for (Token t : tokens) {
            System.out.println(t);
        }
    }
}