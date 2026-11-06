public class Player {
    private String name;
    private int starRank;
    private int totalXP;
    private int dayNumber;
    private int totalSteps;
    
    private int currentRow;
    private int currentCol;
    
    private Inventory inventory;

    public Player(String name, int startRow, int startCol) {
        this.name = name;
        this.starRank = 0;
        this.totalXP = 0;
        this.dayNumber = 1;
        this.totalSteps = 0;
        this.currentRow = startRow;
        this.currentCol = startCol;
        this.inventory = new Inventory();
    }

    public boolean move(String direction, int maxGridBound) {
        int newRow = currentRow;
        int newCol = currentCol;

        switch (direction.toLowerCase()) {
            case "up": newRow--; break;
            case "down": newRow++; break;
            case "left": newCol--; break;
            case "right": newCol++; break;
            // Diagonal movement unlocked at 3-star[cite: 2, 3]
            case "upleft": if (starRank >= 3) { newRow--; newCol--; } break;
            case "upright": if (starRank >= 3) { newRow--; newCol++; } break;
            case "downleft": if (starRank >= 3) { newRow++; newCol--; } break;
            case "downright": if (starRank >= 3) { newRow++; newCol++; } break;
            default: return false;
        }

        // World boundaries do not wrap around[cite: 3]
        if (newRow >= 0 && newRow < maxGridBound && newCol >= 0 && newCol < maxGridBound) {
            this.currentRow = newRow;
            this.currentCol = newCol;
            this.totalSteps++;
            return true;
        }
        return false;
    }

    public void addXP(int amount) {
        this.totalXP += amount;
        updateRank();
    }

    private void updateRank() {
        // Rank thresholds: 100, 500, 1400, 3000, 5500[cite: 2]
        int newRank = 0;
        if (totalXP >= 5500) newRank = 5;
        else if (totalXP >= 3000) newRank = 4;
        else if (totalXP >= 1400) newRank = 3;
        else if (totalXP >= 500) newRank = 2;
        else if (totalXP >= 100) newRank = 1;

        if (newRank > this.starRank) {
            this.starRank = newRank;
            this.inventory.updateCapacity(this.starRank);
        }
    }

    public void endDay() {
        this.dayNumber++;
    }

    // Getters
    public int getRow() { return currentRow; }
    public int getCol() { return currentCol; }
    public Inventory getInventory() { return inventory; }
    public int getStarRank() { return starRank; }
    public String getName() { return name; }
    public int getTotalXP() { return totalXP; }
    public int getTotalSteps() { return totalSteps; }
    public int getDayNumber() { return dayNumber; }
}
