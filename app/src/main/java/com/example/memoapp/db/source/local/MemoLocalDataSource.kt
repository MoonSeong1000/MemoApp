package com.example.memoapp.db.source.local

import com.example.memoapp.db.dao.MemoDao
import com.example.memoapp.db.entity.Image
import com.example.memoapp.db.entity.Memo
import com.example.memoapp.util.AsyncExecutor

class MemoLocalDataSource private constructor(
    private val asyncExecutor: AsyncExecutor,
    private val memoDao: MemoDao
) : MemoDataSource {
    override fun getMemos(callback: (memo: List<Memo>) -> Unit) {
        asyncExecutor.ioThread.execute{
            val memos = memoDao.getMemos()
            asyncExecutor.mainThread.execute{callback(memos)}
        }
    }

    override fun getMemo(memoId: String, callback: (memo: Memo?) -> Unit) {
        asyncExecutor.ioThread.execute{
            val imagesOfMemo = memoDao.getImagesByMemoId(memoId)
            val memo = memoDao.getMemoById(memoId)
            memo?.loadImages(imagesOfMemo)
            asyncExecutor.mainThread.execute{callback(memo)}
        }
    }

    override fun getImagesOfMemo(memoId: String, callback: (images: List<Image>) -> Unit) {
        asyncExecutor.ioThread.execute{
            val imageOfMemo = memoDao.getImagesByMemoId(memoId)
            asyncExecutor.mainThread.execute{callback(imageOfMemo)}
        }
    }


    override fun saveMemo(memo: Memo) {
        asyncExecutor.ioThread.execute { memoDao.saveMemo(memo) }
    }

    override fun modifyMemo(memo: Memo, deletedImages:List<Image>) {
        asyncExecutor.ioThread.execute { memoDao.modifyMemo(memo, deletedImages) }
    }

    override fun deleteMemoById(memoId: String) {
        asyncExecutor.ioThread.execute { memoDao.deleteMemoById(memoId) }
    }

    companion object {
        private var INSTANCE: MemoLocalDataSource? = null

        @JvmStatic
        @Synchronized
        fun getInstance(
            asyncExecutor: AsyncExecutor,
            memoDao: MemoDao
        ): MemoLocalDataSource = INSTANCE
            ?: MemoLocalDataSource(asyncExecutor, memoDao)
                .apply { INSTANCE = this }
    }
}