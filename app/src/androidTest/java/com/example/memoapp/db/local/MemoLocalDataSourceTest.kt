package com.example.memoapp.db.local

import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.transition.Transition
import androidx.annotation.DrawableRes
import androidx.test.platform.app.InstrumentationRegistry
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.memoapp.R
import com.example.memoapp.db.entity.Image
import com.example.memoapp.db.entity.Memo
import com.example.memoapp.db.source.local.MemoLocalDataSource
import com.example.memoapp.util.toByteArray
import junit.framework.Assert.assertEquals
import kotlinx.android.synthetic.main.fragment_first.view.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.concurrent.CountDownLatch

class MemoLocalDataSourceTest : KoinTest {
    private val localDataSource : MemoLocalDataSource by inject()
    private var insertedMemoId = ""

    private lateinit var countDownLatch: CountDownLatch

    @Before
    fun prepareCountDownLatch() {
        countDownLatch = CountDownLatch(1)
    }

    @After
    fun deleteCreatedMemo() {
        localDataSource.deleteMemoById(insertedMemoId)
    }

    private fun createMemoOneImage(
        title: String = "test",
        content: String = "test content",
        categoryId: String = "",
        @DrawableRes imageResource: Int = R.drawable.ic_launcher_background,
        callback: (memo: Memo) -> Unit
    ) {
        createBitmap(imageResource = imageResource) {
            val newMemo = Memo(title = title, content = content, categoryId = categoryId)
            val image = Image(ofMemoId = newMemo.id, byteCode = it.toByteArray())
            newMemo.apply { addImage(image) }
            callback(newMemo)
        }
    }

    private fun createBitmap(
        @DrawableRes imageResource: Int = R.drawable.ic_launcher_background,
        callback: (bitmap: Bitmap) -> Unit
    ) {
        Glide.with(InstrumentationRegistry.getInstrumentation().context)
            .asBitmap()
            .load(imageResource)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback(resource)
                }
            })
    }

    @Test(timeout = 2000)
    fun saveMemoNoImage_retrievesMemo() {
        val newMemo = Memo(title = "test", content = "test content")
        var retrievedMemo:Memo? = null
        localDataSource.saveMemo(newMemo)
        insertedMemoId = newMemo.id

        localDataSource.getMemo(newMemo.id) {
            retrievedMemo = it
            countDownLatch.countDown()
        }
        countDownLatch.await()
        assertEquals(newMemo, retrievedMemo)
    }

    @Test(timeout = 2000)
    fun saveMemoOneImage_retrivedMemo() {
        var newMemo = Memo(title="test", content="test content")
        var retrievedMemo:Memo? = null
        createMemoOneImage {
            newMemo = it
            countDownLatch.countDown()
        }
        countDownLatch.await()
        prepareCountDownLatch()

        localDataSource.saveMemo(newMemo)
        insertedMemoId = newMemo.id

        localDataSource.getMemo(newMemo.id) {
            retrievedMemo = it
            countDownLatch.countDown()
        }

        countDownLatch.await()
        assertEquals(newMemo, retrievedMemo)
        assertEquals(newMemo.images, retrievedMemo?.images)
    }

}