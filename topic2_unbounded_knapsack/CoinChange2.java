package topic2_unbounded_knapsack;

/**
 * Need to find the minimum number of coins that need to be picked in order to total sum
 */
public class CoinChange2 {
    public static void main(String[] args) {
        int[] coins = new int[]{1,2,3};
        int sum = 5;

        int minCoinsSelected = minCoins(coins, sum);
        System.out.printf("Min no of coins to get to sum : %d\n", minCoinsSelected);
    }

    // We initlize INF this way because, when making a choice to pick a coin, we add 1 to choice
    // this then prevents integer overflow
    private static final int INF = Integer.MAX_VALUE - 1;

    private static int minCoins(int[] coins, int sum) {
        int N = coins.length;
        int S = sum;

        int[][] dp = new int[N+1][S+1];

        // We're setting the first row as INF because there is no way we can achieve sum 1 to sum with 0 coins
        for(int j=0; j<S+1; j++) {
            dp[0][j] = INF;
        }
        // We're setting the first col as 0 as there is only 1 in min way we can achieve sum 0, by choosing no coin
        for(int i=0; i<N+1; i++) {
            dp[i][0] = 0;
        }

        for(int i=1; i<N+1; i++) {
            for(int j=1; j<S+1; j++) {
                if(coins[i-1] <= j) {
                    // add 1 for the choice to pick the coin
                    dp[i][j] = Math.min(
                        1 + dp[i][j-coins[i-1]],
                        dp[i-1][j]
                    );
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        // there can be scenario when no coin combination can make up the sum
        // so need to add check for it
        return dp[N][S] == INF ? -1 : dp[N][S];
    }
}

/*

    coins = [1, 2, 3];
    sum = 5;

    dp[i][j] : min no of coins that need to be used to achieve sum j using the coins[1 to i]

        j → sum we are trying to achieve
    i
    ↓

choice of coins
that can be used

        0 1 2 3 4 5
    0   0 I I I I I
    1   0
    2   0
    3   0



*/