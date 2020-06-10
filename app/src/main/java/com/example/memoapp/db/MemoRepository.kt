package com.example.memoapp.db

import com.example.memoapp.db.entity.Image
import com.example.memoapp.db.entity.Memo
import com.example.memoapp.db.source.local.MemoDataSource

class MemoRepository(
    private val memoLocalDataSource: MemoDataSource
) : MemoDataSource {
    override fun getMemos(callback: (memo: List<Memo>) -> Unit) {
        memoLocalDataSource.getMemos(callback)
    }

    override fun getMemo(memoId: String, callback: (memo: Memo?) -> Unit) {
        memoLocalDataSource.getMemo(memoId, callback)
    }

    override fun getImagesOfMemo(memoId: String, callback: (images: List<Image>) -> Unit) {
        memoLocalDataSource.getImagesOfMemo(memoId, callback)
    }

    override fun saveMemo(memo: Memo) {
        memoLocalDataSource.saveMemo(memo)
    }

    override fun modifyMemo(memo: Memo, deletedImages: List<Image>) {
        memoLocalDataSource.modifyMemo(memo, deletedImages)
    }

    override fun deleteMemoById(memoId: String) {
        memoLocalDataSource.deleteMemoById(memoId)
    }
}
