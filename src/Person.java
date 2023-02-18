public class Person {
    private String name; // Name of person
    private String major; // Major of the person
    private String email; // Email of the person
    private String discordID; //  Discord ID of the person
    private String year; // Grade/year level of the person. Freshman: 1, Sophomore: 2, etc.

    public Person(String name, String major, String email, String discordID, String year) {
        this.name = name;
        this.major = major;
        this.email = email;
        this.discordID = discordID;
        this.year = year;
    }
    public Person(String name, String major, String email, String discordID) {
        this.name = name;
        this.major = major;
        this.email = email;
        this.discordID = discordID;
        this.year = null;
    }

    public Person(String name) {
        this.name = name;
        this.major = "";
        this.email = "";
        this.discordID = "";
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        String ret = String.format("%s,%s,%s,%s,%s\n", this.name,
                this.major,
                this.email,
                this.discordID,
                this.year);
        return ret;
    }
}
