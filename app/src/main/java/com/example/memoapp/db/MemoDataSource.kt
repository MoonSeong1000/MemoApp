package com.example.memoapp.db

import com.example.memoapp.db.entity.Category
import com.example.memoapp.db.entity.Memo

interface MemoDataSource {

    fun getMemos(callback: (memo: List<Memo>) -> Unit)

    fun getMemo(memoId: String, callback: (memo: Memo) -> Unit)

    fun saveMemo(memo: Memo)

    fun modifyMemo(memo: Memo)

    fun deleteMemo(memoId: String)

}