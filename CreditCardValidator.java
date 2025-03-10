import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreditCardValidator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o número do cartão de crédito: ");
        String cardNumber = scanner.nextLine();
        scanner.close();

        if (validateCardNumber(cardNumber)) {
            String brand = getCardBrand(cardNumber);
            System.out.println("O número do cartão é válido e pertence à bandeira: " + brand);
        } else {
            System.out.println("O número do cartão é inválido.");
        }
    }

    public static boolean validateCardNumber(String cardNumber) {
        // Valida o número do cartão de crédito usando o algoritmo de Luhn
        cardNumber = removeSpaces(cardNumber);
        if (!cardNumber.matches("\\d+")) {
            return false;
        }

        int total = 0;
        StringBuilder reverseDigits = new StringBuilder(cardNumber).reverse();

        for (int i = 0; i < reverseDigits.length(); i++) {
            int n = Character.getNumericValue(reverseDigits.charAt(i));
            if (i % 2 == 1) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            total += n;
        }

        return total % 10 == 0;
    }

    public static String getCardBrand(String cardNumber) {
        // Retorna a bandeira do cartão com base no prefixo e no comprimento
        cardNumber = removeSpaces(cardNumber);
        int cardLength = cardNumber.length();

        // Prefixos (BIN) e comprimentos de números de cartão das 10 bandeiras listadas no 4devs.com.br
        Map<String, CardBrandData> cardBrands = new HashMap<>();
        cardBrands.put("Visa", new CardBrandData(new String[]{"4"}, new int[]{13, 16, 19}));
        cardBrands.put("Mastercard", new CardBrandData(new String[]{"51", "52", "53", "54", "55"}, new int[]{16}));
        cardBrands.put("American Express", new CardBrandData(new String[]{"34", "37"}, new int[]{15}));
        cardBrands.put("Diners Club", new CardBrandData(new String[]{"300", "301", "302", "303", "304", "305", "36", "38"}, new int[]{14}));
        cardBrands.put("Discover", new CardBrandData(new String[]{"6011", "622126", "622925", "644", "645", "646", "647", "648", "649", "65"}, new int[]{16, 19}));
        cardBrands.put("Elo", new CardBrandData(new String[]{"401178", "401179", "431274", "438935", "451416", "457393", "457631", "457632", "504175", "627780", "636297", "636368"}, new int[]{16}));
        cardBrands.put("Hipercard", new CardBrandData(new String[]{"606282", "637095", "637568", "637599", "637609", "637612"}, new int[]{16}));
        cardBrands.put("Aura", new CardBrandData(new String[]{"50"}, new int[]{16}));
        cardBrands.put("JCB", new CardBrandData(new String[]{"35"}, new int[]{16, 19}));
        cardBrands.put("Maestro", new CardBrandData(new String[]{"5018", "5020", "5038", "5893", "6304", "6759", "6761", "6762", "6763"}, new int[]{12, 13, 14, 15, 16, 17, 18, 19}));

        for (Map.Entry<String, CardBrandData> entry : cardBrands.entrySet()) {
            String brand = entry.getKey();
            CardBrandData data = entry.getValue();
            if (arrayContains(data.lengths, cardLength) && startsWithAny(cardNumber, data.prefixes)) {
                return brand;
            }
        }

        return "Unknown";
    }

    public static String removeSpaces(String cardNumber) {
        // Remove espaços vazios da variável cardNumber
        return cardNumber.replaceAll("\\s+", "");
    }

    private static boolean arrayContains(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    private static boolean startsWithAny(String cardNumber, String[] prefixes) {
        for (String prefix : prefixes) {
            if (cardNumber.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    static class CardBrandData {
        String[] prefixes;
        int[] lengths;

        CardBrandData(String[] prefixes, int[] lengths) {
            this.prefixes = prefixes;
            this.lengths = lengths;
        }
    }
}