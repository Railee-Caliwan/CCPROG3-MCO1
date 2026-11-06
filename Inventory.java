import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {
    private int maxCapacity;
    private List<Candy> slots;

    public Inventory() {
        this.maxCapacity = 20; // Default capacity at 0-star[cite: 2]
        this.slots = new ArrayList<>();
    }

    public boolean addCandy(Candy candy) {
        if (!isFull()) {
            slots.add(candy);
            return true;
        }
        return false;
    }

    public void discardCandies(List<Integer> indicesToDiscard) {
        // Sort in descending order to prevent index shifting issues during removal
        indicesToDiscard.sort(Collections.reverseOrder());
        for (int index : indicesToDiscard) {
            if (index >= 0 && index < slots.size()) {
                slots.remove(index);
            }
        }
    }

    public boolean isFull() {
        return slots.size() >= maxCapacity;
    }

    public void updateCapacity(int starRank) {
        // Scales from 20 to 100 based on star rank[cite: 2]
        this.maxCapacity = 20 + (starRank * 20); 
    }

    public List<Candy> getSlots() {
        return slots;
    }
}
