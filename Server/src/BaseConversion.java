import java.util.Collections;
import java.util.stream.Collectors;

public class BaseConversion {
    public void myStrlen(char [] number, int numbersBeforeRadix[], int umbersAfterRadix[]){
        boolean flag = true;
        int length = number.length;

        for (int i=0 ; i<length ; i++){
            if(number[i] == '.'){
                flag = false;
            }
            if(flag) numbersBeforeRadix[0]++;
            else umbersAfterRadix[0]++;
        }
    }


    public <T> Number anyBaseToDecimal(T number,int fromBase){
        int numberIndex = 0;
        int [] numbersBeforeRadix = {0}, numbersAfterRadix = {0};
        double decimalNumber = 0;
        char [] num = number.toString().toCharArray();

        myStrlen(num, numbersBeforeRadix, numbersAfterRadix);

        while(numbersBeforeRadix[0] > 0){
            if( (int) num[numberIndex] >= 48 && (int) num[numberIndex] <= 57) {
                decimalNumber += (double) ((int) num[numberIndex] - 48) * Math.pow(fromBase, --numbersBeforeRadix[0]);
            }
            else {
                decimalNumber += (double) ( (int) num[numberIndex] - 55) * Math.pow(fromBase, --numbersBeforeRadix[0]);
            }
            numberIndex++;
        }
        numberIndex++; //for avoid radix point

        for(int j=1 ; j<=numbersAfterRadix[0] ; j++){
            if(num[numberIndex] >= 48 && num[numberIndex] <= 57)
                decimalNumber += (double)( (int) num[numberIndex]-48) * Math.pow(fromBase,--j);
            else
                decimalNumber += (double)( (int) num[numberIndex]-55) * Math.pow(fromBase,-j);
            numberIndex++;
        }

        if( decimalNumber == (int) decimalNumber) {
            return (int) decimalNumber;
        }
        else {
            return decimalNumber;
        }
    }

    public String  decimalToBase(Number number,int toBase){
        double decimalNumber = number.doubleValue();
        long whole = (long) decimalNumber;
        double fraction = decimalNumber - (double) whole;
        String basedNumber = "";
        long temp;

        while(whole != 0){
            temp = whole % toBase;
            if(temp < 10) {
                basedNumber += (char)(temp+48);
            }
            else basedNumber += (char) (temp+55);

            whole /= toBase;
        }

        // revarseing the array
        char [] tempStr = basedNumber.toCharArray();
        int length = tempStr.length;
        basedNumber = "";
        for (int i = length-1; i >=0; --i) {
            basedNumber += tempStr[i];
        }

        if(fraction == 0.0) {
            return basedNumber;
        }
        else {
            basedNumber += '.';
            int i = 0;
            while (fraction != 0 && (i < 10)) {
                fraction *= (double) toBase;
                temp = (int) fraction;

                if (temp < 10) basedNumber += (char) (temp + 48);
                else basedNumber += (char) (temp + 55);

                // for whole number
                fraction -= temp;
                i++;
            }

            return basedNumber;
        }
    }


}
