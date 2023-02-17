import java.io.*;
import java.util.ArrayList;

public class Main {
    // ArrayList to store every single mentee
    private static ArrayList<Person> mentees = new ArrayList<>();

    // ArrayList to store every single mentee
    private static ArrayList<Person> mentors = new ArrayList<>();

    // ArrayList to store all of the mentees whose information are not complete
    private static ArrayList<String> err_mentees = new ArrayList<>();

    // ArrayList to store all of the mentees whose information are not complete
    private static ArrayList<String> err_mentors = new ArrayList<>();

    public static void main(String[] args) {
        // Create the mentors
        readMentors("mentors.txt");
        // Create mentees
        readMentees("in.txt", "out.txt");
        for (Person mentee : mentees) {
            System.out.println(mentee);
        }
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
                // their email as the primary key
                File fcheck = new File(out_file);
                if (fcheck.exists()) {
                    String out_file_text = readFileAsString(out_file);
                    if (out_file_text.contains(email)) {
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
            for (Person p : mentees) {
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
