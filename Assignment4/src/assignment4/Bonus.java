package assignment4;

import assignment4.GraphPoet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Bonus {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.ece.utexas.edu/people/faculty/nina-telang").get();
        Element l = doc.select("p").get(1);
        File t = File.createTempFile("KONGISTHEBEST", ".txt");
        PrintWriter out = new PrintWriter(t);
        out.println(l.text());
        final GraphPoet nimoy = new GraphPoet(t);
        System.out.println(nimoy.poem(new File("bonus_input.txt")));
    }
}
