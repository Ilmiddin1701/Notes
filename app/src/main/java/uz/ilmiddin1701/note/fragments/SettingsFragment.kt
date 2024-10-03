package uz.ilmiddin1701.note.fragments

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            MySharedPreferences.init(requireContext())
            if (MySharedPreferences.statusBarColor != "empty") {
                statusBarColorView.setCardBackgroundColor(Color.parseColor(MySharedPreferences.statusBarColor))
            } else {
                statusBarColorView.setCardBackgroundColor(Color.parseColor("#00558A"))
            }
            btnBack.setOnClickListener { findNavController().popBackStack() }
            statusBarColorView.setOnClickListener {
                openColorPickerDialog(1)
            }
            bottomNavigationBarColorView.setOnClickListener {

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
            btnOk.setOnClickListener {
                when (n) {
                    1 -> {
                        val colorFrom = if (MySharedPreferences.statusBarColor != "empty") {
                            Color.parseColor(MySharedPreferences.statusBarColor)
                        } else {
                            Color.parseColor("#00558A")
                        }
                        val colorTo = Color.parseColor(c)
                        val colorAnimation = ValueAnimator.ofArgb(colorFrom, colorTo)
                        colorAnimation.duration = 2000
                        colorAnimation.addUpdateListener { animator ->
                            requireActivity().window.statusBarColor = animator.animatedValue as Int
                        }
                        MySharedPreferences.statusBarColor = c
                        colorAnimation.start()
                        dialog.cancel()
                    }
                    2 -> {
                        requireActivity().findViewById<LinearLayout>(R.id.bottomLinear)
                            .setBackgroundColor(R.drawable.bottom_style)
                    }
                    3 -> {

                    }
                    4 -> {

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
}