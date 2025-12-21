// package topic4_matrix_chain_multiplication;

public class BooleanParenthesization {

    private static final char T = 'T';
    private static final char F = 'F';
    private static final char OR = '|';
    private static final char AND = '&';
    private static final char XOR = '^';

    public static void main(String[] args) {
        String s = "T|T&F^T";

        // String s = "T&F|T";

        // String s = "F&F&F";

        // String s = "T^F|F";

        // String s = "T&F&T";
        
        Count countRec = countParenthesizationRecursive(s, 0, s.length()-1);
        System.out.printf("Count of total Parenthesization eval to True (Recursive) = %d\n", countRec.trueCount);

        Count countTD = countParenthesizationTD(s);
        System.out.printf("Count of total Parenthesization eval to True (Memoization) = %d\n", countTD.trueCount);
    }

    private static Count countParenthesizationRecursive(String s, int i, int j) {
        // BASE CONDITION
        if(i == j) {
            return s.charAt(i) == T ? new Count(1, 0) : new Count(0, 1);
        }

        /**
         * This is not needed since it would be handled in the above base case
         * evalExp just evaluates => Operand1 Operator Operand2 ; like T | F = T
         */
        // if(i == j-2) {
        //     return evalExp(s.charAt(i), s.charAt(j), s.charAt(i+1));
        // }

        /**
         * We're using the Count object to hold all the possible values of expression evaluation
         * Essentially, keeping track of how many ways the expression evaluates to true and false
         * This is done because at the kth expression both sides can eveluate to true/false in multiple ways
         * And all combinations need to be counted
         * 
         * Example : s[i .. k-1] ^ s[k+1 .. j] and s[i .. k-1] | s[k+1 .. j]
         * 
         * for the above two scenarios, for XOR the total ways would be (all true eval) * (all false eval)
         * and for OR the total ways would be any combination of (all true eval) & (all false eval) except where both are false
         * and hence both trueCount and falseCount is needed
         */
        
        Count total = new Count(0, 0);
        for(int k = i+1; k <= j-1; k += 2) {
            Count leftSub = countParenthesizationRecursive(s, i, k-1);
            Count righSub = countParenthesizationRecursive(s, k+1, j);

            /**
             * The totalCombos() function is used to calculate the total ways in which we can get a true eval
             * This depends on the operator in the kth position
             */
            Count combine = totalCombos(leftSub, righSub, s.charAt(k));

            total.trueCount += combine.trueCount;
            total.falseCount += combine.falseCount;
        }

        return total;
    }

    private static Count[][] dp;

    private static Count countParenthesizationTD(String s) {
        int n = s.length();
        dp = new Count[n][n];

        /**
         * Initilization is not needed since the null check is used for uninitilized values in dp
         */
        // for(int i=0; i<n; i++) {
        //     for(int j=0; j<n; j++) {
        //         dp[i][j] = new Count(-1, -1);
        //     }
        // }

        return countParenthesizationMemo(s, 0, n-1);
    }

    private static Count countParenthesizationMemo(String s, int i, int j) {
        if(i == j) {
            return s.charAt(i) == T ? new Count(1, 0) : new Count(0, 1);
        }

        if(dp[i][j] != null) {
            return dp[i][j];
        }
        
        Count total = new Count(0, 0);
        for(int k = i+1; k <= j-1; k += 2) {
            Count leftSub = dp[i][k-1] != null ? dp[i][k-1] : countParenthesizationMemo(s, i, k-1);
            Count righSub = dp[k+1][j] != null ? dp[k+1][j] : countParenthesizationMemo(s, k+1, j);

            Count combine = totalCombos(leftSub, righSub, s.charAt(k));

            total.trueCount += combine.trueCount;
            total.falseCount += combine.falseCount;
        }

        dp[i][j] = total;
        return total;
    }

    /**
     * This calculates all the possible ways the expression can evaluate to both true and false 
     * for all three operator types
     */
    private static Count totalCombos(Count c1, Count c2, char op) {
        Count total = new Count(0, 0);
        switch (op) {
            case OR:
                total.trueCount = (c1.trueCount * c2.trueCount) + (c1.trueCount * c2.falseCount) + (c1.falseCount * c2.trueCount);
                total.falseCount = c1.falseCount * c2.falseCount;
                break;
            case AND:
                total.trueCount = c1.trueCount * c2.trueCount;
                total.falseCount = (c1.trueCount * c2.falseCount) + (c1.falseCount * c2.trueCount) + (c1.falseCount * c2.falseCount);
                break;
            case XOR:
                total.trueCount = (c1.trueCount * c2.falseCount) + (c1.falseCount * c2.trueCount);
                total.falseCount = (c1.trueCount * c2.trueCount) + (c1.falseCount * c2.falseCount);
                break;
            default:
                break;
        }

        return total;
    }

    /**
     * We need to return all the possible counts for the expression when it evaluates to true and false 
     */
    private static class Count {
        int trueCount;
        int falseCount;

        Count(int t, int f) {
            this.trueCount = t;
            this.falseCount = f;
        }
    }
}