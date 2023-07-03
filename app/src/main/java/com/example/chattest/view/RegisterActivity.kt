package com.example.chattest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chattest.R
import com.example.chattest.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


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
                Log.d("Login", "Logado com sucesso: ${it.result.user!!.uid}")
            }
            .addOnFailureListener{
                Log.d("Main", "Falha ao criar usuário: ${it.message}")
                Toast.makeText(this, "Falha ao criar usuário: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}