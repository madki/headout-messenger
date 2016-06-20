package utils;

import models.headout.Currency;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Created by madki on 20/06/16.
 */
public class Prices {
    public static String format(float price, Currency currency) {
        return format(price, currency, true);
    }

    public static String format(float price, Currency currency, boolean freeAllowed) {
        BigDecimal bd = new BigDecimal(Float.toString(price));
        boolean isZero = bd.compareTo(new BigDecimal("0")) == 0;
        if(isZero && freeAllowed) {
            return "FREE";
        }

        bd = bd.setScale(currency.precision, BigDecimal.ROUND_HALF_UP);
        String priceWithDecimal = String.format(Locale.getDefault(), "%s%s", currency.localSymbol, bd.toString());
        if(currency.precision == 0) {
            return priceWithDecimal;
        } else {
            int indexOfDot = priceWithDecimal.indexOf('.');
            String decimal = priceWithDecimal.substring(indexOfDot + 1);
            int d = Integer.parseInt(decimal);
            if(d == 0) {
                return priceWithDecimal.substring(0, indexOfDot);
            } else {
                return priceWithDecimal;
            }
        }
    }
}
