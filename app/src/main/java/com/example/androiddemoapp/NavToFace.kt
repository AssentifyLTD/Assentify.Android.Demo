package  com.example.androiddemoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class NavToFace : AppCompatActivity() {
    private lateinit var image: String;
    private lateinit var faceMatch: LinearLayout;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_to_face)
        image = intent.getStringExtra("image")!!
        faceMatch = findViewById(R.id.faceMatch)

        faceMatch.setOnClickListener {
            val intent = Intent(this, FaceMatchActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("image", image)
            startActivity(intent)
        }


    }
}