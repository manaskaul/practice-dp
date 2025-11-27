// package topic3_longest_common_subsequence;

public class LengthOfLongestCommonSubstring {
    public static void main(String[] args) {
        String x = "abcdef";
        String y = "abxdefg";

        int n = x.length();
        int m = y.length();

        int lcsLen = longestCommonSubstring(x, y, n, m);
        System.out.printf("Length of Longest Common Substring : %d\n", lcsLen);
    }

    private static int longestCommonSubstring(String x, String y, int n, int m) {
        int[][] dp = new int[n+1][m+1];

        for(int j=0; j<m+1; j++) {
            dp[0][j] = 0;
        }
        for(int i=0; i<n+1; i++) {
            dp[i][0] = 0;
        }

        int res = 0;
        for(int i=1; i<n+1; i++) {
            for(int j=1; j<m+1; j++) {
                if(x.charAt(i-1) == y.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                    res = Math.max(res, dp[i][j]);
                } else {
                    // we want to reset the count as soon as we hit the unequal character
                    dp[i][j] = 0;
                }
            }
        }

        /**
         * NOTE: the final result is not present in dp[n][m]
         *       instead it can be anywhere in the dp matrix
         */
        

        return res;
    }
}
