package topic3_longest_common_subsequence;

public class LongestPalindromicSubsequence {
    public static void main(String[] args) {
        String X = "agbcba";

        int lenLongestPalSubseq = longestPalindromicSubseq(X);
        System.out.printf("Length of Longest Palindromic Subsequence : %d\n", lenLongestPalSubseq);
        
        String longestPalSubseq = printLongestPalindromicSubseq(X);
        System.out.printf("Longest Palindromic Subsequence : %s\n", longestPalSubseq);
    }

    private static int longestPalindromicSubseq(String X) {
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

        return lenLcs;
    }

    private static String printLongestPalindromicSubseq(String X) {
        String Y = stringReversed(X);
        
        int n = X.length();

        int[][] dp = new int[n+1][n+1];

        for(int j=0; j<n+1; j++) {
            dp[0][j] = 0;
        }
        for(int i=0; i<n+1; i++) {
            dp[i][0] = 0;
        }

        for(int i=1; i<n+1; i++) {
            for(int j=1; j<n+1; j++) {
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

        StringBuilder str = new StringBuilder();
        int i = n, j = n;
        while(i > 0 && j > 0) {
            if(X.charAt(i-1) == Y.charAt(j-1)) {
                str.append(X.charAt(i-1));
                i--;
                j--;
            } else {
                if(dp[i-1][j] < dp[i][j-1]) {
                    j--;
                } else {
                    i--;
                }
            }
        }

        return str.reverse().toString();
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
