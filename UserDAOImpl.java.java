import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }
    // Add Workout
    @Override
    public void addWorkout(Workout workout) throws SQLException {
        String sql = "INSERT INTO workouts (user_id, workout_type, duration, calories_burned, date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        	stmt.setInt(1, workout.getUser_id());
            stmt.setString(2, workout.getType());
            stmt.setInt(3, workout.getDuration());
            stmt.setInt(4, workout.getCaloriesBurned());
            stmt.setDate(5, workout.getDate());
            stmt.executeUpdate();
        }
    }

    // Get Workout by ID
    @Override
    public List <Workout> getWorkoutById(int user_id) throws SQLException {
    	List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM workouts WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Workout workout= new Workout(
                    	rs.getInt("user_id"),
                        rs.getString("workout_type"),
                        rs.getInt("duration"),
                        rs.getInt("calories_burned"),
                        rs.getDate("date")
                    ); 
                    workout.setId(rs.getInt("id"));
                    workouts.add(workout);
                }
            }
        }
        return workouts;
    }
    // Update Workout
    @Override
    public void updateWorkout(Workout workout) throws SQLException {
        String sql = "UPDATE workouts SET user_id= ?, workout_type = ?, duration = ?, calories_burned = ?, date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        	stmt.setInt(1, workout.getUser_id());
            stmt.setString(2, workout.getType());
            stmt.setInt(3, workout.getDuration());
            stmt.setInt(4, workout.getCaloriesBurned());
            stmt.setDate(5, workout.getDate());
            stmt.setInt(6, workout.getId());
            stmt.executeUpdate();
        }
    }
    // Delete Workout
    @Override
    public String deleteWorkout(int id) throws SQLException {
        String sql = "DELETE FROM workouts WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "User deleted successfully." : "User deletion failed.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
    // View Progress
    @Override
    public void viewProgress(int userId) {
        String query = "SELECT workout_type, SUM(duration) as total_duration FROM Workouts WHERE user_id = ? GROUP BY workout_type";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Progress Tracking:");
            while (rs.next()) {
                System.out.println("Workout Type: " + rs.getString("workout_type") +
                                   ", Total Duration: " + rs.getInt("total_duration") + " minutes");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // Show Challenges
    @Override
    public List<Challenge> showChallenges() throws SQLException {
        List<Challenge> challenges = new ArrayList<>();
        String sql = "SELECT * FROM Challenges";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Challenge challenge = new Challenge(
                    rs.getString("name"),
                    rs.getString("description")
                );
                challenge.setId(rs.getInt("id"));
                challenges.add(challenge);
            }
        }
        return challenges;
    }
    // Join Challenge
    @Override
    public String joinChallenge(int userId, int challengeId) {
        String query = "INSERT INTO UserChallenges (user_id, challenge_id) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, challengeId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Successfully joined the challenge." : "Failed to join challenge.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
    // Show Participated/Finished Challenges
    @Override
    public void displayChallengeHistory(int userId) {
        String query = "SELECT c.name, uc.joined_at FROM UserChallenges uc INNER JOIN Challenges c ON uc.challenge_id = c.id WHERE uc.user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Challenge History:");
            while (rs.next()) {
                System.out.println("Challenge: " + rs.getString("name") +
                                   ", Joined At: " + rs.getTimestamp("joined_at"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // View Your Review
    @Override 
    public List<String> viewReview(int User_id) throws SQLException{
    	List<String> feedbacks = new ArrayList<>();
        String sql = "SELECT * FROM Feedback WHERE User_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, User_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	String feedback=rs.getString("Feedback");
                	feedbacks.add(feedback);
                }
            }
        }
        return feedbacks;
    }
}