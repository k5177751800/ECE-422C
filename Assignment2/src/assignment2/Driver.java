/* EE422C Assignment #2 submission by
 * Replace <...> with your actual data.
 * <Konghwan Shin>
 * <KS54897>
 */
package assignment2;
import java.util.*;

import assignment2.*;

public class Driver {
    public static void main(String[] args) {
        boolean test;

        //if it is 1, start
        if (args.length >=1){
            if (args[0].equals("1")){
                test = true;
            }else{
                test = false;
            }
        } else{
            test =false;
        }

        System.out.println("Welcome to Mastermind. Here are the rules.\n" +
                "\nThis is a text version of the classic board game Mastermind.\n" +
                "\nThe computer will think of a secret code. The code consists of " + GameConfiguration.pegNumber +
                " \ncolored pegs. The pegs MUST be one of six colors: blue, green,\n" +
                "orange, purple, red, or yellow. A color may appear more than once in\n" +
                "the code. You try to guess what colored pegs are in the code and\n" +
                "what order they are in. After you make a valid guess the result\n" +
                "(feedback) will be displayed.\n" +
                "\nThe result consists of a black peg for each peg you have guessed\n" +
                "exactly correct (color and position) in your guess. For each peg in\n" +
                "the guess that is the correct color, but is out of position, you get\n" +
                "a white peg. For each peg, which is fully incorrect, you get no\n" +
                "feedback.\n" +
                "\nOnly the first letter of the color is displayed. B for Blue, R for\n" +
                "Red, and so forth. When entering guesses you only need to enter the\n" +
                "first character of each color as a capital letter.\n" +
                "\nYou have 12 guesses to figure out the secret code or you lose the\n" +
                "game. Are you ready to play? (Y/N): ");
        Scanner scanner = new Scanner(System.in);
        String runGame = scanner.nextLine();

        if (Objects.equals(runGame, "Y")) {  //------if it Y start game
            //System.out.println("\nGame start\n"); //-----test;


            Game go = new Game(test, scanner);
            go.runGame();  //calling game
        }

    }
}
