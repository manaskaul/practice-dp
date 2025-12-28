// package topic4_matrix_chain_multiplication;

/**
 * 0. Martix Chain Multiplication --------------------------------- MatrixChainMultiplication
 * 1. Palindrome Partitioning ------------------------------------- PalindromePartitioning
 * 2. Evaluate Expression to True / Boolean Parenthesization ------ BooleanParenthesization
 * 3. Scramble String --------------------------------------------- ScrambleString
 * 4. Egg Dropping Problem ---------------------------------------- EggDropping
 * 5. Min/Max value of an expression ------------------------------
 * 6. Print Martix Chain Multiplication ---------------------------
 */

/**
 * When solving these type of questions we start with two pointers at the left (i) and right (j) end of the array
 * then using a thrid pointer (k) we iterate over from i to j and calculate the subproblem into two partitions
 * and combine them to find the final ans
 * 
 * In MCM, we're given array (of size n) representing size of n-1 matrices : arr[] = [10, 20, 30, 40]
 * So for the matrix Ai the size is
 * Ai = arr[i-1] x arr[i]
 * 
 * So for the above,
 * A1 = arr[0] x arr[1]
 * A2 = arr[1] x arr[2]
 * A3 = arr[2] x arr[3]
 * 
 * Now to actually solve this, k is used to partition between i and j
 * and the currCost is calculated by adding the cost of left and right subproblem 
 * and also adding the cost of multiplying the left and right subproblems
 * 
 * So essentially after breaking down and multiplying the two subproblems
 * [i,k] and [k+1,j]
 * 
 * leftSubProb = solve(Ai,Ak) = arr[i-1], arr[k]
 * rightSubProb = solve(Ak+1, j) = arr[k], arr[j]
 * combined = arr[i-1] * arr[k] * arr[j]
 * 
 * and then compare this with the minCost and return
 * 
 */

/**
 * 🧩 Universal interval-DP checklist (use this in interviews)
 * When you see an interval DP problem:
 * 
 * What does dp[i][j] represent?
 * (Answer must be a full result, not partial info.)
 * 
 * What is the operation at k?
 * (Last operation inside the interval.)
 * 
 * What information must flow upward?
 * (Counts? cost? min/max?)
 * 
 * Is one value enough?
 * (If not, use pairs / structs.)
 * 
 * Does memoization fully cover overlapping subproblems?
 */
public class MatrixChainMultiplication {
    public static void main(String[] args) {
        int[] arr = new int[]{10,20,30,40};
        
        int mcmCostRecursive = matrixChainMulRecursive(arr, 1, arr.length-1);
        System.out.printf("Cost of Matrix Chain Multiplication (Recursive) : %d\n", mcmCostRecursive);
        
        int mcmCostTD = matrixChainMulTD(arr);
        System.out.printf("Cost of Matrix Chain Multiplication (Memoized) : %d\n", mcmCostTD);
    }

    private static int matrixChainMulRecursive(int[] arr, int i, int j) {
        if(i >= j) {
            return 0;
        }

        int minCost = Integer.MAX_VALUE;

        for(int k = i; k <= j-1; k++) {
            int costOfLeftSubproblem = matrixChainMulRecursive(arr, i, k);
            int costOfRightSubproblem = matrixChainMulRecursive(arr, k+1, j);
            int costOfLeftAndRightSubproblem = arr[i-1] * arr[k] * arr[j];

            int currCost = costOfLeftSubproblem + costOfRightSubproblem + costOfLeftAndRightSubproblem;

            minCost = Math.min(minCost, currCost);
        }

        return minCost;
    }

    private static int[][] dp_td;

    private static int matrixChainMulTD(int[] arr) {
        int n = arr.length;
        dp_td = new int[n][n];

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                dp_td[i][j] = -1;
            }
        }

        return matrixChainMulMemo(arr, 1, n-1);
    }

    private static int matrixChainMulMemo(int[] arr, int i, int j) {
        if(i >= j) {
            return 0;
        }

        if(dp_td[i][j] != -1) {
            return dp_td[i][j];
        }

        int minCost = Integer.MAX_VALUE;
        for(int k = i; k <= j-1; k++) {
            int currCost = matrixChainMulMemo(arr, i, k) 
                            + matrixChainMulMemo(arr, k+1, j) 
                            + (arr[i-1] * arr[k] * arr[j]);
            
            minCost = Math.min(minCost, currCost);
        }

        dp_td[i][j] = minCost;

        return minCost;
    }
}
