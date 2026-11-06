import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
    public List<Factory> getFactories() { return factories; }
    public List<House> getHouses() { return houses; }

    public void initializeData() {
        // ==========================================
        // FACTORY INITIALIZATION (Appendix C)
        // ==========================================
        
        // Factory 1 (Intro: 7x7, Coords: 0, 6)
        List<Candy> poolF1 = new ArrayList<>();
        poolF1.add(new Candy("Candy Corn", null));
        for (String f : new String[]{"Ube", "Mango", "Macapuno", "Pandan"}) 
            poolF1.add(new Candy("Lollipop", f));
        this.factories.add(new Factory(1, 0, 6, poolF1));

        // Factory 2 (Intro: 7x7, Coords: 6, 0)
        List<Candy> poolF2 = new ArrayList<>();
        poolF2.add(new Candy("Candy Corn", null));
        for (String f : new String[]{"Ube", "Mango", "Macapuno", "Pandan"}) {
            poolF2.add(new Candy("Gummy", f));
            poolF2.add(new Candy("Marshmallow", f));
        }
        this.factories.add(new Factory(2, 6, 0, poolF2));

        // Factory 3 (Intro: 9x9, Coords: 0, 0)
        List<Candy> poolF3 = new ArrayList<>();
        poolF3.add(new Candy("Candy Corn", null));
        for (String f : new String[]{"Ube", "Mango", "Macapuno", "Pandan"}) {
            poolF3.add(new Candy("Pastillas", f));
            poolF3.add(new Candy("Yema", f));
            poolF3.add(new Candy("Polvoron", f));
        }
        this.factories.add(new Factory(3, 0, 0, poolF3));

        // Factory 4 (Intro: 11x11, Coords: 10, 10)
        List<Candy> poolF4 = new ArrayList<>();
        for (String f : new String[]{"Tablea", "Melon"}) {
            poolF4.add(new Candy("Gummy", f));
            poolF4.add(new Candy("Lollipop", f));
            poolF4.add(new Candy("Marshmallow", f));
            poolF4.add(new Candy("Pastillas", f));
            poolF4.add(new Candy("Yema", f));
            poolF4.add(new Candy("Polvoron", f));
        }
        this.factories.add(new Factory(4, 10, 10, poolF4));

        // Factory 5 (Intro: 13x13, Coords: 0, 10)
        List<Candy> poolF5 = new ArrayList<>();
        for (String f : new String[]{"Jackfruit", "Durian", "Guyabano"}) {
            poolF5.add(new Candy("Gummy", f));
            poolF5.add(new Candy("Lollipop", f));
            poolF5.add(new Candy("Marshmallow", f));
        }
        this.factories.add(new Factory(5, 0, 10, poolF5));

        // Factory 6 (Intro: 13x13, Coords: 12, 6)
        List<Candy> poolF6 = new ArrayList<>();
        for (String f : new String[]{"Ube", "Mango", "Macapuno", "Pandan", "Tablea", "Melon", "Jackfruit", "Durian", "Guyabano"}) 
            poolF6.add(new Candy("Lollipop", f));
        this.factories.add(new Factory(6, 12, 6, poolF6));

        // Factory 7 (Intro: 13x13, Coords: 5, 12)
        List<Candy> poolF7 = new ArrayList<>();
        for (String f : new String[]{"Ube", "Mango", "Macapuno", "Pandan", "Tablea", "Melon", "Jackfruit", "Durian", "Guyabano"}) 
            poolF7.add(new Candy("Gummy", f));
        this.factories.add(new Factory(7, 5, 12, poolF7));

        // Factory 8 (Intro: 13x13, Coords: 6, 0)
        List<Candy> poolF8 = new ArrayList<>();
        for (String f : new String[]{"Ube", "Mango", "Macapuno", "Pandan", "Tablea", "Melon", "Jackfruit", "Durian", "Guyabano"}) 
            poolF8.add(new Candy("Marshmallow", f));
        this.factories.add(new Factory(8, 6, 0, poolF8));

        // Factory 9 (Intro: 15x15, Coords: 0, 0)
        List<Candy> poolF9 = new ArrayList<>();
        poolF9.add(new Candy("Candy Corn", null));
        poolF9.add(new Candy("Candy Cane", null));
        for (String f : new String[]{"Ube", "Mango", "Melon"}) {
            poolF9.add(new Candy("Pastillas", f));
            poolF9.add(new Candy("Yema", f));
            poolF9.add(new Candy("Polvoron", f));
        }
        this.factories.add(new Factory(9, 0, 0, poolF9));

        // Factory 10 (Intro: 15x15, Coords: 14, 2)
        List<Candy> poolF10 = new ArrayList<>();
        for (String f : new String[]{"Macapuno", "Pandan", "Jackfruit", "Durian", "Guyabano"}) {
            poolF10.add(new Candy("Pastillas", f));
            poolF10.add(new Candy("Yema", f));
            poolF10.add(new Candy("Polvoron", f));
        }
        this.factories.add(new Factory(10, 14, 2, poolF10));


        // ==========================================
        // HOUSE INITIALIZATION (Appendix D)
        // ==========================================

        // House A (Intro: 7x7, Coords: 1, 1, XP: 100)
        Map<Candy, Integer> reqA = new HashMap<>();
        reqA.put(new Candy("Candy Corn", null), 4);
        reqA.put(new Candy("Gummy", "Any"), 2);
        reqA.put(new Candy("Lollipop", "Any"), 2);
        reqA.put(new Candy("Marshmallow", "Any"), 2);
        this.houses.add(new House('A', 1, 1, reqA, 100));

        // House B (Intro: 7x7, Coords: 5, 2, XP: 150)
        Map<Candy, Integer> reqB = new HashMap<>();
        reqB.put(new Candy("Candy Corn", null), 15);
        this.houses.add(new House('B', 5, 2, reqB, 150));

        // House C (Intro: 7x7, Coords: 3, 6, XP: 250)
        Map<Candy, Integer> reqC = new HashMap<>();
        reqC.put(new Candy("Gummy", "Any"), 5);
        reqC.put(new Candy("Marshmallow", "Any"), 5);
        reqC.put(new Candy("Pastillas", "Any"), 5);
        reqC.put(new Candy("Yema", "Any"), 5);
        reqC.put(new Candy("Polvoron", "Any"), 5);
        this.houses.add(new House('C', 3, 6, reqC, 250));

        // House D (Intro: 9x9, Coords: 8, 6, XP: 400)
        Map<Candy, Integer> reqD = new HashMap<>();
        reqD.put(new Candy("Candy Corn", null), 5);
        reqD.put(new Candy("Candy Cane", null), 5);
        reqD.put(new Candy("Pastillas", "Any"), 10);
        reqD.put(new Candy("Yema", "Any"), 10);
        reqD.put(new Candy("Polvoron", "Any"), 10);
        this.houses.add(new House('D', 8, 6, reqD, 400));

        // House E (Intro: 11x11, Coords: 0, 6, XP: 300)
        Map<Candy, Integer> reqE = new HashMap<>();
        reqE.put(new Candy("Candy Corn", null), 10);
        reqE.put(new Candy("Yema", "Any"), 5);
        reqE.put(new Candy("Pastillas", "Any"), 5);
        reqE.put(new Candy("Lollipop", "Ube"), 5);
        reqE.put(new Candy("Lollipop", "Macapuno"), 5);
        this.houses.add(new House('E', 0, 6, reqE, 300));

        // House F (Intro: 11x11, Coords: 10, 1, XP: 300)
        Map<Candy, Integer> reqF = new HashMap<>();
        reqF.put(new Candy("Gummy", "Tablea"), 7);
        reqF.put(new Candy("Marshmallow", "Tablea"), 7);
        reqF.put(new Candy("Pastillas", "Tablea"), 8);
        reqF.put(new Candy("Polvoron", "Tablea"), 8);
        this.houses.add(new House('F', 10, 1, reqF, 300));

        // House G (Intro: 11x11, Coords: 4, 0, XP: 400)
        Map<Candy, Integer> reqG = new HashMap<>();
        reqG.put(new Candy("Pastillas", "Any"), 20);
        reqG.put(new Candy("Pastillas", "Durian"), 10);
        reqG.put(new Candy("Pastillas", "Jackfruit"), 10);
        this.houses.add(new House('G', 4, 0, reqG, 400));

        // House H (Intro: 11x11, Coords: 6, 10, XP: 300)
        Map<Candy, Integer> reqH = new HashMap<>();
        reqH.put(new Candy("Polvoron", "Any"), 10);
        reqH.put(new Candy("Yema", "Any"), 5);
        reqH.put(new Candy("Pastillas", "Any"), 5);
        reqH.put(new Candy("Gummy", "Mango"), 5);
        reqH.put(new Candy("Marshmallow", "Pandan"), 5);
        this.houses.add(new House('H', 6, 10, reqH, 300));

        // House I (Intro: 13x13, Coords: 0, 3, XP: 450)
        Map<Candy, Integer> reqI = new HashMap<>();
        reqI.put(new Candy("Lollipop", "Jackfruit"), 9);
        reqI.put(new Candy("Lollipop", "Durian"), 9);
        reqI.put(new Candy("Lollipop", "Pandan"), 9);
        reqI.put(new Candy("Lollipop", "Ube"), 9);
        reqI.put(new Candy("Lollipop", "Macapuno"), 9);
        this.houses.add(new House('I', 0, 3, reqI, 450));

        // House J (Intro: 13x13, Coords: 3, 0, XP: 350)
        Map<Candy, Integer> reqJ = new HashMap<>();
        reqJ.put(new Candy("Candy Corn", null), 5);
        reqJ.put(new Candy("Marshmallow", "Mango"), 6);
        reqJ.put(new Candy("Yema", "Mango"), 7);
        reqJ.put(new Candy("Pastillas", "Mango"), 8);
        reqJ.put(new Candy("Gummy", "Mango"), 9);
        this.houses.add(new House('J', 3, 0, reqJ, 350));

        // House K (Intro: 13x13, Coords: 9, 0, XP: 400)
        Map<Candy, Integer> reqK = new HashMap<>();
        reqK.put(new Candy("Gummy", "Ube"), 10);
        reqK.put(new Candy("Gummy", "Mango"), 10);
        reqK.put(new Candy("Gummy", "Macapuno"), 10);
        reqK.put(new Candy("Gummy", "Pandan"), 10);
        this.houses.add(new House('K', 9, 0, reqK, 400));

        // House L (Intro: 13x13, Coords: 12, 5, XP: 300)
        Map<Candy, Integer> reqL = new HashMap<>();
        reqL.put(new Candy("Pastillas", "Ube"), 10);
        reqL.put(new Candy("Yema", "Ube"), 10);
        reqL.put(new Candy("Polvoron", "Ube"), 10);
        this.houses.add(new House('L', 12, 5, reqL, 300));

        // House M (Intro: 13x13, Coords: 12, 12, XP: 300)
        Map<Candy, Integer> reqM = new HashMap<>();
        reqM.put(new Candy("Pastillas", "Pandan"), 10);
        reqM.put(new Candy("Yema", "Pandan"), 10);
        reqM.put(new Candy("Polvoron", "Pandan"), 10);
        this.houses.add(new House('M', 12, 12, reqM, 300));

        // House N (Intro: 15x15, Coords: 3, 14, XP: 250)
        Map<Candy, Integer> reqN = new HashMap<>();
        reqN.put(new Candy("Candy Cane", null), 16);
        reqN.put(new Candy("Polvoron", "Jackfruit"), 3);
        reqN.put(new Candy("Polvoron", "Durian"), 3);
        reqN.put(new Candy("Lollipop", "Tablea"), 3);
        this.houses.add(new House('N', 3, 14, reqN, 250));

        // House O (Intro: 15x15, Coords: 14, 0, XP: 1000)
        Map<Candy, Integer> reqO = new HashMap<>();
        reqO.put(new Candy("Candy Cane", null), 100);
        reqO.put(new Candy("Candy Corn", null), 10);
        this.houses.add(new House('O', 14, 0, reqO, 1000));

        // House P (Intro: 15x15, Coords: 14, 10, XP: 250)
        Map<Candy, Integer> reqP = new HashMap<>();
        reqP.put(new Candy("Candy Corn", null), 5);
        reqP.put(new Candy("Candy Cane", null), 5);
        reqP.put(new Candy("Pastillas", "Macapuno"), 5);
        reqP.put(new Candy("Yema", "Macapuno"), 5);
        reqP.put(new Candy("Polvoron", "Macapuno"), 5);
        this.houses.add(new House('P', 14, 10, reqP, 250));
    }
}
