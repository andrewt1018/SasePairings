import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;

public class Main {
    // ArrayList to store every single subject, both mentors and mentees
    private static ArrayList<Person> subjects = new ArrayList<>();
    // ArrayList to store all of the subjects whose information are not complete
    private static ArrayList<String> err_subjects = new ArrayList<>();

    public static void main(String[] args) {
       readAndWriteFiles("in.txt", "out.txt");
    }

    public static void readAndWriteFiles(String in_file, String out_file) {
        // Retrieving all of the mentee's data from a file
        File f = new File(in_file);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                String lineArr[] = line.split(",");
                if (lineArr.length != 5) {
                    err_subjects.add(line);
                    line = bfr.readLine();
                    continue;
                }
                String name = lineArr[0];
                String major = lineArr[1];
                String email = lineArr[2];
                String discordID = lineArr[3];
                int year = 0;
                subjects.add(new Mentee(name, major, email, discordID, year));
                line = bfr.readLine();
            }
            fr.close();
            bfr.close();
            System.out.printf("%s read successfully!\n", in_file);
        } catch (IOException e) {
            System.out.printf("File %s not found\n", in_file);
        }
        File out = new File(out_file);
        try {
            out.createNewFile();
            FileWriter fw = new FileWriter(out_file, true);
            for (Person p : subjects) {
                String append = String.format("%s,%s,%s,%s,%d\n", p.getName(),
                        p.getMajor(),
                        p.getEmail(),
                        p.getDiscordID(),
                        p.getYear());
                fw.append(append);
            }
            fw.close();
            System.out.printf("Printed to file %s successfully!\n", out_file);
        } catch (IOException e) {
            System.out.println("Output file failed");
        }
    }
}
