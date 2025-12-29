package topic3_longest_common_subsequence;

/**
 * The core is LCS
 * To get the min deletions, its similar to the intution behind min insertions/deletions to convert X -> Y
 * the above had a two step process X -> LCS (deletions) & LCS -> Y (insertions)
 * using the half of the above logic, we can solve this problem
 */
public class MinDeletionsToMakePalindrome {
    public static void main(String[] args) {
        String X = "agbcba";

        int minDelToMakePalindrome = minDeletions(X);
        System.out.printf("Min Deletions to make it Palindrome : %d\n", minDelToMakePalindrome);
    }

    private static int minDeletions(String X) {
        String Y = stringReversed(X);
        
        int n = X.length();

        int[][] dp = new int[n+1][n+1];

        for(int j=0; j<n+1; j++) {
            dp[0][j] = 0;
        }
        for(int i=0; i<n+1; i++) {
            dp[i][0] = 0;
        }

        int lenLcs = 0;
        for(int i=1; i<n+1; i++) {
            for(int j=1; j<n+1; j++) {
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
