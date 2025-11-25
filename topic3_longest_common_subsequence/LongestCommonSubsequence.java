// package topic3_longest_common_subsequence;

/**
 * 01. Longest Common Substring
 * 02. Print Longest Common Subsequence
 * 03. Shortest Common Supersequence
 * 04. Print Shortest Common Supersequence
 * 05. Min no of insertions/deletions a -> b
 * 06. Longest Repeating Subsequence
 * 07. Length of Longest Subsequence of a which is a substring in b
 * 08. Subsequence Pattern Matching
 * 09. Count how many times a appears as subsequence in b
 * 10. Longest Palindromic Subsequence
 * 11. Longest Palindromic Substring
 * 12. Count of Palindromic Substring
 * 13. Minimum no of deletions in a string to make it palindrome
 * 14. Minimum no of insertions in a string to make it palindrome
 */
public class LongestCommonSubsequence {
    public static void main(String[] args) {
        String x = "abcdgh";
        String y = "abedfhr";

        int n = x.length();
        int m = y.length();

        int res = findLengthOfLcs(x, y, n, m);
        System.out.printf("Length of Longest Common Subsequence : %d\n", res);

        dp_td = new int[n+1][m+1];
        for(int i=0; i<n+1; i++) {
            for(int j=0; j<m+1; j++) {
                dp_td[i][j] = -1;
            }
        }
        int res_td = findLengthOfLcsTD(x, y, n, m);
        System.out.printf("Length of Longest Common Subsequence TD : %d\n", res_td);

        int res_bu = findLengthOfLcsBU(x, y, n, m);
        System.out.printf("Length of Longest Common Subsequence TD : %d\n", res_bu);
    }

    /**
     * 
     * @param x : string 1
     * @param y : string 2
     * @param n : length of string 1
     * @param m : length of string 2
     * @return Length of Longest Common Subsequence
     * 
     * This is the recursive solution of LCS
     */
    private static int findLengthOfLcs(String x, String y, int n, int m) {
        if(n == 0 || m == 0) {
            return 0;
        }

        if(areCharEqual(x.charAt(n-1), y.charAt(m-1))) {
            return 1 + findLengthOfLcs(x, y, n-1, m-1);
        } else {
            return Math.max(
                findLengthOfLcs(x, y, n, m-1),
                findLengthOfLcs(x, y, n-1, m)
            );
        }
    }

    private static int[][] dp_td;

    private static int findLengthOfLcsTD(String x, String y, int n, int m) {
        if(n == 0 || m == 0) {
            return 0;
        }

        if(dp_td[n][m] != -1) {
            return dp_td[n][m];
        }

        if(areCharEqual(x.charAt(n-1), y.charAt(m-1))) {
            dp_td[n][m] = 1 + findLengthOfLcsTD(x, y, n-1, m-1);
        } else {
            dp_td[n][m] = Math.max(
                findLengthOfLcsTD(x, y, n, m-1),
                findLengthOfLcsTD(x, y, n-1, m)
            );
        }

        return dp_td[n][m];
    }

    /**
     * x = "abcdgh";
     * y = "abedfhr";
     * 
     *          j → len string s1
     *      i
     *      ↓
     * len string s2
     * 
     *            * a b c d g h
     *            0 1 2 3 4 5 6
     * 
     *      * 0   0 0 0 0 0 0 0
     *      a 1   0
     *      b 2   0
     *      e 3   0
     *      d 4   0
     *      f 5   0
     *      h 6   0
     *      r 7   0
     * 
     */
    private static int findLengthOfLcsBU(String x, String y, int n, int m) {
        int[][] dp_bu = new int[n+1][m+1];

        for(int j=0; j<m+1; j++) {
            dp_bu[0][j] = 0;
        }
        for(int i=0; i<n+1; i++) {
            dp_bu[i][0] = 0;
        }

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<m+1; j++) {
                if(areCharEqual(x.charAt(i-1), y.charAt(j-1))) {
                    dp_bu[i][j] = 1 + dp_bu[i-1][j-1];
                } else {
                    dp_bu[i][j] = Math.max(
                        dp_bu[i][j-1],
                        dp_bu[i-1][j]
                    );
                }
            }
        }

        return dp_bu[n][m];
    }

    private static boolean areCharEqual(char c1, char c2) {
        return c1 == c2;
    }
}
