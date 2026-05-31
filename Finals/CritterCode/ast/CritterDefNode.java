package Finals.CritterCode.ast;

/**
 * @author Kia
 */
import java.util.*;
public class CritterDefNode extends ASTNode {
    public String name;
    public List<ASTNode> body = new ArrayList<>();

    public CritterDefNode(String name) {
        this.name = name;
    }
}
