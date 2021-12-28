/**
 * Amazonoa
 */
import java.util.*;

import javax.print.DocFlavor.STRING;
 public class Amazonoa {

    public static void main(String[] args) {
        System.out.print(oa_1("1234567"));
    }

    /*a ~ i is mapped to 1 ~ 9, and
j ~ z is mapped to  10# ~ 26#.
If there are parentheses, it means that there are duplicates.
For example, 
123(2)26#(2)3(2) -> abcczzcc

You need to change the given string to that kind of form. 
The Result form varies so you'd better know splitting string by the condition.
*/

public static String oa_1(String input){
    if(input == null) return "";
    Map<String, String> map = new HashMap<>();
    map.put("1", "a");
    map.put("2", "b");
    map.put("3", "c");
    map.put("4", "d");
    map.put("5", "e");
    map.put("6", "f");
    map.put("7", "g");
    map.put("8", "h");
    map.put("9", "i");
    map.put("10#", "j");
    map.put("11#", "k");
    map.put("12#", "l");
    map.put("13#", "m");
    map.put("14#", "n");
    map.put("15#", "o");
    map.put("16#", "p");
    map.put("17#", "q");
    map.put("18#", "r");
    map.put("19#", "s");
    map.put("20#", "t");
    map.put("21#", "u");
    map.put("22#", "v");
    map.put("23#", "w");
    map.put("24#", "z");
    map.put("25#", "y");
    map.put("26#", "z");
    int size = input.length();
    int count = 1;
    int index = 0;
    String each = "";
    StringBuilder res = new StringBuilder();
    int i = size - 1, j= size - 1;
    while(j >= 0){
        if(input.charAt(j) != ')' && input.charAt(j) != '#'){
            each = map.get(input.charAt(j)+"");
            //System.out.println(each);
        } else {
            if(input.charAt(j) == ')') {
                while(input.charAt(j) != '('){
                    j--;
                }
                count = Integer.parseInt(input.substring(j+1, i));
                //i = j;
            }
            if(input.charAt(j) == '#'){
                j -= 2;
                each = map.get(input.substring(j, i+1));
            }
        }
        if(each != ""){
            for(int c = 0 ;c < count; c++) {
                res.append(each);;
            }
            count = 1;
        }
        j--;
        i = j;
        each = "";
    }
    return res.toString();
    }

    /*
    /*
    Parameters:
    scores : List of int
    cutOffRank : int
    num: int (denoting amount of scores)

    You are given a list of integers representing scores of players in a video game. 
    Players can 'level-up' if by the end of the game they have a rank that is at least the cutOffRank. 
    A player's rank is solely determined by their score relative to the other players' scores. 
    For example:

    Score : 10 | Rank 1
    Score : 5 | Rank 2
    Score : 3 | Rank 3
    etc.

    If multiple players happen to have the same score, then they will all receive the same rank. 
    However, the next player with a score lower than theirs will receive a rank that is offset by this. For example:

    Score: 10 | Rank 1
    Score: 10 | Rank 1
    Score: 10 | Rank 1
    Score : 5 | Rank 4
    */

    public static int level_up(int[] score, int cutoffRank, int num){
        int[] dp = new int[score.length];
        // get the rank of each player
        Arrays.sort(score);
        if(score[num - 1] == 0) return 0;
        int rank = 1;
        int count = 1;
        for(int i = score.length - 1; i > 0; i--){
            dp[i] = rank;
            if(score[i] == score[i - 1]){
                count++;
                continue;
            } else {
                rank += count;
                count = 1;
            }
        }
        if(score[0] == score[1]) dp[0] = rank - 1;
        else dp[0] = rank;

        int not_level_up = 0;
        for(int value: dp){
            if(value <= cutoffRank) break;
            else not_level_up++;
        }
        return score.length - not_level_up;
    }


    /*public int cutOffRank(int cutOffRank, int num, int[] scores) {
        if(cutOffRank == 0) return 0;
        if(cutOffRank == num) return num;

        Arrays.sort(scores);
     
        if(scores[num-1] == 0) return 0;
        
        int count = 0;
        int i = num - cutOffRank;
        while(scores[i] == 0){
            count--;
            i++;
        }
        
        
        while(i > 0){
            if(scores[i] != scores[i-1]){
                break;
            }
            i--;
            count++;
        }
        
        return cutOffRank + count;
        
    }*/



    // Leetcode 146
    class LRUCache {
    
        private Node head = new Node();
        private Node tail = new Node();
        Map<Integer, Node> node_map;
        int cache_capacity;
        public LRUCache(int capacity) {
            this.cache_capacity = capacity;
            node_map = new HashMap(capacity);
            head.next = tail;
            tail.prev = head;
        }
        
        public int get(int key) {
            int res = -1;
            Node cur_node = node_map.get(key);
            if(cur_node != null) {
                // need to remove it first and add it to the front to be constant time
                res = cur_node.val;
                removeNode(cur_node);
                addNode(cur_node);
            }
            return res;
        }
        
        public void put(int key, int value) {
            // if node doesn't exist in the map
            Node cur_node = node_map.get(key);
            if(cur_node != null){
                // update cur_node's value
                removeNode(cur_node);
                cur_node.val = value;
                addNode(cur_node);
            } else{
                // first we need to check if cache reaches capacity
                if(node_map.size() == cache_capacity) {
                    // remove from the back in the double linkedlist and the map
                    node_map.remove(tail.prev.key);
                    removeNode(tail.prev);
                }
                Node new_node = new Node();
                new_node.key = key;
                new_node.val = value;
                // put into node map
                node_map.put(key, new_node);
                // put into linkedlist
                addNode(new_node);
            }
        }
        
        //add node to double linkedlist, add it to the front
        public void addNode(Node node){
            Node head_next = head.next;
            node.next = head_next;
            head_next.prev = node;
            head.next = node;
            node.prev = head;
        }
        
        // remove cur node from double linkedlist
        public void removeNode(Node node){
            Node node_next = node.next;
            Node node_prev = node.prev;
            node_next.prev = node_prev;
            node_prev.next = node_next;
        }
        
        class Node {
            int key;
            int val;
            Node next;
            Node prev;
        }
    }


    // LeetCode 1710
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        Arrays.sort(boxTypes, (a, b) -> b[1] - a[1]);
        int res = 0;
        for(int[] box: boxTypes){
            int count = Math.min(truckSize, box[0]);
            res += count * box[1];
            truckSize -= count;
            if(truckSize == 0) break;
        }
        return res;
    }


    /*
    An e-commerce company is currently celebrating ten years in business. They are having a sale to honor their privileged members, those who have been using their services for the past five years. They receive the best discounts indicated by any discount tags attached to the product. Determine the minimum cost to purchase all products listed. As each potential price is calculated, round it to the nearest integer before adding it to the total. Return the cost to purchase all items as an integer.
There are three types of discount tags:
• Type O: discounted price, the item is sold for a given price.
• Type 1: percentage discount, the customer is given a fixed percentage discount from the retail price.
• Type 2: fixed discount, the customer is given a fixed amount off from the retail price.

Example:
products = [['10', 'do', 'd1'], ['15', 'EMPTY', 'EMPTY'], ['20', 'd1', 'EMPTY"]]
discounts = [['d0','1','27'], ['d1', '2', '5']]
The products array elements are in the form ['price', 'tag 1', 'tag 2', ..., 'tag m-1'). There may be zero or more discount codes associated with a product. Discount tags in the products array may be 'EMPTY' which is the same as a null value.
The discounts array elements are in the form ['tag, 'type', 'amount').

If a privileged member buys product 1 listed at a price of 10 with two discounts available:
Under discount do of type 1, the discounted price is 10 - 10 * 0.27 = 7.30, round to 7.
Under discount d1 of type 2, the discounted price is 10-5 = 5. The price to purchase the product 1 is the lowest of the two, or 5 in this case
The second product is priced at 15 because there are no discounts available
The third product is priced at 20. Using discount tag d1 of type 2, the discounted price is 20-5= 15 The total price to purchase the three items is 5+ 15 +15 = 35.
int FindLowestPrice(List<List<string>>products, List<List<string>> discounts){}*/
public static int FindLowestPrice(List<List<String>>products, List<List<String>> discounts){
    int res = 0;
    Map<String, List<Integer>> map = new HashMap<>();
    for(List<String> discount: discounts){
        List<Integer> type_amount = new ArrayList<>();
        type_amount.add(Integer.parseInt(discount.get(1)));
        type_amount.add(Integer.parseInt(discount.get(2)));
        map.put(discount.get(0),type_amount);
    }

    int type_1_amount = 0;
    int type_2_amount = 0;
    for(List<String> product: products){
        int price = Integer.parseInt(product.get(0));
        int retail = price;
        for(int i = 1; i < product.size(); i++){
            if(product.get(i) == "EMPTY") {
                continue;
            }
            List<Integer> type_amount = map.get(product.get(i));
            int price1 = retail;
            if(type_amount.get(0) == 1) {
                price1 = (price * (100 - type_amount.get(1))/ 100);
            } else if(type_amount.get(0) == 2){
                price1 = price - type_amount.get(1);
            }
            price = Math.min(price, price1);
        }
        res += price;
    }
    return res;
}

    // 1109. Corporate Flight Bookings
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] ans = new int[n];
        for (int[] booking : bookings) {
            ans[booking[0] - 1] += booking[2]; // flights are 1-indexed, so subtract 1
            if (booking[1] < n)
                ans[booking[1]] -= booking[2];
        }
        
        int curr = 0;
        for (int i = 0; i < n; i++) {
            curr += ans[i];
            ans[i] = curr;
        }
        
        return ans;
        /*
        int[] res = new int[n];
        Arrays.sort(bookings, (a,b) -> a[0] - b[0]);
        int prevF = bookings[0][0];
        int prevL = bookings[0][1];
        for(int i = prevF; i <= prevL; i++){
            res[i - 1] += bookings[0][2];
        }
        for(int i = 1; i < bookings.length; i++){
            int first = bookings[i][0];
            int temp = bookings[i][1];
            int count = bookings[i][2];
            for(int j = first; j <= temp; j++){
                res[j - 1] += count;
            }
            prevF = first;
            prevL = temp;
        }
        
        return res;
        */
    }

    /*

    Given an array of integers, determine the number of ways the entire array be split into 
    two non-empty subarrays, left and right, such that the sum of elements in the left subarray 
    is greater than the sum of elements in the right subarray.
    Example
    arr =  [10, 4, -8, 7]
    There are three ways to split it into two non-empty subarrays:
    [10] and [4, -8, 7], left sum = 10, right sum = 3   
    [10, 4] and [-8, 7], left sum = 10 + 4 = 14, right sum = -8 + 7 = -1  
    [10, 4, -8] and [7], left sum = 6, right sum = 7
    The first two satisfy the condition that left sum > right sum, so the return value should be 2.
*/
    public static int split_Array(int[] arr){
        if(arr == null || arr.length <= 1) return 0;
        if(arr.length == 2) return (arr[0] > arr[1]) ? 1: 0;
        int leftSum = 0;
        for(int i = 0; i < arr.length - 1; i++){
            leftSum += arr[i];
        }
        int rightSum = arr[arr.length - 1];
        int res = 0;
        //if(leftSum > rightSum) res += 1;
        for(int i = arr.length - 2; i >= 0; i++){
            if(leftSum > rightSum && leftSum != 0) count++;
            rightSum += arr[i];
            leftSum -= arr[i];
        }
        return res;
    }

    /*
    The second question was more interesting. You are given a List of Integers which is a list of priorities. 
    A priority can be a number from 1-99. Without changing the order of the array, 
    minimize the priority as much as possible without changing the order.
    */

    public static int[] changePriority(List<Integer> input){
        int n = arr.size();
        TreeSet<Integer> ts = new TreeSet<>();
        for (int val : arr) {
            ts.add(val);
        }
        Map<Integer, Integer> mp = new HashMap<>();
        int priority = 1;
        for(int v : ts){
            mp.put(v,priority);
            priority++;
        }
        for(int i=0;i<n;i++){
	        arr.set(i, mp.get(arr.get(i)));
        }
      
        return arr;
    }


    /*

    Sum of similarities of string with all of its suffixes*/
    // Function to calculate the Z-array for the given string
static void getZarr(String str, int n, int Z[]){
    int L, R, k;
 
    // [L, R] make a window which matches with prefix of s
    L = R = 0;
    for (int i = 1; i < n; ++i) {
 
        // if i>R nothing matches so we will calculate.
        // Z[i] using naive way.
        if (i > R) {
            L = R = i;
 
            // R-L = 0 in starting, so it will start
            // checking from 0'th index. For example,
            // for "ababab" and i = 1, the value of R
            // remains 0 and Z[i] becomes 0. For string
            // "aaaaaa" and i = 1, Z[i] and R become 5
            while (R < n && str.charAt(R - L) == str.charAt(R))
                R++;
            Z[i] = R - L;
            R--;
        }
        else {
 
            // k = i-L so k corresponds to number which
            // matches in [L, R] interval.
            k = i - L;
 
            // if Z[k] is less than remaining interval
            // then Z[i] will be equal to Z[k].
            // For example, str = "ababab", i = 3, R = 5
            // and L = 2
            if (Z[k] < R - i + 1)
                Z[i] = Z[k];
 
            // For example str = "aaaaaa" and i = 2, R is 5,
            // L is 0
            else {
                // else start from R and check manually
                L = i;
                while (R < n && str.charAt(R - L) == str.charAt(R))
                    R++;
                Z[i] = R - L;
                R--;
            }
        }
    }
}
 
// Function to return the similarity sum
    public static int sumSimilarities(String s, int n){
    int Z[] = new int[n] ;
 
    // Compute the Z-array for the given string
    getZarr(s, n, Z);
 
    int total = n;
 
    // Summation of the Z-values
    for (int i = 1; i < n; i++)
        total += Z[i];
 
    return total;
    }


    /*
    Caesar Cipher Encrpytion
    You are given a list of string, group them if they are same after using Ceaser Cipher Encrpytion.
    Definition of “same”, “abc” can right shift 1, get “bcd”, here you can shift as many time as you want, the string will be considered as same.

    Example:

    Input: [“abc”, “bcd”, “acd”, “dfg”]
    Output: [[“abc”, “bcd”], [“acd”, “dfg”]]
*/

    public static List<List<String>> caesar(List<String> input){
        List<List<String>> res = new ArrayList<>();
        if(input.size() == 0) return res;
        Map<String, List<String>> map = new HashMap();
        String key = "";
        for(String cur: input){
            for(int i = 0; i < cur.length() - 1; i++){
                key += (cur.charAt(i + 1) - 'a') - (cur.charAt(i) - 'a') + "";
            }
            if(map.get(key) == null) {
                List<String> v = new ArrayList<>();
                v.add(cur);
                map.put(key, v);
            } else {
                map.get(key).add(cur);
            }
            key = "";
        }
        for(String s: map.keySet()){
            List<String> value = map.get(s);
            res.add(value);
        }
        return res;
    }


    // 696. Count Binary Substrings

    /*
preRun count the same item happend before (let say you have 0011, preRun = 2 when you hit the first 1, means there are two zeros before first '1')
curRun count the current number of items (let say you have 0011, curRun = 2 when you hit the second 1, means there are two 1s so far)
Whenever item change (from 0 to 1 or from 1 to 0), preRun change to curRun, reset curRun to 1 (store the curRun number into PreRun, reset curRun)
Every time preRun >= curRun means there are more 0s before 1s, so could do count++ . (This was the tricky one, ex. 0011 when you hit the first '1', curRun = 1, preRun = 2, means 0s number is larger than 1s number, so we could form "01" at this time, count++ . When you hit the second '1', curRun = 2, preRun = 2, means 0s' number equals to 1s' number, so we could form "0011" at this time, that is why count++)
*/
    public int countBinarySubstrings(String s) {
        /*
        int cur = 1, pre = 0, res = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) cur++;
            else {
                res += Math.min(cur, pre);
                pre = cur;
                cur = 1;
            }
        }
        return res + Math.min(cur, pre);
        */
        int prev = 0, cur = 1, res = 0;
        for (int i = 1;i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i-1)) curRunLength++;
            else {
                prevRunLength = curRunLength;
                curRunLength = 1;
            }
            if (prevRunLength >= curRunLength) res++;
        }
        return res;
    }


    // LeetCode 1268: Search Suggestions System
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();
        List<String> cur = new ArrayList<>();
        Arrays.sort(products);
        String key = "";
        int count = 0;
        for(char c: searchWord.toCharArray()){
            key += c;
            count += 1;
            for(int i = 0; i < products.length; i++){
                if(products[i].length() >= count && products[i].substring(0,count).equals(key) && cur.size() < 3) {
                    cur.add(products[i]);
                }
            }
            res.add(new ArrayList<>(cur));
            cur = new ArrayList<>();
        }
        return res;
    }
    /*
    我是先sort了bad numbers 然后看每个相邻bad number l与r 之间的segment的长度， 
    min(upper, r) - max(lower, r)。 当然有些edge case再加一点condition。
    */


    /*
    1. BadNumbers： 给一个array of integers of bad numbers 和两个parameter upper bound 和 lower bound。
目的是找出最长的数组长度并且不含bad number。
例如：bad number = [1, 6, 11] lower = 3 upper = 10
那么【1】，【3, 5】，【7, 10】那么return 4 因为7 8 9 10是最长的 注意是Inclusive
*/
// 2 5 6

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


    // LeetCode 200 Number of Island
    public int numIslands(char[][] grid) {
        int res = 0;
        // edge case?
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == '1') res += dfs(grid, i, j);
            }
        }
        return res;
    }
    
    private int dfs(char[][] grid, int curRow, int curCol){
        if(curRow < 0 || curRow >= grid.length || curCol < 0 || curCol >= grid[0].length || grid[curRow][curCol] == '0') return 0;
        // mark current as seen/ as water
        grid[curRow][curCol] = '0';
        dfs(grid, curRow - 1, curCol);
        dfs(grid, curRow + 1, curCol);
        dfs(grid, curRow, curCol - 1);
        dfs(grid, curRow, curCol + 1);
        return 1;
    }
}
