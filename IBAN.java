/**
 * @author repat
 * https://de.wikipedia.org/wiki/International_Bank_Account_Number
 */
package test;

import java.math.BigInteger;
import java.util.HashMap;

public class IBAN {

    private final int MAGIC98 = 98;
    private final int MAGIC97 = 97;
    private final String MAGIC00 = "00";

    private String ktnr;
    private String blz;
    private String countryCode;
    private HashMap<String, Integer> ccMap = new HashMap<String, Integer>();

    public String getKtnr() {
        return this.ktnr;
    }

    public String getBlz() {
        return this.blz;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public HashMap<String, Integer> getCcMap() {
        return ccMap;
    }

    private void setKtnr(String ktnr) {
        if (ktnr.length() == 9) {
            this.ktnr = "0" + ktnr;
        } else if (ktnr.length() == 10) {
            this.ktnr = ktnr;
        } else {
            System.out.println("Falsche Kontonummer!\n");
            System.exit(-1);
        }
    }

    private void setBlz(String blz) {
        if (blz.length() == 8) {
            this.blz = blz;
        } else {
            System.out.println("Falsche Bankleitzahl!\n");
            System.exit(-1);
        }
    }

    private void setCountryCode(String countryCode) {
        if (countryCode.length() == 2
                && getCcMap().containsKey(countryCode.substring(0, 1))
                && getCcMap().containsKey(countryCode.substring(1, 2))) {
            this.countryCode = countryCode;
        } else {
            System.out.println("Falscher Country Code!\n");
            System.exit(-1);
        }
    }

    private void initHashMap() {
        int i = 10;
        for (char c = 'A'; c <= 'Z'; c++) {
            ccMap.put(String.valueOf(c), i);
            i++;
        }
    }

    private String calculate() {
        String iban = getBlz() + getKtnr()
                + getCcMap().get(getCountryCode().substring(0, 1))
                + getCcMap().get(getCountryCode().substring(1, 2)) + MAGIC00;

        BigInteger bigIban = new BigInteger(iban);

        BigInteger mod = bigIban.mod(BigInteger.valueOf(MAGIC97));

        int checksum = MAGIC98 - mod.intValue();
        if (checksum < 10) {
            return getCountryCode() + "0" + checksum + getBlz() + getKtnr();
        }
        return getCountryCode() + checksum + getBlz() + getKtnr();
    }

    IBAN(String ktnr, String blz) {
        setKtnr(ktnr);
        setBlz(blz);
    }

    IBAN() {

    }

    public static void main(String[] args) {

        IBAN iban = new IBAN("", "");
        iban.initHashMap();
        // iban.setKtnr("");
        // iban.setBlz("");
        iban.setCountryCode("DE");
        System.out.println(iban.calculate());

    }
}
