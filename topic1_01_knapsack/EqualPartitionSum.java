// package topic1_01_knapsack;

public class EqualPartitionSum {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 5, 11, 5};
        // int[] arr = new int[]{2};

        boolean canEqualSumPartitionTD = equalSumPartitionTD(arr);
        System.out.printf("Equal Sum Partition Present TD : %B\n", canEqualSumPartitionTD);

        boolean canEqualSumPartitionBU = equalSumPartitionBU(arr);
        System.out.printf("Equal Sum Partition Present BU : %B\n", canEqualSumPartitionBU);

    }

    /*
     * In the recursive/memo solution, I'm checking for currSum * 2 == total sum
     * This can easily be replaced by running the recursive/memo fn on total/2
     */
    private static boolean equalSumPartitionTD(int[] arr) {
        int total = 0;
        for(int i=0; i<arr.length; i++) {
            total += arr[i];
        }

        // -1 : FALSE
        //  0 : UNDEFINED
        //  1 : TRUE 
        int[][] dp = new int[arr.length+1][total+1];
        for(int i=0; i<arr.length+1; i++) {
            for(int j=0; j<total+1; j++) {
                dp[i][j] = 0;
            }
        }

        return total % 2 == 0 ? isEqualSumPartition(arr, arr.length, total, total, dp) : false;
    }

    private static boolean isEqualSumPartition(int[] arr, int n, int currSum, int totalSum, int[][] dp) {
        if(currSum * 2 == totalSum) {
            return true;
        }

        if(n == 0) {
            return false;
        }

        if(dp[n][currSum] != 0) {
            return dp[n][currSum] == 1;
        }

        boolean res = false;

        if(currSum - arr[n-1] >= 0) {
            res = isEqualSumPartition(arr, n-1, currSum - arr[n-1], totalSum, dp) || isEqualSumPartition(arr, n-1, currSum, totalSum, dp);
        } else {
            res = isEqualSumPartition(arr, n-1, currSum, totalSum, dp);
        }

        dp[n][currSum] = res ? 1 : -1;

        return res;
    }

    /*
     * The bottom appraoch uses the subset sum calculation method to check for target = total/2
     */
    private static boolean equalSumPartitionBU(int[] arr) {
        
        int total = 0;
        for(int i=0; i<arr.length; i++) {
            total += arr[i];
        }

        if(total % 2 != 0) {
            return false;
        }

        int n = arr.length;
        int t = total/2; // target

        boolean[][] dp = new boolean[n+1][t+1];

        // Initlization
        for(int j=0; j<t+1; j++) {
            dp[0][j] = false;
        }
        for(int i=0; i<n+1; i++) {
            dp[i][0] = true;
        }

        // Tabulation
        for(int i=1; i<n+1; i++) {
            for(int j=1; j<t+1; j++) {
                if(arr[i-1] <= j) {
                    dp[i][j] = dp[i-1][j-arr[i-1]] || dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        return dp[n][t];
    }
}

/*
 * 
 *  0 1 2 3
 * {1 5 5 11}
 * 
 *   0 1 2 3 4 5 6 7 8 9 10 11
 * 0 T F F F F F F F F F F  F
 * 1 T
 * 2 T
 * 3 T
 * 4 T
 * 
 * 
 */

/*
 * 
 *  0 1 2 3
 * {1 5 5 11}
 * 
 *   0 1 2 3 4 5 6 7 8 9 ...
 * 0 F F F F F F F F F F F 
 * 1 F
 * 2 F
 * 3 F
 * 4 F
 * 
 * 
 */