package com.example.memoapp.db.entity

import androidx.annotation.VisibleForTesting
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Memo(

    @PrimaryKey
    @ColumnInfo(name = "memo_id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "memo_title")
    var title: String = "",

    @ColumnInfo(name = "memo_created_date")
    val createdDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "memo_category_id")
    var categoryId: String = "",

    @ColumnInfo(name = "memo_content")
    var content: String = ""

) {
    @ColumnInfo(name = "memo_is_important")
    var isImportant: Boolean = false

    @ColumnInfo(name = "memo_thumbnail")
    var thumbnailByteCode: ByteArray? = null

    @ColumnInfo(name = "memo_image_count")
    var imageCount: Int = 0

    @Ignore
    private val _images: MutableList<Image> = ArrayList()

    fun getImages():List<Image> {
        return _images
    }

    fun loadImages(images: List<Image>) {
        this._images.addAll(images)
    }

    fun addImage(image: Image) {
        if (_images.isEmpty()) thumbnailByteCode = image.byteCode.copyOf()
        this._images.add(image)
        imageCount++
    }

    fun addImages(images: List<Image>) {
        if (this._images.isEmpty()) thumbnailByteCode = images[0].byteCode.copyOf()
        this._images.addAll(images)
        imageCount += images.size
    }

    fun removeImageAt(index: Int): Image {
        val removedImage = _images.removeAt(index)
        thumbnailByteCode = if (_images.size < 1) null else _images[0].byteCode.copyOf()
        imageCount--
        return removedImage
    }

    @VisibleForTesting
    fun clearOnlyImageMutableList() {
        _images.clear()
    }


}