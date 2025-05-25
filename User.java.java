import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class User {
    public static void main(int[] is) {
        int userId = is[0];

        try (Connection connection = DatabaseConnection.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(connection);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nFitness Tracker Menu:");
                System.out.println("1. Add Workout");
                System.out.println("2. View All Workouts");
                System.out.println("3. Update Workout");
                System.out.println("4. Delete Workout");
                System.out.println("5. View Progress");
                System.out.println("6. Show Challenges");
                System.out.println("7. Join Challenges");
                System.out.println("8. Display Challenge History");
                System.out.println("9. Check For Reviews");
                System.out.println("10. Exit");
                System.out.print("Enter Your Choice: ");

                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 10.");
                    scanner.next();
                }
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        try {
                            System.out.print("Enter workout type: ");
                            String type = scanner.nextLine().trim();
                            if (type.isEmpty()) {
                                System.out.println("Workout type cannot be empty.");
                                break;
                            }

                            System.out.print("Enter duration in minutes: ");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Invalid input. Please enter a valid number for duration.");
                                scanner.next();
                            }
                            int duration = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("Enter calories burned: ");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Invalid input. Please enter a valid number for calories.");
                                scanner.next();
                            }
                            int calories = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("Enter date (YYYY-MM-DD): ");
                            Date date;
                            try {
                                date = Date.valueOf(scanner.nextLine().trim());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                                break;
                            }

                            Workout workout = new Workout(userId, type, duration, calories, date);
                            userDAO.addWorkout(workout);  
                            System.out.println("Workout added successfully.");
                        } catch (Exception e) {
                            System.out.println("Failed to add workout: " + e.getMessage());
                        }
                        break;

                    case 2:
                        try {
                            List<Workout> workouts = userDAO.getWorkoutById(userId);
                            if (workouts.isEmpty()) {
                                System.out.println("No workouts found.");
                            } else {
                                workouts.forEach(System.out::println);
                            }
                        } catch (Exception e) {
                            System.out.println("Failed to retrieve workouts: " + e.getMessage());
                        }
                        break;

                    case 3:
                        try {
                            System.out.print("Enter new workout type: ");
                            String newType = scanner.nextLine().trim();
                            System.out.print("Enter new duration: ");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Invalid input. Please enter a valid number for duration.");
                                scanner.next();
                            }
                            int newDuration = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("Enter new calories burned: ");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Invalid input. Please enter a valid number for calories.");
                                scanner.next();
                            }
                            int newCalories = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("Enter new date (YYYY-MM-DD): ");
                            Date newDate;
                            try {
                                newDate = Date.valueOf(scanner.nextLine().trim());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                                break;
                            }

                            Workout updatedWorkout = new Workout(userId, newType, newDuration, newCalories, newDate);
                            userDAO.updateWorkout(updatedWorkout);  
                            System.out.println("Workout updated successfully.");
                        } catch (Exception e) {
                            System.out.println("Failed to update workout: " + e.getMessage());
                        }
                        break;

                    case 4:
                        try {
                            System.out.print("Enter workout ID to delete: ");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Invalid input. Please enter a valid workout ID.");
                                scanner.next();
                            }
                            int deleteId = scanner.nextInt();
                            String result=userDAO.deleteWorkout(deleteId);  
                            System.out.println(result);
                        } catch (Exception e) {
                            System.out.println("Failed to delete workout: " + e.getMessage());
                        }
                        break;

                    case 5:
                        try {
                            userDAO.viewProgress(userId);  
                        } catch (Exception e) {
                            System.out.println("Failed to view progress: " + e.getMessage());
                        }
                        break;

                    case 6:
                        try {
                            List<Challenge> challenges = userDAO.showChallenges();
                            if (challenges.isEmpty()) {
                                System.out.println("No challenges found.");
                            } else {
                                challenges.forEach(System.out::println);
                            }
                        } catch (Exception e) {
                            System.out.println("Failed to show challenges: " + e.getMessage());
                        }
                        break;

                    case 7:
                        try {
                            System.out.print("Enter Challenge ID: ");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Invalid input. Please enter a valid Challenge ID.");
                                scanner.next();
                            }
                            int challengeId = scanner.nextInt();
                            userDAO.joinChallenge(userId, challengeId);  
                            System.out.println("Successfully joined the challenge.");
                        } catch (Exception e) {
                            System.out.println("Failed to join challenge: " + e.getMessage());
                        }
                        break;

                    case 8:
                        try {
                            userDAO.displayChallengeHistory(userId);  
                        } catch (Exception e) {
                            System.out.println("Failed to display challenge history: " + e.getMessage());
                        }
                        break;

                    case 9:
                        try {
                            List<String> feedbacks = userDAO.viewReview(userId);
                            if (feedbacks.isEmpty()) {
                                System.out.println("No reviews found.");
                            } else {
                                feedbacks.forEach(System.out::println);
                            }
                        } catch (Exception e) {
                            System.out.println("Failed to retrieve reviews: " + e.getMessage());
                        }
                        break;

                    case 10:
                        System.out.println("Exiting.");
                        return;

                    default:
                        System.out.println("Invalid choice. Please select a valid menu option.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}