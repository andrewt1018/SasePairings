public class Mentee extends Person {
    String preferred_mentor1;
    String preferred_mentor2;
    String preferred_mentor3;
    public Mentee(String name, String major, String email, String discordID, String year, String preferred_mentor1) {
        super(name, major, email, discordID, year);
        this.preferred_mentor1 = preferred_mentor1;
    }

    public Mentee(String name, String major, String email, String discordID,
                  String year, String preferred_mentor1, String preferred_mentor2, String preferred_mentor3) {
        super(name, major, email, discordID, year);
        this.preferred_mentor1 = preferred_mentor1;
        this.preferred_mentor2 = preferred_mentor2;
        this.preferred_mentor3 = preferred_mentor3;
    }

    @Override
    public String toString() {
        String ret = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n", this.getName(),
                this.getMajor(),
                this.getEmail(),
                this.getDiscordID(),
                this.getYear(),
                this.preferred_mentor1,
                this.preferred_mentor2,
                this.preferred_mentor3);
        return ret;
    }

}
