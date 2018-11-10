package com.matheusvillela.bolaohiperweb.bolaohiperweb

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class BolaohiperwebApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Test
    fun unitTest() {
        val lol = { a: String -> a.toInt() }
        val res1 = test(lol)
        val lal = { a: String -> println(a) }
        val res2 = test(lal)

    }

    private fun <T> test(f: (String) -> T): T {
        println(f)
        return f("555")
    }

    private fun dd() {
        val a = listOf(Pair(1, 2), Pair(3, 4), Pair(5, 6))
        for ((index, pair) in a.withIndex()) {
            println("$index, ${pair.first}, ${pair.second}")
        }

    }

}
