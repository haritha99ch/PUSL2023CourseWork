package com.example.login

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.inputmethod.CorrectionInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.database.getStringOrNull
import com.bumptech.glide.Glide
import com.example.login.Data.Entities.Image
import com.example.login.Data.Entities.Post
import com.example.login.Data.GGDbContext
import com.example.login.Session.LoginPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class AddNewPost : AppCompatActivity() {

    lateinit var session: LoginPref

    private lateinit var uploadbtn: Button
    private lateinit var postbtn: Button
    private lateinit var imageView: ImageView
    private lateinit var imagefile:String
    private lateinit var txtGameName:EditText
    private lateinit var txtHeading:EditText
    private val contract=registerForActivityResult(ActivityResultContracts.GetContent()){
        imageView.setImageURI(it)
        Log.i("SelectedFile", "${it.path}")

    }

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_post)
        uploadbtn = findViewById(R.id.filepickerbtn)
        imageView = findViewById(R.id.post)
        postbtn=findViewById<Button?>(R.id.post_btn)
        txtGameName=findViewById(R.id.postGame)
        txtHeading=findViewById(R.id.heading)
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        postbtn.setOnClickListener{
            session= LoginPref(this)
            session.checkLogin()
            var currentUser=session.getUserDetails()
            var currentUserName=currentUser.get(LoginPref.KEY_USERNAME)
            if (txtGameName.text.isNullOrEmpty() || txtHeading.text.isNullOrEmpty()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }else{
                val newPost=Post(
                    0,
                    currentUserName!!,
                    sdf.format(Date()).toString(),
                    txtGameName.text.toString(),
                    txtHeading.text.toString(),
                )

                CoroutineScope(Dispatchers.IO).launch {
                    val Dao=GGDbContext.getInstance(applicationContext).gGdbDao
                    val postId=Dao.NewPost(newPost)
                    val newImage=Image(
                        0,
                        postId.toInt(),
                        imagefile
                    )
                    Dao.NewImage(newImage)
                    Log.i("SelectedFile", "${postId}")
                    this.cancel()

                }
                Toast.makeText(this, "A New Post has Created", Toast.LENGTH_SHORT).show()
                var intent = Intent(applicationContext, gamefeed::class.java)
                startActivity(intent)
                finish()
            }
        }

        uploadbtn.setOnClickListener{
            pickImageGallery()
        }

    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            data?.data.let {returnUri->
                contentResolver.query(returnUri!!, null, null, null, null)
            }?.use {
                it.moveToFirst()
                val nameIndex=it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val res= it.getStringOrNull(nameIndex)
                Log.i("SelectedFile", "$res")
                imagefile= res.toString()
            }
            Glide.with(this)
                .asBitmap()
                .load("/storage/emulated/0/Download/$imagefile")
                .into(imageView)
        }
    }

}