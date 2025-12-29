package topic3_longest_common_subsequence;


/**
 * The core logic to solve this is LCS
 * we find the LCS between the two strings
 * the LCS can be treated as the characters that can be untouched and need no changes
 * rest characters need to be either inserted or deleted
 * 
 * The final answer can be calculated as:
 * ans = len(X) + len(Y) - 2 * LCS(X,Y)
 * 
 * Another way of arriving at this solution:
 * When we find the LCS of the 2 strings, we get the common string in the two
 * Now the conversion of string X -> string Y can be looked at as
 * string X -> string LCS -> string Y
 * 
 * this is a two step process
 * first convert string X to string LCS, which essentially means deletions
 * then convert string LCS to string Y, which essentially means insertions
 * 
 * X -> LCS would be len(X) - LCS(X,Y) deletions
 * LCS -> Y would be len(Y) - LCS(X,Y) insertions
 * 
 * and so it would be = [len(X) - LCS(X,Y)] + [len(Y) - LCS(X,Y)] = len(X) + len(Y) - 2 * LCS(X,Y)
 */
public class MinInsertionsDeletions {
    public static void main(String[] args) {
        // String X = "heap";
        // String Y = "pea";

        String X = "abc";
        String Y = "abc";

        int minInsertionsOrDeletions = minOperations(X, Y);
        System.out.printf("Min Insertions/Deletions needed : %d\n", minInsertionsOrDeletions);
        
    }
    private static int minOperations(String X, String Y) {
        int n = X.length();
        int m = Y.length();

        int[][] dp = new int[n+1][m+1];

        for(int j=0; j<m+1; j++) {
            dp[0][j] = 0;
        }
        for(int i=0; i<n+1; i++) {
            dp[i][0] = 0;
        }

        int lcsLen = 0;
        for(int i=1; i<n+1; i++) {
            for(int j=1; j<m+1; j++) {
                if(X.charAt(i-1) == Y.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                    lcsLen = Math.max(lcsLen, dp[i][j]);
                } else {
                    dp[i][j] = Math.max(
                        dp[i-1][j],
                        dp[i][j-1]
                    );
                }
            }
        }

        return n + m - (2 * lcsLen);
    }
}
