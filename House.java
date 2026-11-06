import java.util.Map;

/**
 * Represents a House in the world map that requires a specific candy delivery.
 */
public class House {
    private char houseID;
    private int row;
    private int col;
    private Map<Candy, Integer> requiredOrder;
    private int xpReward;
    private boolean isDelivered;

    /**
     * Constructs a House with its ID, coordinates, order, and XP reward.
     * @param houseID The character ID of the house (e.g., 'A', 'B').
     * @param row The absolute 15x15 row coordinate.
     * @param col The absolute 15x15 column coordinate.
     * @param requiredOrder A map of required Candy and their quantities.
     * @param xpReward The amount of XP given upon successful delivery.
     */
    public House(char houseID, int row, int col, Map<Candy, Integer> requiredOrder, int xpReward) {
        this.houseID = houseID;
        this.row = row;
        this.col = col;
        this.requiredOrder = requiredOrder;
        this.xpReward = xpReward;
        this.isDelivered = false; // Starts undelivered
    }

    public char getHouseID() { return houseID; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public Map<Candy, Integer> getRequiredOrder() { return requiredOrder; }
    public int getXpReward() { return xpReward; }
    public boolean isDelivered() { return isDelivered; }

    /**
     * Marks this house's order as successfully delivered.
     */
    public void markDelivered() {
        this.isDelivered = true;
    }
}
