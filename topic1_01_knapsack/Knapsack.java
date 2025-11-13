// package topic1_01_knapsack;

public class Knapsack {
    public static void main(String[] args) {
        int[] wgt = new int[]{1, 3, 4, 5}; // Weight Array
        int[] val = new int[]{1, 4, 5, 7}; // Price Array
        int C = 7; // Max capacity the knapsack can hold
        
        int N = wgt.length;

        // RECURSIVE SOLUTION
        int maxProfit_1 = knapsack(wgt, val, N, C);    
        System.out.printf("Max Profit Rec = %d\n", maxProfit_1);
        
        // RECURSIVE SOLUTION + MEMOIZATION
        dp_1 = new int[N+1][C+1];
        for(int i = 0; i < N+1; i++) {
            for(int j = 0; j < C+1; j++) {
                dp_1[i][j] = -1;
            }
        }
        int maxProfit_2 = knapsackTD(wgt, val, N, C);    
        System.out.printf("Max Profit Mem = %d\n", maxProfit_2);
        
        // TABULATION
        dp_2 = new int[N+1][C+1];
        int maxProfit_3 = knapsackBU(wgt, val, N, C);    
        System.out.printf("Max Profit Tab = %d\n", maxProfit_3);
    }


    private static int knapsack(int[] wgt, int[] val, int n, int c) {
        // BASE CONDITION : Smallest valid input
        if(n == 0 || c == 0) {
            return 0;
        }

        // CHOICE DIAGRAM : Choose to pick up the item or to not pick up the item
        if(wgt[n-1] <= c) {
            return Math.max(
                val[n-1] + knapsack(wgt, val, n-1, c - wgt[n-1]),
                knapsack(wgt, val, n-1, c)
            );
        } else {
            return knapsack(wgt, val, n-1, c);
        }
    }
    

    // Memoization array to hold the N vs Capacity combinations
    private static int[][] dp_1; // N+1 x C+1

    private static int knapsackTD(int[] wgt, int[] val, int n, int c) {
        // BASE CONDITION : Smallest valid input
        if(n == 0 || c == 0) {
            return 0;
        }

        // MEMOIZATION CHECK : If the value has been already computed in some other recursive call
        if(dp_1[n][c] != -1) {
            return dp_1[n][c];
        }

        // CHOICE DIAGRAM : Choose to pick up the item or to not pick up the item
        if(wgt[n-1] <= c) {
            return dp_1[n][c] = Math.max(
                val[n-1] + knapsackTD(wgt, val, n-1, c - wgt[n-1]),
                knapsackTD(wgt, val, n-1, c)
            );
        } else {
            return dp_1[n][c] = knapsackTD(wgt, val, n-1, c);
        }
    }


    // Tabulation array to hold the N vs Capacity combinations
    private static int[][] dp_2; // N+1 x C+1

    private static int knapsackBU(int[] wgt, int[] val, int n, int c) {
        // INITLIZATION
        for(int i=0; i<n+1; i++) {
            dp_2[i][0] = 0;
        }
        for(int j=0; j<c+1; j++) {
            dp_2[0][j] = 0;
        }
        
        // RECURSIVE -> ITERATIVE
        for(int i=1; i<n+1; i++) {
            for(int j=1; j<c+1; j++) {
                if(wgt[i-1] <= j) {
                    dp_2[i][j] = Math.max(
                        val[i-1] + dp_2[i-1][j - wgt[i-1]],
                        dp_2[i-1][j]
                    );
                } else {
                    dp_2[i][j] = dp_2[i-1][j];
                }
            }
        }

        return dp_2[n][c];
    }

    /*
        int[] wgt = new int[]{0, 1, 3, 4, 5};
        int[] val = new int[]{0, 1, 4, 5, 7};
        int C = 7;
        // THIS WOULD ONLY WORK IF 0th element is wgt and val is considered as dummy or 0
        // this does not work with natural ordering of the 0-based arrays
        for (i = 1 -> N) {
            for (j = 1 -> C) {
                // current weight is smaller than the remaining capacity
                if(wt[i] <= j) {
                    dp[i][j] = MAX(
                        // select the item
                        val[i] + dp[i-1][j-wt[i]],
                        // do not select the item
                        dp[i-1][j]
                    )
                }
                // current weight is bigger than the remaining capacity
                else {
                    dp[i][j] = dp[i-1][j]
                }
            }
        }

        return dp[N][C]
     */
}