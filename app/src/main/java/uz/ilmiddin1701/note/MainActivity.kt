package uz.ilmiddin1701.note

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import uz.ilmiddin1701.note.databinding.ActivityMainBinding
import uz.ilmiddin1701.note.utils.MySharedPreferences

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        MySharedPreferences.init(this)
        if (MySharedPreferences.statusBarColor != "empty") {
            window.statusBarColor = Color.parseColor(MySharedPreferences.statusBarColor)
        } else {
            window.statusBarColor = Color.parseColor("#00558A")
        }
        binding.apply {
            ViewCompat.setOnApplyWindowInsetsListener(bottomLinear) { view, insets ->
                val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.updatePadding(bottom = systemBarInsets.bottom)
                insets
            }
            //Background Color
            if (MySharedPreferences.backgroundColor != "empty") {
                binding.root.setBackgroundColor(Color.parseColor(MySharedPreferences.backgroundColor))
            } else binding.root.setBackgroundColor(Color.parseColor("#F0F8FF"))

            navHostFragment = supportFragmentManager.findFragmentById(R.id.my_navigation_host) as NavHostFragment
            val navController = navHostFragment.navController
            bottomNavigationView.setupWithNavController(navController)

            when (MySharedPreferences.bottomNavBarColor) {
                0 -> bottomLinear.setBackgroundResource(R.drawable.bottom_style)
                1 -> bottomLinear.setBackgroundResource(R.drawable.bottom_style)
                2 -> bottomLinear.setBackgroundResource(R.drawable.bottom_style_2)
                3 -> bottomLinear.setBackgroundResource(R.drawable.bottom_style_3)
                4 -> bottomLinear.setBackgroundResource(R.drawable.bottom_style_4)
                5 -> bottomLinear.setBackgroundResource(R.drawable.bottom_style_5)
                6 -> bottomLinear.setBackgroundResource(R.drawable.bottom_style_6)
                7 -> bottomLinear.setBackgroundResource(R.drawable.bottom_style_7)
                8 -> bottomLinear.setBackgroundResource(R.drawable.bottom_style_8)
                9 -> bottomLinear.setBackgroundResource(R.drawable.bottom_style_9)
                10 -> bottomLinear.setBackgroundResource(R.drawable.bottom_style_10)
            }
        }
    }
}