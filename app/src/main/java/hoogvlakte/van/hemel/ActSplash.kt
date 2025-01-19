package hoogvlakte.van.hemel

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import pl.droidsonroids.gif.AnimationListener
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

/* -------------------------------------------------------------------------------------------------
 * HEY CODE, WHAT ARE YOU DOING?
 *
 * I pick the right GIF file and set the right Constraint Layout background color according to the
 * Android's dark/white theme. Once the GIF file is done playing, I launch another activity and
 * finish myself for I am but no longer needed.
 * ---------------------------------------------------------------------------------------------- */
class ActSplash : AppCompatActivity(), AnimationListener {

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_splash)

        val gifImage = findViewById<GifImageView>(R.id.image_splash)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint_layout)

        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            true -> {
                val gifDrawable = GifDrawable(resources, R.drawable.punyaku_dark)
                val constraintBackgroundColor = ContextCompat.getColor(this@ActSplash, R.color.black)

                gifImage.setImageDrawable(gifDrawable)
                gifDrawable.addAnimationListener(this)

                constraintLayout.setBackgroundColor(constraintBackgroundColor)
            }
            else -> {
                val gifDrawable = GifDrawable(resources, R.drawable.punyaku_light)
                val constraintBackgroundColor = ContextCompat.getColor(this@ActSplash, R.color.white)

                gifImage.setImageDrawable(gifDrawable)
                gifDrawable.addAnimationListener(this)

                constraintLayout.setBackgroundColor(constraintBackgroundColor)
            }
        }
    }

    override fun onAnimationCompleted(loopNumber: Int) {
        val intent = Intent(this@ActSplash, ActMain::class.java)
        startActivity(intent)
        finish()
    }
}

