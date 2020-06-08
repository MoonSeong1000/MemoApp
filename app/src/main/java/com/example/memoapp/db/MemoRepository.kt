package com.example.memoapp.db

import com.example.memoapp.db.entity.Memo
import com.example.memoapp.db.source.local.MemoDataSource

class MemoRepository(
    private val memoLocalDataSource: MemoDataSource
)
