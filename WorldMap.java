import java.util.ArrayList;
import java.util.List;

public class WorldMap {
    private int gridSize;
    private int centerHomeRow;
    private int centerHomeCol;
    
    private List<Factory> factories;
    private List<House> houses;

    public WorldMap() {
        this.gridSize = 7; // Starts at 7x7[cite: 2]
        this.centerHomeRow = 3; // Center of 7x7[cite: 3]
        this.centerHomeCol = 3;
        this.factories = new ArrayList<>();
        this.houses = new ArrayList<>();
    }

    public void expandWorld(int starRank) {
        // Grid sizes: 7, 9, 11, 13, 15[cite: 2]
        int newGridSize = 7 + (starRank * 2);
        
        // When grid expands, coordinates shift because 1 row/col is added to each side[cite: 7]
        int shift = (newGridSize - this.gridSize) / 2;
        this.gridSize = newGridSize;
        
        this.centerHomeRow += shift;
        this.centerHomeCol += shift;
        
        // Entity coordinates must also be visually/logically shifted if necessary, 
        // though specifications define static final coordinates based on a 15x15 absolute map.
    }

    public boolean isPlayerAtHome(Player player) {
        return player.getRow() == centerHomeRow && player.getCol() == centerHomeCol;
    }

    public Factory getFactoryAt(int row, int col) {
        for (Factory f : factories) {
            if (f.getRow() == row && f.getCol() == col) return f;
        }
        return null;
    }

    public House getHouseAt(int row, int col) {
        for (House h : houses) {
            if (h.getRow() == row && h.getCol() == col && !h.isDelivered()) return h;
        }
        return null;
    }

    public int getGridSize() { return gridSize; }
}
