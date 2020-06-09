package com.example.memoapp.db.local

import org.junit.Test

class test {



    fun sum(a:Int, b:Int):Int {
        return a+b
    }
    @Test
    fun test() {
        println(sum(3,2))
    }
}