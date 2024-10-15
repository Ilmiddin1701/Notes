package uz.ilmiddin1701.note.fragments

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import uz.ilmiddin1701.note.R
import uz.ilmiddin1701.note.databinding.FragmentImageShowBinding
import java.io.File

class ImageShowFragment : Fragment() {
    private val binding by lazy { FragmentImageShowBinding.inflate(layoutInflater) }
    private lateinit var bnb: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val screenWidth = getScreenWidth(requireContext())
        val screenHeight = getScreenHeight(requireContext())

        // 'NoteData' obyektidan fayl pathini olish
        val image = arguments?.getString("keyImageResource")

        try {
            // absolute path orqali faylga murojaat qilish
            val filePath = image
            val file = File(filePath!!)

            if (file.exists()) {
                // Faylni optimallashtirish bilan yuklash
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeFile(file.absolutePath, options)

                options.inSampleSize = calculateInSampleSize(options, screenWidth, screenHeight)
                options.inJustDecodeBounds = false

                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)

                binding.image.setImageBitmap(bitmap)
            }
        } catch (e: Exception) {
            e.printStackTrace() // Xatoliklarni loglash
        }

        return binding.root
    }

    private fun getScreenWidth(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }

    private fun getScreenHeight(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.heightPixels
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    override fun onPause() {
        super.onPause()
        val bottomNavEnterAnim = AnimationUtils.loadAnimation(context, R.anim.bottom_nav_enter)
        bnb.visibility = View.VISIBLE
        bnb.startAnimation(bottomNavEnterAnim)
    }

    override fun onResume() {
        super.onResume()
        val bottomNavExitAnim = AnimationUtils.loadAnimation(context, R.anim.bottom_nav_exit)
        bnb = requireActivity().findViewById(R.id.bottomLinear)
        bnb.startAnimation(bottomNavExitAnim)
        bottomNavExitAnim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) { bnb.visibility = View.GONE }
            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }
}