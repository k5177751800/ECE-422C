package assignment1;

import java.util.Scanner;
public class Problem2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String paragraph = scanner.nextLine();
        scanner.close();

//     String paragraph ="The wicked wizard`s wily wraith garnishes his master`s pasta with garlic. wizard'''';;;''''S!";
        String words[] = paragraph.split("\\s+");
        for (int j = 0; j < words.length; j++){
            String word = words[j];
            int sum = 0;

            for (int i =0; i< word.length(); i++){
                char c = word.charAt(i);
                if (c >= 'a' && c <= 'z'){
                    int val = c - 'a' +1;
                    // System.out.println(val);--------test
                    sum +=val;
                } else if (c >= 'A' && c <= 'Z'){
                    int val =c - 'A' +1;
                    //System.out.println(val);--------test
                    sum +=val;
                }

                }
            // only prints when its a dollar
                if (sum == 100){
                    System.out.println(word);
            }
        }


    }
}
