<?php

$contents = file_get_contents('data.txt');

$contents = array_filter(explode("\n", $contents), function ($val) {
    return !empty($val);
});

$contents = array_map(function ($val) {
    return str_split($val);
}, $contents);

$emptyLineIds = [];

for ($i = 0; $i < count($contents); $i++) {
    if (array_reduce($contents[$i], function ($carry, $item) {
            return $carry && $item == '.';
        }, true)) {
        $emptyLineIds[] = $i;
    }
}

$emptyColumnIds = [];

for ($i = 0; $i < count($contents[0]); $i++) {
    $column = array_column($contents, $i);

    if (array_reduce($column, function ($carry, $item) {
            return $carry && $item == '.';
        }, true)) {
        $emptyColumnIds[] = $i;
    }
}

class Point
{
    public const SPACING = 1000000;

    public function __construct(
        public int $x,
        public int $y,
    ) {
    }

    public function getDistance(Point $point, array $emptyLineIds, array $emptyColumnIds): float
    {
        $columnSpacing = 0;                

        for ($i = min($this->x, $point->x) + 1; $i < max($point->x, $this->x); $i++) {
            if (in_array($i, $emptyColumnIds)) {
                $columnSpacing += self::SPACING - 1;
            }
        }

        $lineSpacing = 0;

        for ($i = min($this->y, $point->y) + 1; $i < max($point->y, $this->y); $i++) {
            if (in_array($i, $emptyLineIds)) {
                $lineSpacing += self::SPACING - 1;
            }
        }

        return abs($this->x - $point->x) + abs($this->y - $point->y) + $columnSpacing + $lineSpacing;
    }
}

$galaxies = [];

for ($i = 0; $i < count($contents); $i++) {
    for ($j = 0; $j < count($contents[$i]); $j++) {
        if ($contents[$i][$j] == '#') {
            $galaxies[] = new Point($j, $i);
        }
    }
}

$sum = 0;

for ($i = 0; $i < count($galaxies); $i++) {
    for ($j = $i; $j < count($galaxies); $j++) {
        if ($i == $j) {
            continue;
        }

        $sum += $galaxies[$i]->getDistance($galaxies[$j], $emptyLineIds, $emptyColumnIds);
    }
}

echo $sum;

/*
foreach ($contents as $line) {
    for ($i = 0; $i < count($line); $i++) {
        echo $line[$i];
    }
    echo "\n";
}
*/
