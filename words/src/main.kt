import javafx.application.Application.*
import java.io.File
import java.io.InputStream
import kotlinx.coroutines.*

fun main() {
    val inputStream: InputStream = File("/Users/andrei/IdeaProjects/words/src/words.txt").inputStream()
    val lineSet = mutableSetOf<String>()
    var sum = 0
    var n = 0

    inputStream.bufferedReader().useLines { lines -> lines.forEach {
            lineSet.add(it)
            sum += it.length
            n += 1
        }
    }
    val mean = sum / n
    var randomWord = lineSet.filter { it.length > mean }.random()
    var inputWord = ""
    val stopWord = "0"
    println("Main word is ${randomWord}. Enter your own words or enter '0' without quotes to stop entering")
    val userWords = mutableSetOf<String>()
    while (inputWord != stopWord) {
        inputWord = (readLine()!!).toLowerCase().trim()
        if (inputWord != stopWord) {
            userWords.add(inputWord)
        }
    }
    println("writing to file...")

    runBlocking {
        val fileJob = CoroutineScope(Dispatchers.IO).launch {
            val path = "/Users/andrei/IdeaProjects/words/src/newWords.txt"
            File(path).printWriter().use { out ->
                userWords.forEach { word ->
                    out.write(word + "\n")
                }
            }
        }
        val scoreJob = CoroutineScope(Dispatchers.Default).launch {
            var score = 0
            var alphabet = randomWord.toSet()
            userWords.forEach {
                if (!alphabet.containsAll(it.toSet())) {
                    println("You have extra letters in word '${it}'")
                    return@forEach
                }
                if (!lineSet.contains(it)) {
                    println("There is no word '${it}' in our dictionary")
                    return@forEach
                }
                score += it.length
            }
            println("You have scored $score points")
        }

        fileJob.join()
        scoreJob.join()
    }


}
