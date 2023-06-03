package android.example.com.project_baru_18411085

import android.content.Intent
import android.example.com.project_baru_18411085.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {

    // View binding adalah fitur yang memudahkan Anda menulis kode yang berinteraksi dengan tampilan.
    // Setelah diaktifkan dalam sebuah modul, view binding akan menghasilkan class binding untuk setiap file tata letak XML yang ada dalam modul tersebut.
    // Instance class binding berisi referensi langsung ke semua tampilan yang memiliki ID di tata letak yang terkait.

    private lateinit var animalRecyclerview: RecyclerView
    private lateinit var animalList: MutableList<Image>
    private lateinit var animalAdapter: ImageAdapter
    private lateinit var binding: ActivityMainBinding

    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null

    private lateinit var auth: FirebaseAuth
    private var gridLayoutManager : GridLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        animalRecyclerview = findViewById(R.id.imageRecyclerView)
        gridLayoutManager = GridLayoutManager(applicationContext, 3,
            LinearLayoutManager.VERTICAL, false)
        animalList = ArrayList()
        animalAdapter = ImageAdapter(this@MainActivity,animalList)
        animalRecyclerview.layoutManager = gridLayoutManager
        animalRecyclerview.setHasFixedSize(true)
        animalRecyclerview.adapter = animalAdapter

        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("animal")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                animalList.clear()
                if (snapshot.exists()) {
                    for (teacherSnapshot in snapshot.children) {
                        val upload = teacherSnapshot.getValue(Image::class.java)
                        upload!!.key = teacherSnapshot.key
                        animalList.add(upload)
                    }
                    animalAdapter.notifyDataSetChanged()
                }
            }
        })

        binding.btnlogout.setOnClickListener {
            auth.signOut()
            Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

    }
}