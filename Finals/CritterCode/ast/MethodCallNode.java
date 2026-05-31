package Finals.CritterCode.ast;

public class MethodCallNode extends ASTNode {
    public String objectName;
    public String methodName;

    public MethodCallNode(String obj, String method) {
        this.objectName = obj;
        this.methodName = method;
    }
}
