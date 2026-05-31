package Finals.CritterCode.lang;

import Finals.CritterCode.ast.*;
import java.util.*;

public class Parser {

    private List<Token> tokens;
    private int pos = 0;

    public ProgramNode parse(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;

        ProgramNode program = new ProgramNode();

        while (!isAtEnd()) {
            ASTNode stmt = parseStatement();
            if (stmt != null) {
                program.statements.add(stmt);
            } else {
                advance();
            }
        }

        return program;
    }

    // -----------------------------------------------
    // STATEMENT DISPATCHER
    // -----------------------------------------------
    private ASTNode parseStatement() {

        if (check("critter")) return parseCritter();
        if (check("teach")) return parseTeach();
        if (check("tell")) return parseTell();
        if (check("if")) return parseIf();
        if (check("say")) return parseSay();
        if (check("ask")) return parseAsk();

        // assignment (local): energy = 10 + 5
        if (peek().type == Token.Type.IDENTIFIER && lookAhead("=")) {
            return parseLocalAssignment();
        }

        // assignment (object): Bibo.energy = 20
        if (peek().type == Token.Type.IDENTIFIER && lookAhead(".")) {
            return parseAssignment();
        }

        return null;
    }

    // -----------------------------------------------
    // CRITTER DEFINITION
    // -----------------------------------------------
    private CritterDefNode parseCritter() {
        consume("critter");
        String name = consumeIdentifier();
        consumeSymbol(":");

        CritterDefNode node = new CritterDefNode(name);

        // parse simple statements inside body
        while (!isAtEnd()
                && !check("teach")
                && !check("tell")
                && !check("critter")
                && !check("if")) {

            ASTNode stmt = parseStatement();
            if (stmt != null)
                node.body.add(stmt);
            else
                advance();
        }

        // parse method blocks
        while (!isAtEnd() && check("teach")) {
            node.body.add(parseTeach());
        }

        return node;
    }

    // -----------------------------------------------
    // TEACH (method definition)
    // -----------------------------------------------
    private MethodDefNode parseTeach() {
        consume("teach");
        String name = consumeIdentifier();
        consumeSymbol(":");

        MethodDefNode node = new MethodDefNode(name);

        while (!isAtEnd()
                && !check("teach")
                && !check("tell")
                && !check("critter")
                && !check("if")
                && !check("ask")) {

            ASTNode stmt = parseStatement();
            if (stmt != null)
                node.body.add(stmt);
            else
                advance();
        }

        return node;
    }

    // -----------------------------------------------
    // SAY
    // -----------------------------------------------
    private SayNode parseSay() {
        consume("say");
        // Case 1: string literal
        if (peek().type == Token.Type.STRING) {
            String txt = consumeString();

            // optional concatenation: say "hi " + name
            if (!isAtEnd() && peek().value.equals("+")) {
                consumeSymbol("+");
                String rhs = parseExpression();
                return new SayNode(txt, rhs); // concat form
            }

            return new SayNode(txt); // pure literal
        }
        // Case 2: bare identifier (say food, say name)
        if (peek().type == Token.Type.IDENTIFIER) {
            String var = consumeIdentifier();
            return new SayNode(null, var); // variable form
        }

        // Case 3: numeric or complex expression (say 5 + 1, say food + 2)
        if (peek().type == Token.Type.NUMBER || peek().type == Token.Type.IDENTIFIER) {
            String expr = parseExpression();
            return new SayNode("EXPR", expr); // expression form
        }

        throw new RuntimeException("Invalid syntax after 'say'");
    }

    // -----------------------------------------------
    // ASK
    // -----------------------------------------------
    private ASTNode parseAsk() {
        consume("ask");

        // ask user for variable
        if (check("user")) {
            consume("user");
            consume("for");
            String var = consumeIdentifier();
            return new InputNode(var);
        }

        // ask Bibo to shout
        String name = consumeIdentifier();
        consume("to");
        String method = consumeIdentifier();
        return new MethodCallNode(name, method);
    }

    // -----------------------------------------------
    // TELL
    // -----------------------------------------------
    private TellNode parseTell() {
        consume("tell");
        String critter = consumeIdentifier();
        consumeSymbol(":");

        TellNode node = new TellNode(critter);

        while (!isAtEnd()
                && !check("critter")
                && !check("teach")
                && !check("tell")
                && !check("if")) {

            if (peek().type == Token.Type.IDENTIFIER && lookAhead("="))
                node.body.add(parseLocalAssignment());
            else
                advance();
        }

        return node;
    }

    // -----------------------------------------------
    // IF / ELSE
    // -----------------------------------------------
    private IfNode parseIf() {
        consume("if");

        String left = parseExpression();
        String op = consumeSymbol();
        String right = parseExpression();

        consumeSymbol(":");

        IfNode node = new IfNode(left, op, right);

        ASTNode stmt = parseStatement();
        if (stmt != null)
            node.body.add(stmt);

        if (check("else")) {
            consume("else");
            consumeSymbol(":");
            ASTNode elseStmt = parseStatement();
            if (elseStmt != null)
                node.elseBody.add(elseStmt);
        }

        return node;
    }

    // -----------------------------------------------
    // ASSIGNMENTS
    // -----------------------------------------------
    private AssignmentNode parseLocalAssignment() {
        String var = consumeIdentifier();
        consumeSymbol("=");
        String expr = parseExpression();
        return new AssignmentNode("SELF", var, expr);
    }

    private AssignmentNode parseAssignment() {
        String obj = consumeIdentifier();
        consumeSymbol(".");
        String prop = consumeIdentifier();
        consumeSymbol("=");
        String expr = parseExpression();
        return new AssignmentNode(obj, prop, expr);
    }

    // -----------------------------------------------
    // EXPRESSIONS (supports +-*/%)
    // -----------------------------------------------
    private String parseExpression() {
        StringBuilder expr = new StringBuilder();
        expr.append(parseAtom());

        while (!isAtEnd()) {
            String v = peek().value;
            if (!(v.equals("+") || v.equals("-") || v.equals("*") || v.equals("/") || v.equals("%")))
                break;

            expr.append(" ").append(advance().value).append(" ");
            expr.append(parseAtom());
        }

        return expr.toString();
    }

    private String parseAtom() {
        Token t = peek();

        if (t.type == Token.Type.NUMBER) {
            advance();
            return t.value;
        }

        if (t.type == Token.Type.STRING) {
            return consumeString();
        }

        if (t.type == Token.Type.IDENTIFIER) {
            String id = consumeIdentifier();

            if (!isAtEnd() && peek().value.equals(".")) {
                advance();
                id = id + "." + consumeIdentifier();
            }

            return id;
        }

        throw new RuntimeException("Expected number, identifier, or string");
    }

    // -----------------------------------------------
    // TOKEN HELPERS
    // -----------------------------------------------
    private boolean check(String keyword) {
        return !isAtEnd()
                && peek().type == Token.Type.KEYWORD
                && peek().value.equals(keyword);
    }

    private Token advance() {
        return tokens.get(pos++);
    }

    private boolean isAtEnd() {
        return peek().type == Token.Type.EOF;
    }

    private Token peek() {
        return tokens.get(pos);
    }

    private boolean lookAhead(String value) {
        if (pos + 1 >= tokens.size()) return false;
        return tokens.get(pos + 1).value.equals(value);
    }

    private void consume(String expected) {
        if (!peek().value.equals(expected))
            throw new RuntimeException("Expected '" + expected + "' but got '" + peek().value + "'");
        advance();
    }

    private String consumeIdentifier() {
        if (peek().type != Token.Type.IDENTIFIER)
            throw new RuntimeException("Expected identifier but got " + peek().value);
        String v = peek().value;
        advance();
        return v;
    }

    private void consumeSymbol(String s) {
        if (!peek().value.equals(s))
            throw new RuntimeException("Expected symbol '" + s + "' but got '" + peek().value + "'");
        advance();
    }

    private String consumeSymbol() {
        String s = peek().value;
        advance();
        return s;
    }

    private String consumeString() {
        if (peek().type != Token.Type.STRING)
            throw new RuntimeException("Expected string literal but got " + peek().value);
        String s = peek().value;
        advance();
        return s;
    }
    
    private String consumeValueOrIdentifier() {
        Token t = peek();

        // Number literal
        if (t.type == Token.Type.NUMBER) {
            advance();
            return t.value;
        }

        // String literal -> keep quotes so interpreter can detect strings
        if (t.type == Token.Type.STRING) {
            advance();
            return "\"" + t.value + "\"";
        }

        // Identifier (possibly dotted like Bibo.energy)
        if (t.type == Token.Type.IDENTIFIER) {
            String id = consumeIdentifier(); // consumes the identifier token

            // dotted property
            if (!isAtEnd() && peek().value.equals(".")) {
                consumeSymbol(".");                // consume the dot
                id = id + "." + consumeIdentifier(); // consume the property name
            }

            return id;
        }

        throw new RuntimeException("Expected number, string, or identifier but got '" + t.value + "'");
    }
    
    private String consumeExpression() {
        StringBuilder sb = new StringBuilder();

        // First value
        sb.append(consumeValueOrIdentifier());

        // Loop through all arithmetic operators
        while (!isAtEnd()) {
            String v = peek().value;

            if (v.equals("+") || v.equals("-") ||
                v.equals("*") || v.equals("/") ||
                v.equals("%")) {

                sb.append(" ").append(v).append(" "); 
                advance(); // consume the operator

                sb.append(consumeValueOrIdentifier()); 
            } else {
                break;
            }
        }
        return sb.toString();
    }
}