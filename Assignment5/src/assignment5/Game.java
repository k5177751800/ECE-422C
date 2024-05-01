/* EE422C Assignment #2 submission by
 * Replace <...> with your actual data.
 * <Konghwan Shin>
 * <KS54897>
 */
package assignment5;
import java.util.*;
import java.util.Scanner;

public class Game {
    private static boolean tester;
    private static Scanner scan;
    private static int guessNumber;
    private static String secreteCode;
    private static ArrayList<String> historyList;

    public Game(boolean test, String secreteCode){
        guessNumber = assignment5.GameConfiguration.guessNumber;
        this.secreteCode = secreteCode;
        tester  = test;
        scan = new Scanner(System.in);
        historyList = new ArrayList<>();
    }
    public static String startGame(){
        if(tester == true){
            return ("\nGenerating secret code ...(for this example the secret code is " + secreteCode + ")\n\n" + "You have " + guessNumber + " guesses left.\n" +
                    "What is your next guess?\n" +
                    "Type in the characters for your guess and press enter.\n" +
                    "Enter guess: ");
        } else{
            return ("\nGenerating secret code ...\n\n" + "You have " + guessNumber + " guesses left.\n" +
                    "What is your next guess?\n" +
                    "Type in the characters for your guess and press enter.\n" +
                    "Enter guess: ");
        }
    }
    public static String runGame(String guesses) {
        while (guessNumber > 0) {
            int black = 0;
            int white = 0;
            //invalid guesses
            if (guesses.equals("HISTORY")) {
                String historyS = "";
                for (String pop : historyList) {
                    historyS += pop + "\n"; // test
                }
                return historyS + "\n You have " + guessNumber + " guesses left.\n" +
                "What is your next guess?\n" +
                        "Type in the characters for your guess and press enter.\n" +
                        "Enter guess: ";
                //    if guess length != secretcode length
            } else if (guesses.length() != secreteCode.length()) {
                return (guesses + " -> INVALID GUESS\n" + "\n You have " + guessNumber + " guesses left.\n" +
                        "What is your next guess?\n" +
                        "Type in the characters for your guess and press enter.\n" +
                        "Enter guess: ");

                //when input is lower case;
            } else if (guesses.equals(guesses.toLowerCase())) {
                //System.out.println("wrong");  //------test;
                return (guesses + " -> INVALID GUESS\n" + "\n You have " + guessNumber + " guesses left.\n" +
                        "What is your next guess?\n" +
                        "Type in the characters for your guess and press enter.\n" +
                        "Enter guess: ");

                //  when there is no input
            } else if (guesses.isEmpty()) {
                return (guesses + " -> INVALID GUESS\n" + "\n You have " + guessNumber + " guesses left.\n" +
                        "What is your next guess?\n" +
                        "Type in the characters for your guess and press enter.\n" +
                        "Enter guess: ");
            } else {
                //If the letters in the guess != any color options
                for (int i = 0; i < guesses.length(); i++) {
                    boolean checkcolors = false;
                    for (int j = 0; j < assignment5.GameConfiguration.colors.length; j++) {
                        if (guesses.charAt(i) == assignment5.GameConfiguration.colors[j].charAt(0)) {
                            checkcolors = true;
                        }
                    }
                    if (checkcolors == false) {
                        return (guesses + " -> INVALID GUESS\n" + "\n You have " + guessNumber + " guesses left.\n" +
                                "What is your next guess?\n" +
                                "Type in the characters for your guess and press enter.\n" +
                                "Enter guess: ");
                    }
                }
                guessNumber--;
                boolean[] usedpeg1 = new boolean[guesses.length()];
                boolean[] secrete = new boolean[secreteCode.length()];

                for (int k = 0; k < guesses.length(); k++) {
                    if (guesses.charAt(k) == secreteCode.charAt(k)) {
                        black++;
                        usedpeg1[k] = true;
                        secrete[k] = true;

                    }
                }
                //white pegs

                for (int g = 0; g < guesses.length(); g++) {
                    for (int s = 0; s < secreteCode.length(); s++) {
                        if (guesses.charAt(g) == secreteCode.charAt(s) && !usedpeg1[g] && !secrete[s]) {
                            white++;
                            usedpeg1[g] = true;
                            secrete[s] = true;
                            break;
                        }
                    }
                }

                //pops history for valid guesses
                historyList.add(guesses + "       " + black + "B_" + white + "W");


                if (guesses.equals(secreteCode)) {
                    return (guesses + " -> Result: " + black + "B_0W - You win !!\n");
                } else {
                    if (guessNumber == 0) {
                        return ("Sorry, you are out of guesses. You lose, boo-hoo. \n" );
                    } else {
                        return (guesses + " -> Result: " + black + "B_" + white + "W" + "\n You have " + guessNumber + " guesses left.\n" +
                                "What is your next guess?\n" +
                                "Type in the characters for your guess and press enter.\n" +
                                "Enter guess: ");
                    }
                }
            }
        }
        System.out.println("Are you ready for another game (Y/N): ");
        //System.out.println("Loop exit"); // --------test;
        return "";
    }
    public String getSecreteCode(){
        return secreteCode;
    }
    public int getNumberGuesses(){
        return guessNumber;
    }
}
