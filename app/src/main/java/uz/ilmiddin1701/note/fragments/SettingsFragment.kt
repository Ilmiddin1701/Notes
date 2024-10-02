package uz.ilmiddin1701.note.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import uz.ilmiddin1701.note.R
import uz.ilmiddin1701.note.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private val binding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            btnBack.setOnClickListener { findNavController().popBackStack() }


//            colorPickerView.setColorListener(object : ColorEnvelopeListener {
//                @SuppressLint("SetTextI18n")
//                override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
//                    colorCode.setBackgroundColor(envelope!!.color)
//                    colorCode.text = "#" + envelope.hexCode
//                }
//            })
//            colorPickerView.attachBrightnessSlider(brightnessSlide)
//            colorPickerView.attachAlphaSlider(alphaSlideBar)
        }
        return binding.root
    }
}