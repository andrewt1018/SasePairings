import java.io.*;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        int[][] arr = {{1, 2, 3}, {2, 3, 4}};
        for (int[] row : arr) {
            for (int element : row) {
                System.out.print(element + " ");
            }
        }
        System.out.println();
        randomizeCol(arr);
        for (int[] row : arr) {
            for (int element : row) {
                System.out.print(element + " ");
            }
        }
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
