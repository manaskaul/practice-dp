package topic1_01_knapsack;

public class CountSubsetSumWithGivenDifference {
    public static void main(String[] args) {
        // int[] arr = new int[]{1,1,2,3};
        // int diff = 1;
        
        
        int[] arr = new int[]{0,1,2,2,2,3,0,3,0,1};
        int diff = 12;

        int count = countSubset(arr, diff);
        System.out.printf("Count of Subset Sum with given Difference : %d\n", count);
    }

    /*
     * S1 <= S2
     * 
     * S1 + S2 = total
     * S2 = total - S1
     * 
     * we want,
     * S2 - S1 = diff
     * (total - S1) - S1 = diff
     * total - 2 * S1 = diff
     * total - diff = 2 * S1
     * S1 = (total - diff) ÷ 2
     * 
     * need to find all subsets which sum to (total-diff)/2
     */
    private static int countSubset(int[] arr, int diff) {
        int total = 0;
        for(int i=0; i<arr.length; i++) {
            total += arr[i];
        }

        // Optional but sensible: work with |diff|
        diff = Math.abs(diff);

        // If diff is impossible relative to total, no ways
        if (diff > total) {
            return 0;
        }

        // total - diff must be even for S1 to be integer
        if ((total - diff) % 2 != 0) {
            return 0;
        }
        
        int n = arr.length;
        int s = (total - diff)/2;

        int[][] dp = new int[n+1][s+1];
        for(int j=0; j<s+1; j++) {
            dp[0][j] = 0;
        }
        for(int i=0; i<n+1; i++) {
            dp[i][0] = 1;
        }

        for(int i=1; i<n+1; i++) {
            // if array has 0's need to count the 0 sum as well, hence j loop starts from 0
            // Counting subsets requires updating all sums, including 0
            // Zero values create extra subsets for the same sum
            // So dp transitions must run for j = 0
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
