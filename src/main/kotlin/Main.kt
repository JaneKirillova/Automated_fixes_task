package org.example

enum class NodeState {
    NOT_VISITED,
    IN_PROGRESS,
    VISITED
}

enum class DfsResult {
    SUCCESS, // if dfs did not find cycles
    FAIL // if dfs found cycle
}

class Solver(private val lines: List<String>) {
    private var result = mutableListOf<Char>()
    private val graph = mutableMapOf<Char, MutableSet<Char>>()
    private val states = mutableMapOf<Char, NodeState>()

    init {
        for (ch in 'a'..'z') {
            graph[ch] = mutableSetOf()
            states[ch] = NodeState.NOT_VISITED
        }
    }

    private fun firstDifferentCharIndex(first: String, second: String): Int {
        var i = 0
        while (i < first.length && i < second.length && first[i] == second[i]) {
            i++
        }
        return i
    }


    private fun dfs(
        graph: MutableMap<Char, MutableSet<Char>>,
        node: Char,
        states: MutableMap<Char, NodeState>
    ): DfsResult {
        states[node] = NodeState.IN_PROGRESS

        for (child in graph[node]!!) {
            when (states[child]!!) {
                NodeState.VISITED -> continue
                NodeState.NOT_VISITED -> if (dfs(graph, child, states) != DfsResult.SUCCESS) return DfsResult.FAIL
                NodeState.IN_PROGRESS -> return DfsResult.FAIL
            }
        }
        states[node] = NodeState.VISITED
        result.add(node)
        return DfsResult.SUCCESS
    }


    fun solve(): List<Char> {
        if (lines.size == 1) return graph.keys.toList()
        for (i in 1 until lines.size) {
            val currentLine = lines[i]
            for (j in 0 until i) {
                val previousLine = lines[j]
                val index = firstDifferentCharIndex(previousLine, currentLine)
                if (index == currentLine.length || index == previousLine.length) {
                    if (previousLine.length <= currentLine.length) continue
                    return emptyList()
                }
                graph[currentLine[index]]?.add(previousLine[index])
            }
        }

        for (ch in 'a'..'z') {
            if (states[ch] == NodeState.NOT_VISITED) {
                if (dfs(graph, ch, states) == DfsResult.FAIL) return emptyList()
            }
        }
        return result
    }
}

fun readInput(): List<String> {
    val number = readlnOrNull()
    if (number == null) {
        println("Problem with reading input. Exiting.")
        return emptyList()
    }
    val lines = mutableListOf<String>()
    for (i in 1..number.toInt()) {
        lines.add(readln())
    }
    return lines
}

fun printResult(result: List<Char>) {
    if (result.isEmpty()) {
        println("Impossible")
    } else {
        println(result.joinToString(""))
    }
}


fun main() {
    val solver = Solver(readInput())
    val result = solver.solve()
    printResult(result)
}