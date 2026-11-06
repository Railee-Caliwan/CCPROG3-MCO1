import java.util.Objects;

/**
 * Represents a Candy item in the game.
 * Each candy has a type and an optional flavor.
 */
public class Candy {
    private String type;
    private String flavor;

    /**
     * Constructs a new Candy.
     * @param type The type of candy (e.g., "Gummy", "Lollipop", "Candy Corn")
     * @param flavor The flavor of candy (e.g., "Ube", "Mango", or null if unflavored)
     */
    public Candy(String type, String flavor) {
        this.type = type;
        this.flavor = flavor;
    }

    public String getType() { 
        return type; 
    }

    public String getFlavor() { 
        return flavor; 
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Candy)) return false;
        Candy other = (Candy) obj;
        return Objects.equals(this.type, other.type) && 
               Objects.equals(this.flavor, other.flavor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, flavor);
    }
}
