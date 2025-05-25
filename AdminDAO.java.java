import java.sql.SQLException;
import java.util.List;

public interface AdminDAO {
	void addChallenge(Challenge challenge) throws SQLException;
    List<Workout> getAllWorkouts() throws SQLException;
	int createUser(String name, String email, String role) throws SQLException;
	String updateUser(int id, String name, String email, String role)  throws SQLException;
    String deleteUser(int id) throws SQLException;
    String userInteraction(int User_id, String Feedback) throws SQLException;
    String updateSetting(String settingName, String settingValue) throws SQLException;
}