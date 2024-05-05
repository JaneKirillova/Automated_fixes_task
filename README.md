# Automated fixes task

## Task 1

### Input:

Sent to standard input. The first line contains an integer n (1 ≤ n ≤ 100), the number of names.

Each of the next n lines contains one word name%i , denoting the i-th name. Each name contains only lowercase letters of the Latin alphabet, no more than 100 characters. All names are different.

### Output:

Sent to standard output.
If there is an order of letters such that the names in the given list appear in lexicographical order, print any such order as a permutation of the characters 'a'–'z' (in other words, print the first letter of the modified alphabet first, then the second, and so on) .

Otherwise, print the single word "Impossible" (without quotes).

### Justification of the logic:

**General logic**: we go through the list of names and form an oriented graph whose vertices are Latin alphabet characters.
The edge from ch1 to ch2 means that in the resulting alphabet ch2 must go before ch1. Then we form the alphabet by the graph.

**How edges are formed**: For each line, we go through the lines that came before it. Find the index of the first different character. The character under this index in the line that came earlier must be the first in the alphabet. We get an edge in the graph. We also handle the situation in which one line is a prefix of another. If this line comes first, then nothing is added to the graph, as it already corresponds to the lexicographic order. 
If the longer string comes first and then its prefix, it is impossible to construct an alphabet.

**How to construct an alphabet from a graph**: Run dfs from an arbitrary vertex. If we find a cycle, it is impossible to build an alphabet. If there are no cycles, then the vertex in the answer must be written when leaving it. When we exit a vertex, it means that we have already considered and written all its children into the answer, but the children of the vertex are just the symbols that should go in the alphabet before the current symbol. So the order is observed. 
We start dfs from unvisited vertices until we have visited them all.

### Testing

Please use `gradlew test` to run tests