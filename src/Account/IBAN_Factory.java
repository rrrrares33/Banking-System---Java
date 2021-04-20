package Account;

import java.util.Random;

public class IBAN_Factory {
    private final String generated_IBAN;

    // IBAN is calculated with this algorithm: http://www.ibantest.com/en/how-is-the-iban-check-digit-calculate
    IBAN_Factory() {
        Integer IBAN_aux = 0;
        Integer bank_number = 26161120; // BCR (R - 26, G - 16, B - 11, K - 20)

        String first_and_middle_part = generate_middle_part(generate_first_part());
        Integer MOD97 = 98 - 272400 % 97;
        Integer last_part = generate_last_part();


        String IBAN = "RO" + Integer.toString(MOD97) + "RGBK" + "00" + "" +
                Integer.toString(last_part) + first_and_middle_part;
        generated_IBAN = IBAN;
    }

    private Integer generate_first_part() {
        Integer account_number = 0;
        while(account_number < 10000000) {
            Random rand = new Random();
            account_number = account_number * 10 + rand.nextInt(9);
        }
        return account_number;
    }

    private String generate_middle_part(Integer first_part) {
        String middle_part = Integer.toString(first_part) + 272400; //27 - R 06 - O - 00

        return middle_part;
    }

    private Integer generate_last_part() {
        Integer last_part = 0;
        while (last_part < 1000000) {
            Random rand = new Random();
            last_part = last_part * 10 + rand.nextInt(9);
        }
        if (last_part < 0)
            last_part *= (-1);
        return last_part;
    }

    public String get_IBAN() {
        return generated_IBAN;
    }
}
