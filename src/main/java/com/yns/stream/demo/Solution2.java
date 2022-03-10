package com.yns.stream.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Solution2 {

    public static void main(String[] args) throws IOException {

        // String result = Result.twoStrings("wouldyoulikefries", "jackandjill");
        // String result = Result.isBalanced("{)[](}]}]}))}(())(");
        String content = new String(Files.readAllBytes(Paths.get("/Users/yunusalkan/IdeaProjects/Stream-Demo/src/main/resources/input.txt")));
        System.out.println(Result2.highestValuePalindrome(content, content.length(), 50790));
    }
}

class Result2 {

    /*
     * Complete the 'lilysHomework' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static String twoStrings(String s1, String s2) {
        //return s1.chars().parallel().anyMatch(c -> s2.indexOf(c) >= 0) ? "YES" : "NO";
        boolean v[]=new boolean[26];
        Arrays.fill(v,false);
        String mainStr;
        String compareStr;

        if (s1.length() < s2.length()) {
            mainStr = s1;
            compareStr = s2;
        }
        else {
            mainStr = s2;
            compareStr =s1;
        }

        // increment vector index for every
        // character of str1
        for (int i = 0; i < mainStr.length(); i++) {
            v[mainStr.charAt(i) - 'a'] = true;
        }

        // checking common substring of str2 in str1
        for (int i = 0; i < compareStr.length(); i++)
            if (v[compareStr.charAt(i) - 'a'])
                return "YES";

        return "NO";
    }

    // {(([])[])[]}
    public static String isBalanced(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char cr = s.charAt(i);
            if(cr == '{' || cr == '[' || cr == '(') {
                stack.push(cr);
            }
            else if(!stack.empty()) {
                if(stack.peek() == '{' && cr == '}') {
                    stack.pop();
                }
                else if(stack.peek() == '[' && cr == ']') {
                    stack.pop();
                }
                else if(stack.peek() == '(' && cr == ')') {
                    stack.pop();
                }
                else {
                    return "NO";
                }
            }
            else {
                return "NO";
            }
        }
        return stack.empty() ? "YES" : "NO";
    }

    public static int lilysHomework(List<Integer> arr) {
        int minSwapSTH = minSwapSortingSmallToHigh(arr);
        int minSwapHTS = minSwapSortingHighToSmall(arr);
        return Math.min(minSwapSTH,minSwapHTS);
    }

    private static int minSwapSortingSmallToHigh(List<Integer> arr) {
        int minSwapCount = 0;
        Map<Integer,Integer> currValueList = new HashMap<>();
        List<Integer> copyArr = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            currValueList.put(arr.get(i), i);
            copyArr.add(arr.get(i));
        }
        List<Integer> sortedArr = Arrays.asList(arr.toArray(new Integer[0]));
        sortedArr.sort(Integer::compareTo);
        for (int i = 0; i < copyArr.size() ; i++) {
            if(copyArr.get(i) != sortedArr.get(i)) {
                minSwapCount++;
                int ind_to_swap = currValueList.get(sortedArr.get(i));
                currValueList.put(copyArr.get(i), currValueList.get(sortedArr.get(i)));
                copyArr.set(ind_to_swap, copyArr.get(i));
                copyArr.set(i, sortedArr.get(i));
            }
        }
        System.out.println("Min to High Value : " + minSwapCount);
        return minSwapCount;
    }

    private static int minSwapSortingHighToSmall(List<Integer> arr) {
        int minSwapCount = 0;
        Map<Integer,Integer> currValueList = new HashMap<>();
        List<Integer> copyArr = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            currValueList.put(arr.get(i), i);
            copyArr.add(arr.get(i));
        }
        List<Integer> sortedArr = Arrays.asList(arr.toArray(new Integer[0]));
        sortedArr.sort(Collections.reverseOrder());
        for (int i = 0; i < copyArr.size() ; i++) {
            if(copyArr.get(i) != sortedArr.get(i)) {
                minSwapCount++;
                int ind_to_swap = currValueList.get(sortedArr.get(i));
                currValueList.put(copyArr.get(i), currValueList.get(sortedArr.get(i)));
                copyArr.set(ind_to_swap, copyArr.get(i));
                copyArr.set(i, sortedArr.get(i));
            }
        }
        System.out.println("High To min swap Value : " + minSwapCount);
        return minSwapCount;
    }

    public static String highestValuePalindrome(String s, int n, int k) {
        // Write your code here
        if(n<k)
            return s.replaceAll("\\d", "9");
        int changedCharCount = 0;
        List<Integer> equalCharIndex = new ArrayList<>();
        List<Integer> doubledCharsIndex = new ArrayList<>();
        if(s.length() == 1) {
            return "9";
        }
        if(s.length() == 2) {
            if(s.charAt(0) == s.charAt(1)) {
                if(k<2)
                    return s;
                else
                    return "99";
            }
            else {
                if(s.indexOf('9') >= 0 && k >=1 || (k >= 2))  {
                    return "99";
                }
                else if(k >= 1) {
                    int largestValue = Math.max(Integer.valueOf(String.valueOf(s.charAt(0))),
                            Integer.valueOf(String.valueOf(s.charAt(1))));
                    return largestValue+""+largestValue;
                }
                else if(k < 1)
                    return "-1";
            }
        }
        int midIndx =  n / 2;
        char[] copyOfInput = s.toCharArray();
        for (int i = 0; i < midIndx; i++) {
            char left = s.charAt(i);
            char right = s.charAt(n-1-i);
            if(left != right) {
                if(left == '9') {
                    copyOfInput[n-1-i] = '9';
                    changedCharCount++;
                }
                else if(right == '9') {
                    copyOfInput[i] = '9';
                    changedCharCount++;
                }
                else {
                    if(Integer.valueOf(String.valueOf(left)) > Integer.valueOf(String.valueOf(right))) {
                        copyOfInput[n-1-i] = left;
                    }
                    else {
                        copyOfInput[i] = right;
                    }
                    equalCharIndex.add(i);
                    doubledCharsIndex.add(i);
                    changedCharCount++;
                }
            }
        else {
            if(left != '9')
                equalCharIndex.add(i);
          }
        }
        if(changedCharCount > k)
            return "-1";
        for (int i = 0; i < equalCharIndex.size() ; i++) {
            int changeIndex = equalCharIndex.get(i);
            int incrementValue = doubledCharsIndex.indexOf(changeIndex) >= 0 ? 1 : 2;
            if(changedCharCount + incrementValue <= k) {
                copyOfInput[changeIndex] = '9';
                copyOfInput[n-1-changeIndex] = '9';
                changedCharCount = changedCharCount + incrementValue;
            }
            else
                break;
        }
        if(changedCharCount < k && (n % 2 != 0)) {
            copyOfInput[n / 2] = '9';
            changedCharCount++;
        }
        return changedCharCount <= k ? new String(copyOfInput) : "-1";
    }
}
