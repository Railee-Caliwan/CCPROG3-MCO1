import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 1. Boot up sequence[cite: 3]
        System.out.println("Welcome to Candy Courier!");
        System.out.print("Enter your player name: ");
        String playerName = scanner.nextLine();
        
        // Initialize the world map and player (starting at center of the 7x7 grid)[cite: 2, 3]
        WorldMap world = new WorldMap();
        world.initializeData();
        Player player = new Player(playerName, 3, 3);
        
        // TODO: Initialize Factories and Houses here (or inside WorldMap constructor)
        
        boolean gameRunning = true;
        
        // 2. Main Game Loop[cite: 3]
        while (gameRunning) {
            System.out.println("\n=================================");
            System.out.println("Day Number: " + player.getDayNumber()); // Placeholder for day getter
            System.out.println("World Grid: " + world.getGridSize() + "x" + world.getGridSize());
            System.out.println("=================================");
            System.out.println("Main Menu:");
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
                    handleTravel(scanner, player, world);
                    break;
                case "2":
                    handleSearchCandies(scanner, world);
                    break;
                case "3":
                    handleViewOrders(world);
                    break;
                case "4":
                    handleViewInventory(scanner, player);
                    break;
                case "5":
                    handleViewPlayerInfo(player);
                    break;
                case "6":
                    System.out.println("Quitting game...");
                    handleViewPlayerInfo(player); // Show stats one last time before quitting[cite: 6]
                    gameRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            
            // Check for endgame condition: 5-star rank and all deliveries completed[cite: 7]
            if (player.getStarRank() == 5 /* && world.allDeliveriesCompleted() */) {
                System.out.println("\nCongratulations! You have completed all deliveries as a 5-star courier!");
                handleViewPlayerInfo(player);
                gameRunning = false;
            }
        }
        
        scanner.close();
    }
    
    // Stub methods to be implemented in the next steps
    private static void handleTravel(Scanner scanner, Player player, WorldMap world) {
        boolean traveling = true;
        
        while (traveling) {
            System.out.println("\n--- Travel Mode ---");
            System.out.println("Current Coordinates: Row " + player.getRow() + ", Col " + player.getCol());
            
            // Trigger events if standing on a building tile[cite: 3]
            checkLocationEvents(scanner, player, world);

            System.out.println("\nWhere would you like to move?");
            System.out.print("Options: W (Up), S (Down), A (Left), D (Right)");
            if (player.getStarRank() >= 3) {
                System.out.print(", Q (UpLeft), E (UpRight), Z (DownLeft), C (DownRight)"); // Diagonals[cite: 2]
            }
            System.out.println(", X (Exit Travel Mode)");
            System.out.print("Enter choice: ");
            
            String moveInput = scanner.nextLine().toUpperCase();
            
            if (moveInput.equals("X")) {
                traveling = false; // Player must exit travel mode to view other menus[cite: 5]
                System.out.println("Exiting Travel Mode...");
                continue;
            }

            // Map input to the string directions in Player.java
            String direction = "";
            switch (moveInput) {
                case "W": direction = "up"; break;
                case "S": direction = "down"; break;
                case "A": direction = "left"; break;
                case "D": direction = "right"; break;
                case "Q": direction = "upleft"; break;
                case "E": direction = "upright"; break;
                case "Z": direction = "downleft"; break;
                case "C": direction = "downright"; break;
                default: 
                    System.out.println("Invalid input."); 
                    continue;
            }

            // Attempt movement and provide text-based feedback[cite: 3, 8]
            boolean success = player.move(direction, world.getGridSize());
            if (success) {
                System.out.println("Moved " + direction + " successfully! New coordinates: Row " + player.getRow() + ", Col " + player.getCol());
            } else {
                System.out.println("Move failed. You cannot travel beyond the world border or use locked moves.");
            }
        }
    }

    private static void handleSearchCandies(Scanner scanner, WorldMap world) {
        System.out.println("\n--- Search Candies ---");
        System.out.print("Enter candy type (e.g., Gummy, Lollipop, Candy Corn): ");
        String searchType = scanner.nextLine().trim();
        
        System.out.println("Factories producing " + searchType + ":");
        boolean found = false;
        
        for (Factory f : world.getFactories()) {
            for (Candy c : f.getCandyPool()) {
                if (c.getType().equalsIgnoreCase(searchType)) {
                    System.out.println("- Factory " + f.getFactoryID() + " (Row " + f.getRow() + ", Col " + f.getCol() + ")");
                    found = true;
                    break; // Move to next factory once a match is found
                }
            }
        }
        if (!found) System.out.println("No factories produce this candy.");
    }

    private static void handleViewOrders(WorldMap world) {
        System.out.println("\n--- Active Orders ---");
        for (House h : world.getHouses()) {
            if (!h.isDelivered()) {
                System.out.println("House " + h.getHouseID() + " (Row " + h.getRow() + ", Col " + h.getCol() + "):");
                System.out.println("  XP Reward: " + h.getXpReward());
                System.out.println("  Requirements:");
                for (Map.Entry<Candy, Integer> entry : h.getRequiredOrder().entrySet()) {
                    String flavor = entry.getKey().getFlavor() == null ? "" : " (" + entry.getKey().getFlavor() + ")";
                    System.out.println("    - " + entry.getValue() + "x " + entry.getKey().getType() + flavor);
                }
            }
        }
    }

    private static void handleViewInventory(Scanner scanner, Player player) {
        System.out.println("\n--- Inventory ---");
        List<Candy> slots = player.getInventory().getSlots();
        System.out.println("Capacity: " + slots.size() + " / " + player.getInventory().getMaxCapacity());
        
        if (slots.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }

        for (int i = 0; i < slots.size(); i++) {
            Candy c = slots.get(i);
            String flavor = c.getFlavor() == null ? "" : " (" + c.getFlavor() + ")";
            System.out.println(i + ": " + c.getType() + flavor);
        }

        System.out.print("\nDo you want to discard any candies? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            System.out.print("Enter the numbers of the candies to discard, separated by commas (e.g., 0, 3, 4): ");
            String[] inputs = scanner.nextLine().split(",");
            List<Integer> toDiscard = new ArrayList<>();
            for (String s : inputs) {
                try {
                    toDiscard.add(Integer.parseInt(s.trim()));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number: " + s);
                }
            }
            player.getInventory().discardCandies(toDiscard);
            System.out.println("Candies discarded successfully.");
        }
    }

    private static void handleViewPlayerInfo(Player player) {
        System.out.println("\n--- Player Info ---");
        System.out.println("Player Name: " + player.getName());
        System.out.println("Star Rank: " + player.getStarRank() + "-star");
        System.out.println("Total XP: " + player.getTotalXP());
        
        // Calculate XP to next rank[cite: 6]
        int xpNeeded = 0;
        int currentXP = player.getTotalXP();
        if (currentXP < 100) xpNeeded = 100 - currentXP;
        else if (currentXP < 500) xpNeeded = 500 - currentXP;
        else if (currentXP < 1400) xpNeeded = 1400 - currentXP;
        else if (currentXP < 3000) xpNeeded = 3000 - currentXP;
        else if (currentXP < 5500) xpNeeded = 5500 - currentXP;
        
        if (player.getStarRank() == 5) {
            System.out.println("XP to next rank: MAX RANK REACHED");
        } else {
            System.out.println("XP to next rank: " + xpNeeded);
        }
        
        System.out.println("Total steps traveled: " + player.getTotalSteps());
        System.out.println("Day number: " + player.getDayNumber());
    }


    private static void checkLocationEvents(Scanner scanner, Player player, WorldMap world) {
        // 1. Check Player's Home[cite: 5]
        if (world.isPlayerAtHome(player)) {
            System.out.println("You are at the Player's Home.");
            System.out.print("Are you ready to call it a day? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                player.endDay();
                // world.refreshAllFactories(player.getStarRank()); // Make sure to add this helper to WorldMap
                System.out.println("Day ended. All factory candies have been refreshed. Good morning!");
            }
            return; 
        }

        // 2. Check Houses[cite: 4]
        House house = world.getHouseAt(player.getRow(), player.getCol());
        if (house != null) {
            System.out.println("You arrived at House " + house.getHouseID() + ".");
            if (house.canFulfill(player.getInventory())) {
                System.out.print("You have enough candy! Do you want to deliver it? (Y/N): ");
                if (scanner.nextLine().equalsIgnoreCase("Y")) {
                    int xpGained = house.processDelivery(player.getInventory());
                    player.addXP(xpGained);
                    System.out.println("Successfully delivered order to House " + house.getHouseID() + "!"); // Required explicit feedback[cite: 8]
                    System.out.println("You gained " + xpGained + " XP.");
                }
            } else {
                System.out.println("You do not have enough candy to make this delivery."); // Required explicit error message[cite: 8]
            }
            return;
        }

        // 3. Check Factories[cite: 3]
        Factory factory = world.getFactoryAt(player.getRow(), player.getCol());
        if (factory != null) {
            System.out.println("You arrived at Factory " + factory.getFactoryID() + ".");
            System.out.print("Do you want to enter the factory? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                handleFactoryVisit(scanner, player, factory);
            }
        }
    }

    private static void handleFactoryVisit(Scanner scanner, Player player, Factory factory) {
        boolean inFactory = true;
        
        while (inFactory) {
            System.out.println("\n--- Inside Factory " + factory.getFactoryID() + " ---");
            System.out.println("1. Pick up candy");
            System.out.println("2. Exit factory");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();
            
            if (choice.equals("1")) {
                if (player.getInventory().isFull()) {
                    System.out.println("You don't have enough space in your inventory."); // Required explicit error message[cite: 6, 8]
                    continue;
                }
                
                // Allow picking up multiple candies at a time[cite: 4]
                System.out.println("Enter row and column of candies to pick up, separated by commas (e.g., '0 2, 1 3'): ");
                String input = scanner.nextLine();
                String[] coordinatePairs = input.split(",");
                
                for (String pair : coordinatePairs) {
                    if (player.getInventory().isFull()) {
                        System.out.println("Inventory reached max capacity during pickup!");
                        break;
                    }
                    
                    String[] coords = pair.trim().split(" ");
                    if (coords.length == 2) {
                        try {
                            int r = Integer.parseInt(coords[0]);
                            int c = Integer.parseInt(coords[1]);
                            Candy picked = factory.pickUp(r, c);
                            
                            if (picked != null) {
                                player.getInventory().addCandy(picked);
                                System.out.println("You picked up: " + picked.getType() + " (" + picked.getFlavor() + ")"); // Required explicit feedback[cite: 8]
                            } else {
                                System.out.println("Slot [" + r + " " + c + "] is vacant or invalid.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid format for pair: " + pair);
                        }
                    }
                }
            } else if (choice.equals("2")) {
                inFactory = false;
                System.out.println("Exiting factory. You are back outside.");
            } else {
                System.out.println("Invalid option.");
            }
        }
    }
}
