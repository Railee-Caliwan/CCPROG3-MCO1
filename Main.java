import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 1. Boot up sequence[cite: 3]
        System.out.println("Welcome to Candy Courier!");
        System.out.print("Enter your player name: ");
        String playerName = scanner.nextLine();
        
        // Initialize the world map and player (starting at center of the 7x7 grid)[cite: 2, 3]
        WorldMap world = new WorldMap();
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
        System.out.println("Travel menu coming soon...");
    }

    private static void handleSearchCandies(Scanner scanner, WorldMap world) {
        System.out.println("Search Candies menu coming soon...");
    }

    private static void handleViewOrders(WorldMap world) {
        System.out.println("View Orders menu coming soon...");
    }

    private static void handleViewInventory(Scanner scanner, Player player) {
        System.out.println("View Inventory menu coming soon...");
    }

    private static void handleViewPlayerInfo(Player player) {
        System.out.println("View Player Info coming soon...");
    }
}
