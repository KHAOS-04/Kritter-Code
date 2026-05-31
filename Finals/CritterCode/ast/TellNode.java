package Finals.CritterCode.ast;
/**
 * @author Kia
 */
import java.util.*;

public class TellNode extends ASTNode {
    public String critterName;
    public List<ASTNode> body = new ArrayList<>();

    public TellNode(String name) {
        this.critterName = name;
    }
}