function validateCardNumber(cardNumber) {
    // Valida o número do cartão de crédito usando o algoritmo de Luhn
    cardNumber = removeSpaces(cardNumber);
    if (!/^\d+$/.test(cardNumber)) {
        return false;
    }

    let total = 0;
    let reverseDigits = cardNumber.split('').reverse();

    for (let i = 0; i < reverseDigits.length; i++) {
        let n = parseInt(reverseDigits[i], 10);
        if (i % 2 === 1) {
            n *= 2;
            if (n > 9) {
                n -= 9;
            }
        }
        total += n;
    }

    return total % 10 === 0;
}

function getCardBrand(cardNumber) {
    // Retorna a bandeira do cartão com base no prefixo e no comprimento
    cardNumber = removeSpaces(cardNumber);
    let cardLength = cardNumber.length;

    // Prefixos (BIN) e comprimentos de números de cartão das 10 bandeiras listadas no 4devs.com.br
    const cardBrands = {
        "Visa": { prefixes: ["4"], lengths: [13, 16, 19] },
        "Mastercard": { prefixes: ["51", "52", "53", "54", "55"], lengths: [16] },
        "American Express": { prefixes: ["34", "37"], lengths: [15] },
        "Diners Club": { prefixes: ["300", "301", "302", "303", "304", "305", "36", "38"], lengths: [14] },
        "Discover": { prefixes: ["6011", "622126", "622925", "644", "645", "646", "647", "648", "649", "65"], lengths: [16, 19] },
        "Elo": { prefixes: ["401178", "401179", "431274", "438935", "451416", "457393", "457631", "457632", "504175", "627780", "636297", "636368"], lengths: [16] },
        "Hipercard": { prefixes: ["606282", "637095", "637568", "637599", "637609", "637612"], lengths: [16] },
        "Aura": { prefixes: ["50"], lengths: [16] },
        "JCB": { prefixes: ["35"], lengths: [16, 19] },
        "Maestro": { prefixes: ["5018", "5020", "5038", "5893", "6304", "6759", "6761", "6762", "6763"], lengths: [12, 13, 14, 15, 16, 17, 18, 19] }
    };

    for (let brand in cardBrands) {
        if (cardBrands[brand].lengths.includes(cardLength) &&
            cardBrands[brand].prefixes.some(prefix => cardNumber.startsWith(prefix))) {
            return brand;
        }
    }

    return "Unknown";
}

function removeSpaces(cardNumber) {
    // Remove espaços vazios da variável cardNumber
    return cardNumber.replace(/\s+/g, '');
}

function main() {
    const readline = require('readline').createInterface({
        input: process.stdin,
        output: process.stdout
    });

    readline.question('Digite o número do cartão de crédito: ', cardNumber => {
        if (validateCardNumber(cardNumber)) {
            const brand = getCardBrand(cardNumber);
            console.log(`O número do cartão é válido e pertence à bandeira: ${brand}`);
        } else {
            console.log('O número do cartão é inválido.');
        }
        readline.close();
    });
}

main();