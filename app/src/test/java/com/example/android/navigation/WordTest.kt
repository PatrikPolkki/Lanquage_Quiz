package com.example.android.navigation

import org.junit.Test

import org.junit.Assert.*

class WordTest {

    @Test
    fun getAnswers() {
        val v = Word("cat", "image.jpg", "english", setOf("kissa", "koira", "lehmä", "lisko"))
        assertEquals(setOf("kissa", "koira", "lehmä", "lisko"), v.answers)
    }

    @Test
    fun addTranslation() {
        val v = Word("cat", "image.jpg", "english", setOf())
        v.addTranslation("kissa")
        assertEquals(setOf("kissa"), v.translations)
    }

    @Test
    fun addTranslations() {
        val v = Word("cat", "image.jpg", "english", setOf())
        v.addTranslations(setOf("kissa", "koira", "lehmä", "lisko"))
        assertEquals(setOf("kissa", "koira", "lehmä", "lisko"), v.translations)
    }

    @Test
    fun isTranslation() {
        val v = Word("cat", "image.jpg", "english", setOf("kissa", "koira", "lehmä", "lisko"))
        v.addTranslations(v.answers)
        assertEquals(true, v.isTranslation("kissa"))
    }

    @Test
    fun translationCount() {
        val v = Word("cat", "image.jpg", "english", setOf("kissa", "koira", "lehmä", "lisko"))
        v.addTranslations(v.answers)
        assertEquals(4, v.translationCount())
    }

    @Test
    fun editDistance() {
        val v = Word("cat", "image.jpg", "english", setOf("kissa", "koira", "lehmä", "lisko"))
        v.addTranslations(v.answers)
        assertEquals(0, v.editDistance("kissa"))
    }

    @Test
    fun getText() {
        val v = Word("cat", "image.jpg", "english", setOf("kissa", "koira", "lehmä", "lisko"))
        v.addTranslations(v.answers)
        assertEquals("cat", v.text)
    }

    @Test
    fun getImage() {
        val v = Word("cat", "image.jpg", "english", setOf("kissa", "koira", "lehmä", "lisko"))
        v.addTranslations(v.answers)
        assertEquals("image.jpg", v.image)
    }

    @Test
    fun getLang() {
        val v = Word("cat", "image.jpg", "english", setOf("kissa", "koira", "lehmä", "lisko"))
        v.addTranslations(v.answers)
        assertEquals("english", v.lang)
    }
}