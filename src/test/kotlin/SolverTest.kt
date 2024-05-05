import org.example.Solver
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SolverTest {
    @Test
    fun testSimpleSequence() {
        val solver = Solver(listOf("jane", "emily", "kate"))
        val result = solver.solve()
        assertTrue(result.indexOf('j') < result.indexOf('e'))
        assertTrue(result.indexOf('e') < result.indexOf('k'))
    }

    @Test
    fun testImpossible() {
        val solver = Solver(listOf("bbbb", "aaaa", "ccca", "cccb"))
        assertTrue(solver.solve().isEmpty())
    }


    companion object {
        private const val NUMBER_OF_TESTS = 10000

        private fun randomString(random: Random, length: Int): String {
            val sb = StringBuilder()
            for (i in 1..length) {
                sb.append(random.nextInt(0, 26).toChar().plus('a'.code))
            }
            return sb.toString()
        }

        private fun applyPermutation(permutation: List<Char>, string: String): String {
            val sb = StringBuilder()
            for (ch in string) {
                sb.append(permutation.indexOf(ch).toChar().plus('a'.code))
            }
            return sb.toString()
        }

        private fun generateRandomTest(random: Random): List<String> {
            val n = random.nextInt(1, 100)
            val lines = mutableListOf<String>()
            for (i in 1..n) {
                val len = random.nextInt(1, 100)
                lines.add(randomString(random, len))
            }
            return lines
        }
    }

    @Test
    fun testStress() {
        val random = Random(123)
        for (testNumber in 1..NUMBER_OF_TESTS) {
            val lines = generateRandomTest(random)
            val result = Solver(lines).solve()

            if (result.isNotEmpty()) {
                assertTrue(result.size == 26)
                for (ch in 'a'..'z') {
                    assertTrue(result.indexOf(ch) >= 0)
                }

                assertEquals(lines, lines.sortedBy { applyPermutation(result, it) })
            }
        }
    }

    @Test
    fun testStressNotImpossible() {
        val random = Random(321)

        for (testNumber in 1..NUMBER_OF_TESTS) {
            val alphabet = ('a'..'z').shuffled()
            val lines = generateRandomTest(random).sortedBy { applyPermutation(alphabet, it) }
            val result = Solver(lines).solve()

            assertTrue(result.isNotEmpty())
        }
    }
}
