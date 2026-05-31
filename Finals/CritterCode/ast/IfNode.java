package Finals.CritterCode.ast;

import java.util.*;

public class IfNode extends ASTNode {

    public String left;
    public String op;
    public String right;

    public List<ASTNode> body = new ArrayList<>();
    public List<ASTNode> elseBody = new ArrayList<>();

    public IfNode() {}

    public IfNode(String left, String op, String right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
}
