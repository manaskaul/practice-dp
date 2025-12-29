package solutions;

/*
    Problem Description:
        Polycarpus has a ribbon, its length is n. 
        He wants to cut the ribbon in a way that fulfils the following two conditions:

        After the cutting each ribbon piece should have length a, b or c.
        After the cutting the number of ribbon pieces should be maximum.

        Help Polycarpus and find the number of ribbon pieces after the required cutting.

    Constraints:
        1 <= n, a, b, c <= 4000 
        The numbers a, b and c can coincide.
 */
public class RibbonCut {
    public static void main(String[] args) {
        int n=16, a=7, b=5, c=3;
        int NEG_INF = Integer.MIN_VALUE + 1;

        int[] pieces = new int[]{a,b,c};
        int p = pieces.length;
        
        int[][] dp = new int[p+1][n+1];

        for(int j=0; j<n+1; j++) {
            dp[0][j] = NEG_INF;
        }
        for(int i=0; i<p+1; i++) {
            dp[i][0] = 0;
        }

        for(int i=1; i<p+1; i++) {
            for(int j=1; j<n+1; j++) {
                if(pieces[i-1] <= j) {
                    dp[i][j] = Math.max(
                        1 + dp[i][j-pieces[i-1]],
                        dp[i-1][j]
                    );
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        int res = dp[p][n] < 0 ? -1 : dp[p][n];

        System.out.printf("Max Ribbon Cut : %d\n", res);
    }
}

/*


        j → sum we'll achieve (n)
    i
    ↓

peices to
choose from
a or b or c

    dp[i][j] : max cuts that can be made to achieve sum j with choice[1 to i]

        0  1  2  3  4 ... 16
    0   0 -I -I -I -I ... -I
    1   0
    2   0
    3   0


*/