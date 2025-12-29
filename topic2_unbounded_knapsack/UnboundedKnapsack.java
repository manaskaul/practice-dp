package topic2_unbounded_knapsack;

/*
 * Unbounded Knapsack : Unlinke the 0-1 Knapsack, an item can be chosen/picked multiple times
 * Sub Problems :
 * 1. Rod Cut Problem
 * 2. Coin Change I : Count number of way to select coins and for sum
 * 3. Coin Change II : Min no of coins that can be selected to get sum
 * 4. Minimum Ribbon Cut
 */
public class UnboundedKnapsack {
    public static void main(String[] args) {
        // Generic Tabulation Solution
        int N = 2, W = 2;
        int[] arr = new int[]{1,2};

        int[][] dp = new int[N+1][W+1];
        for(int j=0; j<W+1; j++) {
            dp[0][j] = 0;
        }
        for(int i=0; i<N+1; i++) {
            dp[i][0] = 0;
        }

        for(int i=1; i<N+1; i++) {
            for(int j=1; j<W+1; j++) {
                if(arr[i-1] <= j) {
                    dp[i][j] = Math.max(
                        // this choice is updated to be dp[i][j-arr[i-1]] in Unbounded, contrary to dp[i-1][j-arr[i-1]]
                        // this is done to let the computation make a choice of picking the current element again
                        arr[i-1] + dp[i][j-arr[i-1]],
                        dp[i-1][j]
                    );
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        System.out.println(dp[N][W]);
    }
}
