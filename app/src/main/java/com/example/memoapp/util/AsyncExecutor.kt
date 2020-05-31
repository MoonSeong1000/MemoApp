package com.example.memoapp.util

import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.logging.Handler

class AsyncExecutor {

    val ioThread: Executor = IoThreadExecutor()
    val mainThread: Executor = MainThreadExecutor()

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = android.os.Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    private class IoThreadExecutor : Executor {
        private val ioThreadExecutor = Executors.newSingleThreadExecutor()

        override fun execute(command: Runnable) {
            ioThreadExecutor.execute(command)
        }
    }

}