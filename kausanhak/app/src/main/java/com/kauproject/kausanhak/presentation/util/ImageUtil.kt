package com.kauproject.kausanhak.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream

object FileUtil {
    // 임시 파일 생성
    fun createTempFile(context: Context, fileName: String): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }

    // 압축 함수 추가
    fun compressAndSave(context: Context, uri: Uri, file: File) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val outputStream = FileOutputStream(file)

        // 압축할 품질과 함께 Bitmap을 압축하여 저장
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)

        outputStream.flush()
        outputStream.close()
    }
}

object UriUtil {
    // URI -> File
    fun toFile(context: Context, uri: Uri): File {
        val fileName = getFileName(context, uri)

        val file = FileUtil.createTempFile(context, fileName)
        FileUtil.compressAndSave(context, uri, file)
        return File(file.absolutePath)
    }

    // get file name & extension
    private fun getFileName(context: Context, uri: Uri): String {
        val name = uri.toString().split("/").last()
        val ext = context.contentResolver.getType(uri)!!.split("/").last()

        return "$name.$ext"
    }
}