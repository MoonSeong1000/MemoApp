package com.example.memoapp.db.source.local

import com.example.memoapp.db.entity.Category
import com.example.memoapp.db.entity.Image
import com.example.memoapp.db.entity.Memo

interface MemoDataSource {

    fun getMemos(callback: (memo: List<Memo>) -> Unit)

    fun getMemo(memoId: String, callback: (memo: Memo?) -> Unit)

    fun getImagesOfMemo(memoId:String, callback:(images:List<Image>)->Unit)

    fun saveMemo(memo: Memo)

    fun modifyMemo(memo: Memo, deleteImages:List<Image>)

    fun deleteMemoById(memoId: String)

}