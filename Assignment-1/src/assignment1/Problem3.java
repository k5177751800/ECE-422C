package assignment1;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.util.Scanner;
public class Problem3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String scanner1 = scanner.nextLine();
        //String scanner1 = "I was slowly walking to the park with my over enthusiastic dog when he bit me, and I shouted, Ouch!";----test
        MaxentTagger tagger = new MaxentTagger("./english-bidirectional-distsim.tagger");
        String taggedScanner = tagger.tagString(scanner1);
        System.out.println(taggedScanner);
    }
}
