// package topic4_matrix_chain_multiplication;

import java.util.HashMap;
import java.util.Map;

public class ScrambleString {
    public static void main(String[] args) {
        String X = "great";
        String Y = "rgeat";

        boolean areScrambleStringsRec = false;
        if(X.length() != Y.length()) {
            areScrambleStringsRec = false;
        } else {
            areScrambleStringsRec = scrambleStringRecursive(X, Y);
        }
        
        System.out.printf("Are X & Y Scramble Strings (Recursive) : %B\n", areScrambleStringsRec);

        
        boolean areScrambleStringsMemo = false;
        if(X.length() != Y.length()) {
            areScrambleStringsMemo = false;
        } else {
            areScrambleStringsMemo = scrambleStringTD(X, Y);
        }
        
        System.out.printf("Are X & Y Scramble Strings (Memoization) : %B\n", areScrambleStringsMemo);
    }

    /**
     * The core of solution is to break the string into intervals and check for the left and the right side
     * There are two possible choices, after splitting the string:
     * Swap left and right - OR - Do not Swap left and right
     * Both these flows need to be checked using both the strings
     *
     * When talking about swap, it means:
     * if a string "ABCDE" has to be split at 2 and swapped, it becomes: "CDEAB"
     *  
     * The split need to be done from 1 to n-1
     * we cannot have the split be done on empty string
     * the split happens recursively
     * 
     * When solving this problem, the checks need to be done on both and compared
     * we take i as the position where the string need to be split
     * the substrings for X and Y are checked and compared
     * 
     * i = 1 to i = n-1
     * 
     * The scenario goes as follows -
     * 
     * When checking the swap scenario:
     * 
     * X = "AB | CDE"      Y = "CDE | AB"
     *      --   ---            ---   --
     *      ^    ^              ^     ^
     *      |    |______________|     |
     *      |_________________________|
     *
     * condition 1 check: X[0 .. i] ~ Y[n-i .. n] & X[i .. n] ~ Y[0 .. n-i]
     * 
     * When checking the do not swap scenario:
     * 
     * X = "AB | CDE"      Y = "AB | CDE"
     *      --   ---            --   ---
     *      ^    ^              ^    ^
     *      |    |______________|____|
     *      |___________________|
     * 
     * condition 2 check: X[0 .. i] ~ Y[0 .. i] & X[i .. n] ~ Y[i .. n]
     * 
     * We need to check both the scenarios recursively and if either one gives a true, i.e. the substrings are scramble (~)
     * We know the current strings are also scramble
     * 
     */
    private static boolean scrambleStringRecursive(String X, String Y) {
        int n = X.length();
        
        // BASE CASE: if the two strings/substrings are equal
        if(X.equals(Y)) {
            return true;
        }

        // OPTIMIZATION: We need to check if the two strings/substrings are anagrams of each other
        // Anagrams means one string can be rearranged to make the other
        // If the strings/substrings aren't anagrams, there is no point in checking if they are scramble strings
        // This helps in pruning a lot of unnecessary branches that may check scramble for strings/substrings that aren't anagrams
        if(!isAnagram(X, Y)) {
            return false;
        }

        boolean scrambled = false;
        
        for(int i = 1; i <= n-1; i++) {
            scrambled = 
                        // Swap left and right and check recursively if the substrings are scramble
                        (
                            // left split of X checked with right split of Y
                            scrambleStringRecursive(X.substring(0, i), Y.substring(n-i, n)) 
                                &&
                            // right split of X checked with left split of Y
                            scrambleStringRecursive(X.substring(i, n), Y.substring(0, n-i))
                        )

                            ||

                        // Don't Swap left and right and check recursively
                        (
                            // left split of X checked with left split of Y
                            scrambleStringRecursive(X.substring(0, i), Y.substring(0, i))
                                &&
                            // right split of X checked with right split of Y
                            scrambleStringRecursive(X.substring(i, n), Y.substring(i, n))
                        );

            // if we found the substrings to be scrambled, no need to check further
            if(scrambled) {
                break;
            }
        }

        return scrambled;
    }

    /**
     * Instead of using the dp matrix, which is tedious and not viable here
     * we use the dp map
     * 
     * Since the changing variable are the strings X & Y itself
     * we need to create a custom key for the dp map: "X_Y" and value of boolean
     * 
     * Map<String, Boolean> means :- <X_Y> : T/F :- Is X & Y substring scramble of each other
     */
    private static Map<String, Boolean> dp;

    private static boolean scrambleStringTD(String X, String Y) {
        dp = new HashMap<>();
        return scrambleStringMemo(X, Y);
    }

    private static boolean scrambleStringMemo(String X, String Y) {
        // Base Condition
        if(X.equals(Y)) {
            return true;
        }

        int n = X.length();
        String key = X + "_" + Y;

        // Memo check
        if(dp.containsKey(key)) {
            return dp.get(key);
        }

        // Anagram check
        if(!isAnagram(X, Y)) {
            return false;
        }

        boolean scrambled = false;

        for(int i = 1; i <= n-1; i++) {
            // Swap and No Swap checks
            scrambled = (
                            scrambleStringMemo(X.substring(0, i), Y.substring(n-i, n))
                                &&
                            scrambleStringMemo(X.substring(i, n), Y.substring(0, n-i))
                        )
                            ||
                        (
                            scrambleStringMemo(X.substring(0, i), Y.substring(0, i))
                                &&
                            scrambleStringMemo(X.substring(i, n), Y.substring(i, n))
                        );
            if(scrambled) {
                break;
            }
        }

        // Populate Memo
        dp.put(key, scrambled);

        return scrambled;
    }

    // Simple isAnagram check
    private static boolean isAnagram(String X, String Y) {
        int[] freq = new int[26];
        for(int i = 0; i < X.length(); i++) {
            freq[X.charAt(i) - 'a']++;
            freq[Y.charAt(i) - 'a']--;
        }
        
        for(int f : freq) {
            if(f != 0) {
                return false;
            }
        }

        return true;
    }
}

/*

    a b c d e

    a b / c d e


*/