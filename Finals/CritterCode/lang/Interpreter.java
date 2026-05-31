package Finals.CritterCode.lang;

import Finals.CritterCode.ast.*;
import Finals.CritterCode.runtime.Critter;

import java.util.*;

/*pinaka mauti pramis huhu, took me 8 YT videos to figure out the right algo*/
public class Interpreter {

    private ProgramNode programRef = null;
    private int stmtIndex = 0; // next top-level statement to run

    public boolean pauseExecution = false;  // set when waiting for input
    private String waitingVar = null;       // name of variable waiting for input

    // runtime state
    private Map<String, Critter> critters = new HashMap<>();
    private StringBuilder output = new StringBuilder();

    // global string inputs (from ask user for X)
    private Map<String, String> stringVars = new HashMap<>();
    
    private Map<String, Integer> globalVars = new HashMap<>();
    
    // a small FIFO queue so provideInput can be called before run resumes
    private Queue<String> pendingInputs = new ArrayDeque<>();

    // ------------------------------
    // run(program) - can be called multiple times (resume)
    // ------------------------------
    public String run(ProgramNode program) {
        // if a new program was supplied, reset execution state
        if (programRef == null || programRef != program) {
            programRef = program;
            stmtIndex = 0;
            pauseExecution = false;
            waitingVar = null;
            critters.clear();
            output.setLength(0);
            stringVars.clear();
            pendingInputs.clear();
        }

        // iterate top-level statements
        while (stmtIndex < programRef.statements.size()) {
            ASTNode stmt = programRef.statements.get(stmtIndex);

            // execute returns true if execution may continue, false if paused
            boolean ok = executeTopLevel(stmt);
            if (!ok) {
                // paused - do not advance stmtIndex (we will resume on next run)
                pauseExecution = true;
                break;
            }

            // move to next top-level statement
            stmtIndex++;
        }

        return output.toString();
    }

    // Called by your IDE when user submits input
    public void provideInput(String text) {
        if (text == null) text = "";
        pendingInputs.add(text);
        // do not automatically resume here — your IDE should call run(...) after provideInput()
    }

    // ------------------------------
    // Execute top-level nodes (statements)
    // returns true if execution finished this node and we can continue
    // returns false if interpreter paused waiting for input
    // ------------------------------
    private boolean executeTopLevel(ASTNode node) {
        if (node instanceof CritterDefNode) {
            execCritterDef((CritterDefNode) node);
            return true;
        } else if (node instanceof TellNode) {
            execTell((TellNode) node);
            return true;
        } else if (node instanceof IfNode) {
            execIf((IfNode) node);
            return true;
        } else if (node instanceof AssignmentNode) {
            execAssignment((AssignmentNode) node, null);
            return true;
        } else if (node instanceof SayNode) {
            execSay((SayNode) node, null);
            return true;
        } else if (node instanceof MethodCallNode) {
            execMethodCall((MethodCallNode) node);
            return true;
        } else if (node instanceof InputNode) {
            // top-level input (rare) — follow same pattern as inside bodies
            return execInput((InputNode) node, null);
        }

        // unknown node — skip
        return true;
    }

    // ------------------------------
    // Critter definition
    // ------------------------------
    private void execCritterDef(CritterDefNode node) {
        Critter c = new Critter(node.name);
        critters.put(node.name, c);

        Critter prev = null;
        // Execute inline assignments inside critter body (methoddefs will be stored)
        for (ASTNode st : node.body) {
            if (st instanceof MethodDefNode) {
                MethodDefNode md = (MethodDefNode) st;
                c.methods.put(md.name, md.body);
            } else if (st instanceof AssignmentNode) {
                execAssignment((AssignmentNode) st, c);
            } else if (st instanceof SayNode) {
                // If a say appears inside critter top-level body, allow it (rare)
                execSay((SayNode) st, c);
            } else if (st instanceof TellNode) {
                // nested tell inside critter (unlikely) - execute in critter context
                execTell((TellNode) st);
            }
            // other nodes ignored here
        }
    }

    // ------------------------------
    // Tell block: tell Bibo: <assignments...>
    // ------------------------------
    private void execTell(TellNode node) {
        Critter c = critters.get(node.critterName);
        if (c == null) return;

        for (ASTNode st : node.body) {
            if (st instanceof AssignmentNode) {
                execAssignment((AssignmentNode) st, c);
            } else if (st instanceof SayNode) {
                execSay((SayNode) st, c);
            } else if (st instanceof MethodCallNode) {
                // allow ask Bibo to shout inside tell? (we'll call as usual)
                execMethodCall((MethodCallNode) st);
            }
        }
    }

    // ------------------------------
    // Assignment
    // ------------------------------
    private void execAssignment(AssignmentNode node, Critter objectCritter) {
    
    int val = evaluateExpression(node.value, objectCritter);

    if (node.objectName.equals("SELF")) {
        if (objectCritter != null) {
            // Assignment inside a critter
            objectCritter.properties.put(node.propertyName, val);
        } else {
            // ✅ Top-level assignment → store in globalVars
            globalVars.put(node.propertyName, val);
        }
        return;
    }

    // Step 3: assignment to a named critter (e.g. Bibo.energy = val)
    Critter c = critters.get(node.objectName);
    if (c != null) {
        c.properties.put(node.propertyName, val);
    }

    }

    // ------------------------------
    // Method call: ask Bibo to shout
    // ------------------------------
    private void execMethodCall(MethodCallNode node) {
        Critter c = critters.get(node.objectName);
        if (c == null) return;

        List<ASTNode> body = c.methods.get(node.methodName);
        if (body == null) return;

        for (ASTNode st : body) {
            // methods execute in the critter context: pass critter as current target
            if (st instanceof AssignmentNode) execAssignment((AssignmentNode) st, c);
            else if (st instanceof SayNode) execSay((SayNode) st, c);
            else if (st instanceof InputNode) {
                boolean cont = execInput((InputNode) st, c);
                if (!cont) return; // paused — bubble up false by stopping body execution
            } else if (st instanceof MethodCallNode) execMethodCall((MethodCallNode) st);
            else if (st instanceof IfNode) {
                // run if inside method: allow nested statements (executeIf will run bodies)
                execIf((IfNode) st, c);
            }
        }
    }

    // ------------------------------
    // Say node
    // ------------------------------
    private void execSay(SayNode node, Critter objectCritter) {
        if (node.text != null && node.variable == null && !node.text.equals("EXPR")) {
        output.append(node.text).append("\n");
        return;
    }

    if ("EXPR".equals(node.text)) {
        String expr = node.variable;
        String valStr = evaluateToString(expr, objectCritter);
        output.append(valStr).append("\n");
        return;
    }

    if (node.text != null && node.variable != null) {
        String left = node.text;
        String right = evaluateToString(node.variable, objectCritter);
        output.append(left).append(right).append("\n");
        return;
    }

    // say name or say food ma print lang siya sang value, indi na variable huhu
    if (node.variable != null && node.text == null) {
        String valStr = evaluateToString(node.variable, objectCritter);
        output.append(valStr).append("\n");
        return;
    }

    }

    // ------------------------------
    // Input node: ask user for <var>
    // ------------------------------
    private boolean execInput(InputNode node, Critter objectCritter) {
        // If there is pending input queued from IDE, consume it
        if (!pendingInputs.isEmpty()) {
            String provided = pendingInputs.poll();
            if (provided == null) provided = "";
            stringVars.put(node.variable, provided);
            // continue execution
            return true;
        }

        // else: pause and ask
        pauseExecution = true;
        waitingVar = node.variable;
        return false; // indicate paused
    }

    // Overload execIf for method context
    private void execIf(IfNode node, Critter objectCritter) {
        // similar to execIf(node) but pass objectCritter into expression evaluation
        int left = evaluateExpression(node.left, objectCritter);
        int right = evaluateExpression(node.right, objectCritter);

        boolean cond = evaluateCondition(node.op, left, right);

        if (cond) {
            for (ASTNode st : node.body) {
                if (pauseExecution) break;
                if (st instanceof AssignmentNode) execAssignment((AssignmentNode) st, objectCritter);
                else if (st instanceof SayNode) execSay((SayNode) st, objectCritter);
                else if (st instanceof MethodCallNode) execMethodCall((MethodCallNode) st);
                else if (st instanceof InputNode) {
                    boolean cont = execInput((InputNode) st, objectCritter);
                    if (!cont) return;
                } else if (st instanceof IfNode) execIf((IfNode) st, objectCritter);
            }
        } else {
            for (ASTNode st : node.elseBody) {
                if (pauseExecution) break;
                if (st instanceof AssignmentNode) execAssignment((AssignmentNode) st, objectCritter);
                else if (st instanceof SayNode) execSay((SayNode) st, objectCritter);
                else if (st instanceof MethodCallNode) execMethodCall((MethodCallNode) st);
                else if (st instanceof InputNode) {
                    boolean cont = execInput((InputNode) st, objectCritter);
                    if (!cont) return;
                } else if (st instanceof IfNode) execIf((IfNode) st, objectCritter);
            }
        }
    }

    // Top-level if handler (no objectCritter)
    private void execIf(IfNode node) {
        execIf(node, null);
    }

    // ------------------------------
    // evaluate condition op
    // ------------------------------
    private boolean evaluateCondition(String op, int left, int right) {
        switch (op) {
            case ">": return left > right;
            case "<": return left < right;
            case "==": return left == right;
            default: return false;
        }
    }

    // ------------------------------
    // Evaluate expression to integer
    // - uses token list already spaced by parser (parser ensures spaces around ops)
    // - supports dotted variables (Bibo.energy), numbers, and stored inputs treated as 0 if not numeric
    // - respects precedence: first */% left-to-right, then +-
    // objectCritter provides a local critter context to read SELF properties
    // ------------------------------
    private int evaluateExpression(String expr, Critter objectCritter) {
        if (expr == null) return 0;
        expr = expr.trim();
        if (expr.length() == 0) return 0;

        // quick single-number
        if (expr.matches("-?\\d+")) return Integer.parseInt(expr);

        // split by spaces (parser produced "a + b" format)
        String[] toks = expr.split(" ");
        List<String> parts = new ArrayList<>(Arrays.asList(toks));

        // Replace values: turn identifiers/dotted into numeric strings
        for (int i = 0; i < parts.size(); i++) {
            String tk = parts.get(i);
            if (isOperator(tk)) continue;
            parts.set(i, resolveNumericString(tk, objectCritter));
        }

        // First pass: * / %
        for (int i = 0; i < parts.size(); i++) {
            String tk = parts.get(i);
            if (tk.equals("*") || tk.equals("/") || tk.equals("%")) {
                int a = Integer.parseInt(parts.get(i - 1));
                int b = Integer.parseInt(parts.get(i + 1));
                int r = 0;
                if (tk.equals("*")) r = a * b;
                if (tk.equals("/")) r = a / b;
                if (tk.equals("%")) r = a % b;
                // replace three entries with result
                parts.set(i - 1, Integer.toString(r));
                parts.remove(i); // operator
                parts.remove(i); // right operand (now at same index)
                i = Math.max(-1, i - 2);
            }
        }

        // Second pass: + -
        int result = Integer.parseInt(parts.get(0));
        for (int i = 1; i < parts.size(); i += 2) {
            String op = parts.get(i);
            int val = Integer.parseInt(parts.get(i + 1));
            if (op.equals("+")) result += val;
            else if (op.equals("-")) result -= val;
        }

        return result;
    }

    private int evaluateExpression(String expr) {
        return evaluateExpression(expr, null);
    }

    // ------------------------------
    // evaluate expression to string (for say concatenation or quoted string)
    // ------------------------------
    private String evaluateToString(String expr, Critter objectCritter) {
        if (expr == null) return "";

        expr = expr.trim();
        if (expr.startsWith("\"") && expr.endsWith("\"") && expr.length() >= 2) {
            return expr.substring(1, expr.length() - 1);
        }

        // if it's a stored input variable
        if (stringVars.containsKey(expr)) {
            return stringVars.get(expr);
        }

        // else try numeric evaluate
        try {
            return Integer.toString(evaluateExpression(expr, objectCritter));
        } catch (Exception e) {
            // fallback: if dotted property and exists, return as number string
            if (expr.contains(".")) {
                String[] p = expr.split("\\.");
                Critter c = critters.get(p[0]);
                if (c != null && c.properties.containsKey(p[1])) {
                    return Integer.toString(c.properties.get(p[1]));
                }
            }
            return "<unidentified>";
        }
    }

    // ------------------------------
    // resolve token to a numeric string (used by evaluator)
    // ------------------------------
    private String resolveNumericString(String token, Critter objectCritter) {
        token = token.trim();
        if (token.matches("-?\\d+")) return token;

        // dotted: Bibo.energy
        if (token.contains(".")) {
            String[] p = token.split("\\.");
            Critter c = critters.get(p[0]);
            if (c != null && c.properties.containsKey(p[1])) {
                return Integer.toString(c.properties.get(p[1]));
            }
            return "0";
        }

        // SELF property if executing in critter context
        if (objectCritter != null && objectCritter.properties.containsKey(token)) {
            return Integer.toString(objectCritter.properties.get(token));
        }

        // ✅ Step 3: Global variable lookup
        if (globalVars.containsKey(token)) {
            return Integer.toString(globalVars.get(token));
        }

        // stringVars (user input)
        if (stringVars.containsKey(token)) {
            String s = stringVars.get(token);
            if (s.matches("-?\\d+")) return s;
            return "0";
        }

        return "0";
    }

    private boolean isOperator(String s) {
        return "+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s) || "%".equals(s);
    }
}