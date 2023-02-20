import java.io.*;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        System.out.println((413*413*413*413*413)%3);
    }

    public static void randomizeCol(int[][] array) {
        Random rand = new Random();
        for (int i = 0; i < array[0].length; i++) {
            int r = rand.nextInt(0, array[0].length);
            for (int j = 0; j < array.length; j++) {
                int temp = array[j][i];
                array[j][i] = array[j][r];
                array[j][r] = temp;
            }
        }
    }
}
