import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void addWorkout(Workout workout) throws SQLException;
    List<Workout> getWorkoutById(int id) throws SQLException;
    void updateWorkout(Workout workout) throws SQLException;
    String deleteWorkout(int id) throws SQLException;
    void viewProgress(int userId) throws SQLException;
    List<Challenge> showChallenges() throws SQLException;
    String joinChallenge(int userId, int challengeId) throws SQLException;
    void displayChallengeHistory(int userId) throws SQLException;
    List<String> viewReview(int User_id) throws SQLException;
}