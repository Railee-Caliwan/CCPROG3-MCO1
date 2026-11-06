import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Player player;
    private static WorldMap worldMap;

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("      WELCOME TO CANDY COURIER     ");
        System.out.println("===================================");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        player = new Player(name);
        worldMap = new WorldMap();

        boolean isRunning = true;

        while (isRunning && player.getStarRank() < 5) {
            System.out.println("\n===================================");
            System.out.println("Day: " + player.getDayNumber() + " | Rank: " + player.getStarRank() + "-Star");
            System.out.println("Location: Row " + player.getRow() + ", Col " + player.getCol());
            System.out.println("===================================");
            System.out.println("MAIN MENU:");
            System.out.println("1. Travel");
            System.out.println("2. Search Candies");
            System.out.println("3. View Orders");
            System.out.println("4. View Inventory");
            System.out.println("5. View Player Info");
            System.out.println("6. Quit Game");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    travelMenu();
                    break;
                case "2":
                    searchCandies();
                    break;
                case "3":
                    viewOrders();
                    break;
                case "4":
                    viewInventory();
                    break;
                case "5":
                    viewPlayerInfo();
                    break;
                case "6":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        // End Game Sequence
        System.out.println("\n===================================");
        if (player.getStarRank() >= 5) {
            System.out.println("CONGRATULATIONS! You have become a 5-Star Courier!");
        } else {
            System.out.println("Thank you for playing, " + player.getName() + "!");
        }
        System.out.println("Final Stats:");
        viewPlayerInfo();
        System.out.println("===================================");
    }

    private static void travelMenu() {
        boolean traveling = true;
        while (traveling) {
            System.out.println("\n--- TRAVEL MODE ---");
            System.out.println("Current Location: Row " + player.getRow() + ", Col " + player.getCol());
            System.out.println("Available Moves: [W] Up, [S] Down, [A] Left, [D] Right");
            if (player.canMoveDiagonally()) {
                System.out.println("Diagonal Moves: [Q] Up-Left, [E] Up-Right, [Z] Down-Left, [C] Down-Right");
            }
            System.out.println("[X] Exit Travel Mode");
            System.out.print("Move: ");
            
            String move = scanner.nextLine().toUpperCase();
            if (move.equals("X")) {
                traveling = false;
                continue;
            }

            int dRow = 0, dCol = 0;
            switch (move) {
                case "W": dRow = -1; break;
                case "S": dRow = 1; break;
                case "A": dCol = -1; break;
                case "D": dCol = 1; break;
                case "Q": if (player.canMoveDiagonally()) { dRow = -1; dCol = -1; } break;
                case "E": if (player.canMoveDiagonally()) { dRow = -1; dCol = 1; } break;
                case "Z": if (player.canMoveDiagonally()) { dRow = 1; dCol = -1; } break;
                case "C": if (player.canMoveDiagonally()) { dRow = 1; dCol = 1; } break;
                default:
                    System.out.println("Invalid move command.");
                    continue;
            }

            if (dRow == 0 && dCol == 0) {
                System.out.println("Diagonal movement is locked until 3-Star rank.");
                continue;
            }

            int newRow = player.getRow() + dRow;
            int newCol = player.getCol() + dCol;

            if (worldMap.isValidLocation(newRow, newCol, player.getStarRank())) {
                player.move(dRow, dCol);
                System.out.println("Successfully moved to Row " + player.getRow() + ", Col " + player.getCol() + ".");
                checkLocationEvents();
            } else {
                System.out.println("You cannot travel beyond the borders of the known world!");
            }
        }
    }

    private static void checkLocationEvents() {
        int r = player.getRow();
        int c = player.getCol();

        // 1. Check Player's Home
        if (r == 7 && c == 7) {
            System.out.print("You are at your Home. Do you want to call it a day? (Y/N): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
                player.advanceDay();
                worldMap.triggerRefresh(player.getStarRank());
                System.out.println("You rested. A new day begins! Factories have been refreshed.");
            }
            return;
        }

        // 2. Check Factory
        Factory factory = worldMap.getFactoryAt(r, c);
        if (factory != null) {
            System.out.print("You landed on Factory " + factory.getFactoryID() + ". Enter factory? (Y/N): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
                handleFactoryInteraction(factory);
            }
            return;
        }

        // 3. Check House
        House house = worldMap.getHouseAt(r, c);
        if (house != null && !house.isDelivered()) {
            System.out.println("You landed on House " + house.getHouseID() + ".");
            if (hasEnoughCandiesForOrder(house)) {
                System.out.print("You have enough candies to fulfill the order! Deliver now? (Y/N): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
                    deliverOrder(house);
                }
            } else {
                System.out.println("You do not have enough candy to make this delivery yet.");
            }
        }
    }

    private static void handleFactoryInteraction(Factory factory) {
        boolean inside = true;
        while (inside) {
            System.out.println("\n--- Inside Factory " + factory.getFactoryID() + " ---");
            System.out.println("1. Pick up candies");
            System.out.println("2. Exit Factory");
            System.out.print("Select option: ");
            String choice = scanner.nextLine();

            if (choice.equals("2")) {
                inside = false;
            } else if (choice.equals("1")) {
                Candy[][] grid = factory.getProductionGrid();
                System.out.println("\nCandy Production Grid:");
                for (int r = 0; r < grid.length; r++) {
                    for (int c = 0; c < grid[r].length; c++) {
                        if (grid[r][c] != null) {
                            String f = grid[r][c].getFlavor() == null ? "" : "(" + grid[r][c].getFlavor() + ")";
                            System.out.println("[" + r + "," + c + "] " + grid[r][c].getType() + " " + f);
                        }
                    }
                }
                
                System.out.print("Enter coordinates to pick up (e.g., '0,1 2,3') or 'C' to cancel: ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("C")) continue;

                String[] coords = input.split(" ");
                for (String coord : coords) {
                    try {
                        String[] parts = coord.split(",");
                        int pr = Integer.parseInt(parts[0]);
                        int pc = Integer.parseInt(parts[1]);
                        
                        if (player.getInventory().isFull()) {
                            System.out.println("You don't have enough space in your inventory to pick up more candies.");
                            break;
                        }

                        Candy picked = factory.pickupCandy(pr, pc);
                        if (picked != null) {
                            player.getInventory().addCandy(picked);
                            String flavor = picked.getFlavor() == null ? "" : "(" + picked.getFlavor() + ")";
                            System.out.println("You picked up: " + picked.getType() + " " + flavor);
                        } else {
                            System.out.println("Slot [" + pr + "," + pc + "] is empty or invalid.");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid format for coordinate: " + coord);
                    }
                }
            }
        }
    }

    private static boolean hasEnoughCandiesForOrder(House house) {
        for (Map.Entry<Candy, Integer> entry : house.getRequiredOrder().entrySet()) {
            if (player.getInventory().countMatching(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    private static void deliverOrder(House house) {
        for (Map.Entry<Candy, Integer> entry : house.getRequiredOrder().entrySet()) {
            player.getInventory().removeMatching(entry.getKey(), entry.getValue());
        }
        house.markDelivered();
        player.addXP(house.getXpReward());
        System.out.println("Successfully delivered order to House " + house.getHouseID() + "!");
        System.out.println("You earned " + house.getXpReward() + " XP!");
    }

    private static void searchCandies() {
        System.out.print("\nEnter candy type to search (e.g., 'Gummy', 'Candy Corn'): ");
        String targetType = scanner.nextLine().trim();
        System.out.println("Factories producing " + targetType + ":");
        boolean found = false;

        for (Factory f : worldMap.getFactories()) {
            // Only check factories within current bounds
            if (worldMap.isValidLocation(f.getRow(), f.getCol(), player.getStarRank())) {
                for (Candy c : f.getCandyPool()) {
                    if (c.getType().equalsIgnoreCase(targetType)) {
                        System.out.println("- Factory " + f.getFactoryID() + " (Row " + f.getRow() + ", Col " + f.getCol() + ")");
                        found = true;
                        break;
                    }
                }
            }
        }
        if (!found) System.out.println("None found in the accessible world.");
    }

    private static void viewOrders() {
        System.out.println("\n--- ACCESSIBLE ORDERS ---");
        boolean any = false;
        for (House h : worldMap.getHouses()) {
            if (!h.isDelivered() && worldMap.isValidLocation(h.getRow(), h.getCol(), player.getStarRank())) {
                System.out.println("House " + h.getHouseID() + " (Row " + h.getRow() + ", Col " + h.getCol() + ") | Reward: " + h.getXpReward() + " XP");
                for (Map.Entry<Candy, Integer> entry : h.getRequiredOrder().entrySet()) {
                    String flavor = entry.getKey().getFlavor() == null ? "" : "(" + entry.getKey().getFlavor() + ")";
                    System.out.println("  - " + entry.getValue() + "x " + entry.getKey().getType() + " " + flavor);
                }
                any = true;
            }
        }
        if (!any) System.out.println("No undelivered orders currently accessible.");
    }

    private static void viewInventory() {
        Inventory inv = player.getInventory();
        System.out.println("\n--- INVENTORY (" + inv.getSlots().size() + "/" + inv.getMaxCapacity() + ") ---");
        List<Candy> slots = inv.getSlots();
        for (int i = 0; i < slots.size(); i++) {
            Candy c = slots.get(i);
            String flavor = c.getFlavor() == null ? "" : "(" + c.getFlavor() + ")";
            System.out.println("[" + i + "] " + c.getType() + " " + flavor);
        }

        System.out.print("\nDo you want to discard candies? (Y/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.print("Enter indices to discard separated by spaces (e.g., '0 2 5'): ");
            String[] inputs = scanner.nextLine().split(" ");
            List<Integer> toDiscard = new ArrayList<>();
            for (String in : inputs) {
                try {
                    toDiscard.add(Integer.parseInt(in));
                } catch (NumberFormatException ignored) {}
            }
            inv.discardCandies(toDiscard);
            System.out.println("Candies discarded.");
        }
    }

    private static void viewPlayerInfo() {
        System.out.println("\n--- PLAYER INFO ---");
        System.out.println("Name: " + player.getName());
        System.out.println("Star Rank: " + player.getStarRank() + "-Star");
        System.out.println("Total XP: " + player.getTotalXP());
        if (player.getStarRank() < 5) {
            System.out.println("XP to Next Rank: " + player.getXpToNextRank());
        } else {
            System.out.println("XP to Next Rank: MAX RANK ACHIEVED");
        }
        System.out.println("Total Steps: " + player.getTotalSteps());
        System.out.println("Day Number: " + player.getDayNumber());
    }
}
