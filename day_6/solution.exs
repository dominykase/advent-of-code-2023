defmodule IntParser do
  def parse_part_1(str) do
    elements = String.split(str, " ")
    filtered = Enum.filter(elements, fn element -> element != "" end)
    Enum.map(filtered, fn element -> String.to_integer(element) end)
  end

  def join_strings(acc, index, list) do
    cond do
      index == length(list) -> acc
      true -> join_strings acc <> Enum.at(list, index), index + 1, list
    end
  end

  def parse_part_2(str) do
    elements = String.split(str, " ")
    filtered = Enum.filter(elements, fn element -> element != "" end)
    [String.to_integer(join_strings "", 0, filtered)]
  end
end

defmodule Solution do
  def multiply_list(product, index, list) do
    cond do
      index == length(list) -> product
      true -> multiply_list product * Enum.at(list, index), index + 1, list
    end
  end

  def calc_race(waitingTime, raceTime, distance, count) do
    distanceTravelled = waitingTime * (raceTime - waitingTime)

    cond do
      waitingTime == raceTime -> count
      distanceTravelled > distance -> calc_race waitingTime + 1, raceTime, distance, count + 1  
      true -> calc_race waitingTime + 1, raceTime, distance, count
    end 
  end

  def run(raceIndex, times, distances, winningCounts) do
    count = if raceIndex < length(times) do
      calc_race 1, Enum.at(times, raceIndex), Enum.at(distances, raceIndex), 0
    end
    winningCounts = if count != nil do
      winningCounts ++ [count]
    else
      winningCounts
    end
    cond do 
      raceIndex == length(times) -> winningCounts
      true -> run raceIndex + 1, times, distances, winningCounts
    end
  end
end

{:ok, binary_contents} = File.read("data.txt")
contents = binary_contents |> String.trim_trailing()
[timeStr, distanceStr] = String.split(contents, "\n")

[_ , timesDataStr] = String.split(timeStr, ":")
[_ , distanceDataStr] = String.split(distanceStr, ":")

times = IntParser.parse_part_2(String.trim(timesDataStr))
distances = IntParser.parse_part_2(String.trim(distanceDataStr))

counts = Solution.run 0, times, distances, []
IO.inspect(counts, charlists: :as_lists)
IO.inspect(Solution.multiply_list 1, 0, counts)
