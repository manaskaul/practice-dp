// package topic3_longest_common_subsequence;

public class LongestRepeatingSubsequence {
    public static void main(String[] args) {
        String X = "AABAAB";

        int lenLongestRepeatingSubseq = lengthLongestRepSubseq(X, X);
        System.out.printf("Length of Longest Repeating Subsequence : %d\n", lenLongestRepeatingSubseq);
    }

    /**
     * The key idea behind solving this is when finding the LCS between two strings
     * or simply, when including a character into lcs while checking the two characters,
     * they should always be on different index
     * 
     * that's why we add additional check of i != j in if(X.charAt(i-1) == Y.charAt(j-1) && i != j)
     * 
     * SIDE NOTE: I had an idea of splitting the string X into even times occuring letter and odd times occuring letter
     *            but this is wrong, since it inherently breaks the order of the characters of string X. 
     *            Example: AABAAB would be split into ABA and AAB and this would give the wrong answer
     */
    // both the strings passed here are the same
    // the actual check of letter with different character happens in the LCS logic
    private static int lengthLongestRepSubseq(String X, String Y) {
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
                // This additional check prevents comparison and of same indexed letter with itself
                if(X.charAt(i-1) == Y.charAt(j-1) && i != j) {
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

        return lenLcs;
    }
}
