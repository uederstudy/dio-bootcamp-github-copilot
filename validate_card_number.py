def validate_card_number(card_number):
    """Valida o número do cartão de crédito usando o algoritmo de Luhn."""
    card_number = remove_spaces(card_number)
    if not card_number.isdigit():
        return False

    total = 0
    reverse_digits = card_number[::-1]

    for i, digit in enumerate(reverse_digits):
        n = int(digit)
        if i % 2 == 1:
            n *= 2
            if n > 9:
                n -= 9
        total += n

    return total % 10 == 0


def get_card_brand(card_number):
    """Retorna a bandeira do cartão com base no prefixo e no comprimento."""
    card_number = remove_spaces(card_number)
    card_length = len(card_number)

    # Prefixos (BIN) e comprimentos de números de cartão das 10 bandeiras listadas no 4devs.com.br
    card_brands = {
        "Visa": {"prefixes": ["4"], "lengths": [13, 16, 19]},
        "Mastercard": {"prefixes": ["51", "52", "53", "54", "55"], "lengths": [16]},
        "American Express": {"prefixes": ["34", "37"], "lengths": [15]},
        "Diners Club": {"prefixes": ["300", "301", "302", "303", "304", "305", "36", "38"], "lengths": [14]},
        "Discover": {"prefixes": ["6011", "622126", "622925", "644", "645", "646", "647", "648", "649", "65"], "lengths": [16, 19]},
        "Elo": {"prefixes": ["401178", "401179", "431274", "438935", "451416", "457393", "457631", "457632", "504175", "627780", "636297", "636368"], "lengths": [16]},
        "Hipercard": {"prefixes": ["606282", "637095", "637568", "637599", "637609", "637612"], "lengths": [16]},
        "Aura": {"prefixes": ["50"], "lengths": [16]},
        "JCB": {"prefixes": ["35"], "lengths": [16, 19]},
        "Maestro": {"prefixes": ["5018", "5020", "5038", "5893", "6304", "6759", "6761", "6762", "6763"], "lengths": [12, 13, 14, 15, 16, 17, 18, 19]},
    }

    for brand, data in card_brands.items():
        if card_length in data["lengths"] and any(card_number.startswith(prefix) for prefix in data["prefixes"]):
            return brand

    return "Unknown"


def remove_spaces(card_number):
    """Remove espaços vazios da variável card_number."""
    return card_number.replace(" ", "")


def main():
    card_number = input("Digite o número do cartão de crédito: ")

    if validate_card_number(card_number):
        brand = get_card_brand(card_number)
        print(f"O número do cartão é válido e pertence à bandeira: {brand}")
    else:
        print("O número do cartão é inválido.")


if __name__ == "__main__":
    main()