package com.example.memoapp.db.local

import com.example.memoapp.db.AppDatabase
import com.example.memoapp.db.dao.MemoDao
import com.example.memoapp.db.entity.Category
import com.example.memoapp.db.entity.Image
import com.example.memoapp.db.entity.Memo
import com.example.memoapp.db.source.local.CategoryLocalDataSource
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.concurrent.CountDownLatch
class MemoDaoTest : KoinTest {
    private val database : AppDatabase by inject()
    private lateinit var memoDao: MemoDao

    @Before
    fun initMemoDao() {
        memoDao = database.memoDao()
    }

    @After
    fun deleteCreateMemo() {
        memoDao.deleteMemoById(DEFAULT_MEMO.id)
    }

    @Test
    fun insertMemo_getAllMemosNoImages() {
        memoDao.insertMemoNoImages(DEFAULT_MEMO)

        val retrievedMemos = memoDao.getMemosNoImages()

        assertEquals(1,retrievedMemos.size)
        assertEquals(DEFAULT_MEMO_NO_IMAGES, retrievedMemos[0])
    }

    @Test
    fun insertMemo_getMemoNoImagesById() {
        memoDao.insertMemoNoImages(DEFAULT_MEMO)
        val retrievedMemo = memoDao.getImagesByMemoId(DEFAULT_ID)

        assertEquals(DEFAULT_MEMO_NO_IMAGES,retrievedMemo)
    }

    @Test
    fun insertMemo_getImagesByMemoId() {
        memoDao.saveMemo(DEFAULT_MEMO)

        val rettievedMemo = memoDao.getImagesByMemoId(DEFAULT_ID)
        assertEquals(DEFAULT_IMAGES,rettievedMemo)
    }

    companion object {
        private const val DEFAULT_ID = "default id"
        private const val DEFAULT_TITLE = "default memo"
        private const val DEFAULT_CATEGORY_ID = "default category id"
        private const val DEFAULT_CONTENT = "default content"

        private val DEFAULT_IMAGE1 = Image(ofMemoId = DEFAULT_ID, byteCode = byteArrayOf(1,1,1))
        private val DEFAULT_IMAGE2 = Image(ofMemoId = DEFAULT_ID, byteCode = byteArrayOf(2, 2, 2))
        private val DEFAULT_IMAGES = listOf(DEFAULT_IMAGE1, DEFAULT_IMAGE2)

        private val DEFAULT_MEMO = Memo(
            id = DEFAULT_ID,
            title = DEFAULT_TITLE,
            categoryId = DEFAULT_CATEGORY_ID,
            content = DEFAULT_CONTENT
        ).apply { addImages(DEFAULT_IMAGES) }

        private val DEFAULT_MEMO_NO_IMAGES = DEFAULT_MEMO.copy().apply {
            addImages(DEFAULT_IMAGES)
            clearOnlyImageMutableList()
        }
    }
}