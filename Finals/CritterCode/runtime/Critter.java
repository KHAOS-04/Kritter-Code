package Finals.CritterCode.runtime;

import java.util.*;
import Finals.CritterCode.ast.ASTNode;

public class Critter {
    public String name;
    public Map<String, Integer> properties = new HashMap<>();
    public Map<String, List<ASTNode>> methods = new HashMap<>();

    public Critter(String name) {
        this.name = name;
    }
}