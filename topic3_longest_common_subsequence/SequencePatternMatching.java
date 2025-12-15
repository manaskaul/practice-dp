// package topic3_longest_common_subsequence;

/**
 * Given two strings X and Y
 * we need to return TRUE when X is a subsequence of Y and FALSE otherwise
 * essentially, X can be created from subsequence of Y
 * 
 * This is a simple LCS logic
 * Finding the LCS of X and Y => LCS(X,Y)
 * 
 * return TRUE if len(X) == LCS(X,Y)
 * else return FALSE
 * 
 */
public class SequencePatternMatching {
    public static void main(String[] args) {
        String X = "AXB";
        String Y = "ACXDPB";

        boolean doMatch = patternMatch(X, Y);
        System.out.printf("Does X and Y pattern match? : %B\n", doMatch);
    }

    private static boolean patternMatch(String X, String Y) {
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

        return n == lenLcs;
    }
}
