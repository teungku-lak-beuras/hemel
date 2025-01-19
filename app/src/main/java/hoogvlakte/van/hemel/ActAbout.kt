package hoogvlakte.van.hemel

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hoogvlakte.van.hemel.databinding.ActAboutBinding

class ActAbout : AppCompatActivity {
    private lateinit var actAboutBinding: ActAboutBinding

    constructor() : super() {}

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actAboutBinding = ActAboutBinding.inflate(this@ActAbout.layoutInflater)

        enableEdgeToEdge()
        this@ActAbout.setContentView(actAboutBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(actAboutBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}