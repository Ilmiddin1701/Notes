package uz.ilmiddin1701.note

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import uz.ilmiddin1701.note.databinding.ActivityMainBinding
import uz.ilmiddin1701.note.utils.MyData

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.apply {
            navHostFragment = supportFragmentManager.findFragmentById(R.id.my_navigation_host) as NavHostFragment
            val navController = navHostFragment.navController
            bottomNavigationView.setupWithNavController(navController)

            val bottomNavExitAnim = AnimationUtils.loadAnimation(this@MainActivity, R.anim.bottom_nav_exit)
            val bottomNavEnterAnim = AnimationUtils.loadAnimation(this@MainActivity, R.anim.bottom_nav_enter)

            MyData.noteClick.observe(this@MainActivity) {
                if (it) {
                    bottomLinear.startAnimation(bottomNavExitAnim)
                    bottomNavExitAnim.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {}
                        override fun onAnimationEnd(p0: Animation?) {
                            bottomLinear.visibility = View.GONE
                        }
                        override fun onAnimationRepeat(p0: Animation?) {}
                    })
                } else {
                    bottomLinear.startAnimation(bottomNavEnterAnim)
                    bottomNavEnterAnim.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {}
                        override fun onAnimationEnd(p0: Animation?) {
                            bottomLinear.visibility = View.VISIBLE
                        }
                        override fun onAnimationRepeat(p0: Animation?) {}
                    })
                }
            }
        }
    }
}