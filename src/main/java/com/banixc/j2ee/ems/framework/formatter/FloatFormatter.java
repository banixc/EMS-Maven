package com.banixc.j2ee.ems.framework.formatter;

import org.springframework.format.Formatter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

public class FloatFormatter implements Formatter<BigDecimal> {
    @Override
    public BigDecimal parse(String s, Locale locale) throws ParseException {
        return new BigDecimal(s);
    }

    @Override
    public String print(BigDecimal bigDecimal, Locale locale) {
        int p = bigDecimal.scale();
        while(bigDecimal.scale() != 0 && bigDecimal.toString().charAt(bigDecimal.toString().length()-1)=='0' )
            bigDecimal = bigDecimal.setScale(bigDecimal.scale()-1,BigDecimal.ROUND_DOWN);
        return bigDecimal.toString();
    }

    public static BigDecimal Formatter(BigDecimal b){
        FloatFormatter f = new FloatFormatter();
        try {
            return f.parse(f.print(b,null),null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new BigDecimal(0);
    }
}
