import java.io.*;
public class lab7 {

    public static void main(String[] args){
        System.out.println(longestWordLen("Hello hello hello helloo"));
    }
    public static int longestWordLen(String words) {
        String[] arr = words.split(" ");

        return longestWordArr(arr, 0, arr.length-1);
    }

    public static int longestWordArr(String[] arr, int start, int end){
        if(start >= end)
            return arr[start].length();

        else if(arr[start].length() > arr[end].length())
            return longestWordArr(arr, start, end-1);

        else
            return longestWordArr(arr, start+1, end);
    }

}
