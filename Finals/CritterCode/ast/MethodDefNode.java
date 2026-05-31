package Finals.CritterCode.ast;

import java.util.*;

public class MethodDefNode extends ASTNode {
    public String name;
    public List<ASTNode> body = new ArrayList<>();

    public MethodDefNode(String name) {
        this.name = name;
    }
}