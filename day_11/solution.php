<?php

$contents = file_get_contents('data.txt');

$contents = array_filter(explode("\n", $contents), function ($val) {
    return !empty($val);
});

$contents = array_map(function ($val) {
    return str_split($val);
}, $contents);

for ($i = 0; $i < count($contents); $i++) {
    if (array_reduce($contents[$i], function ($carry, $item) {
            return $carry && $item == '.';
        }, true)) {
        $line = array_fill(0, count($contents[$i]), '.');

        array_splice($contents, $i, 0, [$line]);
        $i++;
    }
}


for ($i = 0; $i < count($contents[0]); $i++) {
    $column = array_column($contents, $i);

    if (array_reduce($column, function ($carry, $item) {
            return $carry && $item == '.';
        }, true)) {
        for ($j = 0; $j < count($contents); $j++) {
            array_splice($contents[$j], $i, 0, ['.']);
        }
        $i++;
    }
}

class Point
{
    public function __construct(
        public int $x,
        public int $y,
    ) {
    }

    public function getDistance(Point $point): float
    {
        return abs($this->x - $point->x) + abs($this->y - $point->y);
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

        $sum += $galaxies[$i]->getDistance($galaxies[$j]);
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
