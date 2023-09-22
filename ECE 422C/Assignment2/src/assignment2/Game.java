/* EE422C Assignment #2 submission by
 * Replace <...> with your actual data.
 * <Konghwan Shin>
 * <KS54897>
 */
package assignment2;
import java.nio.file.LinkPermission;
import java.util.*;
import assignment2.*;
import java.util.Scanner;

public class Game {
    private static boolean tester;
    private static Scanner scan;
    private static int guessNumber;
    private static String secreteCode;
    private static Stack<String> history;   //-----History push and pop

    private static ArrayList<String> historyList;

    public Game(boolean test, Scanner scanner){
        guessNumber = GameConfiguration.guessNumber;
        secreteCode = SecretCodeGenerator.getInstance().getNewSecretCode();
        tester  = test;
        scan = scanner;
        history = new Stack<>();
        historyList = new ArrayList<>();
    }

    public static void runGame(){
        if(tester == true){
            System.out.println("\nGenerating secret code ...(for this example the secret code is " + secreteCode + ")\n");
        } else{
            System.out.println("\nGenerating secret code ...\n");
        }

        while(guessNumber > 0) {
            System.out.println("You have " + guessNumber + " guesses left.\n" +
                        "What is your next guess?\n" +
                        "Type in the characters for your guess and press enter.\n" +
                        "Enter guess: ");
            String guesses = scan.nextLine();
            //System.out.println("\n" + guesses + " -> Result " + "test");  //--------test;

            // if invalid guess don't put in history
            // if valid guess, 1. count how many black pegs
            //                  2. count how many white pegs
            // guessNumer--;
            // store the guess and peg counts to history
            // output result



            /*  black and white pegs algo
            *    When one peg is used for either black or white pegs, it should not be used for other pegs
            *   RRYY
            *   RRRR
            *   2B_0W
            *
            *   [TTFF]
            *   [TTFF]
            *
            *   create 2 boolean arrays one for guess, one for secretcode
            *   When a peg in either guess or secretcode is used, turn the respective index in boolean array to true
            *
            *
            *  1. find all black pegs
            *   (location, color)
            *  2. find all white pegs
            *   (!location, color)
            * */

            /* Invalid guesses
             *  1.
             *  2.
             *  3.
             *  4.
             * */
            int black = 0;
            int white = 0;
            //invalid guesses

            if (guesses.equals("HISTORY")) {
                for (String pop : historyList) {
                    System.out.println(pop); // test
                }
            //    if guess length != secretcode length
            }else if (guesses.length() != secreteCode.length()) {
                System.out.println(guesses + " -> INVALID GUESS\n");

                //when input is lower case;
            }else if (guesses.equals(guesses.toLowerCase())){
                //System.out.println("wrong");  //------test;
                System.out.println(guesses + " -> INVALID GUESS\n");

                //  when there is no input
            } else if (guesses.isEmpty()){
                System.out.println(guesses + " -> INVALID GUESS\n");
            }else{
                //If the letters in the guess != any color options
                boolean validguess = true;
                for (int i = 0; i < guesses.length(); i++){
                    boolean checkcolors = false;
                    for (int j = 0; j < GameConfiguration.colors.length; j++){
                        if (guesses.charAt(i) == GameConfiguration.colors[j].charAt(0)){
                            checkcolors = true;
                        }
                    }
                    if (checkcolors == false){
                        System.out.println(guesses + " -> INVALID GUESS\n");
                        validguess =false;
                        break;
                    }
                }
                if (validguess){
                    guessNumber --;

                    boolean[] usedpeg1 = new boolean[guesses.length()];
                    boolean[] secrete = new boolean[secreteCode.length()];

                    for (int k = 0; k < guesses.length(); k++){
                        if (guesses.charAt(k) == secreteCode.charAt(k)){
                            black ++;
                            usedpeg1[k] = true;
                            secrete[k] = true;

                        }
                    }
                    //white pegs

                    for (int g = 0; g< guesses.length(); g++){
                        for (int s = 0; s < secreteCode.length(); s++){
                            if (guesses.charAt(g) == secreteCode.charAt(s) && !usedpeg1[g] && !secrete[s]){
                                white++;
                                usedpeg1[g] = true;
                                secrete[s] = true;
                                break;
                            }
                        }
                    }

                    //pops history for valid guesses
                    historyList.add(guesses + "       " + black + "B_" + white +"W");


                    if (guesses.equals(secreteCode)) {
                        System.out.println(guesses + " -> Result: " + black + "B_0W - You win !!\n");
                        guessNumber = 0;
                    }
                    else{
                        if (guessNumber == 0){
                            System.out.println("Sorry, you are out of guesses. You lose, boo-hoo. \n");
                            break;
                        } else{
                            System.out.println(guesses + " -> Result: " + black + "B_" + white +"W");
                        }

                }
            }
            // valid guesses

            // * when you use a peg for black or white, you need ot make sure that you don't use that peg again
            // check black // one for loop compare each element at the same index
            // check white // 2 for loops compare
            // guess , result
            // historyList.add(guess + "       " + result);
            // [R R R R]
            // [R B R B]
//            for (int k = 0; k )

            //black pegs

            }
        }
        System.out.println("Are you ready for another game (Y/N): ");
        String yes = scan.nextLine();
        if (Objects.equals(yes, "Y")) {
            historyList.clear();
            guessNumber = GameConfiguration.guessNumber;
            secreteCode = SecretCodeGenerator.getInstance().getNewSecretCode();
            runGame();

            //System.out.println("Loop exit"); // --------test;

        }

    }
}
