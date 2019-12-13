package matrix;

import matrix.entity.Matrix;
import matrix.entity.NotSuitableMatrixSizeException;
import matrix.entity.WrongInputDataException;
import matrix.operation.MatrixMultiplier;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Runner {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert input method? File(f)/Keyboard(k)/Random(r)");
        String methodInput = scanner.next();
        int size = 0;
        try {
            //Changes input source to file
            if (methodInput.equalsIgnoreCase("f")) {
                System.out.println("Insert path to file");
                //Example: C:\\Users\\user\\IdeaProjects\\MatrixOperations\\src\\main\\resources\\input.txt
                String pathName = scanner.next();
                File file = new File(pathName);
                scanner = new Scanner(file);
            }

            if (!methodInput.equalsIgnoreCase("f")) {
                System.out.println("Insert array size");
            }

            size = scanner.nextInt();
            if (size < 1) throw new NotSuitableMatrixSizeException();

            Matrix matrixA;
            Matrix matrixB;

            if (methodInput.equalsIgnoreCase("r")) {

                matrixA = new Matrix(size);
                matrixB = new Matrix(size);

                System.out.println("Do you want write matrices to file? Yes(y)/No(n)");
                String writeToFile = scanner.next();

                if (writeToFile.equalsIgnoreCase("y")) {
                    System.out.println("Insert path to file");
                    //Example: C:\\Users\\user\\IdeaProjects\\MatrixOperations\\src\\main\\resources\\input.txt
                    String pathName = scanner.next();
                    writeInputMatricesToFile(pathName, size, matrixA, matrixB);
                } else {
                    System.out.println(Arrays.deepToString(matrixA.getIntMatrix()));
                    System.out.println(Arrays.deepToString(matrixB.getIntMatrix()));
                }
            } else {

                matrixA = readMatrix(scanner, size);
                matrixB = readMatrix(scanner, size);
            }

            MatrixMultiplier matrixMultiplier = new MatrixMultiplier();
            int[][] result = matrixMultiplier.multiplyAndTrace(matrixA, matrixB, false);
            scanner = new Scanner(System.in);
            System.out.println("Do you want write the result into a file? Yes(y)/No(n)");
            String writeResponseToFile = scanner.next();
            if (writeResponseToFile.equalsIgnoreCase("y")) {
                System.out.println("Insert path to file");
                //Example: C:\\Users\\user\\IdeaProjects\\MatrixOperations\\src\\main\\resources\\input.txt
                String pathName = scanner.next();
                writeOutputMatrixToFile(pathName, size, result);
            } else {
                System.out.printf("Result matrix \n %s \n", Arrays.deepToString(result));
            }
        } catch (NotSuitableMatrixSizeException e) {
            System.out.printf("Wrong matrix size %d. Should be above 0. \n", size);
        } catch (FileNotFoundException e) {
            System.out.println("Wrong filename");
        } catch (WrongInputDataException e) {
            System.out.println("Wrong input data");
        }
    }

    private static void writeInputMatricesToFile(String fileName, int size, Matrix matrixA, Matrix matrixB) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            int[][] a = matrixA.getIntMatrix();
            int[][] b = matrixB.getIntMatrix();
            writer.println(size);
            printMatrixToWriter(size, writer, a);
            writer.println();
            printMatrixToWriter(size, writer, b);
        } catch (IOException e) {
            System.out.printf("Wrong file location %s \n", fileName);
        }
    }

    private static void writeOutputMatrixToFile(String fileName, int size, int[][] matrix) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            writer.println(size);
            printMatrixToWriter(size, writer, matrix);
        } catch (IOException e) {
            System.out.printf("Wrong file location %s \n", fileName);
        }
    }

    private static void printMatrixToWriter(int size, PrintWriter writer, int[][] a) {
        for (int i = 0; i < size; i++) {
            writer.println(Arrays.stream(a[i])
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" ")));
            writer.flush();
        }
    }

    private static Matrix readMatrix(Scanner scanner, int size) throws WrongInputDataException {
        int[][] digits = new int[size][];
        int lineCount = 0;
        while (scanner.hasNextLine() &&
                lineCount < size) {
            String strLine = scanner.nextLine();
            if (StringUtils.isNotBlank(strLine)) {
                String[] strGidits = strLine.split(" ");
                if (strGidits.length != size) {
                    throw new WrongInputDataException();
                }
                int[] array = Arrays.stream(strGidits)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                digits[lineCount] = array;

                lineCount++;
            }
        }
        return new Matrix(size, digits);
    }

}
