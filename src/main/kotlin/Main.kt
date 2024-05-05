package org.example

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.io.File

const val PROBLEMS_KEY = "problems"

data class Input(
    val inputFile1: String,
    val inputFile2: String,
    val outputFileOnly1: String,
    val outputFileOnly2: String,
    val outputFileBoth: String
)

data class Result(
    val problemsOnlyInFirst: Set<JsonObject>,
    val problemsOnlyInSecond: Set<JsonObject>,
    val problemsInBoth: Set<JsonObject>
)

fun readInput(): Input {
    val paths = readln().split(" ")
    check(paths.size == 5) { "Invalid number of arguments" }
    return Input(
        inputFile1 = paths[0],
        inputFile2 = paths[1],
        outputFileOnly1 = paths[2],
        outputFileOnly2 = paths[3],
        outputFileBoth = paths[4]
    )
}

fun writeToFile(fileName: String, problems: Set<JsonObject>) {
    val jsonArray = JsonArray()
    problems.forEach { problem ->
        jsonArray.add(problem)
    }
    val jsonObject = JsonObject()
    jsonObject.add(PROBLEMS_KEY, jsonArray)
    File(fileName).writeText(Gson().toJson(jsonObject))
}

fun getSetOfProblems(filePath: String): Set<JsonObject> {
    val fileContent = File(filePath).readText()
    val jsonObject = Gson().fromJson(fileContent, JsonObject::class.java)
    return extractProblems(jsonObject)
}

fun extractProblems(jsonObject: JsonObject): Set<JsonObject> {
    val problemSet = mutableSetOf<JsonObject>()
    if (jsonObject.has(PROBLEMS_KEY)) {
        jsonObject.getAsJsonArray(PROBLEMS_KEY).forEach { problem ->
            problem.asJsonObject.let { problemSet.add(it) }
        }
    }
    return problemSet
}

fun getResult(problems1: Set<JsonObject>, problems2: Set<JsonObject>): Result {
    val result = Result(
        problemsOnlyInFirst = problems1.subtract(problems2),
        problemsOnlyInSecond = problems2.subtract(problems1),
        problemsInBoth = problems1.intersect(problems2)
    )
    return result
}


fun makeFilesWithProblems(input: Input) {
    val firstProblemSet = getSetOfProblems(input.inputFile1)
    val secondProblemSet = getSetOfProblems(input.inputFile2)

    val result = getResult(firstProblemSet, secondProblemSet)

    writeToFile(input.outputFileOnly1, result.problemsOnlyInFirst)
    writeToFile(input.outputFileOnly2, result.problemsOnlyInSecond)
    writeToFile(input.outputFileBoth, result.problemsInBoth)
}



fun main() {
    try {
        makeFilesWithProblems(readInput())
        println("Execution completed successfully")
    } catch (e: Exception) {
        println("A problem during execution occurred: ${e.message}")
    }
}