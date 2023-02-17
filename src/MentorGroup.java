import java.util.ArrayList;

public class MentorGroup {
    ArrayList<Person> mentors;
    boolean isProfessional;
    public MentorGroup(ArrayList<Person> mentors) {
        this.mentors = mentors;
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < mentors.size(); i++) {
            ret += String.format("Mentor %d: %s\n", i, mentors.get(i).getName());
        }
        return ret;
    }
}
