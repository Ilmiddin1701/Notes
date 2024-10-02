package uz.ilmiddin1701.note.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.ilmiddin1701.note.R
import uz.ilmiddin1701.note.databinding.FragmentShowBinding
import uz.ilmiddin1701.note.utils.MyData

class ShowFragment : Fragment() {
    private val binding by lazy { FragmentShowBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {

        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        MyData.noteClick.postValue(false)
    }
}