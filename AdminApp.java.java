// Source code is decompiled from a .class file using FernFlower decompiler.
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FitnessApp {
   private static int loggedInUserId = -1;

   public FitnessApp() {
   }

   private static boolean validateUser(String name, String password) {
      boolean isValid = false;
      if (name != null && !name.isEmpty() && password != null && !password.isEmpty()) {
         try {
            Connection connection = DatabaseConnection.getConnection();

            try {
               String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
               PreparedStatement statement = connection.prepareStatement(sql);
               statement.setString(1, name);
               statement.setString(2, password);
               ResultSet resultSet = statement.executeQuery();
               if (resultSet.next()) {
                  isValid = true;
                  loggedInUserId = resultSet.getInt("id");
               } else {
                  System.out.println("Invalid username or password.");
               }

               resultSet.close();
               statement.close();
            } catch (SQLException var12) {
               System.out.println("Error executing query: " + var12.getMessage());
               var12.printStackTrace();
            } finally {
               connection.close();
            }
         } catch (SQLException var14) {
            System.out.println("Database connection error: " + var14.getMessage());
            var14.printStackTrace();
         } catch (Exception var15) {
            System.out.println("Unexpected error: " + var15.getMessage());
            var15.printStackTrace();
         }

         return isValid;
      } else {
         System.out.println("Username and password cannot be empty.");
         return false;
      }
   }

   private static boolean isValidEmail(String email) {
      String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
      Pattern pattern = Pattern.compile(emailRegex);
      return email != null && pattern.matcher(email).matches();
   }

   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);

      try {
         Connection connection = DatabaseConnection.getConnection();
         AdminDAO adminService = new AdminDAOImpl(connection);

         while(true) {
            label199:
            while(true) {
               System.out.println("Welcome to the Fitness Tracker App!");
               System.out.println("Press 1 for Admin functions");
               System.out.println("Press 2 for User functions");
               System.out.println("Press 3 for Exit");
               int choice = scanner.nextInt();
               scanner.nextLine();
               String name;
               if (choice == 1) {
                  String inputUsername = "";
                  name = "";

                  while(true) {
                     while(true) {
                        System.out.print("Enter your username: ");
                        inputUsername = scanner.nextLine();
                        System.out.print("Enter your password: ");
                        name = scanner.nextLine();
                        if (!inputUsername.isEmpty() && !name.isEmpty()) {
                           if (inputUsername.equals("Admin") && name.equals("Admin1234")) {
                              System.out.println("Login successful! Welcome!");

                              while(true) {
                                 System.out.println("\nAdmin Dashboard:");
                                 System.out.println("1. Create User");
                                 System.out.println("2. Update User");
                                 System.out.println("3. Delete User");
                                 System.out.println("4. Add Challenge");
                                 System.out.println("5. View Fitness Content");
                                 System.out.println("6. Add Reviews For User");
                                 System.out.println("7. Update System Setting");
                                 System.out.println("8. Exit");
                                 System.out.print("Choose an option: ");
                                 int adminChoice = scanner.nextInt();
                                 scanner.nextLine();
                                 if (adminChoice == 8) {
                                    System.out.println("Exiting...");
                                    continue label199;
                                 }

                                 System.out.println("You selected option " + adminChoice);
                              }
                           }

                           System.out.println("Invalid username or password. Please try again.");
                        } else {
                           System.out.println("Username and password cannot be empty.");
                        }
                     }
                  }
               } else if (choice != 2) {
                  if (choice == 3) {
                     System.out.println("Exiting....");
                     return;
                  }

                  System.out.println("Invalid choice. Please press 1 or 2.");
               } else {
                  System.out.println("Welcome to the Fitness Tracker App!");
                  System.out.println("Press 1 for Sign up");
                  System.out.println("Press 2 for Login");
                  int userChoice = scanner.nextInt();
                  scanner.nextLine();
                  switch (userChoice) {
                     case 1:
                        name = "";
                        String email = "";
                        String pass = "";

                        while(true) {
                           while(true) {
                              System.out.print("Enter name: ");
                              name = scanner.nextLine().trim();
                              if (name.isEmpty()) {
                                 System.out.println("Name cannot be empty.");
                              } else {
                                 System.out.print("Enter email: ");
                                 email = scanner.nextLine().trim();
                                 if (!isValidEmail(email)) {
                                    System.out.println("Invalid email format. Please enter a valid email.");
                                 } else {
                                    System.out.print("Enter password: ");
                                    pass = scanner.nextLine().trim();
                                    if (pass.isEmpty()) {
                                       System.out.println("Password cannot be empty.");
                                    } else {
                                       try {
                                          loggedInUserId = adminService.createUser(name, email, pass);
                                          if (loggedInUserId != -1) {
                                             System.out.println("Sign up successful! Welcome, " + name);
                                             new User();
                                             User.main(new int[]{loggedInUserId});
                                             continue label199;
                                          }

                                          System.out.println("User registration failed. Please try again.");
                                       } catch (SQLException var16) {
                                          System.out.println("Error creating user: " + var16.getMessage());
                                          var16.printStackTrace();
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     case 2:
                        String Username = "";
                        String Password = "";

                        while(true) {
                           while(true) {
                              System.out.print("Enter your username: ");
                              Username = scanner.nextLine().trim();
                              System.out.print("Enter your password: ");
                              Password = scanner.nextLine().trim();
                              if (!Username.isEmpty() && !Password.isEmpty()) {
                                 if (validateUser(Username, Password)) {
                                    System.out.println("Login successful! Welcome!");
                                    new User();
                                    User.main(new int[]{loggedInUserId});
                                    continue label199;
                                 }

                                 System.out.println("Invalid username or password. Please try again.");
                              } else {
                                 System.out.println("Username and password cannot be empty.");
                              }
                           }
                        }
                     default:
                        System.out.println("Invalid choice. Please press 1 or 2.");
                  }
               }
            }
         }
      } catch (SQLException var17) {
         System.out.println("Database connection error: " + var17.getMessage());
         var17.printStackTrace();
      } finally {
         scanner.close();
      }

   }
}
