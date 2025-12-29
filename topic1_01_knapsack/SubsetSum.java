package topic1_01_knapsack;

// TC : O(N * T)
// TC : O(N * T)
public class SubsetSum {
    public static void main(String[] args) {
        int[] arr = new int[]{2,3,7,8,10};
        int target = 11;

        boolean isSubsetPossible = findSubset(arr, arr.length, target);
        System.out.printf("Subset Sum Possible : %B\n", isSubsetPossible);


        int N = arr.length;
        dp_1 = new int[N+1][target+1];
        for(int i=0; i<N+1; i++) {
            for(int j=0; j<target+1; j++) {
                dp_1[i][j] = 0;
            }
        }
        boolean isSubsetPossibleTD = findSubsetTD(arr, N, target);
        System.out.printf("Subset Sum Possible TD : %B\n", isSubsetPossibleTD);


        boolean isSubsetPossibleBU = findSubsetBU(arr, N, target);
        System.out.printf("Subset Sum Possible BU : %B\n", isSubsetPossibleBU);
    }

    private static boolean findSubset(int[] arr, int n, int t) {
        // subset found
        if(t == 0) {
            return true;
        }
        
        // subset not found and no items left
        if(n == 0) {
            return false;
        }

        if(arr[n-1] <= t) {
            return findSubset(arr, n-1, t - arr[n-1]) || findSubset(arr, n-1, t);
        } else {
            return findSubset(arr, n-1, t);
        }
    }

    // -1 : false 
    // +1 : true
    //  0 : undefined
    private static int[][] dp_1; // n+1 x t+1

    private static boolean findSubsetTD(int[] arr, int n, int t) {
        if(t == 0) {
            return true;
        }
        
        if(n == 0) {
            return false;
        }

        if(dp_1[n][t] != 0) {
            // return false for -1 and true for +1
            return dp_1[n][t] == 1;
        }

        boolean res = false;

        if(arr[n-1] <= t) {
            res = findSubset(arr, n-1, t - arr[n-1]) || findSubset(arr, n-1, t);
        } else {
            res = findSubset(arr, n-1, t);
        }

        dp_1[n][t] = res ? 1 : -1;

        return res;
    }

    /*
     *      j → t+1
     * i
     * ↓            0   1   2   3   4   5   ...
     * n+1      0   T   F   F   F   F   F   ...
     *          1   T
     *          2   T
     *          3   T
     *          4   T
     *          5   T
     * 
     * i : no of elements choosen from list arr[]
     * j : target sum 
     */
    private static boolean[][] dp_2; // n+1 x t+1

    private static boolean findSubsetBU(int[] arr, int n, int t) {
        // Initlize dp
        dp_2 = new boolean[n+1][t+1];
        // first row is set with false since no way to get a sum > 0 when choosing 0 elements
        for(int j=0; j<t+1; j++) {
            dp_2[0][j] = false;
        }
        // first column is set with true as it's always possible to get 0 sum by choosing no elements
        // element at dp[0][0] is overriden here
        // so order of initlizing the row & col matters
        for(int i=0; i<n+1; i++) {
            dp_2[i][0] = true;
        }

        // recursive -> tabulation
        // start for {1,1} as first row and col are already computed and initlized
        for(int i=1; i<n+1; i++) {
            for(int j=1; j<t+1; j++) {
                // choice of including the current element only when it's smaller than the current sum
                if(arr[i-1] <= j) {
                    dp_2[i][j] = dp_2[i-1][j - arr[i-1]] || dp_2[i-1][j];
                } else {
                    dp_2[i][j] = dp_2[i-1][j];
                }
            }
        }

        return dp_2[n][t];
    }
}
