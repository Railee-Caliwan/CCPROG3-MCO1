import java.util.Map;
import java.util.HashMap;

public class House {
    private char houseID;
    private int row;
    private int col;
    private Map<Candy, Integer> requiredOrder;
    private int xpReward;
    private boolean isDelivered;

    public House(char houseID, int row, int col, Map<Candy, Integer> requiredOrder, int xpReward) {
        this.houseID = houseID;
        this.row = row;
        this.col = col;
        this.requiredOrder = requiredOrder;
        this.xpReward = xpReward;
        this.isDelivered = false;
    }

    public boolean canFulfill(Inventory inventory) {
        if (isDelivered) return false;

        Map<Candy, Integer> playerCounts = new HashMap<>();
        for (Candy c : inventory.getSlots()) {
            playerCounts.put(c, playerCounts.getOrDefault(c, 0) + 1);
        }

        // Check if player has required quantities of required candies[cite: 4]
        for (Map.Entry<Candy, Integer> req : requiredOrder.entrySet()) {
            if (playerCounts.getOrDefault(req.getKey(), 0) < req.getValue()) {
                return false; 
            }
        }
        return true;
    }

    public int processDelivery(Inventory inventory) {
        if (!canFulfill(inventory)) return 0;

        // Remove exact candies from player inventory
        for (Map.Entry<Candy, Integer> req : requiredOrder.entrySet()) {
            int toRemove = req.getValue();
            for (int i = inventory.getSlots().size() - 1; i >= 0 && toRemove > 0; i--) {
                if (inventory.getSlots().get(i).equals(req.getKey())) {
                    inventory.getSlots().remove(i);
                    toRemove--;
                }
            }
        }
        this.isDelivered = true; // House no longer triggers event[cite: 5]
        return xpReward; // Return XP for player to add[cite: 4]
    }

    public boolean isDelivered() { return isDelivered; }
    public int getRow() { return row; }
    public int getCol() { return col; }
}
