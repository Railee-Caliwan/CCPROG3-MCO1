import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player's inventory containing slots for candies.
 */
public class Inventory {
    private List<Candy> slots;
    private int maxCapacity;

    /**
     * Constructs an Inventory with a default capacity of 20 slots.
     */
    public Inventory() {
        this.slots = new ArrayList<>();
        this.maxCapacity = 20;
    }

    public List<Candy> getSlots() {
        return slots;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isFull() {
        return slots.size() >= maxCapacity;
    }

    /**
     * Adds a candy to inventory if space is available.
     * @param candy The candy to add.
     * @return true if added, false if inventory is full.
     */
    public boolean addCandy(Candy candy) {
        if (isFull()) {
            return false;
        }
        slots.add(candy);
        return true;
    }

    /**
     * Discards candies at the specified zero-based slot indices.
     * @param indices List of slot indices to remove.
     */
    public void discardCandies(List<Integer> indices) {
        // Sort in descending order to avoid array index shifting during removal
        indices.sort((a, b) -> b.compareTo(a));
        for (int idx : indices) {
            if (idx >= 0 && idx < slots.size()) {
                slots.remove(idx);
            }
        }
    }

    /**
     * Counts how many candies match a target candy type and flavor.
     * If target flavor is "Any", matches any candy of that type.
     * @param target The target candy criteria.
     * @return The count of matching candies.
     */
    public int countMatching(Candy target) {
        int count = 0;
        for (Candy c : slots) {
            if (c.getType().equalsIgnoreCase(target.getType())) {
                if ("Any".equalsIgnoreCase(target.getFlavor()) || 
                   (target.getFlavor() == null && c.getFlavor() == null) ||
                   (target.getFlavor() != null && target.getFlavor().equalsIgnoreCase(c.getFlavor()))) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Removes up to quantity matching candies for order fulfillment.
     * @param target Target candy type and flavor to remove.
     * @param quantity Amount to remove.
     */
    public void removeMatching(Candy target, int quantity) {
        int removed = 0;
        for (int i = slots.size() - 1; i >= 0 && removed < quantity; i--) {
            Candy c = slots.get(i);
            if (c.getType().equalsIgnoreCase(target.getType())) {
                if ("Any".equalsIgnoreCase(target.getFlavor()) || 
                   (target.getFlavor() == null && c.getFlavor() == null) ||
                   (target.getFlavor() != null && target.getFlavor().equalsIgnoreCase(c.getFlavor()))) {
                    slots.remove(i);
                    removed++;
                }
            }
        }
    }
}
