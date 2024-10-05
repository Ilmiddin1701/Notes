package uz.ilmiddin1701.note.fragments

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import uz.ilmiddin1701.note.R
import uz.ilmiddin1701.note.databinding.ColorPickerDialogBinding
import uz.ilmiddin1701.note.databinding.FragmentSettingsBinding
import uz.ilmiddin1701.note.utils.MySharedPreferences

class SettingsFragment : Fragment() {
    private val binding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }
    private var statusBarColorSaver = ""
    private var bottomNavBarColorSaver = MySharedPreferences.bottomNavBarColor
    private var backgroundColorSaver = ""
    private var actionBarColorSaver = ""
    private var v = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            MySharedPreferences.init(requireContext())

            val colorsGoneAnim = AnimationUtils.loadAnimation(context, R.anim.colors_gone_anim)
            val colorsVisibleAnim = AnimationUtils.loadAnimation(context, R.anim.colors_visible_anim)

            // Status Bar Color Change
            statusBarColorChange.setOnClickListener { openColorPickerDialog(1) }
            if (MySharedPreferences.statusBarColor != "empty") {
                statusBarColorView.setCardBackgroundColor(Color.parseColor(MySharedPreferences.statusBarColor))
            } else {
                statusBarColorView.setCardBackgroundColor(Color.parseColor("#00558A"))
            }

            // Bottom Navigation Bar Color Change
            bottomNavBarColorChange.setOnClickListener {
                if (v) {
                    closeColors()
                } else {
                    v = true
                    bottomNavBarColorChange.startAnimation(colorsGoneAnim)
                    colorsGoneAnim.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {}
                        override fun onAnimationEnd(p0: Animation?) {
                            colorsVisibility.visibility = View.VISIBLE
                            bottomNavBarColorChange.startAnimation(colorsVisibleAnim)
                        }
                        override fun onAnimationRepeat(p0: Animation?) {}
                    })
                }
            }
            style1.setOnClickListener {
                bottomNavBarColorSaver = 1
                bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style)
                closeColors()
            }
            style2.setOnClickListener {
                bottomNavBarColorSaver = 2
                bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_2)
                closeColors()
            }
            style3.setOnClickListener {
                bottomNavBarColorSaver = 3
                bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_3)
                closeColors()
            }
            style4.setOnClickListener {
                bottomNavBarColorSaver = 4
                bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_4)
                closeColors()
            }
            style5.setOnClickListener {
                bottomNavBarColorSaver = 5
                bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_5)
                closeColors()
            }
            style6.setOnClickListener {
                bottomNavBarColorSaver = 6
                bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_6)
                closeColors()
            }
            style7.setOnClickListener {
                bottomNavBarColorSaver = 7
                bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_7)
                closeColors()
            }
            style8.setOnClickListener {
                bottomNavBarColorSaver = 8
                bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_8)
                closeColors()
            }
            style9.setOnClickListener {
                bottomNavBarColorSaver = 9
                bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_9)
                closeColors()
            }
            style10.setOnClickListener {
                bottomNavBarColorSaver = 10
                bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_10)
                closeColors()
            }
            when (MySharedPreferences.bottomNavBarColor) {
                0 -> {
                    actionBarColorSaver = "#096EB4"
                    actionBar.setBackgroundColor(Color.parseColor("#096EB4"))
                    bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style)
                }
                1 -> {
                    actionBarColorSaver = "#096EB4"
                    actionBar.setBackgroundColor(Color.parseColor("#096EB4"))
                    bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style)
                }
                2 -> {
                    actionBarColorSaver = "#673AB7"
                    actionBar.setBackgroundColor(Color.parseColor("#673AB7"))
                    bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_2)
                }
                3 -> {
                    actionBarColorSaver = "#FF5722"
                    actionBar.setBackgroundColor(Color.parseColor("#FF5722"))
                    bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_3)
                }
                4 -> {
                    actionBarColorSaver = "#FF9800"
                    actionBar.setBackgroundColor(Color.parseColor("#FF9800"))
                    bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_4)
                }
                5 -> {
                    actionBarColorSaver = "#E91E63"
                    actionBar.setBackgroundColor(Color.parseColor("#E91E63"))
                    bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_5)
                }
                6 -> {
                    actionBarColorSaver = "#009688"
                    actionBar.setBackgroundColor(Color.parseColor("#009688"))
                    bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_6)
                }
                7 -> {
                    actionBarColorSaver = "#4CAF50"
                    actionBar.setBackgroundColor(Color.parseColor("#4CAF50"))
                    bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_7)
                }
                8 -> {
                    actionBarColorSaver = "#363F5E"
                    actionBar.setBackgroundColor(Color.parseColor("#363F5E"))
                    bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_8)
                }
                9 -> {
                    actionBarColorSaver = "#457B9D"
                    actionBar.setBackgroundColor(Color.parseColor("#457B9D"))
                    bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_9)
                }
                10 -> {
                    actionBarColorSaver = "#B93E20"
                    actionBar.setBackgroundColor(Color.parseColor("#B93E20"))
                    bottomNavBarColorView.setBackgroundResource(R.drawable.bottom_style_10)
                }
            }

            // Background Color Change
            backgroundColorChange.setOnClickListener { openColorPickerDialog(3) }
            if (MySharedPreferences.backgroundColor != "empty") {
                binding.root.setBackgroundColor(Color.parseColor(MySharedPreferences.backgroundColor))
                backgroundColorView.setCardBackgroundColor(Color.parseColor(MySharedPreferences.backgroundColor))
            } else {
                binding.root.setBackgroundColor(Color.parseColor("#F0F8FF"))
                backgroundColorView.setCardBackgroundColor(Color.parseColor("#F0F8FF"))
            }

            btnSave.setOnClickListener {
                //Status bar Color
                if (statusBarColorSaver != MySharedPreferences.statusBarColor && statusBarColorSaver != "") {
                    val colorFrom = if (MySharedPreferences.statusBarColor != "empty") {
                        Color.parseColor(MySharedPreferences.statusBarColor)
                    } else Color.parseColor("#00558A")
                    animationColorChange(colorFrom, Color.parseColor(statusBarColorSaver), 1)
                    MySharedPreferences.statusBarColor = statusBarColorSaver
                }

                //Bottom Navigation Bar Color
                if (bottomNavBarColorSaver != MySharedPreferences.bottomNavBarColor) {
                    MySharedPreferences.bottomNavBarColor = bottomNavBarColorSaver
                    val bottomNavExitAnim = AnimationUtils.loadAnimation(context, R.anim.bottom_nav_exit)
                    val bottomNavEnterAnim = AnimationUtils.loadAnimation(context, R.anim.bottom_nav_enter)
                    val bnb = requireActivity().findViewById<LinearLayout>(R.id.bottomLinear)
                    bnb.startAnimation(bottomNavExitAnim)
                    bottomNavExitAnim.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {}
                        override fun onAnimationEnd(p0: Animation?) {
                            when (bottomNavBarColorSaver) {
                                0 -> {
                                    animationColorChange(Color.parseColor(actionBarColorSaver), Color.parseColor("#096EB4"), 2)
                                    bnb.setBackgroundResource(R.drawable.bottom_style)
                                }
                                1 -> {
                                    animationColorChange(Color.parseColor(actionBarColorSaver), Color.parseColor("#096EB4"), 2)
                                    bnb.setBackgroundResource(R.drawable.bottom_style)
                                }
                                2 -> {
                                    animationColorChange(Color.parseColor(actionBarColorSaver), Color.parseColor("#673AB7"), 2)
                                    bnb.setBackgroundResource(R.drawable.bottom_style_2)
                                }
                                3 -> {
                                    animationColorChange(Color.parseColor(actionBarColorSaver), Color.parseColor("#FF5722"), 2)
                                    bnb.setBackgroundResource(R.drawable.bottom_style_3)
                                }
                                4 -> {
                                    animationColorChange(Color.parseColor(actionBarColorSaver), Color.parseColor("#FF9800"), 2)
                                    bnb.setBackgroundResource(R.drawable.bottom_style_4)
                                }
                                5 -> {
                                    animationColorChange(Color.parseColor(actionBarColorSaver), Color.parseColor("#E91E63"), 2)
                                    bnb.setBackgroundResource(R.drawable.bottom_style_5)
                                }
                                6 -> {
                                    animationColorChange(Color.parseColor(actionBarColorSaver), Color.parseColor("#009688"), 2)
                                    bnb.setBackgroundResource(R.drawable.bottom_style_6)
                                }
                                7 -> {
                                    animationColorChange(Color.parseColor(actionBarColorSaver), Color.parseColor("#4CAF50"), 2)
                                    bnb.setBackgroundResource(R.drawable.bottom_style_7)
                                }
                                8 -> {
                                    animationColorChange(Color.parseColor(actionBarColorSaver), Color.parseColor("#363F5E"), 2)
                                    bnb.setBackgroundResource(R.drawable.bottom_style_8)
                                }
                                9 -> {
                                    animationColorChange(Color.parseColor(actionBarColorSaver), Color.parseColor("#457B9D"), 2)
                                    bnb.setBackgroundResource(R.drawable.bottom_style_9)
                                }
                                10 -> {
                                    animationColorChange(Color.parseColor(actionBarColorSaver), Color.parseColor("#B93E20"), 2)
                                    bnb.setBackgroundResource(R.drawable.bottom_style_10)
                                }
                            }
                            bnb.startAnimation(bottomNavEnterAnim)
                        }
                        override fun onAnimationRepeat(p0: Animation?) {}
                    })
                }

                //Background Color Change
                if (backgroundColorSaver != MySharedPreferences.backgroundColor && backgroundColorSaver != "") {
                    val fromColor = if (MySharedPreferences.backgroundColor != "empty") {
                        Color.parseColor(MySharedPreferences.backgroundColor)
                    } else Color.parseColor("#F0F8FF")
                    animationColorChange(fromColor, Color.parseColor(backgroundColorSaver), 3)
                    MySharedPreferences.backgroundColor = backgroundColorSaver
                }
            }
        }
        return binding.root
    }

    @SuppressLint("Range")
    private fun openColorPickerDialog(n: Int) {
        MySharedPreferences.init(requireContext())
        var c = ""
        val dialog = AlertDialog.Builder(context).create()
        val dialogItem = ColorPickerDialogBinding.inflate(layoutInflater)
        dialogItem.apply {
            btnCancel.setOnClickListener { dialog.cancel() }
            //Copy Color Code
            colorCode.setOnClickListener { clipboard(colorCode.text.toString()) }
            colorCodeText.setOnClickListener { clipboard(colorCode.text.toString()) }
            selectedColorView.setOnClickListener { clipboard(colorCode.text.toString()) }
            when (n) {
                1 -> {
                    if (MySharedPreferences.statusBarColor != "empty") {
                        colorPickerView.setInitialColor(Color.parseColor(MySharedPreferences.statusBarColor))
                    } else {
                        colorPickerView.setInitialColor(Color.parseColor("#00558A"))
                    }
                }
                3 -> {
                    if (MySharedPreferences.backgroundColor != "empty") {
                        colorPickerView.setInitialColor(Color.parseColor(MySharedPreferences.backgroundColor))
                    } else {
                        colorPickerView.setInitialColor(Color.parseColor("#F0F8FF"))
                    }
                }
            }
            btnOk.setOnClickListener {
                when (n) {
                    1 -> {
                        statusBarColorSaver = c
                        dialog.cancel()
                    }
                    3 -> {
                        backgroundColorSaver = c
                        dialog.cancel()
                    }
                }
            }
            colorPickerView.setColorListener(object : ColorEnvelopeListener {
                @SuppressLint("SetTextI18n")
                override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
                    selectedColorView.setCardBackgroundColor(envelope!!.color)
                    colorCode.text = "#" + envelope.hexCode
                    c = "#" + envelope.hexCode
                }
            })
            colorPickerView.attachBrightnessSlider(brightnessSlide)
            colorPickerView.attachAlphaSlider(alphaSlideBar)
        }
        dialog.setView(dialogItem.root)
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun clipboard(colorCode: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Color Code", colorCode)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Color code copied👌", Toast.LENGTH_SHORT).show()
    }

    private fun closeColors() {
        v = false
        binding.apply {
            val colorsGoneAnim = AnimationUtils.loadAnimation(context, R.anim.colors_gone_anim)
            val colorsVisibleAnim = AnimationUtils.loadAnimation(context, R.anim.colors_visible_anim)
            bottomNavBarColorChange.startAnimation(colorsGoneAnim)
            colorsGoneAnim.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(p0: Animation?) {}
                override fun onAnimationEnd(p0: Animation?) {
                    colorsVisibility.visibility = View.GONE
                    bottomNavBarColorChange.startAnimation(colorsVisibleAnim)
                }
                override fun onAnimationRepeat(p0: Animation?) {}
            })
        }
    }

    private fun animationColorChange(colorFrom: Int, colorTo: Int, q: Int) {
        val colorAnimation = ValueAnimator.ofArgb(colorFrom, colorTo)
        colorAnimation.duration = 2000
        colorAnimation.addUpdateListener { animator ->
            when (q) {
                1 -> {
                    requireActivity().window.statusBarColor = animator.animatedValue as Int
                    binding.statusBarColorView.setCardBackgroundColor(animator.animatedValue as Int)
                }
                2 -> binding.actionBar.setBackgroundColor(animator.animatedValue as Int)
                3 -> {
                    binding.backgroundColorView.setCardBackgroundColor(animator.animatedValue as Int)
                    binding.root.setBackgroundColor(animator.animatedValue as Int)
                }
            }
        }
        colorAnimation.start()
    }
}