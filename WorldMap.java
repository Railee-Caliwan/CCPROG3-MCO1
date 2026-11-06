import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the grid boundaries, factories, and houses in the game.
 */
public class WorldMap {
    private List<Factory> factories;
    private List<House> houses;

    public WorldMap() {
        this.factories = new ArrayList<>();
        this.houses = new ArrayList<>();
        initializeData();
    }

    public List<Factory> getFactories() { return factories; }
    public List<House> getHouses() { return houses; }

    /**
     * Determines the minimum accessible row/col bound based on rank.
     * Absolute center is 7, 7.
     */
    public int getMinBound(int starRank) {
        switch(starRank) {
            case 0: return 4; // 7x7 grid (7 - 3)
            case 1: return 3; // 9x9 grid (7 - 4)
            case 2: return 2; // 11x11 grid (7 - 5)
            case 3: return 1; // 13x13 grid (7 - 6)
            default: return 0; // 15x15 grid (4-star and 5-star)
        }
    }

    /**
     * Determines the maximum accessible row/col bound based on rank.
     */
    public int getMaxBound(int starRank) {
        switch(starRank) {
            case 0: return 10; // 7 + 3
            case 1: return 11; // 7 + 4
            case 2: return 12; // 7 + 5
            case 3: return 13; // 7 + 6
            default: return 14; // 15x15 grid max index
        }
    }

    /**
     * Checks if a target coordinate is within the current allowed grid.
     */
    public boolean isValidLocation(int r, int c, int starRank) {
        int min = getMinBound(starRank);
        int max = getMaxBound(starRank);
        return r >= min && r <= max && c >= min && c <= max;
    }

    /**
     * Returns the Factory at the specified coordinates, if any.
     */
    public Factory getFactoryAt(int r, int c) {
        for (Factory f : factories) {
            if (f.getRow() == r && f.getCol() == c) {
                return f;
            }
        }
        return null;
    }

    /**
     * Returns the House at the specified coordinates, ONLY if it has not been delivered yet.
     */
    public House getHouseAt(int r, int c) {
        for (House h : houses) {
            if (h.getRow() == r && h.getCol() == c && !h.isDelivered()) {
                return h;
            }
        }
        return null;
    }

    /**
     * Triggers the end-of-day refresh event for all factories.
     */
    public void triggerRefresh(int starRank) {
        int newGridSize = Math.min(10, 6 + starRank);
        for (Factory f : factories) {
            f.updateGridSize(newGridSize);
            f.refreshGrid();
        }
    }
    
    /**
     * Prints a visual text grid representation of the map based on current rank boundaries.
     */
    public void printMap(Player player) {
        int minBound = getMinBound(player.getStarRank());
        int maxBound = getMaxBound(player.getStarRank());
        
        System.out.println("\n=== WORLD MAP ===");
        
        // Print column numbers header
        System.out.print("   ");
        for (int c = minBound; c <= maxBound; c++) {
            System.out.printf("%2d ", c);
        }
        System.out.println();
        
        // Print row numbers and grid cells
        for (int r = minBound; r <= maxBound; r++) {
            System.out.printf("%2d ", r); // Row coordinate label
            
            for (int c = minBound; c <= maxBound; c++) {
                if (r == player.getRow() && c == player.getCol()) {
                    System.out.print("[P]"); // Player icon
                } else if (r == 7 && c == 7) {
                    System.out.print("[H]"); // Home icon
                } else {
                    Factory f = getFactoryAt(r, c);
                    House h = getHouseAt(r, c);
                    if (f != null) {
                        System.out.print("[F]"); // Factory icon
                    } else if (h != null) {
                        System.out.print("[" + h.getHouseID() + "]"); // House character ID (e.g. [A], [B])
                    } else {
                        System.out.print(" . "); // Empty tile
                    }
                }
            }
            System.out.println();
        }
        System.out.println("=================\n");
    }

    /**
     * Populates the map with the official 15x15 absolute coordinates.
     */
    public void initializeData() {
        // Factory 1 (Final Coords: 4, 10)
        List<Candy> poolF1 = new ArrayList<>();
        poolF1.add(new Candy("Candy Corn", null));
        for (String f : new String[]{"Ube", "Mango", "Macapuno", "Pandan"}) 
            poolF1.add(new Candy("Lollipop", f));
        this.factories.add(new Factory(1, 4, 10, poolF1));

        // Factory 2 (Final Coords: 10, 4)
        List<Candy> poolF2 = new ArrayList<>();
        for (String f : new String[]{"Ube", "Mango", "Macapuno", "Pandan"}) {
            poolF2.add(new Candy("Gummy", f));
            poolF2.add(new Candy("Marshmallow", f));
        }
        this.factories.add(new Factory(2, 10, 4, poolF2));

        // Factory 3 (Final Coords: 3, 3)
        List<Candy> poolF3 = new ArrayList<>();
        for (String f : new String[]{"Ube", "Mango", "Macapuno", "Pandan"}) {
            poolF3.add(new Candy("Pastillas", f));
            poolF3.add(new Candy("Yema", f));
            poolF3.add(new Candy("Polvoron", f));
        }
        this.factories.add(new Factory(3, 3, 3, poolF3));

        // Factory 4 (Final Coords: 12, 12)
        List<Candy> poolF4 = new ArrayList<>();
        for (String f : new String[]{"Tablea"}) {
            poolF4.add(new Candy("Gummy", f));
            poolF4.add(new Candy("Lollipop", f));
            poolF4.add(new Candy("Marshmallow", f));
            poolF4.add(new Candy("Pastillas", f));
            poolF4.add(new Candy("Yema", f));
            poolF4.add(new Candy("Polvoron", f));
        }
        this.factories.add(new Factory(4, 12, 12, poolF4));

        // Factory 5 (Final Coords: 1, 11)
        List<Candy> poolF5 = new ArrayList<>();
        for (String f : new String[]{"Jackfruit", "Durian"}) {
            poolF5.add(new Candy("Gummy", f));
            poolF5.add(new Candy("Lollipop", f));
            poolF5.add(new Candy("Marshmallow", f));
        }
        this.factories.add(new Factory(5, 1, 11, poolF5));

        // Factory 6 (Final Coords: 13, 7)
        List<Candy> poolF6 = new ArrayList<>();
        for (String f : new String[]{"Ube", "Mango", "Macapuno", "Pandan", "Tablea", "Jackfruit", "Durian"}) 
            poolF6.add(new Candy("Lollipop", f));
        this.factories.add(new Factory(6, 13, 7, poolF6));

        // Factory 7 (Final Coords: 6, 13)
        List<Candy> poolF7 = new ArrayList<>();
        for (String f : new String[]{"Ube", "Mango", "Macapuno", "Pandan", "Tablea", "Jackfruit", "Durian"}) 
            poolF7.add(new Candy("Gummy", f));
        this.factories.add(new Factory(7, 6, 13, poolF7));

        // Factory 8 (Final Coords: 7, 1)
        List<Candy> poolF8 = new ArrayList<>();
        for (String f : new String[]{"Ube", "Mango", "Macapuno", "Pandan", "Tablea", "Jackfruit", "Durian"}) 
            poolF8.add(new Candy("Marshmallow", f));
        this.factories.add(new Factory(8, 7, 1, poolF8));

        // Factory 9 (Final Coords: 0, 0)
        List<Candy> poolF9 = new ArrayList<>();
        poolF9.add(new Candy("Candy Corn", null));
        poolF9.add(new Candy("Candy Cane", null));
        for (String f : new String[]{"Ube", "Mango"}) {
            poolF9.add(new Candy("Pastillas", f));
            poolF9.add(new Candy("Yema", f));
            poolF9.add(new Candy("Polvoron", f));
        }
        this.factories.add(new Factory(9, 0, 0, poolF9));

        // Factory 10 (Final Coords: 14, 2)
        List<Candy> poolF10 = new ArrayList<>();
        for (String f : new String[]{"Macapuno", "Pandan", "Jackfruit", "Durian"}) {
            poolF10.add(new Candy("Pastillas", f));
            poolF10.add(new Candy("Yema", f));
            poolF10.add(new Candy("Polvoron", f));
        }
        this.factories.add(new Factory(10, 14, 2, poolF10));

        // House A (Final Coords: 5, 5, XP: 100)
        Map<Candy, Integer> reqA = new HashMap<>();
        reqA.put(new Candy("Candy Corn", null), 4);
        reqA.put(new Candy("Gummy", "Any"), 2);
        reqA.put(new Candy("Lollipop", "Any"), 2);
        reqA.put(new Candy("Marshmallow", "Any"), 2);
        this.houses.add(new House('A', 5, 5, reqA, 100));

        // House B (Final Coords: 9, 6, XP: 150)
        Map<Candy, Integer> reqB = new HashMap<>();
        reqB.put(new Candy("Candy Corn", null), 15);
        this.houses.add(new House('B', 9, 6, reqB, 150));

        // House C (Final Coords: 7, 10, XP: 250)
        Map<Candy, Integer> reqC = new HashMap<>();
        reqC.put(new Candy("Gummy", "Any"), 5);
        reqC.put(new Candy("Marshmallow", "Any"), 5);
        reqC.put(new Candy("Pastillas", "Any"), 5);
        reqC.put(new Candy("Yema", "Any"), 5);
        reqC.put(new Candy("Polvoron", "Any"), 5);
        this.houses.add(new House('C', 7, 10, reqC, 250));

        // House D (Final Coords: 11, 9, XP: 400)
        Map<Candy, Integer> reqD = new HashMap<>();
        reqD.put(new Candy("Candy Corn", null), 5);
        reqD.put(new Candy("Candy Cane", null), 5);
        reqD.put(new Candy("Pastillas", "Any"), 10);
        reqD.put(new Candy("Yema", "Any"), 10);
        reqD.put(new Candy("Polvoron", "Any"), 10);
        this.houses.add(new House('D', 11, 9, reqD, 400));

        // House E (Final Coords: 2, 8, XP: 300)
        Map<Candy, Integer> reqE = new HashMap<>();
        reqE.put(new Candy("Candy Corn", null), 10);
        reqE.put(new Candy("Yema", "Any"), 5);
        reqE.put(new Candy("Pastillas", "Any"), 5);
        reqE.put(new Candy("Lollipop", "Ube"), 5);
        reqE.put(new Candy("Lollipop", "Macapuno"), 5);
        this.houses.add(new House('E', 2, 8, reqE, 300));

        // House F (Final Coords: 12, 3, XP: 300)
        Map<Candy, Integer> reqF = new HashMap<>();
        reqF.put(new Candy("Gummy", "Tablea"), 7);
        reqF.put(new Candy("Marshmallow", "Tablea"), 7);
        reqF.put(new Candy("Pastillas", "Tablea"), 8);
        reqF.put(new Candy("Polvoron", "Tablea"), 8);
        this.houses.add(new House('F', 12, 3, reqF, 300));

        // House G (Final Coords: 6, 2, XP: 400)
        Map<Candy, Integer> reqG = new HashMap<>();
        reqG.put(new Candy("Pastillas", "Any"), 20);
        reqG.put(new Candy("Pastillas", "Durian"), 10);
        reqG.put(new Candy("Pastillas", "Jackfruit"), 10);
        this.houses.add(new House('G', 6, 2, reqG, 400));

        // House H (Final Coords: 8, 12, XP: 300)
        Map<Candy, Integer> reqH = new HashMap<>();
        reqH.put(new Candy("Polvoron", "Any"), 10);
        reqH.put(new Candy("Yema", "Any"), 5);
        reqH.put(new Candy("Pastillas", "Any"), 5);
        reqH.put(new Candy("Gummy", "Mango"), 5);
        reqH.put(new Candy("Marshmallow", "Pandan"), 5);
        this.houses.add(new House('H', 8, 12, reqH, 300));

        // House I (Final Coords: 1, 4, XP: 450)
        Map<Candy, Integer> reqI = new HashMap<>();
        reqI.put(new Candy("Lollipop", "Jackfruit"), 9);
        reqI.put(new Candy("Lollipop", "Durian"), 9);
        reqI.put(new Candy("Lollipop", "Pandan"), 9);
        reqI.put(new Candy("Lollipop", "Ube"), 9);
        reqI.put(new Candy("Lollipop", "Macapuno"), 9);
        this.houses.add(new House('I', 1, 4, reqI, 450));

        // House J (Final Coords: 4, 1, XP: 350)
        Map<Candy, Integer> reqJ = new HashMap<>();
        reqJ.put(new Candy("Candy Corn", null), 5);
        reqJ.put(new Candy("Marshmallow", "Mango"), 6);
        reqJ.put(new Candy("Yema", "Mango"), 7);
        reqJ.put(new Candy("Pastillas", "Mango"), 8);
        reqJ.put(new Candy("Gummy", "Mango"), 9);
        this.houses.add(new House('J', 4, 1, reqJ, 350));

        // House K (Final Coords: 10, 1, XP: 400)
        Map<Candy, Integer> reqK = new HashMap<>();
        reqK.put(new Candy("Gummy", "Ube"), 10);
        reqK.put(new Candy("Gummy", "Mango"), 10);
        reqK.put(new Candy("Gummy", "Macapuno"), 10);
        reqK.put(new Candy("Gummy", "Pandan"), 10);
        this.houses.add(new House('K', 10, 1, reqK, 400));

        // House L (Final Coords: 13, 6, XP: 300)
        Map<Candy, Integer> reqL = new HashMap<>();
        reqL.put(new Candy("Pastillas", "Ube"), 10);
        reqL.put(new Candy("Yema", "Ube"), 10);
        reqL.put(new Candy("Polvoron", "Ube"), 10);
        this.houses.add(new House('L', 13, 6, reqL, 300));

        // House M (Final Coords: 13, 13, XP: 300)
        Map<Candy, Integer> reqM = new HashMap<>();
        reqM.put(new Candy("Pastillas", "Pandan"), 10);
        reqM.put(new Candy("Yema", "Pandan"), 10);
        reqM.put(new Candy("Polvoron", "Pandan"), 10);
        this.houses.add(new House('M', 13, 13, reqM, 300));

        // House N (Final Coords: 3, 14, XP: 250)
        Map<Candy, Integer> reqN = new HashMap<>();
        reqN.put(new Candy("Candy Cane", null), 16);
        reqN.put(new Candy("Polvoron", "Jackfruit"), 3);
        reqN.put(new Candy("Polvoron", "Durian"), 3);
        reqN.put(new Candy("Lollipop", "Tablea"), 3);
        this.houses.add(new House('N', 3, 14, reqN, 250));

        // House O (Final Coords: 14, 0, XP: 1000)
        Map<Candy, Integer> reqO = new HashMap<>();
        reqO.put(new Candy("Candy Cane", null), 100);
        this.houses.add(new House('O', 14, 0, reqO, 1000));

        // House P (Final Coords: 14, 10, XP: 250)
        Map<Candy, Integer> reqP = new HashMap<>();
        reqP.put(new Candy("Candy Corn", null), 5);
        reqP.put(new Candy("Candy Cane", null), 5);
        reqP.put(new Candy("Pastillas", "Macapuno"), 5);
        reqP.put(new Candy("Yema", "Macapuno"), 5);
        reqP.put(new Candy("Polvoron", "Macapuno"), 5);
        this.houses.add(new House('P', 14, 10, reqP, 250));
    }
}
