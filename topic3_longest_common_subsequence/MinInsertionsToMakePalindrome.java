package topic3_longest_common_subsequence;

/**
 * Similar to the Min Deletions Needed problem
 * core of the problem lies in finding the LCS
 */
public class MinInsertionsToMakePalindrome {
    public static void main(String[] args) {
        String X = "AEBCBDA";

        int minInsertionsNeeded = minInsertions(X);
        System.out.printf("Min Instertions to make Palindrome : %d\n", minInsertionsNeeded);
    }

    private static int minInsertions(String X) {
        String Y = stringReversed(X);

        int n = X.length();
        int m = Y.length();

        int[][] dp = new int[n+1][m+1];

        for(int j=0; j<m+1; j++) {
            dp[0][j] = 0;
        }
        for(int i=0; i<n+1; i++) {
            dp[i][0] = 0;
        }

        int lenLcs = 0;
        for(int i=1; i<n+1; i++) {
            for(int j=1; j<m+1; j++) {
                if(X.charAt(i-1) == Y.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                    lenLcs = Math.max(lenLcs, dp[i][j]);
                } else {
                    dp[i][j] = Math.max(
                        dp[i-1][j],
                        dp[i][j-1]
                    );
                }
            }
        }

        return n - lenLcs;
    }

    private static String stringReversed(String s) {
        StringBuilder str = new StringBuilder();
        int i = s.length() - 1;
        while(i >= 0) {
            str.append(s.charAt(i));
            i--;
        }
        return str.toString();
    }
}
