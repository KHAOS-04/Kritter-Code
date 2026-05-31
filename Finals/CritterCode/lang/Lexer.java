package Finals.CritterCode.lang;

import java.util.*;

public class Lexer {

    private String code;
    private int pos;
    private int length;

    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
        "critter", "teach", "say", "ask", "user", "for",
        "to", "if", "else", "tell"
    ));

    /** TOKENIZE **/
    public List<Token> tokenize(String code) {
        this.code = code;
        this.pos = 0;
        this.length = code.length();

        List<Token> tokens = new ArrayList<>();

        while (!isAtEnd()) {
            char c = peek();

            // Skip whitespace
            if (Character.isWhitespace(c)) {
                advance();
                continue;
            }

            // Comment (# ...)
            if (c == '#') {
                skipComment();
                continue;
            }

            // String literal
            if (c == '"') {
                tokens.add(readString());
                continue;
            }

            // Number
            if (Character.isDigit(c)) {
                tokens.add(readNumber());
                continue;
            }

            // Identifier or keyword
            if (Character.isLetter(c)) {
                tokens.add(readIdentifier());
                continue;
            }

            // Operator symbols
            if ("+-*/%=.:".indexOf(c) >= 0) {
                tokens.add(new Token(Token.Type.SYMBOL, String.valueOf(c)));
                advance();
                continue;
            }

            // Other symbol
            tokens.add(readSymbol());
        }

        tokens.add(new Token(Token.Type.EOF, ""));
        return tokens;
    }

    // -------------------------------------
    // HELPERS
    // -------------------------------------

    private boolean isAtEnd() {
        return pos >= length;
    }

    private char peek() {
        return code.charAt(pos);
    }

    private char advance() {
        return code.charAt(pos++);
    }

    private void skipComment() {
        while (!isAtEnd() && peek() != '\n') advance();
    }

    private Token readString() {
        advance(); // skip opening quote

        StringBuilder sb = new StringBuilder();

        while (!isAtEnd() && peek() != '"') {
            sb.append(advance());
        }

        if (!isAtEnd()) advance(); // closing quote

        return new Token(Token.Type.STRING, sb.toString());
    }

    private Token readNumber() {
        StringBuilder sb = new StringBuilder();
        while (!isAtEnd() && Character.isDigit(peek())) {
            sb.append(advance());
        }
        return new Token(Token.Type.NUMBER, sb.toString());
    }

    private Token readIdentifier() {
        StringBuilder sb = new StringBuilder();
        while (!isAtEnd() && 
               (Character.isLetterOrDigit(peek()) || peek() == '_')) {
            sb.append(advance());
        }

        String text = sb.toString();

        if (KEYWORDS.contains(text)) {
            return new Token(Token.Type.KEYWORD, text);
        }

        return new Token(Token.Type.IDENTIFIER, text);
    }

    private Token readSymbol() {
        return new Token(Token.Type.SYMBOL, String.valueOf(advance()));
    }
}