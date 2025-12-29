package topic3_longest_common_subsequence;

/**
 * This follows the Length of Shortest Common Supersequence
 * The core logic is to find the LCS of the 2 strings and then only include the LCS once
 * printing the SCS is similar to printing LCS
 * 
 * we create the dp[][] which contains the length of LCS and then traverse the matrix to construct the string
 * the logic is essentially same as LCS:
 *      if characters are same, include it
 *      if characters are different, include the one with higher LCS length
 * but we're not finished, since there would be some string remaining after the above steps
 * we need to include all the remaining characters
 * so we include all the left over characters from both the string
 */
public class PrintShortestCommonSupersequence {
    public static void main(String[] args) {
        // String X = "ACBCF";
        // String Y = "ABCDAF";

        // String X = "DCBA";
        // String Y = "ABCD";

        String X = "ABAC";
        String Y = "CAB";

        String scs = shortestCommonSuperseq(X, Y);
        System.out.printf("Shortest Common Supersequence: %s\n", scs);
    }

    private static String shortestCommonSuperseq(String X, String Y) {
        int n = X.length();
        int m = Y.length();
        
        int[][] dp = new int[n+1][m+1];
        for(int j=0; j<m+1; j++) {
            dp[0][j] = 0;
        }
        for(int i=0; i<n+1; i++) {
            dp[i][0] = 0;
        }
        
        // STANDARD LCS COMPUTATION
        for(int i=1; i<n+1; i++) {
            for(int j=1; j<m+1; j++) {
                if(X.charAt(i-1) == Y.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.max(
                        dp[i-1][j],
                        dp[i][j-1]
                    );
                }
            }
        }

        // CONSTRUCT THE SUPERSEQUENCE - similar to LCS
        StringBuilder str = new StringBuilder();
        int i = n, j = m;
        while(i > 0 && j > 0) {
            if(X.charAt(i-1) == Y.charAt(j-1)) {
                str.append(X.charAt(i-1)); // could also do Y.charAt(j-1)
                i--;
                j--;
            } else {
                if(dp[i-1][j] < dp[i][j-1]) {
                    str.append(Y.charAt(j-1));
                    j--;
                } else {
                    str.append(X.charAt(i-1));
                    i--;
                }
            }
        }
        
        // INCLUDE THE LEFT OVER FROM X
        while(i > 0) {
            str.append(X.charAt(i-1));
            i--;
        }

        // INCLUDE THE LEFT OVER FROM Y
        while(j > 0) {
            str.append(Y.charAt(j-1));
            j--;
        }
        
        // REVERSE THE STRING
        return str.reverse().toString();
    }
}
