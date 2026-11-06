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

    public void initializeData() {
        // --- FACTORY INITIALIZATION (Appendix C) ---
        // Factory 1: Introduced at 0-star. 7x7 Intro Coords: Row 0, Col 6[cite: 16]
        List<Candy> poolF1 = new ArrayList<>();
        poolF1.add(new Candy("Candy Corn", null));
        poolF1.add(new Candy("Lollipop", "Ube"));
        poolF1.add(new Candy("Lollipop", "Mango"));
        poolF1.add(new Candy("Lollipop", "Macapuno"));
        poolF1.add(new Candy("Lollipop", "Pandan"));
        this.factories.add(new Factory(1, 0, 6, poolF1)); // Use 7x7 intro coordinates

        // Factory 2: Introduced at 0-star. 7x7 Intro Coords: Row 6, Col 0[cite: 16]
        List<Candy> poolF2 = new ArrayList<>();
        poolF2.add(new Candy("Gummy", "Ube"));
        poolF2.add(new Candy("Gummy", "Mango"));
        poolF2.add(new Candy("Gummy", "Macapuno"));
        poolF2.add(new Candy("Gummy", "Pandan"));
        poolF2.add(new Candy("Marshmallow", "Ube"));
        poolF2.add(new Candy("Marshmallow", "Mango"));
        poolF2.add(new Candy("Marshmallow", "Macapuno"));
        poolF2.add(new Candy("Marshmallow", "Pandan"));
        this.factories.add(new Factory(2, 6, 0, poolF2));

        // TODO: Add Factories 3 through 10 here following the Appendix C specs...

        // --- HOUSE INITIALIZATION (Appendix D) ---
        // House A: 7x7 Intro Coords: Row 1, Col 1. XP: 100[cite: 21]
        Map<Candy, Integer> reqA = new HashMap<>();
        reqA.put(new Candy("Candy Corn", null), 4);
        // Note: For "any flavor", you will need to adjust your House canFulfill logic 
        // to accept a base type, or explicitly list all accepted flavors here.
        reqA.put(new Candy("Gummy", "Any"), 2); 
        reqA.put(new Candy("Lollipop", "Any"), 2);
        reqA.put(new Candy("Marshmallow", "Any"), 2);
        this.houses.add(new House('A', 1, 1, reqA, 100));

        // House B: 7x7 Intro Coords: Row 5, Col 2. XP: 150[cite: 21]
        Map<Candy, Integer> reqB = new HashMap<>();
        reqB.put(new Candy("Candy Corn", null), 15);
        this.houses.add(new House('B', 5, 2, reqB, 150));

        // TODO: Add Houses C through P here following the Appendix D specs...
    }
}
