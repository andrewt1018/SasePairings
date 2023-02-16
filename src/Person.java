public class Person {
    private String name; // Name of person
    private String major; // Major of the person
    private String email; // Email of the person
    private String discordID; //  Discord ID of the person
    private int year; // Grade/year level of the person. Freshman: 1, Sophomore: 2, etc.

    public Person() {
        this.name = null;
        this.major = null;
        this.email = null;
        this.discordID = null;
        this.year = 0;
    }
    public Person(String name, String major, String email, String discordID, int year) {
        this.name = name;
        this.major = major;
        this.email = email;
        this.discordID = discordID;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiscordID() {
        return discordID;
    }

    public void setDiscordID(String discordID) {
        this.discordID = discordID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}
