// package topic1_01_knapsack;

public class Knapsack {
    public static void main(String[] args) {
        int[] wgt = new int[]{1, 3, 4, 5}; // Weight Array
        int[] val = new int[]{1, 4, 5, 7}; // Price Array
        int C = 7; // Max capacity the knapsack can hold
        
        int N = wgt.length;
        
        dp = new int[N+1][C+1];
        for(int i = 0; i < N+1; i++) {
            for(int j = 0; j < C+1; j++) {
                dp[i][j] = -1;
            }
        }

        int maxProfit = knapsack(wgt, val, N, C);    
        System.out.printf("Max Profit = %d\n", maxProfit);
    }
    
    // Memoization array to hold the val vs wgt combinations
    private static int[][] dp; // N+1 x C+1

    private static int knapsack(int[] wgt, int[] val, int n, int capacity) {
        // BASE CONDITION : Smallest valid input
        if(n == 0 || capacity == 0) {
            return 0;
        }

        // MEMOIZATION CHECK : If the value has been already coputed in some other recursive call
        if(dp[n][capacity] != -1) {
            return dp[n][capacity];
        }

        // CHOICE DIAGRAM : Choose to pick up the item or to not pick up the item
        if(wgt[n-1] <= capacity) {
            return dp[n][capacity] = Math.max(
                val[n-1] + knapsack(wgt, val, n-1, capacity-wgt[n-1]),
                knapsack(wgt, val, n-1, capacity)
            );
        } else {
            return dp[n][capacity] = knapsack(wgt, val, n-1, capacity);
        }
    }
}