package Finals.CritterCode.ast;

public class AssignmentNode extends ASTNode {
    public String objectName; // "SELF" or "Bibo"
    public String propertyName;
    public String value;      // expression string

    public AssignmentNode(String obj, String prop, String val) {
        this.objectName = obj;
        this.propertyName = prop;
        this.value = val;
    }
}
