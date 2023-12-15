package main

import (
	"fmt"
	"os"
	"strings"
)

func main() {
    bytes, err := os.ReadFile("data.txt")

    if err != nil {
        panic(err)
    }

    contents := string(bytes)

    arr := parse(contents)

    sum := 0

    for _, part := range arr {
        sum += hash(part)
    }

    fmt.Println(sum)
}

func hash (part []rune) int {
    result := 0

    for _, char := range part {
        if (char == 10) {
            continue
        }


        result += int(char)
        result *= 17
        result %= 256
    }

    return result
}

func parse (contents string) [][]rune {
    parts := strings.Split(contents, ",")
    result := make([][]rune, 0)

    for _, part := range parts {
        result = append(result, []rune(part))
    }

    return result
}
