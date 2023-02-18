import java.util.ArrayList;

public class MentorGroup {
    private ArrayList<Person> mentors;
    ArrayList<Mentee> mentees;
    boolean isProfessional;
    public MentorGroup(ArrayList<Person> mentors) {
        this.mentors = mentors;
        this.mentees = new ArrayList<>();
    }

    public ArrayList<Person> getMentors() {
        return mentors;
    }

    public void setMentors(ArrayList<Person> mentors) {
        this.mentors = mentors;
    }

    public ArrayList<Mentee> getMentees() {
        return mentees;
    }

    public void setMentees(ArrayList<Mentee> mentees) {
        this.mentees = mentees;
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < mentors.size(); i++) {
            ret += String.format("Mentor %d: %s\n", i, mentors.get(i).getName());
        }
        return ret;
    }

    public boolean isProfessional() {
        return isProfessional;
    }

    public void setProfessional(boolean professional) {
        isProfessional = professional;
    }
}

