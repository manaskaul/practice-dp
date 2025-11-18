// package topic1_01_knapsack;

/*
 * We need to minimize the difference between sum of subset partition of array
 * say two subsets are picked with sum S1 and S2, we need to minimize |S1 - S2|
 * 
 * Intution:-
 * The sum of all the elements of array is total
 * When creating two subsets/partitions they would always sum up be on the either side of total/2
 * example for arr = [1,2,7]  total = 10
 * and the two partitions are always on the either side of total/2 = 5
 * 
 * the subset options formed would be
 *      S1 = {}         S2 = {1,2,7}
 *      S1 = {1}        S2 = {2, 7}
 *      S1 = {2}        S2 = {1,7}
 *      S1 = {1,2}      S2 = {7}
 * 
 * the difference would be minimized when the S1 and S2 are closer to total/2
 * i.e. when S1 = 5 and S2 = 5 (which in this case is not possible), the diff is 0
 *
 * we can assume that S1 is always to the left, and S2 is always to the right of total/2
 * i.e. S1 <= S2
 * and so :
 *      S1 + S2 = total
 *      S2 = total - S1
 * 
 * so S2 - S1 becomes
 * (total - S1) - S1
 * total - 2 * S1 
 * 
 * S1 - S2 = total - 2 * S1
 * 
 * now we know that all the possible values of S1 can be 0 to total/2
 * and we can calculate the dp tabulation solution to find all subsets that sum up to [0 to total/2]
 * and then find the closet S1 to the total/2 to find min difference
 */
public class MinSubsetSumDiff {
    public static void main(String[] args) {
        int[] arr = new int[]{1,6,11,5};
        // int[] arr = new int[]{1,2,4};
        // int[] arr = new int[]{1,2,7};

        int ans = findMinSubSumDiff(arr);
        System.out.printf("Minimum Subset Sum Diff : %d\n", ans);
    }

    private static int findMinSubSumDiff(int[] arr) {
        int n = arr.length;
        int total = 0;
        for(int i=0; i<n; i++) {
            total += arr[i];
        }

        int t = total/2;

        boolean[][] dp = new boolean[n+1][t+1];

        for(int j=0; j<t+1; j++) {
            dp[0][j] = false;
        }
        for(int i=0; i<n+1; i++) {
            dp[i][0] = true;
        }

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<t+1; j++) {
                if(arr[i-1] <= j) {
                    dp[i][j] = dp[i-1][j-arr[i-1]] || dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        for(int j = t; j >= 0; j--) {
            if(dp[n][j]) {
                return total - 2 * j;
            }
        }

        return 0;
    }
}
