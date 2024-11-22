import java.util.Arrays;

public class MissingNumber {

    // Method 1: Using Sum Formula (Time Complexity: O(n), Space Complexity: O(1))
    public static int findMissingNumberSum(int[] arr, int n) {
        int expectedSum = (n + 1) * (n + 2) / 2; // Sum of numbers from 1 to n+1
        int actualSum = 0; // Sum of array elements
        for (int num : arr) {
            actualSum += num;
        }
        return expectedSum - actualSum; // The missing number
    }

    // Method 2: Using XOR (Time Complexity: O(n), Space Complexity: O(1))
    public static int findMissingNumberXOR(int[] arr, int n) {
        int xor1 = 0; // XOR of numbers from 1 to n+1
        int xor2 = 0; // XOR of array elements

        // XOR all numbers from 1 to n+1
        for (int i = 1; i <= n + 1; i++) {
            xor1 ^= i;
        }

        // XOR all elements in the array
        for (int num : arr) {
            xor2 ^= num;
        }

        return xor1 ^ xor2; // The missing number
    }

    // Method 3: Using Sorting (Time Complexity: O(n log n), Space Complexity: O(1))
    public static int findMissingNumberSort(int[] arr, int n) {
        Arrays.sort(arr); // Sort the array

        // Check for the missing number
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != i + 1) {
                return i + 1; // Missing number found
            }
        }

        // If no number is missing in the middle, the missing number is n+1
        return n + 1;
    }

    public static void main(String[] args) {
        int[] arr = {3, 7, 1, 2, 6, 4};
        int n = 6; // n is the size of the array

        // Call Method 1: Sum Formula
        int missingNumberSum = findMissingNumberSum(arr, n);
        System.out.println("Missing Number (Sum Formula): " + missingNumberSum);

        // Call Method 2: XOR
        int missingNumberXOR = findMissingNumberXOR(arr, n);
        System.out.println("Missing Number (XOR): " + missingNumberXOR);

        // Call Method 3: Sorting
        int missingNumberSort = findMissingNumberSort(arr, n);
        System.out.println("Missing Number (Sorting): " + missingNumberSort);
    }
}
