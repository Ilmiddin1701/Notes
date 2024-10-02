package uz.ilmiddin1701.note.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.ilmiddin1701.note.databinding.FragmentAddBinding
import uz.ilmiddin1701.note.utils.MyData

class AddFragment : Fragment() {
    private val binding by lazy { FragmentAddBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {

        }
        return binding.root
    }
}