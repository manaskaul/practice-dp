// package topic4_matrix_chain_multiplication;

/**
 * The core of this problem is the minimum number of attempts that it would take
 * to find the threshold floor in the worst scenario
 * 
 * By this it means that for a building with f floors, 
 * we need to find the min number of attempts that it would take to find t
 * when we're given e eggs
 * 
 * The problem is not to find the floor t, but attempts it would take to find the floor t, in worst case, whatever it maybe and minimize that
 * It is a worst-case optimization problem, not a search problem.
 * 
 * So the most basic stratergy is to test dropping the egg from every floor one by one starting from 1 till f
 * Now when we drop the floor from every floor, there are two possibilities - Egg Breaks OR Egg doesn't Break
 * 
 * Considering both these possibilities after throwing the egg from a floor k
 * If the egg drops, we know the threshold floor is somewhere in the below
 * If the egg doesn't break, we know the threshold floor is somewhere above
 * 
 * And the recursive function takes it over from there
 */
public class EggDropping {
    public static void main(String[] args) {
        int f = 5;
        int e = 3;

        int attemptsRec = minAttemptsRec(f, e);
        System.out.printf("Min Attempts needed to find threashold floor (Recursive) : %d\n", attemptsRec);

        int attemptsMemo = minAttemptsTD(f, e);
        System.out.printf("Min Attempts needed to find threashold floor (Memoization) : %d\n", attemptsMemo);

        int attemptsOptimized = minAttemptsBinarySearchOptimized(f, e);
        System.out.printf("Min Attempts needed to find threashold floor (Binary Search Optimized) : %d\n", attemptsOptimized);

        // TODO: Revisit this approach and solution
        int attemptsTabulation = minAttemptsOptimizedBU(f, e);
        System.out.printf("Min Attempts needed to find threashold floor (Tabulation) : %d\n", attemptsTabulation);
    }

    /**
     * The method only takes the total floors in the building (or the floor need to test for threshold)
     * and the eggs given (or the eggs remaining)
     */
    private static int minAttemptsRec(int f, int e) {
        // BASE CASE 1:
        // If there are 0 or 1 floors, attempts needed = number of floors
        // 0 floors → 0 attempts
        // 1 floor  → 1 attempt
        if(f == 0 || f == 1) {
            return f;
        }

        // If we only have 1 egg to test (or 1 egg left), then in worst case to find the threshold floor
        // we need to test each and every floor
        // The worst case here would be when threshold floor is the top floor
        if(e == 1) {
            return f;
        }

        int minTotalAttempts = Integer.MAX_VALUE;

        // Try dropping the egg from every floor k (1 to f)
        // and calculate the worst-case attempts for each choice.
        // k is the floor from which we choose to drop the egg from
        for(int k = 1; k <= f; k++) {

            // Dropping the egg, it breaks
            // The threshold floor must be below
            // Total floors to check now = k-1
            // Total eggs remaining = e-1
            int thresholdIsBelow = minAttemptsRec(k-1, e-1);

            // Dropping the egg, it did not break
            // The threshold floor must be above
            // Total floors to check = f-k
            // Total eggs remaining = e
            int thresholdIsAbove = minAttemptsRec(f-k, e);

            // In all cases we need worst case to find the threshold floor
            // Since we do NOT control whether the egg breaks or not, we must consider the WORST case.
            // Hence checking MAX
            int attemptsInWorstCase = Math.max(thresholdIsBelow, thresholdIsAbove);

            // This took 1 attempt to check
            // hence the combination cost is 1
            int combine = 1;


            // Attempts it took to find the threshold flooe
            // This will be multiple attempts
            int currAtempts = attemptsInWorstCase + combine;
            
            // We choose the floor k that gives the minimum number of attempts in worst case
            // We take the MIN from all the k attempts it took to find the threshold floor in worst case
            minTotalAttempts = Math.min(minTotalAttempts, currAtempts);
        }

        return minTotalAttempts;
    }

    private static int[][] dp;

    private static int minAttemptsTD(int f, int e) {
        dp = new int[f+1][e+1];

        for(int i=0; i<=f; i++) {
            for(int j=0; j<=e; j++) {
                dp[i][j] = -1;
            }
        }

        return minAttemptsMemo(f, e);
    }

    private static int minAttemptsMemo(int f, int e) {
        if(f == 0 || f == 1) {
            return f;
        }

        if(e == 1) {
            return f;
        }

        if(dp[f][e] != -1) {
            return dp[f][e];
        }

        int minAttempts = Integer.MAX_VALUE;

        for(int k = 1; k <= f; k++) {
            // egg breaks : k-1, e-1
            int thresholdIsBelow = minAttemptsMemo(k-1, e-1);
            // egg survives : f-k, e
            int thresholdIsAbove = minAttemptsMemo(f-k, e);
            
            int attemptsInWorstCase = Math.max(thresholdIsBelow, thresholdIsAbove);

            int combine = 1;

            int currAtempts = attemptsInWorstCase + combine;

            minAttempts = Math.min(minAttempts, currAtempts);
        }

        dp[f][e] = minAttempts;

        return minAttempts;
    }

    private static int[][] dp_bin;

    private static int minAttemptsBinarySearchOptimized(int f, int e) {
        dp_bin = new int[f+1][e+1];

        for(int i=0; i<=f; i++) {
            for(int j=0; j<=e; j++) {
                dp_bin[i][j] = -1;
            }
        }

        return minAttemptsBinarySearch(f, e);
    }

    /**
     * The core of the solution is choosing the fist kth floor to drop the egg from
     * Since we want to compute for the worst case, it could be either of the Egg breaks OR Egg doesn't break
     * 
     * for any k, there are two costs for dropping the egg from that floor
     * Break Cost : Threshold Floor is BELOW k 
     * Survive Cost : Threshold Floor is ABOVE k
     * and the actual value of k is larger of the two values
     * 
     * Now this observation can be deduced from selecting the floor k
     * If k increases :
     *      Break Cost Increases, as there are more floors (k-1) to test
     *      Survive Cost Decreases, as there are less floor remaining (f-k) to test
     *
     * Now since we check for the worst case, we compare and pick the MAX from break cost and survive cost
     * This implies, when selecting k :
     * At lower k
     *      Break cost is small
     *      Survive cost is large
     *          Worst case is survive
     * At higher k
     *      Break cost is large
     *      Survive cost us small
     *          Worst case is break
     * 
     * And when k is somewhere in the middle, worst case is minimized overall
     * 
     * 
     * Now this is where binary search based solution comes into play
     * Using binary search we check which side we're currently more leaning towards
     * the Break side or the Survive side
     * and balance by going in the opposite direction
     * essentially we try to move optimally towards the middle of break & survive scenarios
     * we move towards the balance point
     * 
     * So the job of the binary search is to choose the kth floor optimally
     * and balance every time between the break & survive scenario
     * Choose the first drop so that “egg breaks” and “egg survives” are equally bad
     * This is the optimization for calculation of attemptsInWorstCase
    */
    private static int minAttemptsBinarySearch(int f, int e) {
        if(f == 0 || f == 1) {
            return f;
        }

        if(e == 1) {
            return f;
        }

        if(dp_bin[f][e] != -1) {
            return dp_bin[f][e];
        }

        int minAttempts = Integer.MAX_VALUE;

        // start by check all the available floors (search space)
        int low = 1, high = f;

        while(low <= high) {
            // find the middle point
            int mid = low + (high-low)/2;

            // calculate the worst case of the egg breaking at this mid point
            // egg breaks : k-1, e-1
            int thresholdIsBelow = minAttemptsBinarySearch(mid-1, e-1);
            
            // calculate the worst case of the egg surviving at this mid point
            // egg survives : f-k, e
            int thresholdIsAbove = minAttemptsBinarySearch(f-mid, e);

            // attempt in worst case is max of the egg breaks and egg survives scenarios
            int attemptsInWorstCase = Math.max(thresholdIsBelow, thresholdIsAbove);

            int combine = 1;

            int currAtempts = attemptsInWorstCase + combine;

            minAttempts = Math.min(minAttempts, currAtempts);

            /**
             * Depending on the attempts needed for both scenarios
             * Move towards balance:
             * - If breaking is worse than surviving, the mid point (or the kth floor) choosen is too high
             *   We'll have worse case (number of attempts) of breaking the eggs from this floor and this is not optimal
             *   optimal move is to move DOWN
             * - If surviving is worse than breaking, the mid point (or the kth floor) choosen is too low
             *   We'll have worse case (number of attempts) of surviving the eggs from this floor and this is not optimal
             *   optimal move is to move UP
             */
            // attemts in which egg breaks > attempts in which egg survives : move DOWN
            if (thresholdIsBelow > thresholdIsAbove) {
                high = mid - 1;
            } 
            // attemts in which egg breaks < attempts in which egg survives : move UP
            else {
                low = mid + 1;
            }
        }
        
        dp_bin[f][e] = minAttempts;

        return minAttempts;
    }

    /**
     * Bottom Up Appraoch
     * 
     * INTUTION :
     * 
     * Instead of asking:
     * “With f floors and e eggs, what’s the minimum attempts?”
     * 
     * We ask:
     * “With m attempts and e eggs, how many floors can I GUARANTEE to test?”
     * 
     * This is a guarantee, not a best case.
     * 
     * dp[m][e] = maximum number of floors
     *            that can be tested
     *            with m moves and e eggs
     *            in the worst case
     */
    public static int minAttemptsOptimizedBU(int floors, int eggs) {

        // Edge cases
        if (floors == 0 || floors == 1) {
            return floors;
        }
        
        if (eggs == 1) {
            return floors;
        }

        // dp[m][e] = max floors testable with m moves and e eggs
        // We only need up to 'floors' moves in the worst case
        long[][] dp = new long[floors + 1][eggs + 1];

        int moves = 0;

        // Increase moves until we can cover all floors
        while (dp[moves][eggs] < floors) {
            moves++;

            for (int e = 1; e <= eggs; e++) {
                dp[moves][e] =
                        dp[moves - 1][e - 1]   // egg breaks → floors below
                      + dp[moves - 1][e]       // egg survives → floors above
                      + 1;                     // current floor
            }
        }

        return moves;
    }
}
