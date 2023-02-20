import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    // File name containing all the applicants
    private static final String IN_FILE = "applicants.txt";

    // File name containing the desired file to output results
    private static final String OUT_FILE = "results.csv";

    // File name containing all of the mentors' data
    private static final String MENTOR_FILE = "mentors.txt";

    // ArrayList containing all of the names of every cabinet member
    private static final ArrayList<String> CABINET = new ArrayList<>(
            List.of("Andrew Tan", "Katrina Leon", "Elisa Chen",
                    "David Fu", "Aryamaan Dhomne", "Leo Ye", "Tyler Kim",
                    "Mei-Ching Huang", "Tiana Lin"));
    private static int numOfMentees;
    private static int numOfGroups = 7;
    // ArrayList to store every single mentee

    private static ArrayList<Mentee> mentees = new ArrayList<>();

    // ArrayList to store every single mentee
    private static ArrayList<Person> mentors = new ArrayList<>();

    // ArrayList to store all mentor groups
    private static ArrayList<MentorGroup> mentorGroups = new ArrayList<>();

    // ArrayList to store the best mentor group pairings
    private static ArrayList<MentorGroup> bestGroups = new ArrayList<>();

    // ArrayList to store all of the mentees whose information are not complete
    private static ArrayList<String> err_mentees = new ArrayList<>();

    // ArrayList to store all of the mentees whose information are not complete
    private static ArrayList<String> err_mentors = new ArrayList<>();

    // Total points from each mentee based off of the pairing up results
    private static int totalPoints = 0;

    /**
     * 2D array to generate all of the rankings for the mentees.
     * 1st preference: 5 points
     * 2nd preference: 3 points
     * 3rd preference: 1 point
     * Same major as one of the mentors: 2 point
     * Same goals (professional vs. social): 1 point
     *
     * Row 0: group 1, Row 1: group 2 etc
     * Column 0: mentee idNum 0, Column 1: mentee idNum 1
     */
    private final static int PREFERENCE1 = 5;
    private final static int PREFERENCE2 = 3;
    private final static int PREFERENCE3 = 1;
    private final static int SAME_MAJOR = 2;
    private static int[][] preferences;

    public static void main(String[] args) {
        // Create mentees
        readMentees(IN_FILE);

        // Create the mentors
        readMentors(MENTOR_FILE);

        // Create the mentor groups
        createMentorGroups();
        System.out.printf("Number of mentor groups: %d\n", numOfGroups);

        System.out.printf("Number of mentees: %d\n", numOfMentees);
        // Main algorithm for creating preferences
        preferences = generatePreferences();

        // Pairing up mentees with mentor groups.
        // Run for 1000 times. Find the best pairing results.
        int iterations = 1000;
        for (int count = 0; count < iterations; count++) {
            for (int j = 0; j < numOfGroups; j++) {
                mentorGroups.get(j).mentees.clear();
            }
            int points = pairUp();
            if (totalPoints <= points) {
                bestGroups = mentorGroups;
                totalPoints = points;
            }
        }

        // Updating the mentees ArrayList to include the updated group assignments
        int counter = 0;
        for (int i = 0; i < numOfGroups; i++) {
            for (Mentee m :  mentorGroups.get(i).mentees) {
                mentees.set(counter, m);
                mentees.get(counter).setGroupNum(i + 1);
                counter++;
            }
        }

        // Print out the pairing results
        pairingResults();

        // Outputting results to file if and only if total score is higher than 106
        if (totalPoints > 106) outputPairings(OUT_FILE);
    }

    /**
     * This function outputs the best pairings into the specified output file as indicated in outFile
     * @param outFile
     */
    public static void outputPairings(String outFile) {
        File f = new File(outFile);
        try {
            f.createNewFile();
            FileWriter fw = new FileWriter(f, true);
            for (int i = 0; i < numOfGroups; i++) {
                fw.append(String.format("Group %d,", i + 1));
                for (Mentee m : bestGroups.get(i).mentees) {
                    int pref = 0;
                    if (m.preferredGroup1 == i + 1) pref = 1;
                    else if (m.preferredGroup2 == i + 1) pref = 2;
                    else if (m.preferredGroup3 == i + 1) pref = 3;
                    fw.append(String.format("%s%d", m.getName(), pref));
                    if (bestGroups.get(i).mentees.indexOf(m) == bestGroups.get(i).mentees.size() - 1) {
                        fw.append("\n");
                    } else {
                        fw.append(",");
                    }
                }
            }
            fw.append(String.format("Total score: %d\n", totalPoints));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function outputs all of the pairing results and the stats in the command line
     */
    public static void pairingResults() {
        for (int i = 0; i < numOfGroups; i++) {
            System.out.printf("Group %d has %d members: ", i + 1, bestGroups.get(i).mentees.size());
            for (int j = 0; j < bestGroups.get(i).mentees.size(); j++) {
                System.out.printf("%s", bestGroups.get(i).mentees.get(j).getName());
                if (j < bestGroups.get(i).mentees.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        System.out.println("Total points of pairing: " + totalPoints);

        int firstCount = 0;
        int secondCount = 0;
        int thirdCount = 0;
        int noneCount = 0;
        // Print out the members that did not get their preferences.
        for (Mentee m : mentees) {
            if (m.getGroupNum() == m.getPreferredGroup1()) {
//                System.out.printf("%s got their most desired group.\n", m.getName());
                firstCount++;
            } else if (m.getGroupNum() == m.getPreferredGroup2()) {
//                System.out.printf("%s got their second most desired group.\n", m.getName());
                secondCount++;
            } else if (m.getGroupNum() == m.getPreferredGroup3()) {
//                System.out.printf("%s got their third most desired group.\n", m.getName());
                thirdCount++;
            } else {
                System.out.printf("%s did not get any of their desired groups.\n", m.getName());
                noneCount++;
            }
        }
        System.out.printf("%d mentees got their most desired group.\n", firstCount);
        System.out.printf("%d mentees got their second most desired group.\n", secondCount);
        System.out.printf("%d mentees got their third most desired group.\n", thirdCount);
        System.out.printf("%d mentees did not get their desired group.\n", noneCount);
    }

    /**
     * This function is the main method to pair up mentor groups with mentees. Based on the preferences
     * assigned by the generatePreferences() function, each mentee is assigned into (hopefully) a desired
     * mentor group based on their preferences. General members are sorted first, and cabinet members
     * are assigned afterwards.
     */
    public static int pairUp() {
        randomizeCol(preferences);

        int maxPerGroup = numOfMentees / numOfGroups;
        int points = 0;

        // Pair up each non-cabinet member
        for (int i = 0; i < numOfMentees; i++) {
            if (mentees.get(i).isCabinet) continue;

            int ideal = -1;
            int maxPoints = -1;
            for (int j = 0; j < numOfGroups; j++) {
                if (preferences[j][i] >= maxPoints && mentorGroups.get(j).mentees.size() < maxPerGroup) {
                    ideal = j;
                    maxPoints = preferences[j][i];
                }
            }
            if (ideal == -1) {
                // If no available group, check if their preferred groups can be somehow stretched
                if (mentorGroups.get(mentees.get(i).preferredGroup1 - 1).mentees.size() < maxPerGroup + 2) {
                    ideal = mentees.get(i).preferredGroup1 - 1;
                } else if (mentorGroups.get(mentees.get(i).preferredGroup2 - 1).mentees.size() < maxPerGroup + 2) {
                    ideal = mentees.get(i).preferredGroup2 - 1;
                } else if (mentorGroups.get(mentees.get(i).preferredGroup3 - 1).mentees.size() < maxPerGroup + 2) {
                    ideal = mentees.get(i).preferredGroup3 - 1;
                } else {
                    ArrayList<Integer> randGroupNum = new ArrayList<>();
                    for (int k = 0; k < numOfGroups; k++) {
                        if (mentorGroups.get(k).mentees.size() <= maxPerGroup) {
                            randGroupNum.add(k);
                        }
                    }
                    Random rand = new Random();
                    ideal = randGroupNum.get(rand.nextInt(randGroupNum.size()));
                }
            }
            points += preferences[ideal][i];
            mentees.get(i).setGroupNum(ideal + 1);
            mentorGroups.get(ideal).mentees.add(mentees.get(i));
        }

        // Pair up each cabinet member
        for (int i = 0; i < numOfMentees; i++) {
            if (!mentees.get(i).isCabinet) continue;

            int ideal = -1;
            int maxPoints = -1;
            for (int j = 0; j < numOfGroups; j++) {
                if (preferences[j][i] >= maxPoints && mentorGroups.get(j).mentees.size() < maxPerGroup) {
                    ideal = j;
                    maxPoints = preferences[j][i];
                }
            }
            if (ideal == -1) {
                // If no available group, check if their preferred groups can be somehow stretched
                if (mentorGroups.get(mentees.get(i).preferredGroup1 - 1).mentees.size() < maxPerGroup + 2) {
                    ideal = mentees.get(i).preferredGroup1 - 1;
                } else if (mentorGroups.get(mentees.get(i).preferredGroup2 - 1).mentees.size() < maxPerGroup + 2) {
                    ideal = mentees.get(i).preferredGroup2 - 1;
                } else if (mentorGroups.get(mentees.get(i).preferredGroup3 - 1).mentees.size() < maxPerGroup + 2) {
                    ideal = mentees.get(i).preferredGroup3 - 1;
                } else {
                    ArrayList<Integer> randGroupNum = new ArrayList<>();
                    for (int k = 0; k < numOfGroups; k++) {
                        if (mentorGroups.get(k).mentees.size() <= maxPerGroup) {
                            randGroupNum.add(k);
                        }
                    }
                    Random rand = new Random();
                    ideal = randGroupNum.get(rand.nextInt(randGroupNum.size()));
                }
            }
            points += preferences[ideal][i];
            mentees.get(i).setGroupNum(ideal + 1);
            mentorGroups.get(ideal).mentees.add(mentees.get(i));
        }
        return points;
    }

    /**
     * This function generates the preferences each mentee has for each mentor group. The preferences
     * are stored in a 2D int array called preferences with the rows being the mentor groups
     * (0 indexed in the function but 1 indexed irl), and the columns being each mentee
     */
    public static int[][] generatePreferences() {
        int[][] ret = new int[numOfGroups][numOfMentees];
        for (int row = 0; row < numOfGroups; row++) {
            for (int col = 0; col < numOfMentees; col++) {
                int points = 0;
                Mentee current = mentees.get(col);
                // Preferences based on preferred groups
                if (current.preferredGroup1 == row + 1) {
                    points += PREFERENCE1;
                } else if (current.preferredGroup2 == row + 1) {
                    points += PREFERENCE2;
                } else if (current.preferredGroup3 == row + 1) {
                    points += PREFERENCE3;
                }

                // Updating preferences based on major
                String major = mentees.get(col).getMajor();
                if (mentorGroups.get(row).getMentors().stream()
                        .filter(mentor -> mentor.getMajor().equals(major))
                        .collect(Collectors.toCollection(ArrayList::new))
                        .size() != 0) {
                    points += SAME_MAJOR;
                }
                ret[row][col] = points;
            }
        }
        return ret;
    }

    /**
     * This function creates all of the mentor groups based on the user's choice.
     */
    public static void createMentorGroups() {
        ArrayList<Person> group1, group2, group3, group4, group5, group6, group7;
        group1 = mentors.stream().filter(n -> (n.getName().equals("Andre Tan") ||
                                             (n.getName().equals("Kerway Tsai")) ||
                                             (n.getName().equals("Shivam Duhan"))))
                                 .collect(Collectors.toCollection(ArrayList::new));

        group2 = mentors.stream().filter(n -> (n.getName().equals("Kyle Yang") ||
                                             (n.getName().equals("Claire Huang")) ||
                                             (n.getName().equals("Matthew Kumnoonsate"))))
                                 .collect(Collectors.toCollection(ArrayList::new));

        group3 = mentors.stream().filter(n -> (n.getName().equals("Duclan Ngo") ||
                                             (n.getName().equals("Alia Rumreich")) ||
                                             (n.getName().equals("Jackie Fung He"))))
                                 .collect(Collectors.toCollection(ArrayList::new));

        group4 = mentors.stream().filter(n -> (n.getName().equals("Alex Zhuo") ||
                                             (n.getName().equals("Janine Lee")) ||
                                             (n.getName().equals("Ethan Chu"))))
                                 .collect(Collectors.toCollection(ArrayList::new));

        group5 = mentors.stream().filter(n -> (n.getName().equals("Dillon Kim") ||
                                             (n.getName().equals("Emily Yeh")) ||
                                             (n.getName().equals("Winston Wang"))))
                                 .collect(Collectors.toCollection(ArrayList::new));

        group6 = mentors.stream().filter(n -> (n.getName().equals("Caroline Wang") ||
                                             (n.getName().equals("Abby Zahm")) ||
                                             (n.getName().equals("Janathan Balakrishnan"))))
                                 .collect(Collectors.toCollection(ArrayList::new));

        group7 = mentors.stream().filter(n -> (n.getName().equals("Yong Sung Kim") ||
                                             (n.getName().equals("Jessica Wong")) ||
                                             (n.getName().equals("Michael Bi"))))
                                 .collect(Collectors.toCollection(ArrayList::new));

        // Add all of the mentor groups to the list
        mentorGroups.add(new MentorGroup(group1));
        mentorGroups.add(new MentorGroup(group2));
        mentorGroups.add(new MentorGroup(group3));
        mentorGroups.add(new MentorGroup(group4));
        mentorGroups.add(new MentorGroup(group5));
        mentorGroups.add(new MentorGroup(group6));
        mentorGroups.add(new MentorGroup(group7));

        ArrayList<Person> unassignedMentors = new ArrayList<>();
        for (Person mentor : mentors) {
            unassignedMentors.add(mentor);
        }
        for (MentorGroup group : mentorGroups) {
            for (Person mentor: mentors) {
                if (group.getMentors().contains(mentor)) {
                    unassignedMentors.remove(mentor);
                }
            }
        }

        for (Person unassigned : unassignedMentors) {
            System.out.printf("%s has yet to be assigned to a mentor group!\n", unassigned.getName());
        }
    }

    /**
     * This function reads in all of the mentors from the specified mentors file stored in mentorFile.
     * @param in_file
     */
    public static void readMentors(String in_file) {
        // Retrieving all of the mentors' data from a file
        // Expected file layout: name,major,email,discordID
        File f = new File(in_file);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            int counter = 0;
            while (line != null) {
                String lineArr[] = line.split(",");
                if (lineArr.length != 4) {
                    err_mentors.add(line);
                    line = bfr.readLine();
                    continue;
                }
                String name = lineArr[0];
                String major = lineArr[1];
                String email = lineArr[2];
                String discordID = lineArr[3];
                mentors.add(new Person(name, major, email, discordID));
                counter++;
                line = bfr.readLine();
            }
            fr.close();
            bfr.close();
            System.out.printf("Successfully read in %d mentors.\n", counter);
        } catch (IOException e) {
            System.out.println("Reading mentors unsuccessful.");
            e.printStackTrace();
        }
    }

    /**
     * This function takes in an input file containing all of the mentees' data. It then stores all of
     * this data in the mentees ArrayList.
     * @param in_file
     */
    public static void readMentees(String in_file) {
        // Retrieving all of the mentees' data from a file
        // Expected file layout: name,major,email,discordID,year,preferred_mentor1,preferred_mentor2,preferred_mentor3,isProfessional
        File f = new File(in_file);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            int idNumber = 0;
            String line = bfr.readLine();
            while (line != null) {
                String lineArr[] = line.split(",");
                if (lineArr.length != 4) {
                    err_mentees.add(line);
                    line = bfr.readLine();
                    continue;
                }
                String name = lineArr[0].replace("\uFEFF","");
                int mentor1 = Integer.parseInt(lineArr[1]);
                int mentor2 = Integer.parseInt(lineArr[2]);
                int mentor3 = Integer.parseInt(lineArr[3]);
                Mentee current = new Mentee(name, mentor1, mentor2, mentor3, idNumber);
                current.setCabinet(CABINET.stream().anyMatch(name::contains));
                mentees.add(current);
                idNumber++;
                line = bfr.readLine();
            }
            fr.close();
            bfr.close();
            numOfMentees = idNumber;
            System.out.printf("%s read successfully!\n", in_file);
        } catch (IOException e) {
            System.out.printf("Error reading %s\n", in_file);
        }
    }

    /**
     * Helper method that reads all of the contents of a file as a single long string
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readFileAsString(String fileName) throws IOException {
        File f = new File(fileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String ret = "";
        String line;
        while ((line = bfr.readLine()) != null)
            ret += line;
        return ret;
    }

    /**
     * Helper method for pairUp(). Used to randomize the preferences 2D array
     * @param array
     */
    public static void randomizeCol(int[][] array) {
        Random rand = new Random();
        for (int i = 0; i < array[0].length; i++) {
            int r = rand.nextInt(0, array[0].length);
            Mentee tempMentee = mentees.get(i);
            mentees.set(i, mentees.get(r));
            mentees.set(r, tempMentee);
            for (int j = 0; j < array.length; j++) {
                int temp = array[j][i];
                array[j][i] = array[j][r];
                array[j][r] = temp;
            }
        }
    }
}
