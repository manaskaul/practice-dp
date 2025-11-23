// package topic1_01_knapsack;

/*
 * Given the array, we need to assign +ve/-ve signs to the elements
 * such that the sum of elements equals given sum value
 * 
 * This is similar to the Count Subset Sum with Given Difference
 * we can divide the array into 2 subsets with sum S1 and S2
 * and can write ABS(S1 - S2) = sum
 * 
 * this essentially means, assigning +ve signs to all elements of subset S1
 * and assigning -ve signs to all elements of subset S2
 */
public class TargetSum {
    public static void main(String[] args) {
        // int[] arr = new int[]{1,1,2,3};
        // int sum = 1;
        
        int[] arr = new int[]{0,1,2,2,2,3,0,3,0,1};
        int sum = 12;

        int count = countWithTargetSum(arr, sum);
        System.out.printf("Count of Subset Sum with Target Sum : %d\n", count);
    }

    /*
     * Given array with non-negative elements and some sum
     * we choose 2 subsets with sum S1 and S2
     * such that 
     *     S1 - S2 = sum
     * and S1 + S2 = total
     * 
     * solving this, we get S1 = (total+sum)/2
     * so we need to find the subset with sum = (total+sum)/2
     * NOTE: we cannot find S1 if sum > total or if (total+sum) is odd and can return 0
     */
    private static int countWithTargetSum(int[] arr, int sum) {
        int total = 0;
        for(int i=0; i<arr.length; i++) {
            total += arr[i];
        }
        
        int n = arr.length;
        int s = (total+sum)/2;

        if(sum > total || (total+sum) % 2 != 0) {
            return 0;
        }

        int[][] dp = new int[n+1][s+1];
        
        // initlize first column
        // we cannot create any sum when choosing 0 elements
        for(int j=0; j<s+1; j++) {
            dp[0][j] = 0;
        }

        // setting this since we can have 1 subset with 0 sum and choosing 0 elements
        // null set
        dp[0][0] = 1;

        for(int i=1; i<n+1; i++) {
            // starting j loop from 0 so we can count and include 0's in the calculation as well
            for(int j=0; j<s+1; j++) {
                if(arr[i-1] <= j) {
                    dp[i][j] = dp[i-1][j-arr[i-1]] + dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        
        return dp[n][s];
    }
}
