package topic4_matrix_chain_multiplication;

public class PalindromePartitioning {
    public static void main(String[] args) {
        // String s = "NITIN";
        String s = "ABCB";

        /* PHASE-1 */
        // This is the basic recursive solution
        int minPartitions = partitionRecursive(s, 0, s.length()-1);
        System.out.printf("Min Partitions needed (Recursive) : %d\n", minPartitions);

        /* PHASE-2 */
        // This is the memoized solution for the recursive code
        int minPartitionsTd = partitionTd(s);
        System.out.printf("Min Partitions needed (Memoized) : %d\n", minPartitionsTd);

        /* PHASE-3 */
        // The above two solutions have a time complexity of O(n^2)
        // This is because even after the memoized solution, 
        // the palindrome check at every step add an extra O(n^2) complexity
        // This can be optimized further by precomputing the palindromes
        precompIsPalindromeTD(s);

        precompIsPalindromeBU(s);

        /* PHASE-4 */
        // The actual computation can now be done in an optimized way
        // the palindrome check for a substing s[i .. j] can be done in O(1)
        int minPartitionsRecOptimized = partitionRecursiveOptimized(s, 0, s.length()-1);
        System.out.printf("Min Partitions needed (Optimized Recursive) : %d\n", minPartitionsRecOptimized);

        /* PHASE-2 */
        // This is the memoized solution for the recursive code
        int minPartitionsTdOptimized = partitionTdOptimized(s);
        System.out.printf("Min Partitions needed (Optimized Memoized) : %d\n", minPartitionsTdOptimized);

    }

/* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx */
    
    private static int partitionRecursive(String s, int i, int j) {
        /* BASE CONDITION */
        // First Invalid Input : Cant create a partition for this case
        if(i >= j) {
            return 0;
        }

        // Additional Base Condition : check if s[i .. j] is already a palindrome
        // no need to do recursive computation if it is
        if(isPalindrome(s, i, j)) {
            return 0;
        }

        /* RECURSIVE SOLUTION */
        int minParts = Integer.MAX_VALUE;

        for(int k = i; k <= j-1; k++) {
            // Solve the left subproblem s[i .. k]
            int leftSubProb = partitionRecursive(s, i, k);
            
            // Solve the right subproblem s[k+1 .. j]
            int rightSubProb = partitionRecursive(s, k+1, j);
            
            // If partition is possible the combination will take 1 partition
            int combine = 1;

            int currParts = leftSubProb + rightSubProb + combine;

            // store the minimum across all the computations
            minParts = Math.min(minParts, currParts);
        }

        return minParts;
    }

    private static int[][] dp;

    private static int partitionTd(String s) {
        int n = s.length();
        dp = new int[n][n];

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                dp[i][j] = -1;
            }
        }

        return partitionMemo(s, 0, n-1);
    }

    private static int partitionMemo(String s, int i, int j) {
        // BASE CONDITION - First invalid input
        // same as the recursive solution
        if(i >= j) {
            return 0;
        }

        // If the input is valid, check if the memo exists
        if(dp[i][j] != -1) {
            return dp[i][j];
        }

        // Check the second base condition
        // This is done after the memo check to optimize the solution
        // and reduce the computation time of unnecessary palindrome check of already computed node
        if(isPalindrome(s, i, j)) {
            dp[i][j] = 0;
            return 0;
        }

        int minParts = Integer.MAX_VALUE;
        for(int k = i; k <= j-1; k++) {
            // Additional memo checks for both left and right sub problems
            // check if it was already computed and recursive call if not
            int leftSubProb = dp[i][k] != -1 ? dp[i][k] : partitionMemo(s, i, k);
            int rightSubProb = dp[k+1][j] != -1 ? dp[k+1][j] : partitionMemo(s, k+1, j);
            
            // If partition is possible the combination will take 1 partition
            int combine = 1;

            int currParts = leftSubProb + rightSubProb + combine;
            
            // store the minimum across all the computations
            minParts = Math.min(minParts, currParts);
        }

        // Store the final result in dp before returning
        dp[i][j] = minParts;

        return minParts;
    }

    // Utility function to check palindrome for a string s[i .. j]
    // This takes O(n^2) time
    private static boolean isPalindrome(String s, int i, int j) {
        while(i < j) {
            if(s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

/* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx */

    /**
     * We need to precompute the matrix of isPal
     * This matrix stores the boolean value of every possible substring combination of a string
     * 
     * This precomputation has to be done in O(n^2)
     * and we can use a DP based approach to calculate it
     * 
     * The core idea is - if we have two pointers (i & j) at some characters of a string
     * then the string s[i .. j] is a palindrome if these two conditions are met -
     * 1. s.charAt(i) == s.charAt(j)
     * 2. The inner substring is also a palindrome, i.e. s[i+1 .. j-1] is a palindrome
     * 
     * Using these two conditions we can build a dp based precomputation function
     */


    //  0 : not defined
    //  1 : TRUE
    // -1 : FALSE
    private static int[][] isPal_TD;

    private static void precompIsPalindromeTD(String s) {
        int n = s.length();
        isPal_TD = new int[n][n];

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                isPal_TD[i][j] = 0;
            }
        }
        
        // explicitly force all (i, j) states to be visited.
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                precomputeIsPalindromeTD(s, i, j);
            }
        }
    }

    /**
     * This is the DP Top Down approach to precompute all the palindrome checks
     */
    private static boolean precomputeIsPalindromeTD(String s, int i, int j) {
        // BASE CONDITION
        // empty string or string with single character is always a palindrome
        // also add in memo
        if(i >= j) {
            isPal_TD[i][j] = 1;
            return true;
        }
        
        // Memo check at the beginning
        if(isPal_TD[i][j] != 0) {
            return isPal_TD[i][j] == 1;
        }

        // We first check if the current (i & j) characters are different
        // If so, the palindrome check doesn't matter
        // It will not be a palindrome
        if(s.charAt(i) != s.charAt(j)) {
            isPal_TD[i][j] = -1;
            return false;
        }

        // If the current (i & j) characters are same
        // then, we check if the [i+1 .. j-1] is also a palindrome
        // and store it in memo
        boolean res = precomputeIsPalindromeTD(s, i+1, j-1);
        isPal_TD[i][j] = res ? 1 : -1;
        
        return res;
    }


    private static boolean[][] isPal_BU;

    /**
     * This is the DP Bottom Up approach to precompute all the palindrome checks
     */
    private static void precompIsPalindromeBU(String s) {
        int n = s.length();
        isPal_BU = new boolean[n][n];

        // BASE CASE
        // window length = 1, all the single characters are palindrome
        // diagonal of the matrix
        for(int i=0; i<n; i++) {
            isPal_BU[i][i] = true;
        }

        // window length >= 2 and upto n
        for(int windowLength = 2; windowLength <= n; windowLength++) {
            // For every i compute the corrosponding j that's windowLen - 1 apart
            // i moves from 0 till the end and computing the palindrome of all the lengths
            // this is a increasing sliding window based approach
            for(int i = 0; i + windowLength - 1 < n; i++) {
                int j = i + windowLength - 1;

                // if the window length is 2, there would be no [i+1 .. j-1]
                // so no checks there
                if(windowLength == 2) {
                    isPal_BU[i][j] = s.charAt(i) == s.charAt(j);
                } 
                // this check of [i+1 .. j-1] is only for window length 3 or above
                else {
                    isPal_BU[i][j] = s.charAt(i) == s.charAt(j) && isPal_BU[i+1][j-1];
                }
            }
        }
    }

/* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx */

    // This recursive approach only has one optimization
    // Which is using the precomputed palindrome checks
    // But this would still give TLE as both the sub problems are computed multiple times
    private static int partitionRecursiveOptimized(String s, int i, int j) {
        if(i >= j) {
            return 0;
        }
        
        if(isPal_BU[i][j]) { // or this can be repalced with if(isPal_TD[i][j] == 1) {}
            return 0;
        }

        int minParts = Integer.MAX_VALUE;
        for(int k = i; k <= j-1; k++) {
            int leftSubProb = partitionRecursiveOptimized(s, i, k);
            int rightSubProp = partitionRecursiveOptimized(s, k+1, j);
            int combine = 1;

            int currParts = leftSubProb + rightSubProp + combine;

            minParts = Math.min(minParts, currParts);
        }

        return minParts;
    }

    private static int[][] dp_TD;

    private static int partitionTdOptimized(String s) {
        int n = s.length();
        dp_TD = new int[n][n];

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                dp_TD[i][j] = -1;
            }
        }

        return partitionMemoOptimized(s, 0, n-1);
    }

    private static int partitionMemoOptimized(String s, int i, int j) {
        if(i >= j) {
            return 0;
        }

        if(dp_TD[i][j] != -1) {
            return dp_TD[i][j];
        }

        if(isPal_BU[i][j]) {
            dp_TD[i][j] = 0;
            return 0;
        }

        int minParts = Integer.MAX_VALUE;
        for(int k = i; k <= j-1;  k++) {
            // Only check if the left side is a palindrome
            if(isPal_BU[i][k]) {
                // no need to solve the left sub problem as it's already a palindrome
                int leftSubProb = 0;
                int rightSubprob = dp_TD[k+1][j] != -1 ? dp_TD[k+1][j] : partitionMemoOptimized(s, k+1, j);
                int combine = 1;

                int currParts = leftSubProb + rightSubprob + combine;
                
                minParts = Math.min(minParts, currParts);
            }
        }
        
        dp_TD[i][j] = minParts;

        return minParts;
    }
}

