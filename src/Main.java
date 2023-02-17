import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {
    // ArrayList to store every single mentee
    private static ArrayList<Mentee> mentees = new ArrayList<>();

    // ArrayList to store every single mentee
    private static ArrayList<Person> mentors = new ArrayList<>();

    // ArrayList to store all mentor groups
    private static ArrayList<MentorGroup> mentorGroups = new ArrayList<>();

    // ArrayList to store all of the mentees whose information are not complete
    private static ArrayList<String> err_mentees = new ArrayList<>();

    // ArrayList to store all of the mentees whose information are not complete
    private static ArrayList<String> err_mentors = new ArrayList<>();

    public static void main(String[] args) {
        // Create mentees
        readMentees("in.txt", "out.txt");
        for (Person mentee : mentees) {
            System.out.println(mentee);
        }

        // Create the mentors
        readMentors("mentors.txt");

        // Create the mentor groups
        createMentorGroups();

    }

    public static void createMentorGroups() {
        ArrayList<Person> group1, group2, group3, group4, group5, group6, group7;
        group1 = (ArrayList<Person>) mentors.stream()
                                            .filter(n -> (n.getName().equals("Andre Tan") ||
                                                        (n.getName().equals("Kerway Tsai")) ||
                                                        (n.getName().equals("Shivam Duhan"))))
                                            .collect(Collectors.toCollection(ArrayList::new));
        group2 = (ArrayList<Person>) mentors.stream()
                                            .filter(n -> (n.getName().equals("Kyle Yang") ||
                                                        (n.getName().equals("Claire Huang")) ||
                                                        (n.getName().equals("Matthew Kumnoonsate"))))
                                            .collect(Collectors.toCollection(ArrayList::new));
        group3 = (ArrayList<Person>) mentors.stream()
                                            .filter(n -> (n.getName().equals("Duclan Ngo") ||
                                                        (n.getName().equals("Alia Rumreich")) ||
                                                        (n.getName().equals("Jackie Fung He"))))
                                            .collect(Collectors.toCollection(ArrayList::new));
        group4 = (ArrayList<Person>) mentors.stream()
                                            .filter(n -> (n.getName().equals("Alex Zhuo") ||
                                                        (n.getName().equals("Janine Lee")) ||
                                                        (n.getName().equals("Ethan Chu"))))
                                            .collect(Collectors.toCollection(ArrayList::new));
        group5 = (ArrayList<Person>) mentors.stream()
                                            .filter(n -> (n.getName().equals("Dillon Kim") ||
                                                        (n.getName().equals("Emily Yeh")) ||
                                                        (n.getName().equals("Winston Wang"))))
                                            .collect(Collectors.toCollection(ArrayList::new));
        group6 = (ArrayList<Person>) mentors.stream()
                                            .filter(n -> (n.getName().equals("Caroline Wang") ||
                                                        (n.getName().equals("Abby Zahm")) ||
                                                        (n.getName().equals("Janathan Balakrishnan"))))
                                            .collect(Collectors.toCollection(ArrayList::new));
        group7 = (ArrayList<Person>) mentors.stream()
                                            .filter(n -> (n.getName().equals("Yong Sung Kim") ||
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
    }

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

    public static void readMentees(String in_file, String out_file) {
        // Retrieving all of the mentees' data from a file
        // Expected file layout: name,major,email,discordID,year,preferred_mentor1,preferred_mentor2,preferred_mentor3
        File f = new File(in_file);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                String lineArr[] = line.split(",");
                if (lineArr.length != 8) {
                    err_mentees.add(line);
                    line = bfr.readLine();
                    continue;
                }
                String name = lineArr[0];
                String major = lineArr[1];
                String email = lineArr[2];
                String discordID = lineArr[3];
                String year = lineArr[4];
                String mentor1 = lineArr[5];
                String mentor2 = lineArr[6];
                String mentor3 = lineArr[7];

                // Checks whether the current person already exists in the file using
                // their email and discord ID as the primary key
                File fcheck = new File(out_file);
                if (fcheck.exists()) {
                    String out_file_text = readFileAsString(out_file);
                    if (out_file_text.contains(email) && out_file_text.contains(discordID)) {
                        line = bfr.readLine();
                        continue;
                    }
                }

                mentees.add(new Mentee(name, major, email, discordID, year, mentor1, mentor2, mentor3));
                line = bfr.readLine();

            }
            fr.close();
            bfr.close();
            System.out.printf("%s read successfully!\n", in_file);
        } catch (IOException e) {
            System.out.printf("Error reading %s\n", in_file);
        }
        File out = new File(out_file);
        try {
            out.createNewFile();
            FileWriter fw = new FileWriter(out_file, true);
            for (Mentee p : mentees) {
                fw.append(p.toString());
            }
            fw.close();
            System.out.printf("Printed to file %s successfully!\n", out_file);
        } catch (IOException e) {
            System.out.println("Output file failed");
        }
    }

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
}
