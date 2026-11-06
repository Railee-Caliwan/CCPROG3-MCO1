import java.util.Map;
import java.util.HashMap;

/**
 * Represents a House in the Candy Courier game.
 * Houses have specific candy requirements and grant XP upon successful delivery.
 */
public class House {
    private char houseID; // The character ID of the house (e.g., 'A', 'B')
    private int row; // The row coordinate on the world map
    private int col; // The column coordinate on the world map
    private Map<Candy, Integer> requiredOrder; // The candies required to fulfill the house's order
    private int xpReward; // The amount of XP granted for a successful delivery
    private boolean isDelivered; // Tracks whether the house's order has been fulfilled

    /**
     * Constructs a new House with specified coordinates and requirements.
     */
    public House(char houseID, int row, int col, Map<Candy, Integer> requiredOrder, int xpReward) {
        this.houseID = houseID;
        this.row = row;
        this.col = col;
        this.requiredOrder = requiredOrder;
        this.xpReward = xpReward;
        this.isDelivered = false;
    }

    /**
     * Checks if the provided inventory has enough candies to fulfill the house's order.
     */
    public boolean canFulfill(Inventory inventory) {
        if (isDelivered) return false;

        // Group inventory by exact candy and by base type for "Any" flavor checks
        Map<Candy, Integer> exactCounts = new HashMap<>();
        Map<String, Integer> typeCounts = new HashMap<>();
        
        for (Candy c : inventory.getSlots()) {
            exactCounts.put(c, exactCounts.getOrDefault(c, 0) + 1);
            typeCounts.put(c.getType(), typeCounts.getOrDefault(c.getType(), 0) + 1);
        }

        for (Map.Entry<Candy, Integer> req : requiredOrder.entrySet()) {
            Candy reqCandy = req.getKey();
            int reqAmount = req.getValue();

            if (reqCandy.getFlavor() != null && reqCandy.getFlavor().equalsIgnoreCase("Any")) {
                if (typeCounts.getOrDefault(reqCandy.getType(), 0) < reqAmount) {
                    return false; 
                }
            } else {
                if (exactCounts.getOrDefault(reqCandy, 0) < reqAmount) {
                    return false; 
                }
            }
        }
        return true;
    }

    /**
     * Processes the delivery, removing the required candies from the inventory and returning the XP reward.
     */
    public int processDelivery(Inventory inventory) {
        if (!canFulfill(inventory)) return 0;

        for (Map.Entry<Candy, Integer> req : requiredOrder.entrySet()) {
            Candy reqCandy = req.getKey();
            int toRemove = req.getValue();
            
            for (int i = inventory.getSlots().size() - 1; i >= 0 && toRemove > 0; i--) {
                Candy inventoryCandy = inventory.getSlots().get(i);
                
                boolean match = false;
                if (reqCandy.getFlavor() != null && reqCandy.getFlavor().equalsIgnoreCase("Any")) {
                    match = inventoryCandy.getType().equals(reqCandy.getType());
                } else {
                    match = inventoryCandy.equals(reqCandy);
                }

                if (match) {
                    inventory.getSlots().remove(i);
                    toRemove--;
                }
            }
        }
        
        this.isDelivered = true; 
        return xpReward; 
    }

    public boolean isDelivered() { return isDelivered; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public char getHouseID() { return houseID; }
    public int getXpReward() { return xpReward; }
    public Map<Candy, Integer> getRequiredOrder() { return requiredOrder; }
}
