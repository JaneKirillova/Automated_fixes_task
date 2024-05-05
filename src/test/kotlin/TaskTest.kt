import com.google.gson.Gson
import com.google.gson.JsonObject
import org.example.extractProblems
import org.example.getResult
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskTest {
    @Test
    fun testExtractProblems() {
        val jsonContent = """
        {
          "problems": [
            {
              "hash": "hash1",
              "data": [
                "data 1",
                "data 2"
              ]
            },
            {
              "hash": "hash2",
              "data": [
                "data 11",
                "data 22"
              ]
            }
          ]
        }
        """.trimIndent()
        val problem1 = """
            {
              "hash": "hash1",
              "data": [
                "data 1",
                "data 2"
              ]
            }
        """.trimIndent()

        val problem2 = """
            {
              "hash": "hash2",
              "data": [
                "data 11",
                "data 22"
              ]
            }
        """.trimIndent()
        val jsonObject = Gson().fromJson(jsonContent, JsonObject::class.java)
        val jsonProblem1 = Gson().fromJson(problem1, JsonObject::class.java)
        val jsonProblem2 = Gson().fromJson(problem2, JsonObject::class.java)

        val problems = extractProblems(jsonObject)
        val expected = setOf(jsonProblem1, jsonProblem2)
        assertEquals(expected, problems)
    }

    @Test
    fun testGetResult() {
        val onlyInFirst = """
            {
              "hash": "hash1",
              "data": [
                "data 11",
                "data 12"
              ]
            }
        """.trimIndent()

        val onlyInSecond = """
            {
              "hash": "hash2",
              "data": [
                "data 21",
                "data 22"
              ]
            }
        """.trimIndent()
        val inBoth = """
            {
              "hash": "hash3",
              "data": [
                "data 31",
                "data 32"
              ]
            }
        """.trimIndent()
        val sameHashAsInBoth = """
            {
              "hash": "hash3",
              "data": [
                "data 111",
                "data 32"
              ]
            }
        """.trimIndent()

        val sameDataAsInBoth = """
            {
              "hash": "hash4",
              "data": [
                "data 31",
                "data 32"
              ]
            }
        """.trimIndent()

        val jsonOnlyInFirst = Gson().fromJson(onlyInFirst, JsonObject::class.java)
        val jsonOnlyInSecond = Gson().fromJson(onlyInSecond, JsonObject::class.java)
        val jsonInBoth = Gson().fromJson(inBoth, JsonObject::class.java)
        val jsonSameHashAsInBoth = Gson().fromJson(sameHashAsInBoth, JsonObject::class.java)
        val jsonSameDataAsInBoth = Gson().fromJson(sameDataAsInBoth, JsonObject::class.java)

        val first = setOf(jsonOnlyInFirst, jsonInBoth)
        val second = setOf(jsonOnlyInSecond, jsonInBoth, jsonSameHashAsInBoth, jsonSameDataAsInBoth)

        val result = getResult(first, second)

        val expectedOnlyInFirst = setOf(jsonOnlyInFirst)
        val expectedOnlyInSecond = setOf(jsonOnlyInSecond, jsonSameHashAsInBoth, jsonSameDataAsInBoth)
        val expectedInBoth = setOf(jsonInBoth)

        assertEquals(expectedOnlyInFirst, result.problemsOnlyInFirst)
        assertEquals(expectedOnlyInSecond, result.problemsOnlyInSecond)
        assertEquals(expectedInBoth, result.problemsInBoth)
    }
}