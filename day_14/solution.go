package main

import (
	"fmt"
	"os"
	"strings"
)

func main() {
    bytes, err := os.ReadFile("data.txt");

    if err != nil {
        panic(err)
    }

    contents := string(bytes)
    values := parse(contents)

    values = tiltNorth(values)

    for _, line := range values {
        for i := 0; i < len(line); i++ {
            fmt.Printf("%c", line[i])
        }
        fmt.Print("\n")
    }

    fmt.Println(calcTotalLoad(values))
}

func calcTotalLoad(values [][]rune) int {
    total := 0
    start := len(values)

    for y := 0; y < len(values); y++ {
        for x := 0; x < len(values[y]); x++ {
            if values[y][x] == 'O' {
                total += start
            }
        }

        start -= 1
    }

    return total
}

func tiltNorth(values [][]rune) [][]rune {
    for y := 1; y < len(values); y++ {
        for x := 0; x < len(values[y]); x++ {
            if values[y][x] == 'O' {
                tempY := y

                for ;true; {
                    if tempY-1 < 0 || values[tempY-1][x] == '#' || values[tempY-1][x] == 'O' {
                        break 
                    }
                    
                    values[tempY-1][x] = 'O'
                    values[tempY][x] = '.'
                    tempY--
                }
            }
        }
    }

    return values
}

func parse(contents string) [][]rune {
    lines := strings.Split(contents, "\n")

    values := make([][]rune, 0)

    for _, line := range lines {
        if line == "" {
            continue
        }

        values = append(values, []rune(line))
    }

    return values
}
