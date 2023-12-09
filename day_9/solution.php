<?php

$contents = file_get_contents('data.txt');

$lines = array_filter(explode("\n", $contents), function ($line) {
    return !empty($line);
});

$lines = array_map(function ($line) {
    return array_map(function ($str) {
        return intval($str);
    }, explode(' ', $line));
}, $lines);

function isZeros($line) {
    return array_reduce($line, function ($carry, $item) {
        return $carry && $item === 0;
    }, true);
}

function findDerivative($line) {
    $arr = [];

    for ($i = 0; $i < count($line) - 1; $i++) {
        $arr[] = $line[$i + 1] - $line[$i];
    }

    return [$arr, isZeros($arr)];
}

$sum = 0;

for ($i = 0; $i < count($lines); $i++) {
    $isZeros = isZeros($lines[$i]);
    $log = [$lines[$i]];

    while (!$isZeros) {
        [$next, $isZeros] = findDerivative($log[count($log) - 1]);
        $log[] = $next;
    }

    $nextNumber = 0;

    for ($j = count($log) - 1; $j > 0; $j--) {
        $nextNumber = $log[$j - 1][0] - $nextNumber;
    }

    $sum += $nextNumber;
}

echo $sum;
