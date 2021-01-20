//Patrik Pölkki
//1901921
package com.example.android.navigation


data class Word(
        val text: String,
        val image: String,
        val lang: String,
        val answers: Set<String>) {
    private val _translations = mutableSetOf<String>()
    val translations: Set<String>
        get() = _translations

    fun addTranslation(t: String) {
        _translations.add(t)
    }

    fun addTranslations(ts: Set<String>) {
        _translations.addAll(ts)
    }

    fun isTranslation(word: String): Boolean {
        return _translations.any { it == word }
    }

    fun translationCount(): Int {
        return _translations.count()
    }


    // Levenshtein distance with Wagner-Fischer algorithm
    // See https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm
    fun editDistance(another: String): Int {
        val m = answers.elementAt(0).length
        val n = another.length

        val d: Array<IntArray> = Array(m + 1) { IntArray(n + 1) { 0 } } // set all (m+1) * (n+1) elements to zero

        for (i in 0 until m + 1) {
            d[i][0] = i
        }

        for (j in 0 until n + 1) {
            d[0][j] = j
        }

        for (j in 0 until n) {
            for (i in 0 until m) {
                val cost = if (answers.elementAt(0)[i] == another[j]) 0 else 1
                d[i + 1][j + 1] = minOf(d[i][j + 1] + 1, d[i + 1][j] + 1, d[i][j] + cost)
            }
        }

        return d[m][n]
    }
}

