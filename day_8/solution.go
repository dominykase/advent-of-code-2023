package main

import (
	"fmt"
	"os"
	"strings"
)

type Tuple struct {
    left string
    right string
}

func main() {
    bytes, err := os.ReadFile("data.txt")

    if err != nil {
        panic(err)
    }

    contents := string(bytes)

    directions, mapping, _ := parse(contents)
    //fmt.Println(directions)
    
    //for key, value := range mapping {
    //    fmt.Println(key, value)
    //}

    fmt.Println(findPathLengthPart2(directions, mapping))
}

func findPathLengthPart2(directions string, mapping map[string]Tuple) int {
    count := 0
    
    keys := make([]string, 0)

    for tempKey, _ := range mapping {
        if rune(tempKey[2]) == 'A' {
            keys = append(keys, tempKey)
        }
    }

    for i := 0; true; i++ {
        direction := rune(directions[i])
        
        if direction == 'L' {
            for i, tempKey := range keys {
                keys[i] = mapping[tempKey].left
            }
        } else if direction == 'R' {
            for i, tempKey := range keys {
                keys[i] = mapping[tempKey].right
            }
        }
        
        count++
        
        isAllEndingInZ := true

        for _, tempKey := range keys {
            if tempKey[2] != 'Z' {
                isAllEndingInZ = false
                break
            }
        }

        if isAllEndingInZ {
            break
        }

        if i == len(directions) - 1 {
            i = -1
        }
    }

    return count
}

func findPathLengthPart1(directions string, mapping map[string]Tuple, key string) int {
    count := 0

    for i := 0; true; i++ {
        direction := rune(directions[i])
        
        if direction == 'L' {
            key = mapping[key].left
        } else if direction == 'R' {
            key = mapping[key].right
        }
        
        count++
        
        if key == "ZZZ" {
            break;
        }

        if i == len(directions) - 1 {
            i = -1
        }
    }

    return count
}

func parse(contents string) (string, map[string]Tuple, string) {
    directions := strings.Split(contents, "\n\n")[0]
    mappingsStr := strings.Split(contents, "\n\n")[1]

    mapping := make(map[string]Tuple)
    first := ""

    for _, mappingStr := range strings.Split(mappingsStr, "\n") {
        if mappingStr == "" {
            continue
        }

        parts := strings.Split(mappingStr, " = ")
        key := parts[0]
        innerParts := strings.Split(parts[1], ", ")

        left := strings.Split(innerParts[0], "(")[1]
        right := strings.Split(innerParts[1], ")")[0]

        mapping[key] = Tuple{left, right}

        if first == "" {
            first = key
        }
    }

    return directions, mapping, first
}
