package main

import (
	"fmt"
	"os"
	"strings"
)

func main() {
    startingDirection := rune(os.Args[1][0])

    bytes, err := os.ReadFile("data.txt")
    
    if err != nil {
        panic(err)
    }

    contents := string(bytes)

    mapOfChars := parse(contents)

    x, y := findStart(mapOfChars)

    length := findLoopLength(mapOfChars, x, y, startingDirection)

    fmt.Println(length / 2)
}

func findLoopLength(pipes [][]rune, x int, y int, direction rune) int {
    steps := 0

    for ; true; {
        switch direction {
        case 'n':
            y--
            break
        case 'e':
            x++
            break
        case 's':
            y++
            break
        case 'w':
            x--
        }

        if pipes[y][x] == 'L' {
            if direction == 'n' || direction == 's' {
                direction = 'e'
            } else {
                direction = 'n'
            }
        } else if pipes[y][x] == 'J' {
            if direction == 'n' || direction == 's' {
                direction = 'w'
            } else {
                direction = 'n'
            }
        } else if pipes[y][x] == '7' {
            if direction == 's' || direction == 'n' {
                direction = 'w'
            } else {
                direction = 's'
            }
        } else if pipes[y][x] == 'F' {
            if direction == 's' || direction == 'n' {
                direction = 'e'
            } else {
                direction = 's'
            }
        }

        // fmt.Println(string(pipes[y][x]), string(direction))

        steps++

        if pipes[y][x] == 'S' {
            break
        }
    }

    return steps
}

func findStart(mapOfChars [][]rune) (int, int) {
    for y, line := range mapOfChars {
        for x, char := range line {
            if char == 'S' {
                return x, y
            }
        }
    }

    panic("No start found")
}

func parse(contents string) [][]rune {
    lines := strings.Split(contents, "\n")

    mapOfChars := make([][]rune, 0)

    for _, line := range lines {
        if line == "" {
            continue
        }

        chars := []rune(line)
        mapOfChars = append(mapOfChars, chars)
    }

    return mapOfChars
}
