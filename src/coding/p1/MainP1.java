package coding.p1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Problem 1 Solution.
 * 
 * The assignment is to code an application that can convert a product ID number to a standard ISBN­10 number.

 * If we take the Da Vinci Code as an example, the product ID is 978140007917 and the ISBN is 1400079179. 
 * The first 3 digits of the product ID (978) are a prefix that can be removed. The 
 * remaining digits of the product ID (140007917) are the digits of the ISBN excluding the error
 * control digit. Refer to http://www.ee.unb.ca/tervo/ee4243/isbn4243.htm for a description of
 * how an ISBN number is constructed.
 * 
 * Your task is to develop an application that can accept a product ID number and generate the
 * ISBN­10 number.
 * 
 * You may use Python, C, C++, C#, or Java. You may not use any non­standard libraries (ex.
 * core python libraries are fine, but PyPi packages are not; STL is fine, but boost is not.)
 * 
 * Please put your code in a single file for convenience and provide the command to compile it,
 * or the interpreter version used in a comment.
 * 
 * @author Patricio
 */
public class MainP1 {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input;
            while ((input = br.readLine()) != null) {
                input = input.trim();
                if(input.length() == 0) continue;
                String isbn = getISBN(input.trim());
                if(isbn == null) System.err.println("[ERROR] Bad product ID: "+ input);
                System.out.println(isbn);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
    
    /**
    * Converts the product ID to ISBN number.
    */
    public static String getISBN(String productId){
        if(productId == null || productId.length() != 12 || !productId.matches("[0-9]+")) return null;
        
        String remainingDigits = productId.substring(3);
        String isbn = remainingDigits + getErrorControlDigit(remainingDigits);
        return isbn;
    }
    
    /**
    * Calculates the error control digit for the product ID.
    */
    public static String getErrorControlDigit(String lastDigits){        
        int productsSum = 0;
        int position = 10;
        for(int i=0;i<9;i++){
            int digit = Integer.parseInt(lastDigits.charAt(i)+"");
            productsSum += digit * position--;
        }
        int productsSumMod11 = productsSum%11;
        int controlDigit = (productsSumMod11 == 0) ? productsSumMod11 : 11 - productsSumMod11;
        if(controlDigit == 10)
            return "x";
        return controlDigit+"";
    }
}
