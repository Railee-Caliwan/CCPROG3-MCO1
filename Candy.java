import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class Candy {
    private String type;
    private String flavor;

    public Candy(String type, String flavor) {
        this.type = type;
        this.flavor = flavor;
    }

    public String getType() { return type; }
    public String getFlavor() { return flavor; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Candy)) return false;
        Candy other = (Candy) obj;
        return this.type.equals(other.type) && 
               (this.flavor == null ? other.flavor == null : this.flavor.equals(other.flavor));
    }

    @Override
    public int hashCode() {
        return type.hashCode() + (flavor != null ? flavor.hashCode() : 0);
    }
}
