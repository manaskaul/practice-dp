// package topic2_unbounded_knapsack;

/**
 * Given some array of coins representing the denomination and a sum
 * what is the max number of unique ways to get to sum, with the given coins
 * a coin can be used multiple times
 */
public class CoinChange1 {
    public static void main(String[] args) {
        int[] coins = new int[]{1,2,3};
        int sum = 5;

        int noOfWaysToGetSum = countCoinVariations(coins, sum);
        System.out.printf("No of ways to get to sum : %d\n", noOfWaysToGetSum);
    }

    private static int countCoinVariations(int[] coins, int sum) {
        int N = coins.length;
        int S = sum;

        int[][] dp = new int[N+1][S+1];
        
        for(int j=0; j<S+1; j++) {
            dp[0][j] = 0;
        }
        for(int i=0; i<N+1; i++) {
            dp[i][0] = 1;
        }

        for(int i=1; i<N+1; i++) {
            for(int j=1; j<S+1; j++) {
                // the denomonation of the coin should be less or equal to the curr sum we're checking
                if(coins[i-1] <= j) {
                    dp[i][j] = dp[i][j-coins[i-1]] + dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        
        return dp[N][S];
    }
}

/*

    coins = [1, 2, 3];
    sum = 5;

    dp[i][j] : number of ways to achieve sum j when choice from coins[1 to i]

        j → sum we are trying to achieve
    i
    ↓

choice of coins
that can be used

        0 1 2 3 4 5
    0   1 0 0 0 0 0
    1   1
    2   1
    3   1

*/
