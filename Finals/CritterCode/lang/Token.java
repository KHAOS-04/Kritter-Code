package Finals.CritterCode.lang;

public class Token {
    public enum Type {
        IDENTIFIER,
        KEYWORD,
        NUMBER,
        STRING,
        SYMBOL,
        EOF
    }

    public final Type type;
    public final String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        return type + "('" + value + "')";
    }
}