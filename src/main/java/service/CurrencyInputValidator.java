package service;

import dao.CurrencyDao;

public final class CurrencyInputValidator {
    private CurrencyInputValidator() {}

    public static boolean isValid(CurrencyDao currency) {
        String name = currency.name();
        String code = currency.code();
        String sign = currency.sign();

        return isValidCurrencyName(name) && isValidCurrencyCode(code) && isValidCurrencySymbol(sign);
    }

    private static boolean isValidCurrencyName(String name){
        if(name == null || name.length() < 3 || name.length() > 50){
            return false;
        }
        return true;
    }

    private static boolean isValidCurrencyCode(String code){
        if(code == null || code.length() != 3){
            return false;
        }

        for(int i = 0; i< 3; i++){
            if(code.charAt(i) < 'A' || code.charAt(i) > 'Z'){
                return false;
            }
        }

        return true;
    }

    private static boolean isValidCurrencySymbol(String symbol){
        if(symbol == null || symbol.length() >3){
            return false;
        }
        return true;
    }
}
