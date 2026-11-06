/**
 * Represents the Candy Courier player, tracking their stats, inventory, and location.
 */
public class Player {
    private String name;
    private int totalXP;
    private int starRank;
    private int totalSteps;
    private int dayNumber;
    private int row;
    private int col;
    private Inventory inventory;

    /**
     * Constructs a new Player and spawns them at the Player's Home (absolute center: 7, 7).
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.totalXP = 0;
        this.starRank = 0;
        this.totalSteps = 0;
        this.dayNumber = 1;
        
        // Absolute center of the 15x15 map
        this.row = 7;
        this.col = 7;
        
        this.inventory = new Inventory();
    }

    // Getters
    public String getName() { return name; }
    public int getTotalXP() { return totalXP; }
    public int getStarRank() { return starRank; }
    public int getTotalSteps() { return totalSteps; }
    public int getDayNumber() { return dayNumber; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public Inventory getInventory() { return inventory; }

    /**
     * Adds XP to the player and checks if they qualify for a rank up.
     * Automatically expands inventory capacity upon ranking up.
     * @param amount The amount of XP to add.
     */
    public void addXP(int amount) {
        this.totalXP += amount;
        updateRank();
    }

    /**
     * Evaluates total XP and updates the player's star rank and inventory capacity.
     */
    private void updateRank() {
        int oldRank = this.starRank;
        
        if (totalXP >= 5500) {
            starRank = 5;
        } else if (totalXP >= 3000) {
            starRank = 4;
        } else if (totalXP >= 1400) {
            starRank = 3;
        } else if (totalXP >= 500) {
            starRank = 2;
        } else if (totalXP >= 100) {
            starRank = 1;
        }

        // Update inventory capacity if rank changed
        if (starRank > oldRank) {
            switch(starRank) {
                case 1: inventory.setMaxCapacity(40); break;
                case 2: inventory.setMaxCapacity(60); break;
                case 3: inventory.setMaxCapacity(80); break;
                case 4: inventory.setMaxCapacity(100); break;
            }
        }
    }

    /**
     * Calculates how much more XP is needed to reach the next rank.
     * @return The remaining XP needed, or 0 if max rank (5-star).
     */
    public int getXpToNextRank() {
        if (starRank == 0) return Math.max(0, 100 - totalXP);
        if (starRank == 1) return Math.max(0, 500 - totalXP);
        if (starRank == 2) return Math.max(0, 1400 - totalXP);
        if (starRank == 3) return Math.max(0, 3000 - totalXP);
        if (starRank == 4) return Math.max(0, 5500 - totalXP);
        return 0; // Max rank achieved
    }

    /**
     * Moves the player by the given deltas and increments the step counter.
     * @param dRow Row delta (e.g., -1 for up, 1 for down).
     * @param dCol Column delta (e.g., -1 for left, 1 for right).
     */
    public void move(int dRow, int dCol) {
        this.row += dRow;
        this.col += dCol;
        this.totalSteps++;
    }

    /**
     * Increments the day counter when a refresh event is triggered.
     */
    public void advanceDay() {
        this.dayNumber++;
    }

    /**
     * Determines if diagonal movement is unlocked based on rank.
     * @return true if rank is 3 or higher.
     */
    public boolean canMoveDiagonally() {
        return starRank >= 3;
    }
}
