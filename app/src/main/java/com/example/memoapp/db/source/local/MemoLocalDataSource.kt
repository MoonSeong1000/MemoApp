package com.example.memoapp.db.source.local

import com.example.memoapp.db.dao.MemoDao
import com.example.memoapp.db.entity.Memo

class MemoLocalDataSource private constructor(private val memoDao: MemoDao) :
    MemoDataSource {
    override fun getMemos(callback: (memo: List<Memo>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getMemo(memoId: String, callback: (memo: Memo) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun saveMemo(memo: Memo) {
        TODO("Not yet implemented")
    }

    override fun modifyMemo(memo: Memo) {
        TODO("Not yet implemented")
    }

    override fun deleteMemo(memoId: String) {
        TODO("Not yet implemented")
    }

    companion object {
        private var INSTANCE: MemoLocalDataSource? = null

        @JvmStatic
        @Synchronized
        fun getInstance(memoDao: MemoDao): MemoLocalDataSource = INSTANCE
            ?: MemoLocalDataSource(memoDao)
                .apply { INSTANCE = this }
    }
}