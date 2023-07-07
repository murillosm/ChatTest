package com.example.chattest.network

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.chattest.R
import com.example.chattest.databinding.ProfileInfoBinding
import com.example.chattest.model.User
import com.example.chattest.view.registerlogin.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ProfileInfo : AppCompatActivity() {

    private lateinit var binding: ProfileInfoBinding

    companion object {
        var currentUser: User? = null
        val TAG = "UserProfile"
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ProfileInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.profile_info)

        val uid = FirebaseAuth.getInstance().uid

        var username : String? = null
        val email = FirebaseAuth.getInstance().currentUser?.email

        val profilePicImgView = findViewById<CircleImageView>(R.id.profile_pic_view)

        FirebaseDatabase.getInstance().getReference("/users/$uid/profileImageUrl").get().addOnCompleteListener {
            if(!it.isSuccessful)
                return@addOnCompleteListener

            val temp = it.result?.value as String?
            FirebaseStorage.getInstance().getReferenceFromUrl(temp!!).downloadUrl
                .addOnSuccessListener { it ->
                    var pic = it

                    Picasso.get().load(pic).into(profilePicImgView)

                    FirebaseDatabase.getInstance().getReference("users/$uid/username").get().addOnCompleteListener{ it ->
                        username = it.result?.value as String

                        val usernameText = findViewById<TextView>(R.id.username_view)
                        val emailText = findViewById<TextView>(R.id.email_view)

                        usernameText.text = "Username: " + username
                        emailText.text = "Email: " + email

                        binding.profilePic.setOnClickListener {
                            Log.d(RegisterActivity.TAG, "Try to show photo selector")

                            val intent = Intent(Intent.ACTION_PICK)
                            intent.type = "image/*"
                            startActivityForResult(intent, 0)
                        }
                    }
                }
                .addOnFailureListener{

                }

        }
    }
}