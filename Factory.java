import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a Factory in the world map that produces specific types of candies.
 */
public class Factory {
    private int factoryID;
    private int row;
    private int col;
    private List<Candy> candyPool;
    private Candy[][] productionGrid; // Represents the physical grid inside the factory

    /**
     * Constructs a Factory with a specified ID, coordinates, and candy pool.
     * @param factoryID Unique numerical ID of the factory.
     * @param row Row coordinate on the 15x15 map.
     * @param col Column coordinate on the 15x15 map.
     * @param candyPool List of candies this factory is allowed to produce.
     */
    public Factory(int factoryID, int row, int col, List<Candy> candyPool) {
        this.factoryID = factoryID;
        this.row = row;
        this.col = col;
        this.candyPool = candyPool;
        this.productionGrid = new Candy[6][6]; // Default 6x6 grid size for 0-star
        refreshGrid();
    }

    public int getFactoryID() { return factoryID; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public List<Candy> getCandyPool() { return candyPool; }
    public Candy[][] getProductionGrid() { return productionGrid; }

    /**
     * Resizes the production grid when the player ranks up.
     * New slots start as empty (null) until the next day's refresh.
     * @param newSize The new grid dimension (e.g., 7 for 7x7).
     */
    public void updateGridSize(int newSize) {
        if (newSize <= productionGrid.length) return;
        
        Candy[][] newGrid = new Candy[newSize][newSize];
        int oldSize = productionGrid.length;
        
        // Copy existing grid contents
        for (int r = 0; r < oldSize; r++) {
            for (int c = 0; c < oldSize; c++) {
                newGrid[r][c] = productionGrid[r][c];
            }
        }
        // New slots are left null (vacant)
        this.productionGrid = newGrid;
    }

    /**
     * Refreshes the production grid by filling all empty or existing slots 
     * with randomly selected candies from the factory's pool.
     */
    public void refreshGrid() {
        Random rand = new Random();
        int size = productionGrid.length;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (!candyPool.isEmpty()) {
                    Candy picked = candyPool.get(rand.nextInt(candyPool.size()));
                    // Create a fresh instance of the candy
                    productionGrid[r][c] = new Candy(picked.getType(), picked.getFlavor());
                }
            }
        }
    }

    /**
     * Removes and returns a candy from a specific grid coordinate.
     * @param r Grid row.
     * @param c Grid column.
     * @return The Candy picked up, or null if slot was empty or out of bounds.
     */
    public Candy pickupCandy(int r, int c) {
        if (r < 0 || r >= productionGrid.length || c < 0 || c >= productionGrid[0].length) {
            return null;
        }
        Candy candy = productionGrid[r][c];
        productionGrid[r][c] = null; // Leave slot vacant
        return candy;
    }
}
