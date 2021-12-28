/**
 * 亚麻OA
 */
public class 亚麻OA {

    public static void main(String[] args) {
        
    }

/*
LeetCode 696

preRun count the same item happend before 
(let say you have 0011, preRun = 2 when you hit the first 1, means there are two zeros before first '1')
curRun count the current number of items 
(let say you have 0011, curRun = 2 when you hit the second 1, means there are two 1s so far)
Whenever item change 
(from 0 to 1 or from 1 to 0), preRun change to curRun, reset curRun to 1 (store the curRun number into PreRun, reset curRun)
Every time preRun >= curRun means there are more 0s before 1s, so could do count++ . 
(This was the tricky one, ex. 0011 when you hit the first '1', curRun = 1, preRun = 2, means 0s number is larger than 1s number, so we could form "01" at this time, count++ . When you hit the second '1', curRun = 2, preRun = 2, means 0s' number equals to 1s' number, so we could form "0011" at this time, that is why count++)
*/
    public int countBinarySubstrings(String s) {
        if (s == null || s.length() == 0) return 0;
                int preRun = 0;
                int curRun =1;
                int count = 0;
                for (int i = 1; i < s.length(); i++){
                    if (s.charAt(i) == s.charAt(i-1)) curRun++;
                    else {
                        preRun = curRun;
                        curRun = 1;
                    }
                    if (preRun >= curRun) count++;
                }
                return count;
    }

    //bad numbers
    public static int badNumbers(int[] bad_number, int lower, int upper){
        int res = 0;
        int cur = 0;
        int temp = lower;

        // Sort bad numbers
        Arrays.sort(badNumbers);

        // Traverse over bad numbers, exploring left segment till all numbers are covered or upper is exceeded
        for (int i = 0; i < badNumbers.length && badNumbers[i] <= upper; i++) {
            if(badNumbers[i] < lower) continue;
            cur = badNumbers[i] - temp;
            res = Math.max(res, cur);
            temp = badNumbers[i] + 1;
        }

        // Handle left segment of upper
        cur = upper - temp + 1;
        res = Math.max(cur, res);
        return res;
    }

}