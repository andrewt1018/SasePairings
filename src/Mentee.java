public class Mentee extends Person {
    int preferredGroup1;
    int preferredGroup2;
    int preferredGroup3;
    boolean isCabinet;
    private int groupNum;

    public Mentee(String name, int preferredGroup1, int preferredGroup2, int preferredGroup3) {
        super(name);
        this.preferredGroup1 = preferredGroup1;
        this.preferredGroup2 = preferredGroup2;
        this.preferredGroup3 = preferredGroup3;
        this.groupNum = -1;
        this.isCabinet = false;
    }

    public Mentee(String name, int preferred_mentor1, int preferred_mentor2, int preferred_mentor3, String major) {
        super(name);
        this.preferredGroup1 = preferred_mentor1;
        this.preferredGroup2 = preferred_mentor2;
        this.preferredGroup3 = preferred_mentor3;
        this.setMajor(major);
        this.groupNum = -1;
    }

    public int getPreferredGroup1() {
        return preferredGroup1;
    }

    public void setPreferredGroup1(int preferredGroup1) {
        this.preferredGroup1 = preferredGroup1;
    }

    public int getPreferredGroup2() {
        return preferredGroup2;
    }

    public void setPreferredGroup2(int preferredGroup2) {
        this.preferredGroup2 = preferredGroup2;
    }

    public int getPreferredGroup3() {
        return preferredGroup3;
    }

    public void setPreferredGroup3(int preferredGroup3) {
        this.preferredGroup3 = preferredGroup3;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public boolean isCabinet() {
        return isCabinet;
    }

    public void setCabinet(boolean cabinet) {
        isCabinet = cabinet;
    }

    @Override
    public String toString() {
        String ret = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n", this.getName(),
                this.getMajor(),
                this.getEmail(),
                this.getDiscordID(),
                this.getYear(),
                this.preferredGroup1,
                this.preferredGroup2,
                this.preferredGroup3);
        return ret;
    }

}
