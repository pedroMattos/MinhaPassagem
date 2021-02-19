package peoplesolutions.com.minhapassagem;

import java.text.DecimalFormat;

public class FormatDec {
        //formata os numeros decimais para apenas 2 apos a virgula
        public String FormatDecimal(double dec){
            DecimalFormat df = new DecimalFormat("0.00");
            df.setMaximumFractionDigits(2);
            String decimalFormat = df.format(dec);
            return decimalFormat;
        }
    }
