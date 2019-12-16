package matrix.operation;

import matrix.entity.Matrix;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class MatrixMultiplierTest {

    private final static Logger logger = Logger.getLogger(MatrixMultiplierTest.class);

    private MatrixMultiplier matrixMultiplier = new MatrixMultiplier();

    @DataProvider(name = "filesDataProvider")
    public Object[][] dataProvider() {
        return new Object[][] {
                { "src\\test\\resources\\matrix\\input\\input1.txt",
                        "src\\test\\resources\\matrix\\expected\\output1_expected.txt"},
                { "src\\test\\resources\\matrix\\input\\input2.txt",
                        "src\\test\\resources\\matrix\\expected\\output2_expected.txt"} };
    }

    @Test(dataProvider = "filesDataProvider")
    public void testMultiplyMatricesInParallel(String pathName, String expectedResultPath) {

        try (Scanner fileScanner = new Scanner(new File(pathName));
             Scanner expectedResultScanner = new Scanner(new File(expectedResultPath))) {
            int size = fileScanner.nextInt();

            Matrix matrixA = TestHelper.readMatrix(fileScanner, size);
            Matrix matrixB = TestHelper.readMatrix(fileScanner, size);

            long time = System.currentTimeMillis();
            int[][] result = matrixMultiplier.multiplyMatricesInParallel(matrixA, matrixB);

            time = System.currentTimeMillis() - time;
            logger.info(String.format(
                    "Parallel multiplication for %d element matrices took %d milliseconds..\n",
                    size, time));

            int expectedSize = expectedResultScanner.nextInt();
            Assert.assertEquals(size, expectedSize);
            int[][] expectedResult = TestHelper.readDigitMatrix(expectedResultScanner, size);

            Assert.assertTrue(Arrays.deepEquals(result, expectedResult));

        } catch (FileNotFoundException e) {
            logger.error("Wrong files path");
        }

    }

    @Test(dataProvider = "filesDataProvider")
    public void testMultiplyMatricesSequentially(String pathName, String expectedResultPath) {

        try (Scanner fileScanner = new Scanner(new File(pathName));
             Scanner expectedResultScanner = new Scanner(new File(expectedResultPath))) {
            int size = fileScanner.nextInt();

            Matrix matrixA = TestHelper.readMatrix(fileScanner, size);
            Matrix matrixB = TestHelper.readMatrix(fileScanner, size);

            long time = System.currentTimeMillis();
            int[][] result = matrixMultiplier.multiplyMatricesSequentially(matrixA, matrixB);

            time = System.currentTimeMillis() - time;
            logger.info(String.format(
                    "Sequential multiplication for %d element matrices took %d milliseconds..\n",
                    size, time));

            int expectedSize = expectedResultScanner.nextInt();
            Assert.assertEquals(size, expectedSize);
            int[][] expectedResult = TestHelper.readDigitMatrix(expectedResultScanner, size);

            Assert.assertTrue(Arrays.deepEquals(result, expectedResult));

        } catch (FileNotFoundException e) {
            logger.error("Wrong files path");
        }
    }

    @Test
    public void testMultiplySequentiallySmallMatrices() {

        int size = 2;
        int[][] a = {{0, 1},
                     {1, 1}};
        int[][] b = {{0, 0},
                     {1, 0}};
        Matrix matrixA = new Matrix(size, a);
        Matrix matrixB = new Matrix(size, b);

        long time = System.currentTimeMillis();
        int[][] result = matrixMultiplier.multiplyMatricesSequentially(matrixA, matrixB);

        time = System.currentTimeMillis() - time;
        logger.info(String.format(
                "Sequential multiplication for %d element matrices took %d milliseconds..\n",
                size, time));

        int[][] expectedResult = {{1, 0},
                                  {1, 0}};

        Assert.assertTrue(Arrays.deepEquals(result, expectedResult));
    }

}