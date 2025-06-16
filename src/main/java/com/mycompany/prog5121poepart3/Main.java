package com.mycompany.prog5121poepart3;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (scanner) {
            MessageManager manager = new MessageManager();
            boolean running = true;

            populateInitialMessages(manager);

            while (running) {
                displayMenu();
                try {
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1 -> viewSentMessagesReport(manager);
                        case 2 -> viewLongestMessage(manager);
                        case 3 -> searchByMessageID(manager);
                        case 4 -> searchByRecipient(manager);
                        case 5 -> deleteMessageByHash(manager);
                        case 6 -> addNewMessage(manager);
                        case 7 -> {
                            running = false;
                            System.out.println("Exiting program...");
                        }
                        default -> System.out.println("Invalid option. Please choose 1-7.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }

                if (running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
            }
        }
    }

    private static void populateInitialMessages(MessageManager manager) {
        manager.populateMessage("Did you get the cake?", "sent", "+27834557896",
                "01:1:DIDCAKE", "0838880001", LocalDateTime.now());
        manager.populateMessage("Where are you? You are late! I have asked you to be on time.",
                "stored", "+27838884567", "02:2:WHERETIME", "0838880002", LocalDateTime.now());
        manager.populateMessage("Yohoooo, I am at your gate.", "disregard",
                "+27834484567", "03:3:YOHOOGATE", "0838880003", LocalDateTime.now());
        manager.populateMessage("It is dinner time!", "sent", "0838884567",
                "04:4:ITTIME", "0838884567", LocalDateTime.now());
        manager.populateMessage("Ok, I am leaving without you.", "stored",
                "+27838884567", "05:5:OKYOU", "0838880005", LocalDateTime.now());
    }

    private static void displayMenu() {
        System.out.println("\n=== Message Management System ===");
        System.out.println("1. View Sent Messages Report");
        System.out.println("2. View Longest Sent Message");
        System.out.println("3. Search by Message ID");
        System.out.println("4. Search by Recipient");
        System.out.println("5. Delete Message by Hash");
        System.out.println("6. Add New Message");
        System.out.println("7. Exit");
        System.out.print("Enter your choice (1-7): ");
    }

    private static void viewSentMessagesReport(MessageManager manager) {
        System.out.println("\n--- SENT MESSAGES REPORT ---");
        String report = manager.generateReport();
        System.out.println(report.isEmpty() ? "No sent messages found." : report);
    }

    private static void viewLongestMessage(MessageManager manager) {
        System.out.println("\n--- Longest Sent Message ---");
        String longest = manager.displayLongestMessage();
        System.out.println(longest.equals("No sent messages found.") ? longest : "Message: " + longest);
    }

    private static void searchByMessageID(MessageManager manager) {
        System.out.print("\nEnter Message ID to search: ");
        String messageId = scanner.nextLine();
        String result = manager.searchByMessageID(messageId);
        System.out.println(result);
    }

    private static void searchByRecipient(MessageManager manager) {
        System.out.print("\nEnter Recipient number to search: ");
        String recipient = scanner.nextLine();
        List<String> results = manager.searchByRecipient(recipient);

        System.out.println("\nMessages to " + recipient + ":");
        if (results.isEmpty()) {
            System.out.println("No messages found for recipient: " + recipient);
        } else {
            results.forEach(msg -> {
                System.out.println("---------------------------");
                System.out.println(msg);
            });
        }
    }

    private static void deleteMessageByHash(MessageManager manager) {
        System.out.print("\nEnter message hash to delete: ");
        String hash = scanner.nextLine();
        boolean deleted = manager.deleteByHash(hash);
        System.out.println(deleted ? "Message successfully deleted." : "Message hash not found.");
    }

    private static void addNewMessage(MessageManager manager) {
        System.out.println("\n--- Add New Message ---");
        System.out.print("Enter message content: ");
        String content = scanner.nextLine();

        System.out.print("Enter status (sent / stored / disregard): ");
        String status = scanner.nextLine().toLowerCase();

        if (!status.equals("sent") && !status.equals("stored") && !status.equals("disregard")) {
            System.out.println("Invalid status. Must be 'sent', 'stored', or 'disregard'.");
            return;
        }

        System.out.print("Enter recipient number: ");
        String recipient = scanner.nextLine();

        System.out.print("Enter message hash: ");
        String hash = scanner.nextLine();

        System.out.print("Enter message ID: ");
        String messageId = scanner.nextLine();

        System.out.print("Enter sender number: ");
        String sender = scanner.nextLine();

        
    }
}
