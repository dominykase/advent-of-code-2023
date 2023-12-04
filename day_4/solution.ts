const fs = require('fs');
const readline = require('readline');

const parseAndCaculate = (line: string): number => {
    const remainingLine = line.split(": ")[1]; 
    const [winningStr, ticketStr] = remainingLine.split(" | ");

    let winningCount = 0;
    
    let winningNumbers: number[] = winningStr.split(" ").filter((x) => x !== "").map((x) => parseInt(x));
    let ticketNumbers: number[] = ticketStr.split(" ").filter((x) => x !== "").map((x) => parseInt(x));

    for (let i = 0; i < winningNumbers.length; i++) {
        for (let j = 0; j < ticketNumbers.length; j++) {
            if (winningNumbers[i] === ticketNumbers[j]) {
                winningCount++;
                break;
            }
        }
    }

    // Part 1: return winningCount > 0 ? Math.pow(2, winningCount - 1) : 0;
    return winningCount;
}

type CardInfo = {
    count: number;
}

async function processLineByLine(filename: string) {
    const fileStream = fs.createReadStream(filename);

    const rl = readline.createInterface({
        input: fileStream,
        crlfDelay: Infinity
    });

    let cards: Map<number, CardInfo> = new Map<number, CardInfo>();
    let cardNum: number = 1;

    for await (const line of rl) {
        if (!cards.has(cardNum)) {
            cards.set(cardNum, {count: 0});
        }
        
        let current: CardInfo = cards.get(cardNum) as CardInfo;

        cards.set(cardNum, { count: current.count + 1 });
        
        for (let j = 0; j < (cards.get(cardNum) as CardInfo).count; j++) {
            const count = parseAndCaculate(line);

            for (let i = cardNum + 1; i <= count + cardNum; i++) {
                if (!cards.has(i)) {
                    cards.set(i, { count: 0 });
                }

                cards.set(i, { count: (cards.get(i) as CardInfo).count + 1 });
            }
        }

        console.log(cardNum);
        cardNum++;
    }

    let sum = 0;

    Array.from(cards.values()).forEach((card: CardInfo) => sum += card.count);

    console.log(sum);
}

processLineByLine("data.txt");
