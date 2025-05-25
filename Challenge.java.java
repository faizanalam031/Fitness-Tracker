public class Challenge {
	private int id;
    private String name;
    private String description;

    public Challenge(String name ,String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name=name; }
    public String getDescription() { return description; }
    public void setDiscription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Challenge{" +
                "id=" + id +
                ", name='" + name +
                ", description=" + description +
                '}';
    }
}