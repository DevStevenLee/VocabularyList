package com.example.vocabularylist.add_new_vocabulary

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.vocabularylist.R
import com.example.vocabularylist.database.BookmarkDatabaseHandler
import com.example.vocabularylist.vocas.VocaModel
import com.example.vocabularylist.vocas.Vocas
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import java.util.jar.Manifest

class AddNewVocabulary : AppCompatActivity(), View.OnClickListener {
    private lateinit var ivBackInAddNewVoca : ImageView
    private lateinit var etMyNewVoca : EditText
    private lateinit var ivMyNewVoca : ImageView
    private lateinit var tvToPermitCameraForNewVoca : TextView
    private lateinit var tvToPermitGalleryForNewVoca : TextView
    private lateinit var tvSaveNewVoca : TextView

    private var newVocaUri = ""

    companion object {
        const val IMAGE_DIRECTORY = "myVocas"

        const val CAMERA_REQUEST_CODE = 1
        const val GALLERY_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_vocabulary)

        ivBackInAddNewVoca = findViewById(R.id.ivBackInAddNewVoca)
        etMyNewVoca = findViewById(R.id.etMyNewVoca)
        ivMyNewVoca = findViewById(R.id.ivMyNewVoca)
        tvToPermitCameraForNewVoca = findViewById(R.id.tvToPermitCameraForNewVoca)
        tvToPermitGalleryForNewVoca = findViewById(R.id.tvToPermitGalleryForNewVoca)
        tvSaveNewVoca = findViewById(R.id.tvSaveNewVoca)

        ivBackInAddNewVoca.setOnClickListener {
            onBackPressed()
        }

        tvToPermitCameraForNewVoca.setOnClickListener(this)
        tvToPermitGalleryForNewVoca.setOnClickListener(this)
        tvSaveNewVoca.setOnClickListener(this)
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap) : Uri{
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try{
            val stream : OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException){
            e.printStackTrace()
        }

        return Uri.parse(file.absolutePath)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_REQUEST_CODE){
                if(data != null){
                    val picOfNewVoca : Bitmap = data.extras!!.get("data") as Bitmap
                    newVocaUri = saveImageToInternalStorage(picOfNewVoca).toString()
                    ivMyNewVoca.setImageBitmap(picOfNewVoca)
                }
            } else if(requestCode == GALLERY_REQUEST_CODE){
                if(data != null){
                    val contentURI = data.data
                    try {
                        val selectImageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                        newVocaUri = saveImageToInternalStorage(selectImageBitmap).toString()
                        ivMyNewVoca.setImageBitmap(selectImageBitmap)
                    } catch (e : IOException){
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun showRationalDialogForPermissions(){
        AlertDialog.Builder(this).setMessage("권한을 다시 요구합니다.")
                .setPositiveButton("GO TO SETTINGS"){
                    _, _ ->
                    try{
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)

                        val uri = Uri.fromParts("package", packageName, null)

                        intent.data = uri
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException){
                        e.printStackTrace()
                    }
                }
                .setNegativeButton("Cancel"){  dialog, _ ->
                    dialog.dismiss()
                }.show()
    }

    private fun takePhotoFromCamera(){
        Dexter.withContext(this).withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        ).withListener(object: MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if(report!!.areAllPermissionsGranted()){
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
            ) {
                showRationalDialogForPermissions()
            }

        }).onSameThread().check()
    }

    private fun choosePhotoFromGallery(){
        Dexter.withContext(this).withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object: MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if(report!!.areAllPermissionsGranted()){
                    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
            ) {
                showRationalDialogForPermissions()
            }

        }).onSameThread().check()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvToPermitCameraForNewVoca -> {
                takePhotoFromCamera()
            } R.id.tvToPermitGalleryForNewVoca -> {
                choosePhotoFromGallery()
            } R.id.tvSaveNewVoca -> {
                if(etMyNewVoca.text.toString().isNotEmpty() && newVocaUri.isNotEmpty()){
                    val newVoca = VocaModel(0, etMyNewVoca.text.toString(), 0, newVocaUri, false)
                    val vocas = Vocas()

                    vocas.customDatabaseHandler.addVoca(newVoca)
                    Toast.makeText(this, "단어가 새롭게 추가되었습니다 !", Toast.LENGTH_LONG).show()

                    etMyNewVoca.setText("")
                    ivMyNewVoca.setImageBitmap(null)
                } else{
                    Toast.makeText(this, "단어명과 사진 모두를 선택해주세요 !", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}