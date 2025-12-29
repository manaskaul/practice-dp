package topic1_01_knapsack;

public class CountSubsetSum {
    public static void main(String[] args) {
        int[] arr = new int[]{2,3,5,6,8,10};
        int sum = 10;

        // int countTD = subsetSumCountTD(arr, sum);
        // System.out.printf("Count of Subsets TD : %d\n", countTD);

        int countBU = subsetSumCountBU(arr, sum);
        System.out.printf("Count of Subsets BU : %d\n", countBU);
    }

    private static int subsetSumCountBU(int[] arr, int sum) {
        int n = arr.length;

        int[][] dp = new int[n+1][sum+1];
        // not possible to create a sum 0
        for(int j=0; j<sum+1; j++) {
            dp[0][j] = 0;
        }
        // there will always be a null set that will sum 0
        for(int i=0; i<n+1; i++) {
            dp[i][0] = 1;
        }

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<sum+1; j++) {
                if(arr[i-1] <= j) {
                    dp[i][j] = dp[i-1][j] + dp[i-1][j-arr[i-1]];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        return dp[n][sum];
    }
}

/*
 * 
 * n : no of elements in arr
 * s : given sum
 * 
 * dp[n+1][s+1]
 * 
 *        0 1 2 3 4 5 6 7 8 9 10
 *      0 1 0 0 0 0 0 0 0 0 0 0
 *      1 1
 *      2 1
 *      3 1
 *      4 1
 *      5 1
 *      6 1
 * 
 * dp[i][j] = MAX(dp[i-1][j] + dp[i-1][j-arr[i-1]], dp[i-1][j])
 */