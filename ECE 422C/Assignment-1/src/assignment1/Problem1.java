package assignment1;

import java.util.Scanner;
public class Problem1 {
    public static void main(String[] args) {

        Scanner scanner2 = new Scanner(System.in);

        int n = Integer.parseInt(scanner2.nextLine());


        String str = scanner2.nextLine();

        int chartoint;
        long maximum =0;
        for(int i = 0; i < str.length() - n +1; i ++){
            String calculate = str.substring(i, i + n);

            long multiple =0;
              // one more  max variable
            long fmax =0;

              for (int j = 0; j<n; j++){
                  // skips when its zero
                  if (calculate.charAt(j) == '0'){
                      if (fmax< multiple){
                          fmax= multiple;
                      }
                      multiple = 0;
                  } else{
                      if (multiple == 0 ){
                          multiple++;
                      }
                      char stringtochar = calculate.charAt(j);
                      chartoint = Character.getNumericValue(stringtochar); //char to int
                      // multiplication function
                      multiple *= chartoint;
                      if (fmax< multiple){
                          fmax= multiple;
                      }
                  }
              }
//            System.out.println(multiple); //----test
            if (maximum < fmax){
                maximum = fmax;
            }
        }
        System.out.println(maximum);
    }
}

