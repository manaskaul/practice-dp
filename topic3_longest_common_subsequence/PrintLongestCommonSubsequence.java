// package topic3_longest_common_subsequence;

public class PrintLongestCommonSubsequence {
    public static void main(String[] args) {
        // String x = "acbcf";
        // String y = "abcdaf";

        String x = "agbcba";
        String y = "abcbga";

        int n = x.length();
        int m = y.length();

        String res = printLcs(x, y, n, m);
        System.out.printf("Longest Common Subsequence : %s\n", res);
    }

    private static String printLcs(String x, String y, int n, int m) {
        int[][] dp = new int[n+1][m+1];
        
        for(int j=0; j<m+1; j++) {
            dp[0][j] = 0;
        }
        for(int i=0; i<n+1; i++) {
            dp[i][0] = 0;
        }

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<m+1; j++) {
                if(x.charAt(i-1) == y.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                }
                else {
                    dp[i][j] = Math.max(
                        dp[i-1][j],
                        dp[i][j-1]
                    );
                }
            }
        }

        StringBuilder str = new StringBuilder();
        int i = n, j = m;
        while(i > 0 && j > 0) {
            if(x.charAt(i-1) == y.charAt(j-1)) {
                str.append(x.charAt(i-1));
                i--;
                j--;
            } else {
                if(dp[i-1][j] > dp[i][j-1]) {
                    i--;
                } else {
                    j--;
                }
            }
        }

        return str.reverse().toString();
    }
}
