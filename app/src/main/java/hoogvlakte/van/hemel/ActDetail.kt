package hoogvlakte.van.hemel

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hoogvlakte.van.hemel.databinding.ActDetailBinding

class ActDetail : AppCompatActivity {
    private lateinit var actDetailBinding: ActDetailBinding

    constructor() : super() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this@ActDetail.actDetailBinding = ActDetailBinding.inflate(this@ActDetail.layoutInflater)

        this@ActDetail.enableEdgeToEdge()
        this@ActDetail.setContentView(this@ActDetail.actDetailBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(this@ActDetail.actDetailBinding.root) {
             view, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
        }

        val dataReason = if (Build.VERSION.SDK_INT >= 33) {
            this@ActDetail.intent.getParcelableExtra("dataReasonPosition", DataReason::class.java)
        } else {
            @Suppress("DEPRECATION")
            this@ActDetail.intent.getParcelableExtra("dataReasonPosition")
        }

        val title = this@ActDetail.actDetailBinding.actDetailContentTitle
        val description = this@ActDetail.actDetailBinding.actDetailContentDescription
        val image = this@ActDetail.actDetailBinding.actDetailImage

        title.text = dataReason?.title
        description.text = dataReason?.description
        if (dataReason?.image != null) {
            image.setImageResource(dataReason.image)
        }
        else {
            image.setImageResource(R.drawable.punyaku_1)
        }

        actDetailBinding.actDetailShare.setOnClickListener({
            val intent = Intent()
            intent.setAction(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at: https://play.google.com/store/apps/details?id=")
            intent.setType("text/plain")
            this@ActDetail.startActivity(intent)
        })
    }
}