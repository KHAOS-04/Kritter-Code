package Finals.CritterCode.ast;

public class SayNode extends ASTNode {
    public String text;      // literal or EXPR::something
    public String variable;  // food, answer, Bibo.energy, etc.

    public SayNode(String text) {
        this.text = text;
        this.variable = null;
    }

    public SayNode(String text, String variable) {
        this.text = text;
        this.variable = variable;
    }
}