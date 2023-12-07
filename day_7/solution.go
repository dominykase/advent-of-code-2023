package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

const (
    None int        = 0
    HighCard        = 1
    OnePair         = 2
    TwoPair         = 3
    ThreeOfAKind    = 4
    FullHouse       = 5
    FourOfAKind     = 6
    FiveOfAKind     = 7
)

type Hand struct {
    cards []int
    play int
    bid int
}

type Match struct {
    card int
    count int
}

func main() {
    bytes, err := os.ReadFile("data.txt")

    if err != nil {
        panic(err)
    }

    contents := string(bytes)

    hands := parse(contents)

    //for _, hand := range hands {
    //    fmt.Println(hand.cards, hand.play, hand.bid)
    //}

    hands = sortByHighCard(hands)
    hands = sortByPlay(hands)

    sum := 0

    for i, hand := range hands {
        sum += (i + 1) * hand.bid
    }

    fmt.Println(sum)
}

func sortByHighCard(hands []Hand) []Hand {
    for ub := len(hands); ub > 0; ub-- {
        for i := 0; i < ub - 1; i++ { 
            if compareHighCard(hands[i], hands[i + 1]) == 1 {
                hands[i], hands[i + 1] = hands[i + 1], hands[i]
            }
        }
    }

    return hands
}

func compareHighCard(hand1 Hand, hand2 Hand) int {
    for i := 0; i < len(hand1.cards); i++ {
        if hand1.cards[i] > hand2.cards[i] {
            return 1
        } else if hand1.cards[i] < hand2.cards[i] {
            return -1
        }
    }

    return 0;
}

func sortByPlay(hands []Hand) []Hand {
    for ub := len(hands); ub > 0; ub-- {
        for i := 0; i < ub - 1; i++ {
            if hands[i].play > hands[i + 1].play {
                hands[i], hands[i + 1] = hands[i + 1], hands[i]
            }
        }
    }

    return hands
}

func convertCharToScore(r rune) int {
    num := int(r - '0')

    if num >= 2 && num <= 9 {
        return num
    } else {
        switch r {
        case 'T':
            return 10
        case 'Q':
            return 12
        case 'K':
            return 13
        case 'A':
            return 14
        case 'J':
            return 1
        }
    }

    return num
}

func parse(contents string) []Hand {
    hands := make([]Hand, 0)

    lines := strings.Split(contents, "\n")

    for _, line := range lines {
        if len(line) == 0 {
            continue
        }

        cardsStr := strings.Split(line, " ")[0]
        bidStr := strings.Split(line, " ")[1]
        
        cards := make([]int, 0)

        for _, byte := range []rune(cardsStr) {
            cards = append(cards, convertCharToScore(byte))
        }

        bid, err := strconv.Atoi(bidStr)

        if err != nil {
            panic(err)
        }

        hands = append(hands, Hand{cards: cards, play: findPlay(cards), bid: bid})
    }

    return hands
}

func findPlay(cards []int) int {
    if isFiveOfAKind(cards) {
        return FiveOfAKind
    } else if isFourOfAKind(cards) {
        return FourOfAKind
    } else if isFullHouse(cards) {
        return FullHouse
    } else if isThreeOfAKind(cards) {
        return ThreeOfAKind
    } else if isTwoPair(cards) {
        return TwoPair
    } else if isOnePair(cards) {
        return OnePair
    }
    
    return HighCard
}

func maxOfSlice(slice []int) int {
    max := 0

    for _, num := range slice {
        if num > max {
            max = num
        }
    }

    return max
}

func replaceJokers(cards []int, val int) []int {
    for i, card := range cards {
        if card == 1 {
            cards[i] = val
        }
    }

    return cards
}

func findJokerCount(cards []int) int {
    count := 0

    for _, card := range cards {
        if card == 1 {
            count++
        }
    }

    return count
}

func isFiveOfAKind(cards []int) bool {
    cards = replaceJokers(cards, maxOfSlice(cards))

    for _, card := range cards {
        if card != cards[0] {
            return false
        }
    }

    return true
}

func isFourOfAKind(cards []int) bool {
    for i := 0; i < 2; i++ {
        count := 0

        for j := i; j < len(cards); j++ {
            if cards[i] == cards[j] {
                count++
            }
        }

        if count + findJokerCount(cards) == 4 {
            return true
        }
    }

    return false
}


func isFullHouse(cards []int) bool {
    cards = replaceJokers(cards, maxOfSlice(cards))
    var threeMatch *Match = nil

    for i := 0; i < 3; i++ {
        count := 0

        for j := i; j < len(cards); j++ {
            if cards[i] == cards[j] {
                count++
            }
        }

        if count + findJokerCount(cards) == 3 {
            threeMatch = &Match{card: cards[i], count: count}
        }
    }

    if threeMatch == nil {
        return false
    }

    for i := 0; i < 4; i++ {
        count := 0

        for j := i; j < len(cards); j++ {
            if cards[i] == cards[j] {
                count++
            }
        }

        if count + findJokerCount(cards) == 2 && cards[i] != threeMatch.card {
            return true
        }
    }

    return false
}

func isThreeOfAKind(cards []int) bool {
    cards = replaceJokers(cards, maxOfSlice(cards))

    for i := 0; i < 3; i++ {
        count := 0

        for j := i; j < len(cards); j++ {
            if cards[i] == cards[j] {
                count++
            }
        }

        if count + findJokerCount(cards) == 3 {
            return true
        }
    }

    return false
}

func isTwoPair(cards []int) bool {
    cards = replaceJokers(cards, maxOfSlice(cards))
    var firstPair *Match = nil

    for i := 0; i < 4; i++ {
        count := 0

        for j := i; j < len(cards); j++ {
            if cards[i] == cards[j] {
                count++
            }
        }

        if count + findJokerCount(cards) == 2 {
            firstPair = &Match{card: cards[i], count: count}
        }
    }

    if firstPair == nil {
        return false
    }

    for i := 0; i < 4; i++ {
        count := 0

        for j := i; j < len(cards); j++ {
            if cards[i] == cards[j] {
                count++
            }
        }

        if count + findJokerCount(cards) == 2 && cards[i] != firstPair.card {
            return true
        }
    }

    return false
}

func isOnePair(cards []int) bool {
    cards = replaceJokers(cards, maxOfSlice(cards))

    for i := 0; i < 4; i++ {
        count := 0

        for j := i; j < len(cards); j++ {
            if cards[i] == cards[j] {
                count++
            }
        }

        if count + findJokerCount(cards) == 2 {
            return true
        }
    }

    return false
}

