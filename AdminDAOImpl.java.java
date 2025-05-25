import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {
	private final Connection connection;

    public AdminDAOImpl(Connection connection) {
        this.connection = connection;
    }
    // Add Challenge
    @Override
    public void addChallenge(Challenge challenge) throws SQLException{
    	String sql = "INSERT INTO Challenges (name, description) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, challenge.getName());
            stmt.setString(2, challenge.getDescription());
            stmt.executeUpdate();
        }
    }
    // Create User
    @Override
    public int createUser(String name, String email, String password) throws SQLException {
    	int userId=-1;
        String query = "INSERT INTO Users (name, email, password) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0 ){
            	try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        userId = rs.getInt(1);  // Get the generated user ID
                    }
            	}
            }
        } 
		return userId;
    }
    // Update User
    @Override
    public String updateUser(int id, String name, String email, String password) throws SQLException{
        String query = "UPDATE Users SET name = ?, email = ?, password = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setInt(4, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "User updated successfully." : "User update failed.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
    // Delete User
    @Override
    public String deleteUser(int id) throws SQLException {
        String query = "DELETE FROM Users WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "User deleted successfully." : "User deletion failed.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
    // Feedback To Users
    @Override
    public String userInteraction(int User_id, String Feedback) throws SQLException{
    	String query="INSERT INTO Feedback (User_id, Feedback) Values (?, ?)";
    	try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
               stmt.setInt(1, User_id);
               stmt.setString(2, Feedback);
               int rowsAffected = stmt.executeUpdate();
               return rowsAffected > 0 ? "Feedback Send successfully." : "Feedback failed to reach.";
           } catch (SQLException e) {
               return "Error: Can't Update to Non-Existing Users " ;
           }
    }

    // Fitness Content Management
    
    // View All Workout
    @Override
    public List<Workout> getAllWorkouts() throws SQLException {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM workouts";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Workout workout = new Workout(
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
        return workouts;
    }


    // System Settings
    @Override
    public String updateSetting(String settingName, String settingValue) {
        String query = "UPDATE SystemSettings SET setting_value = ? WHERE setting_name = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, settingValue);
            stmt.setString(2, settingName);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Setting updated successfully." : "Setting update failed.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}