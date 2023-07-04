@file:Suppress("DEPRECATION")

package com.example.chattest.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chattest.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    companion object {
        val TAG = "RegisterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btRegister.setOnClickListener{
            performRegister()
        }

        binding.tAccount.setOnClickListener{
            Log.d(TAG, "Tente mostrar a atividade de login")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btphoto.setOnClickListener{
            Log.d(TAG, "Tente mostrar a foto selecionada")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // prossiga e verifique qual foi a imagem selecionada....
            Log.d(TAG, "A foto foi selecionada")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            binding.imageview.setImageBitmap(bitmap)
            //val bitmapDrawable = BitmapDrawable(bitmap)
            binding.btphoto.alpha = 0f
            //binding.btphoto.setBackgroundDrawable(bitmapDrawable)
        }
    }


    /**
     * Method for registering new users
     * Método para cadastrar novos usuários
     */
    fun performRegister() {
        //val tUserName = findViewById<TextView>(id.txtUserName)
        //val userName = tUserName.text.toString()
        val userName = binding.txtUserName.text.toString()
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Por favor, digite o e-mail/pw", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "E-mail is: " + email)
        Log.d(TAG, "Password:  $password")
        Log.d(TAG, "UserName: " + userName)

        //Firebase Authentication para criar um usuário com e-mail e senha
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("Login", "Usuário criado com sucesso com uid: ${it.result!!.user!!.uid}")

                uploadImageToFirebaseStorage()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener{
                Log.d("Main", "Falha ao criar usuário: ${it.message}")
                Toast.makeText(this, "Falha ao criar usuário: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebaseStorage() {
        if(selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")


        //if (selectedPhotoUri == null){
            //Log.d(TAG, "Changing to the default image")

           // val profileIcon = getBitmap(R.drawable.account_circle)

            //val baos = ByteArrayOutputStream()
            //profileIcon.compress(Bitmap.CompressFormat.PNG, 100, baos)
            /*val data = baos.toByteArray()

            ref.putBytes(data)
                .addOnSuccessListener {
                    Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                    ref.downloadUrl.addOnSuccessListener {
                        Log.d(TAG, "File Location: $it")

                        saveUserToFirebaseDatabase(it.toString())
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failed to upload image to storage: ${it.message}")
                }*/
       // } else {

            ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    Log.d(TAG, "Imagem enviada com sucesso: ${it.metadata?.path}")

                    ref.downloadUrl.addOnSuccessListener {
                        Log.d(TAG, "Localização de arquivo: $it")

                        saveUserToFirebaseDatabase(it.toString())
                   }
                }
                .addOnFailureListener {
                 //   Log.d(TAG, "Falha ao carregar a imagem para o armazenamento: ${it.message}")
                }
        //}
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, binding.txtUserName.text.toString(), profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "Por fim, salvamos o usuário no Firebase Database")


            }
            .addOnFailureListener {
                Log.d(TAG, "Falha ao definir valor para o banco de dados: ${it.message}")
            }
    }

    class User(val uid: String, val username: String, val profileImageUrl: String)
}