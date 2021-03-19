
import acm.program.CommandLineProgram;

import java.util.Arrays;

public class Polynomials extends CommandLineProgram {

    private static final int DEGREE = 0;
    private static final int COEFFICIENT = 1;

    public void run() {

        testExpandedSize(); // DONE
        testCompressedSize(); // DONE
        testCreateExpanded(); // DONE
        testCreateCompressed(); // DONE
        testCopyToFromExpandedToCompressed(); // DONE
        testCopyToFromCompressedToExpanded(); // DONE
        testCompress(); // DONE
        testExpand(); // DONE
        testPow(); // DONE
        testEvaluateExpanded(); // DONE
        testEvaluateCompressed(); // DONE
        testAddExpanded(); // DONE
        testAddCompressed(); // DONE

        //extra test with extra polynomials
        extraAddTest();

        //extra function to know the best array type for a polynomial
        //bestType( ARRAY )
    }

    public int expandedSize(int[][] compressed) {
        //throw new UnsupportedOperationException("Apartado 1");

        /*
        * Returns the size of an expanded type array in case input array (compressed type) were converted to expanded
        */

        if (compressed.length == 0) { // if array size = 0
            return 0;
        }
        else {
            return compressed[compressed.length - 1][DEGREE] + 1; // last exponent value + 1
        }

    }

    public int compressedSize(int[] expanded) {
        //throw new UnsupportedOperationException("Apartado 2");

        /*
         * Returns the size of an compressed type array in case input array (expanded type) were converted to compressed.
         */

        int count = 0; // counter of values != 0

        for (int element : expanded) { // counts values != 0
            if (element != 0) {
                count++;
            }
        }

        return count;
    }

    public int[] createExpanded(int expandedSize) {
        //throw new UnsupportedOperationException("Apartado 3");

        /*
         * Returns memory allocation of an expanded type array. The input sets the size.
         */

        return new int[expandedSize]; // allocating array to memory
    }

    public int[][] createCompressed(int compressedSize) {
        //throw new UnsupportedOperationException("Apartado 4");

        /*
         * Returns memory allocation of a compressed type array. The input sets the size.
         */

        return new int[compressedSize][2]; // allocating array to memory
    }

    public void copyTo(int[] fromExpanded, int[][] toCompressed) {
        //throw new UnsupportedOperationException("Apartado 5");

        /*
         * Passes the values of an expanded type array to a compressed one by following the protocol.
         */

        int index = 0; // index of toCompressed array

        for (int i = 0; i < fromExpanded.length; i++) { // for every fromExpanded element only copy values != 0
            if (fromExpanded[i] != 0) {
                toCompressed[index][DEGREE] = i;
                toCompressed[index][COEFFICIENT] = fromExpanded[i];
                index++;
            }
        }
    }

    public void copyTo(int[][] fromCompressed, int[] toExpanded) {
        //throw new UnsupportedOperationException("Apartado 6");

        /*
         * Passes the values of an compressed type array to an expanded one by following the protocol.
         */

        for (int[] element : fromCompressed) { // for every element, copy the values to the corresponding index
            toExpanded[element[DEGREE]] = element[COEFFICIENT];
        }
    }

    public int[][] compress(int[] expanded) {
        //throw new UnsupportedOperationException("Apartado 7");

        /*
         * Returns a compressed type array which is a conversion from an expanded one using the function copyTo().
         */

        int[][] compressedArray = createCompressed(compressedSize(expanded)); // declaring array and allocating memory to it

        copyTo(expanded, compressedArray); // conversion of arrays

        return compressedArray;
    }

    public int[] expand(int[][] compressed) {
        //throw new UnsupportedOperationException("Apartado 8");

        /*
         * Returns an expanded type array which is a conversion from a compressed one using the function copyTo().
         */

        int[] expandedArray = createExpanded(expandedSize(compressed)); // declaring array and allocating memory to it

        copyTo(compressed, expandedArray); // conversion of arrays

        return expandedArray;
    }

    public int pow(int base, int exp) {
        //throw new UnsupportedOperationException("Apartado 9");

        /*
         * Returns the result of base^exp.
         */

        int result = 1; // result of exponential operation // initialized by 1 to make multiplication work

        for (int i = 0; i < exp; i++) { // make as many multiplications as the exponent given
            result *= base;
        }

        return result;
    }

    public int evaluate(int[] expanded, int x) {
        //throw new UnsupportedOperationException("Apartado 10");

        /*
         * Returns the result of solving the polynomial by setting the input "x" as the "x" of the monomials.
         * Horner's Algorithm is applied. More on wikipedia's article:
         * https://en.wikipedia.org/wiki/Horner%27s_method
         */

        int total = 0; // result of operation
        int len = expanded.length; // length of expanded array

        for (int i = len - 1; i >= 0; i--) { // to run array backwards
            total *= x; // the first multiplication will be "0 * x" but is done like this to keep the code cleaner
            total += expanded[i];
        }

        return total;
    }

    public int evaluate(int[][] compressed, int x) {
        //throw new UnsupportedOperationException("Apartado 11");

        /*
         * Returns the result of solving the polynomial by setting the input "x" as the "x" of the monomials.
         */

        int total = 0; // result of operation

        for (int[] element : compressed) { // for every element of compressed
            total += pow(x, element[DEGREE]) * element[COEFFICIENT]; // solved by sum of monomials
        }

        return total;
    }

    // Extra function // Return a copy of the input array
    public void makeCopy(int[] originExpanded, int[] destinationExpanded) {
        /*
         * Passes the values from an expanded type array to another expanded type array.
         */

        for (int i = 0; i < originExpanded.length; i++) { // for every element in the array
            destinationExpanded[i] = originExpanded[i];
        }
    }

    // Extra function //  Return a copy of the input array
    public void makeCopy(int[][] originCompressed, int[][] destinationCompressed) {
        /*
         * Passes the values from an compressed type array to another compressed type array.
         */

        for (int i = 0; i < originCompressed.length; i++) { // for every element in the array
            destinationCompressed[i][DEGREE] = originCompressed[i][DEGREE];
            destinationCompressed[i][COEFFICIENT] = originCompressed[i][COEFFICIENT];
        }
    }

    public int[] add(int[] expanded1, int[] expanded2) {
        //throw new UnsupportedOperationException("Apartado 12");

        /*
         * Returns the sum of two expanded type arrays.
         * In case the two arrays aren't empty there's a process:
         *      First section: "max size of expandedResultIsh"
         *          Finds the largest and shortest size arrays and which is the greatest or if they are equal.
         *      Second section: "passing values to expandedResultIsh"
         *          Sets a temporal array (expandedResultIsh) where temporal values will be stored.
         *          Iterates as many times as the length of the shortest input array (if they are equal on length,
         *          is done the same), and sums values of both cells of the input arrays of the same index,
         *          and store the result on the expandedResultIsh array on the same index.
         *          If one array is larger than another it adds the rest of its values to the expandedResultIsh array.
         *      Third and final section: "passing values to expandedResult"
         *          Stores the values of expandedResultIsh array to expandedResult array.
         *          expandedResult will never have zeros in cells if in further cells there's no values different
         *          from zero.
         * If one of both arrays is empty, the length is equal to zero, it's going to be returned a copy array of the
         * other one, the one that is larger than 0.
         * If both of arrays are empty, the length is equal to zero, it's going to be returned one array of length of 0.
         */

        int len1 = expanded1.length; // length of expanded1 array
        int len2 = expanded2.length; // length of expanded2 array
        int largest; // largest length
        int shortest; // shortest length
        int largestArray; // largest array // 0 = equals, 1 = expanded1, 2 = expanded2
        int lastIndex = 0; // last index before the "holes"
        int[] expandedResultIsh; // temporal array
        int[] expandedResult; // output array

        if (len1 > 0 && len2 > 0) { // if both input arrays aren't empty
            //max size of expandedResultIsh
            if (len1 > len2) { // know the largest and shortest array
                largest = len1;
                shortest = len2;
                largestArray = 1;
            }
            else if (len1 < len2) {
                largest = len2;
                shortest = len1;
                largestArray = 2;
            }
            else {
                largest = shortest = len1;
                largestArray = 0;
            }

            expandedResultIsh = createExpanded(largest); // temporal array

            //passing values to expandedResultIsh
            for (int i = 0; i < shortest; i++) { // sum of all values in the range of the shortest array
                expandedResultIsh[i] = expanded1[i] + expanded2[i];
                if (expanded1[i] + expanded2[i] != 0 && largestArray == 0) {
                    lastIndex = i + 1;
                }
            }

            if (largestArray != 0) { // add the rest of the other
                if (largestArray == 1) {
                    for (int i = shortest; i < largest; i++) {
                        expandedResultIsh[i] = expanded1[i];
                    }
                } else if (largestArray == 2) {
                    for (int i = shortest; i < largest; i++) {
                        expandedResultIsh[i] = expanded2[i];
                    }
                }

                lastIndex = largest;
            }

            expandedResult = createExpanded(lastIndex); // output array

            //passing values to expandedResult
            for (int i = 0; i < lastIndex; i++) { // passes all values to expandedResult
                expandedResult[i] = expandedResultIsh[i];
            }
        }

        else if (len1 > 0 && len2 == 0) {  // if only compressed2 array is empty
            expandedResult = createExpanded(len1);
            makeCopy(expanded1, expandedResult);
        }

        else if (len1 == 0 && len2 > 0) {  // if only compressed1 array is empty
            expandedResult = createExpanded(len2);
            makeCopy(expanded2, expandedResult);
        }

        else {  // if both input arrays are empty
            expandedResult = createExpanded(0);
        }

        return expandedResult;
    }

    public int[][] add(int[][] compressed1, int[][] compressed2) {
        //throw new UnsupportedOperationException("Apartado 13");

        /*
         * Returns the sum of two expanded type arrays.
         * In case the two arrays aren't empty there's a process:
         *      First section: "passing values to compressedResultIsh"
         *          Its a loop where compares the two elements of input arrays and stores the one with lowest exponent
         *          in the compressedResultIsh array. If the values are equal and the sum of both values aren't
         *          equal to zero the value is stored too. If is reached the end of one array, the rest of the other
         *          will be added after the other values in the compressedResultIsh array.
         *      Second and final section: "passing values to compressedResult"
         *          Stores the values of compressedResultIsh array to compressedResult array.
         * If one of both arrays is empty, the length is equal to zero, it's going to be returned a copy array of the
         * other one, the one that is larger than 0.
         * If both of arrays are empty, the length is equal to zero, it's going to be returned one array of length of 0.
         */

        int len1 = compressed1.length; // length of compressed1 array
        int len2 = compressed2.length; // length of compressed2 array
        int index1 = 0; // index of compressed1 array
        int index2 = 0; // index of compressed1 array
        boolean sumIndex1 = false; // if need to increase index1
        boolean sumIndex2 = false; // if need to increase index2
        boolean ended1 = false; // if last value has been added
        boolean ended2 = false; // if last value has been added
        int indexRIsh = 0; // index of compressedResultIsh // size of compressedResult array
        int[][] compressedResultIsh = createCompressed(len1 + len2); // output array
        int indexR = 0; // index to allocate values to compressedResult
        int[][] compressedResult; // output array

        if (len1 > 0 && len2 > 0) { // if both input arrays aren't empty
            //passing values to compressedResultIsh
            while (!ended1 || !ended2) { // while never reached both end of the input arrays
                if (!ended1 && !ended2) { // if never reached any of the input arrays
                    if (compressed1[index1][DEGREE] < compressed2[index2][DEGREE]) { // if one exponent is greater than the other
                        compressedResultIsh[indexRIsh][DEGREE] = compressed1[index1][DEGREE];
                        compressedResultIsh[indexRIsh][COEFFICIENT] = compressed1[index1][COEFFICIENT];
                        indexRIsh++;
                        sumIndex1 = true;
                    }
                    else if (compressed2[index2][DEGREE] < compressed1[index1][DEGREE]) { // if one exponent is greater than the other
                        compressedResultIsh[indexRIsh][DEGREE] = compressed2[index2][DEGREE];
                        compressedResultIsh[indexRIsh][COEFFICIENT] = compressed2[index2][COEFFICIENT];
                        indexRIsh++;
                        sumIndex2 = true;
                    }
                    else if (compressed1[index1][DEGREE] == compressed2[index2][DEGREE]) { // if both exponents are equal
                        if (compressed1[index1][COEFFICIENT] + compressed2[index2][COEFFICIENT] != 0) {
                            compressedResultIsh[indexRIsh][DEGREE] = compressed1[index1][DEGREE];
                            compressedResultIsh[indexRIsh][COEFFICIENT] = compressed1[index1][COEFFICIENT] + compressed2[index2][COEFFICIENT];
                            indexRIsh++;
                        }
                        sumIndex1 = true;
                        sumIndex2 = true;
                    }
                    if (sumIndex1) { // increase index of compressed1 if possible
                        sumIndex1 = false;
                        if (index1 + 1 < len1) {
                            index1++;
                        }
                        else if (!ended1 && index1 + 1 == len1) {
                            ended1 = true;
                        }
                    }
                    if (sumIndex2) { // increase index of compressed2 if possible
                        sumIndex2 = false;
                        if (index2 + 1 < len2) {
                            index2++;
                        }
                        else if (!ended2 && index2 + 1 == len2) {
                            ended2 = true;
                        }
                    }

                }
                else { // once is reached one end
                    if (ended2 && !ended1) { // finish adding the rest of the unfinished array
                        compressedResultIsh[indexRIsh][DEGREE] = compressed1[index1][DEGREE];
                        compressedResultIsh[indexRIsh][COEFFICIENT] = compressed1[index1][COEFFICIENT];
                        indexRIsh++;
                        if (!ended1 && index1 + 1 == len1) {
                            ended1 = true;
                        }
                        index1++;
                    }
                    else if (ended1 && !ended2) { // finish adding the rest of the unfinished array
                        compressedResultIsh[indexRIsh][DEGREE] = compressed2[index2][DEGREE];
                        compressedResultIsh[indexRIsh][COEFFICIENT] = compressed2[index2][COEFFICIENT];
                        indexRIsh++;
                        if (!ended2 && index2 + 1 == len2) {
                            ended2 = true;
                        }
                        index2++;
                    }
                }
            }

            //passing values to compressedResult
            compressedResult = createCompressed(indexRIsh); // output array

            if (compressedResult.length > 0) { // passes all values to compressedResult
                for (int[] element : compressedResultIsh) {
                    if (!(element[DEGREE] == 0 && element[COEFFICIENT] == 0)) {
                        compressedResult[indexR][DEGREE] = element[DEGREE];
                        compressedResult[indexR][COEFFICIENT] = element[COEFFICIENT];
                        indexR++;
                    }
                }
            }
        }

        else if(len1 > 0 && len2 == 0) {  // if only compressed2 array is empty
            compressedResult = createCompressed(len1);
            makeCopy(compressed1, compressedResult);
        }

        else if(len1 == 0 && len2 > 0) {  // if only compressed1 array is empty
            compressedResult = createCompressed(len2);
            makeCopy(compressed2, compressedResult);
        }

        else {  // if both input arrays are empty
            compressedResult = createCompressed(0);
        }
        return compressedResult;
    }

    // -----
    // TESTS
    // -----

    public int[] EXPANDED_ZERO = new int[0];
    public int[] EXPANDED_LEFT_ZEROS = new int[]{0, 0, 0, 0, 42};
    public int[] EXPANDED_MIDDLE_ZEROS = new int[]{42, 0, 12, 0, 24};
    public int[] EXPANDED_NON_ZEROS = new int[]{42, 25, 12, 18, 24};
    public int[] EXPANDED_NON_ZEROS_NEG = new int[]{-42, -25, -12, -18, -24};

    public int[][] COMPRESSED_ZERO = new int[0][2];
    public int[][] COMPRESSED_LEFT_ZEROS = new int[][]{{4, 42}};
    public int[][] COMPRESSED_MIDDLE_ZEROS = new int[][]{{0, 42},
            {2, 12},
            {4, 24}};
    public int[][] COMPRESSED_NON_ZEROS = new int[][]{{0, 42},
            {1, 25},
            {2, 12},
            {3, 18},
            {4, 24}};

    public int[][] COMPRESSED_NON_ZEROS_NEG = new int[][]{{0, -42},
            {1, -25},
            {2, -12},
            {3, -18},
            {4, -24}};

    public String stringify(int[] expanded) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < expanded.length; i++) {
            builder.append(expanded[i]);
            if (i != expanded.length - 1) {
                builder.append(", ");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    public String stringify(int[][] compressed) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < compressed.length; i++) {
            builder.append(stringify(compressed[i]));
            if (i != compressed.length - 1) {
                builder.append(", ");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    public boolean matrixEquals(int[][] matrix1, int[][] matrix2) {
        if (matrix1.length != matrix2.length) {
            return false;
        }
        for (int i = 0; i < matrix1.length; i++) {
            if (!Arrays.equals(matrix1[i], matrix2[i])) {
                return false;
            }
        }
        return true;
    }

    public int[][] duplicate(int[][] source) {
        int[][] result = new int[source.length][];
        for (int i = 0; i < source.length; i++) {
            result[i] = Arrays.copyOf(source[i], source[i].length);
        }
        return result;
    }

    public void checkExpandedSize(int[][] compressed, int expected) {
        int[][] savedCompressed = duplicate(compressed);
        int expandedSize = expandedSize(savedCompressed);
        if (expandedSize != expected) {
            printlnError("expandedSize of " + stringify(compressed)
                    + " returns " + expandedSize
                    + " but should be " + expected);
        } else if (!matrixEquals(compressed, savedCompressed)) {
            printlnError("expandedSize of " + stringify(compressed)
                    + " should not modify its parameter");
        } else {
            printlnOk("expandedSize of " + stringify(compressed));
        }
    }

    public void testExpandedSize() {
        printlnInfo("BEGIN testExpandedSize");
        checkExpandedSize(COMPRESSED_ZERO, 0);
        checkExpandedSize(COMPRESSED_LEFT_ZEROS, 5);
        checkExpandedSize(COMPRESSED_MIDDLE_ZEROS, 5);
        checkExpandedSize(COMPRESSED_NON_ZEROS, 5);
        printlnInfo("END testExpandedSize");
        printBar();
    }

    public void checkCompressedSize(int[] expanded, int expected) {
        int[] savedExpanded = Arrays.copyOf(expanded, expanded.length);
        int compressedSize = compressedSize(savedExpanded);
        if (compressedSize != expected) {
            printlnError("compressedSize of " + stringify(expanded)
                    + " returns " + compressedSize
                    + " but should be " + expected);
        } else if (!Arrays.equals(expanded, savedExpanded)) {
            printlnError("compressedSize of " + stringify(expanded)
                    + " should not modify its parameter");
        } else {
            printlnOk("compressedSize of " + stringify(expanded));
        }
    }

    public void testCompressedSize() {
        printlnInfo("BEGIN testCompressedSize");
        checkCompressedSize(EXPANDED_ZERO, 0);
        checkCompressedSize(EXPANDED_LEFT_ZEROS, 1);
        checkCompressedSize(EXPANDED_MIDDLE_ZEROS, 3);
        checkCompressedSize(EXPANDED_NON_ZEROS, 5);
        printlnInfo("END testCompressedSize");
        printBar();
    }

    public void checkCreateExpanded(int expandedSize) {
        int[] createExpanded = createExpanded(expandedSize);
        int[] expected = new int[expandedSize];
        if (!Arrays.equals(createExpanded, expected)) {
            printlnError("createExpanded of " + expandedSize
                    + " returns " + stringify(createExpanded)
                    + " but should be " + stringify(expected));
        } else {
            printlnOk("createExpanded of " + expandedSize);
        }
    }

    public void testCreateExpanded() {
        printlnInfo("BEGIN testCreateExpanded");
        checkCreateExpanded(0);
        checkCreateExpanded(1);
        checkCreateExpanded(5);
        printlnInfo("END testCreateExpanded");
        printBar();
    }

    public void checkCreateCompressed(int compressedSize) {
        int[][] createCompressed = createCompressed(compressedSize);
        int[][] expected = new int[compressedSize][2];
        if (!matrixEquals(createCompressed, expected)) {
            printlnError("createCompressed of " + compressedSize
                    + " returns " + stringify(createCompressed)
                    + " but should be " + stringify(expected));
        } else {
            printlnOk("createCompressed of " + compressedSize);
        }
    }

    public void testCreateCompressed() {
        printlnInfo("BEGIN testCreateCompressed");
        checkCreateCompressed(0);
        checkCreateCompressed(1);
        checkCreateCompressed(5);
        printlnInfo("END testCreateCompressed");
        printBar();
    }

    public void testCopyToFromExpandedToCompressed() {
        printlnInfo("BEGIN testCopyToFromExpandedToCompressed");
        checkCopyToFromExpandedToCompressed(EXPANDED_ZERO, COMPRESSED_ZERO);
        checkCopyToFromExpandedToCompressed(EXPANDED_LEFT_ZEROS, COMPRESSED_LEFT_ZEROS);
        checkCopyToFromExpandedToCompressed(EXPANDED_MIDDLE_ZEROS, COMPRESSED_MIDDLE_ZEROS);
        checkCopyToFromExpandedToCompressed(EXPANDED_NON_ZEROS, COMPRESSED_NON_ZEROS);
        checkCopyToFromExpandedToCompressed(EXPANDED_NON_ZEROS_NEG, COMPRESSED_NON_ZEROS_NEG);
        printlnInfo("END testCopyToFromExpandedToCompressed");
        printBar();
    }

    private void checkCopyToFromExpandedToCompressed(int[] fromExpanded, int[][] expected) {
        int[] savedFromExpanded = Arrays.copyOf(fromExpanded, fromExpanded.length);
        int[][] toCompressed = createCompressed(compressedSize(fromExpanded));
        copyTo(savedFromExpanded, toCompressed);
        if (!matrixEquals(toCompressed, expected)) {
            printlnError("copyTo (fromExpanded) " + stringify(fromExpanded)
                    + " returns " + stringify(toCompressed)
                    + " but should be " + stringify(expected));
        } else if (!Arrays.equals(fromExpanded, savedFromExpanded)) {
            printlnError("copyTo (fromExpanded) " + stringify(fromExpanded)
                    + " should not modify its parameter");
        } else {
            printlnOk("copyTo (fromExpanded) " + stringify(fromExpanded));
        }
    }

    public void testCopyToFromCompressedToExpanded() {
        printlnInfo("BEGIN testCopyToFromCompressedToExpanded");
        checkCopyToFromCompressedToExpanded(COMPRESSED_ZERO, EXPANDED_ZERO);
        checkCopyToFromCompressedToExpanded(COMPRESSED_LEFT_ZEROS, EXPANDED_LEFT_ZEROS);
        checkCopyToFromCompressedToExpanded(COMPRESSED_MIDDLE_ZEROS, EXPANDED_MIDDLE_ZEROS);
        checkCopyToFromCompressedToExpanded(COMPRESSED_NON_ZEROS, EXPANDED_NON_ZEROS);
        checkCopyToFromCompressedToExpanded(COMPRESSED_NON_ZEROS_NEG, EXPANDED_NON_ZEROS_NEG);
        printlnInfo("END testCopyToFromCompressedToExpanded");
        printBar();
    }

    private void checkCopyToFromCompressedToExpanded(int[][] fromCompressed, int[] expected) {
        int[][] savedFromCompressed = duplicate(fromCompressed);
        int[] toExpanded = createExpanded(expandedSize(fromCompressed));
        copyTo(savedFromCompressed, toExpanded);
        if (!Arrays.equals(toExpanded, expected)) {
            printlnError("copyTo (fromCompressed) " + stringify(fromCompressed)
                    + " returns " + stringify(toExpanded)
                    + " but should be " + stringify(expected));
        } else if (!matrixEquals(fromCompressed, savedFromCompressed)) {
            printlnError("copyTo (fromCompressed) " + stringify(fromCompressed)
                    + " should not modify its parameter");
        } else {
            printlnOk("copyTo (fromCompressed) " + stringify(fromCompressed));
        }
    }

    public void checkCompress(int[] expanded, int[][] expected) {
        int[] savedExpanded = Arrays.copyOf(expanded, expanded.length);
        int[][] compressed = compress(savedExpanded);
        if (!matrixEquals(compressed, expected)) {
            printlnError("compress of " + stringify(expanded)
                    + " returns " + stringify(compressed)
                    + " but should be " + stringify(expected));
        } else if (!Arrays.equals(expanded, savedExpanded)) {
            printlnError("compress of " + stringify(expanded)
                    + " should not modify its parameter");
        } else {
            printlnOk("compress of " + stringify(expanded));
        }
    }

    public void testCompress() {
        printlnInfo("BEGIN testCompress");
        checkCompress(EXPANDED_ZERO, COMPRESSED_ZERO);
        checkCompress(EXPANDED_LEFT_ZEROS, COMPRESSED_LEFT_ZEROS);
        checkCompress(EXPANDED_MIDDLE_ZEROS, COMPRESSED_MIDDLE_ZEROS);
        checkCompress(EXPANDED_NON_ZEROS, COMPRESSED_NON_ZEROS);
        printlnInfo("END testCompress");
        printBar();
    }

    public void checkExpand(int[][] compressed, int[] expected) {
        int[][] savedCompressed = duplicate(compressed);
        int[] expanded = expand(savedCompressed);
        if (!Arrays.equals(expanded, expected)) {
            printlnError("expand of " + stringify(compressed)
                    + " returns " + stringify(expanded)
                    + " but should be " + stringify(expected));
        } else if (!matrixEquals(compressed, savedCompressed)) {
            printlnError("expand of " + stringify(compressed)
                    + " should not modify its parameter");
        } else {
            printlnOk("expand of " + stringify(compressed));
        }
    }

    public void testExpand() {
        printlnInfo("BEGIN testExpand");
        checkExpand(COMPRESSED_ZERO, EXPANDED_ZERO);
        checkExpand(COMPRESSED_LEFT_ZEROS, EXPANDED_LEFT_ZEROS);
        checkExpand(COMPRESSED_MIDDLE_ZEROS, EXPANDED_MIDDLE_ZEROS);
        checkExpand(COMPRESSED_NON_ZEROS, EXPANDED_NON_ZEROS);
        printlnInfo("END testExpand");
        printBar();
    }

    public void checkPow(int base, int exponent, int expected) {
        int pow = pow(base, exponent);
        if (pow != expected) {
            printlnError("pow of " + base + "^" + exponent
                    + " returns " + pow
                    + " but should be " + expected);
        } else {
            printlnOk("pow of " + base + "^" + exponent);
        }
    }

    public void testPow() {
        printlnInfo("BEGIN testPow");
        checkPow(0, 0, 1);
        checkPow(2, 0, 1);
        checkPow(4, 1, 4);
        checkPow(4, 4, 256);
        checkPow(6, 4, 1296);
        printlnInfo("END testPow");
        printBar();
    }

    public void checkEvaluateExpanded(int[] expanded, int x, int expected) {
        int[] savedExpanded = Arrays.copyOf(expanded, expanded.length);
        int value = evaluate(savedExpanded, x);
        if (value != expected) {
            printlnError("evaluation of " + stringify(expanded)
                    + " at " + x
                    + " returns " + value
                    + " but should be " + expected);
        } else if (!Arrays.equals(expanded, savedExpanded)) {
            printlnError("evaluation of " + stringify(expanded)
                    + " at " + x
                    + " should not modify its parameter");
        } else {
            printlnOk("evaluation of " + stringify(expanded) + " at " + x);
        }
    }

    public void testEvaluateExpanded() {
        printlnInfo("BEGIN evaluation of expanded");
        checkEvaluateExpanded(EXPANDED_ZERO, 10, 0);
        checkEvaluateExpanded(EXPANDED_MIDDLE_ZEROS, 0, 42);
        checkEvaluateExpanded(EXPANDED_MIDDLE_ZEROS, 1, 78);
        checkEvaluateExpanded(EXPANDED_NON_ZEROS, 2, 668);
        checkEvaluateExpanded(EXPANDED_NON_ZEROS, 3, 2655);
        printlnInfo("END evaluation of expanded");
        printBar();
    }

    public void checkEvaluateCompressed(int[][] compressed, int x, int expected) {
        int[][] savedCompressed = duplicate(compressed);
        int value = evaluate(savedCompressed, x);
        if (value != expected) {
            printlnError("evaluation of " + stringify(compressed)
                    + " at " + x
                    + " returns " + value
                    + " but should be " + expected);
        } else if (!matrixEquals(compressed, savedCompressed)) {
            printlnError("evaluation of " + stringify(compressed)
                    + " at " + x
                    + " should not modify its parameter");
        } else {
            printlnOk("evaluation of " + stringify(compressed) + " at " + x);
        }
    }

    public void testEvaluateCompressed() {
        printlnInfo("BEGIN evaluation of compressed");
        checkEvaluateCompressed(COMPRESSED_ZERO, 10, 0);
        checkEvaluateCompressed(COMPRESSED_MIDDLE_ZEROS, 0, 42);
        checkEvaluateCompressed(COMPRESSED_MIDDLE_ZEROS, 1, 78);
        checkEvaluateCompressed(COMPRESSED_NON_ZEROS, 2, 668);
        checkEvaluateCompressed(COMPRESSED_NON_ZEROS, 3, 2655);
        printlnInfo("END evaluation of compressed");
        printBar();
    }

    public void checkAddExpanded(int[] exp1, int[] exp2, int[] expected) {
        int[] savedExp1 = Arrays.copyOf(exp1, exp1.length);
        int[] savedExp2 = Arrays.copyOf(exp2, exp2.length);
        int[] added = add(savedExp1, savedExp2);
        if (!Arrays.equals(added, expected)) {
            printlnError("adding " + stringify(exp1)
                    + " to " + stringify(exp2)
                    + " returns " + stringify(added)
                    + " but should be " + stringify(expected));
        } else if (!Arrays.equals(exp1, savedExp1) || !Arrays.equals(exp2, savedExp2)) {
            printlnError("adding " + stringify(exp1)
                    + " to " + stringify(exp2)
                    + " should not modify any of its parameters");
        } else {
            printlnOk("adding " + stringify(exp1) + " to " + stringify(exp2));
        }
    }

    public void testAddExpanded() {
        printlnInfo("BEGIN add expanded");
        checkAddExpanded(EXPANDED_ZERO, EXPANDED_NON_ZEROS, EXPANDED_NON_ZEROS);
        checkAddExpanded(EXPANDED_NON_ZEROS, EXPANDED_ZERO, EXPANDED_NON_ZEROS);
        checkAddExpanded(EXPANDED_NON_ZEROS, EXPANDED_NON_ZEROS_NEG, EXPANDED_ZERO);
        printlnInfo("END add expanded");
        printBar();
    }

    public void checkAddCompressed(int[][] comp1, int[][] comp2, int[][] expected) {
        int[][] savedComp1 = duplicate(comp1);
        int[][] savedComp2 = duplicate(comp2);
        int[][] added = add(savedComp1, savedComp2);
        if (!matrixEquals(added, expected)) {
            printlnError("adding " + stringify(comp1)
                    + " to " + stringify(comp2)
                    + " returns " + stringify(added)
                    + " but should be " + stringify(expected));
        } else if (!matrixEquals(comp1, savedComp1) || !matrixEquals(comp2, savedComp2) ) {
            printlnError("adding " + stringify(comp1)
                    + " to " + stringify(comp2)
                    + " should not modify any of its parameters");
        } else {
            printlnOk("adding " + stringify(comp1) + " to " + stringify(comp2));
        }
    }

    public void testAddCompressed() {
        printlnInfo("BEGIN add compressed");
        checkAddCompressed(COMPRESSED_ZERO, COMPRESSED_NON_ZEROS, COMPRESSED_NON_ZEROS);
        checkAddCompressed(COMPRESSED_NON_ZEROS, COMPRESSED_ZERO, COMPRESSED_NON_ZEROS);
        checkAddCompressed(COMPRESSED_NON_ZEROS, COMPRESSED_NON_ZEROS_NEG, COMPRESSED_ZERO);
        printlnInfo("END add compressed");
        printBar();
    }

    // Colorize output for CommandLineProgram

    public String ANSI_RESET = "\u001B[0m";
    public String ANSI_RED = "\u001B[31m";
    public String ANSI_GREEN = "\u001B[32m";
    public String ANSI_BLUE = "\u001B[34m";

    public void printlnInfo(String message) {
        if (acm.program.CommandLineProgram.class.isInstance(this))
            println(ANSI_BLUE + message + ANSI_RESET);
        else
            println(message);
    }

    public void printlnOk(String message) {
        if (acm.program.CommandLineProgram.class.isInstance(this))
            println(ANSI_GREEN + "OK: " + message + ANSI_RESET);
        else
            println("OK: " + message);
    }

    public void printlnError(String message) {
        if (acm.program.CommandLineProgram.class.isInstance(this))
            println(ANSI_RED + "ERROR: " + message + ANSI_RESET);
        else
            println("ERROR: " + message);
    }

    public void printBar() {
        println("--------------------------------------------------");
    }

    // Some combinations of OS & JVM need this
    public static void main(String[] args) {
        new Polynomials().start(args);
    }

    // -----
    // MY TESTS
    // -----

    //EXPANDED // RESULT = ONE + TWO
    public int[] EXPANDED_TEST_A_ONE = new int[]{0, 10, -10, 10};
    public int[] EXPANDED_TEST_A_TWO = new int[]{0, 0, 10, 10};
    public int[] EXPANDED_TEST_A_RESULT = new int[]{0, 10, 0, 20};
    public int[] EXPANDED_TEST_B_ONE = new int[]{0, 0, 10};
    public int[] EXPANDED_TEST_B_TWO = new int[]{0, -10};
    public int[] EXPANDED_TEST_B_RESULT = new int[]{0, -10, 10};
    public int[] EXPANDED_TEST_C_ONE = new int[]{0, 0, 10};
    public int[] EXPANDED_TEST_C_TWO = new int[]{0, 0, 10};
    public int[] EXPANDED_TEST_C_RESULT = new int[]{0, 0, 20};

    //COMPRESSED // RESULT = ONE + TWO
    public int[][] COMPRESSED_TEST_A_ONE = new int[][]{{3, -10},
            {4, -10}};
    public int[][] COMPRESSED_TEST_A_TWO = new int[][]{{2, -10}};
    public int[][] COMPRESSED_TEST_A_RESULT = new int[][]{{2, -10},
           {3, -10},
           {4, -10}};
    public int[][] COMPRESSED_TEST_B_ONE = new int[][]{{1, -10},
           {3, -10},
           {4, -10}};
    public int[][] COMPRESSED_TEST_B_TWO = new int[][]{{1, -10},
           {2, -10}};
    public int[][] COMPRESSED_TEST_B_RESULT = new int[][]{{1, -20},
           {2, -10},
           {3, -10},
           {4, -10}};
    public int[][] COMPRESSED_TEST_C_ONE = new int[][]{{0, -10},
           {2, -10},
           {4, -10}};
    public int[][] COMPRESSED_TEST_C_TWO = new int[][]{{1, -10},
           {2, -10}};
    public int[][] COMPRESSED_TEST_C_RESULT = new int[][]{{0, -10},
           {1, -10},
           {2, -20},
           {4, -10}};
    public int[][] COMPRESSED_TEST_D_ONE = new int[][]{{0, -10},
           {2, -10},
           {4, -10}};
    public int[][] COMPRESSED_TEST_D_TWO = new int[][]{{1, -10},
           {2, 10}};
    public int[][] COMPRESSED_TEST_D_RESULT = new int[][]{{0, -10},
           {1, -10},
           {4, -10}};
    public int[][] COMPRESSED_TEST_E_ONE = new int[][]{{0, -10},
            {2, -10},
            {4, -10},
            {5, -10},
            {6, -10}};
    public int[][] COMPRESSED_TEST_E_TWO = new int[][]{{0, 10},
            {1, -10},
            {2, 10},
            {5, -10},
            {6, 10}};
    public int[][] COMPRESSED_TEST_E_RESULT = new int[][]{{1, -10},
            {4, -10},
            {5, -20}};
    public int[][] COMPRESSED_TEST_F_ONE = new int[][]{{0, -10},
            {1, -10},
            {2, -10},
            {4, -10}};
    public int[][] COMPRESSED_TEST_F_TWO = new int[][]{{0, 10},
            {1, -10},
            {2, -10},
            {4, -10}};
    public int[][] COMPRESSED_TEST_F_RESULT = new int[][]{{1, -20},
            {2, -20},
            {4, -20}};

    //EXTRA ADD TEST FUNCTION
    public void extraAddTest() {
        /*
         * Make extra tests of add function using add test function used above.
         */

        //extra separation
        println();
        printBar();
        printlnInfo("EXTRA TEST WITH EXTRA MATRIX");
        printBar();

        // test of expanded extra arrays
        printlnInfo("BEGIN add expanded EXTRA TEST");
        checkAddExpanded(EXPANDED_TEST_A_ONE, EXPANDED_TEST_A_TWO, EXPANDED_TEST_A_RESULT);
        checkAddExpanded(EXPANDED_TEST_B_ONE, EXPANDED_TEST_B_TWO, EXPANDED_TEST_B_RESULT);
        checkAddExpanded(EXPANDED_TEST_C_ONE, EXPANDED_TEST_C_TWO, EXPANDED_TEST_C_RESULT);
        printlnInfo("END add expanded EXTRA TEST");
        printBar();

        // test of compressed extra arrays
        printlnInfo("BEGIN add compressed EXTRA TEST");
        checkAddCompressed(COMPRESSED_TEST_A_ONE, COMPRESSED_TEST_A_TWO, COMPRESSED_TEST_A_RESULT);
        checkAddCompressed(COMPRESSED_TEST_B_ONE, COMPRESSED_TEST_B_TWO, COMPRESSED_TEST_B_RESULT);
        checkAddCompressed(COMPRESSED_TEST_C_ONE, COMPRESSED_TEST_C_TWO, COMPRESSED_TEST_C_RESULT);
        checkAddCompressed(COMPRESSED_TEST_D_ONE, COMPRESSED_TEST_D_TWO, COMPRESSED_TEST_D_RESULT);
        checkAddCompressed(COMPRESSED_TEST_E_ONE, COMPRESSED_TEST_E_TWO, COMPRESSED_TEST_E_RESULT);
        checkAddCompressed(COMPRESSED_TEST_F_ONE, COMPRESSED_TEST_F_TWO, COMPRESSED_TEST_F_RESULT);
        printlnInfo("END add compressed EXTRA TEST");
        printBar();
    }

    // EXTRA FUNCTION TO KNOW THE BEST ARRAY TYPE FOR A POLYNOMIAL
    public boolean bestType(int[] expanded) {
        /*
         * Returns true if input array is better than the other type.
         * Better means that an array of x type uses less space in memory than the other.
         * If they use the same space, expanded is chosen because of the less complexity of some calculus.
         */

        return (expanded.length <= compressedSize(expanded) * 2);
    }

    // EXTRA FUNCTION TO KNOW THE BEST ARRAY TYPE FOR A POLYNOMIAL
    public boolean bestType(int[][] compressed) {
        /*
         * Returns true if input array is better than the other type.
         * Better means that an array of x type uses less space in memory than the other.
         * If they use the same space, expanded is chosen because of the less complexity of some calculus.
         */

        return (compressed.length * 2 < expandedSize(compressed));
    }
}