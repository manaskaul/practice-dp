// package topic3_longest_common_subsequence;


/**
 * If given two strings X, Y
 * Supersequence is the merged string of X and Y which will have X and Y as subsequence in it
 * We need to find the length of the shortest supersequence that can be created
 * 
 * In the worst case, just concatinating X and Y would give us the supersequence
 * and the ans would be len(X) + len(Y)
 * 
 * But we can do better
 * 
 * We can choose to include the common letters only once
 * Example:
 * 
 * X : AGGTAB
 * Y : GXTXAYB
 *                                      
 * the shortest supersequence would be 
 * 
 *  X subseq :  --- - - -
 *              AGGXTXAYB
 *  Y subseq :    -------
 * 
 * the common letters that were written once is the LCS of X and Y
 * 
 * so the length of shortest common supersequence = len(X) + len(Y) - len(LCS(X,Y))
 * 
 * len(LCS(X,Y)) is subtracted once because it is counted twice in len(X) + len(Y)
 */
public class ShortestCommonSupersequence {
    public static void main(String[] args) {
        String X = "ABAC";
        String Y = "CAB";

        int lengthLcs = lengthLongestCommonSubseq(X, Y);

        int lengthShortestCommonSupersequence = X.length() + Y.length() - lengthLcs;
        System.out.printf("Length of Shortest Common Supersequence: %d\n", lengthShortestCommonSupersequence);
    }

    private static int lengthLongestCommonSubseq(String X, String Y) {
        int n = X.length();
        int m = Y.length();
        
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
                if(X.charAt(i-1) == Y.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                    res = Math.max(dp[i][j], res);
                } else {
                    dp[i][j] = Math.max(
                        dp[i-1][j],
                        dp[i][j-1]
                    );
                }
            }
        }

        System.out.printf("\n");
        for(int i=0; i<n+1; i++) {
            for(int j=0; j<m+1; j++) {
                System.out.printf("%d ", dp[i][j]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");
        
        return res;
    }
}
