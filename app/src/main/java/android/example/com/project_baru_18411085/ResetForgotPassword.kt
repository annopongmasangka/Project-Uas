package android.example.com.project_baru_18411085

import android.content.Intent
import android.example.com.project_baru_18411085.databinding.ActivityResetForgotPasswordBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResetForgotPassword : AppCompatActivity() {
    private lateinit var binding: ActivityResetForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_forgot_password)

        binding = ActivityResetForgotPasswordBinding.inflate(layoutInflater)

        binding.btnReset.setOnClickListener{
            val email : String = binding.edtEmail.text.toString().trim()
            if (email.isEmpty()){
                binding.edtEmail.error = "Input Email"
                binding.edtEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmail.error = "Invalid email"
                binding.edtEmail.requestFocus()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Cek email for reset password", Toast.LENGTH_SHORT).show()
                    Intent(this,LoginActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }
                else {
                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}