import java.util.List;
import java.util.Random;

public class Factory {
    private int factoryID;
    private int row;
    private int col;
    private int productionGridSize;
    private List<Candy> candyPool;
    private Candy[][] availableCandies;
    private Random random;

    public Factory(int factoryID, int row, int col, List<Candy> candyPool) {
        this.factoryID = factoryID;
        this.row = row;
        this.col = col;
        this.candyPool = candyPool;
        this.productionGridSize = 6; // Default 6x6 at 0-star[cite: 2]
        this.availableCandies = new Candy[11][11]; // Max size is 11x11 at 5-stars
        this.random = new Random();
        generateCandies();
    }

    public void generateCandies() {
        // Only generates within the current active bounds based on rank
        for (int i = 0; i < productionGridSize; i++) {
            for (int j = 0; j < productionGridSize; j++) {
                if (availableCandies[i][j] == null) {
                    availableCandies[i][j] = candyPool.get(random.nextInt(candyPool.size()));
                }
            }
        }
    }

    public Candy pickUp(int gridRow, int gridCol) {
        if (gridRow >= 0 && gridRow < productionGridSize && gridCol >= 0 && gridCol < productionGridSize) {
            Candy picked = availableCandies[gridRow][gridCol];
            availableCandies[gridRow][gridCol] = null; 
            return picked;
        }
        return null;
}

    public void refreshEvent(int currentStarRank) {
        // Expand grid if rank increased (6x6 up to 10x10)[cite: 2]
        this.productionGridSize = 6 + currentStarRank; 
        
        // Refresh replaces unpicked candies and fills vacant slots[cite: 5]
        for (int i = 0; i < productionGridSize; i++) {
            for (int j = 0; j < productionGridSize; j++) {
                availableCandies[i][j] = candyPool.get(random.nextInt(candyPool.size()));
            }
        }
    }

    // Getters for location mapping
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getFactoryID() { return factoryID; }
    public List<Candy> getCandyPool() { return candyPool; }
    
}
