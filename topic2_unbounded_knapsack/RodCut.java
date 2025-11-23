// package topic2_unbounded_knapsack;

public class RodCut {
    public static void main(String[] args) {
        int[] price = new int[]{1,5,8,9,10,17,17,20};
        
        int profit = maxProfit(price);
        System.out.printf("Max Profit : %d\n", profit);
        
        int N_2 = 8;
        int[] length_2 = new int[]{1,2,5,6};
        int[] price_2 = new int[]{1,5,10,17};
        
        int profit_2 = maxProfitWithGivenRodCutLength(N_2, length_2, price_2);
        System.out.printf("Max Profit with Given Rod Cut Lengths : %d\n", profit_2);

    }

    /*
     * Given the rod of length N and it can be cut into peices of length 1 to N
     * selling price of each cut peice given in price array
     * need to calculate the max profit
     */
    private static int maxProfit(int[] price) {
        int L = price.length;
        int N = price.length;
        
        // dp matrix of size L+1 * N+1
        // here L == N but using them seperately for clearity purpose
        // i : col will store the allowed piece length
        // j : row will store the rod length that we're trying to fill
        // i.e. dp[i][j] => max profit using peices of length 1 to i so as to fill a rod of total length j
        int[][] dp = new int[L+1][N+1];
        
        for(int j = 0; j < N+1; j++) {
            dp[0][j] = 0;
        }
        for(int i = 0; i < N+1; i++) {
            dp[i][0] = 0;
        }

        for(int i = 1; i < L+1; i++) {
            for(int j = 1; j < N+1; j++) {
                // the allowed piece length can be any 1 to L (for this case 1 to total rod length N)
                // we can directly use i instead of length[i-1]
                if(i <= j) {
                    dp[i][j] = Math.max(
                        price[i-1] + dp[i][j-i],
                        dp[i-1][j]
                    );
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        return dp[L][N];

    }

    /*
     * The length of rod is given as rodLength
     * also the allowed peice lengths that can be made to the rod
     * and the price of that particular cut peice
     * need to calculate the max profit
     */
    private static int maxProfitWithGivenRodCutLength(int rodLength, int[] pieceLength, int[] piecePrice) {
        int L = pieceLength.length;
        int N = rodLength;

        int[][] dp = new int[L+1][N+1];

        for(int j=0; j<N+1; j++) {
            dp[0][j] = 0;
        }
        for(int i=0; i<L+1; i++) {
            dp[i][0] = 0;
        }

        for(int i=1; i<L+1; i++) {
            for(int j=1; j<N+1; j++) {
                // since we're given the allowed peice length we will use that
                if(pieceLength[i-1] <= j) {
                    dp[i][j] = Math.max(
                        piecePrice[i-1] + dp[i][j-pieceLength[i-1]],
                        dp[i-1][j]
                    );
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        return dp[L][N];
    }
}

/*

    int N = 8;
    int[] length = new int[]{1,2,5,6};
    int[] price = new int[]{1,5,10,17};

        j →
    i
    ↓
        0 1 2 3 4 5 6 7 8
    0
    1
    2
    3
    4

    i : allowed peice length
    j : rod length we're trying to cut
    dp[i][j] : profit when we're trying to cut rod of length j using choice from 1 to i peices

*/