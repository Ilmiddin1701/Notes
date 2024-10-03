package uz.ilmiddin1701.note.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.ilmiddin1701.note.R
import uz.ilmiddin1701.note.adapters.NotesAdapter
import uz.ilmiddin1701.note.databinding.FragmentHomeBinding
import uz.ilmiddin1701.note.models.NoteData
import uz.ilmiddin1701.note.utils.MyData

class HomeFragment : Fragment(), NotesAdapter.NotesActionListener {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var list: ArrayList<NoteData>
    private lateinit var notesAdapter: NotesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        list = ArrayList()
        list.add(NoteData("Mening kun tartibim", "Note texts", "01.01.2024", "00 : 00"))
        list.add(NoteData("Tabiat haqida she'r", "Qo‘llarida soz bilan,\n" +
                "Gul g‘uncha parvoz bilan,\n" +
                "Silkinib parvoz bilan,\n" +
                "Uchib turna, g‘oz bilan,\n" +
                "Qadrdon bahor keldi.\n" +
                "\n" +
                "Bahorni kuting bog‘da,\n" +
                "Bog‘ emas, dala, tog‘da,\n" +
                "Maysa yashnagan chog‘da,\n" +
                "Qushlar sayrar butog‘da,\n" +
                "Sevikli bahor keldi.\n" +
                "\n" +
                "Har narsada o‘zgarish,\n" +
                "Erib, oqib jo‘nar qish.\n" +
                "Har yoqda yashnar turmush,\n" +
                "O’rik gulladi kumush,\n" +
                "Mehnat bahori keldi.\n" +
                "\n" +
                "Bezanar maktab bogi,\n" +
                "Gulga to‘lib quchog‘i.\n" +
                "Toshar hayot irmog‘i,\n" +
                "Maktab dam olar chog‘i,\n" +
                "O’quvchi, bahor keldi.\n" +
                "\n" +
                "O’tmasin go‘zal fursat,\n" +
                "Qani g‘ayrating ko‘rsat.\n" +
                "Imtihon, sinovga vaqt\n" +
                "Hozirmi, darslaring taxt,\n" +
                "Hushyor bo‘l, bahor keldi.\n\n" +
                "— Quddus Muhammadiy (1907-1997)", "01.01.2024", "00 : 00"))
        list.add(NoteData("Mashinalar turlari", "Mashina (lotincha: machina — qurol, qurilma) — bir turdagi harakat energiyasini ikkinchi turdagi harakat energiyasiga aylantirish, materiallar yoki axborotlarni oʻzgartirish, aloqa oʻrnatish, yuk va odamlarni tashish uchun moʻljallangan mexanizm yoki mexanizmlar majmui. Jamiyatdagi ishlab chiqarish kuchlarining eng muhim tarkibiy qismi, yirik mashinali ishlab chiqarishning moddiy negizi, aloqa va transportning asosiy vositasi hisoblanadi. M. — fantexnika inqilobining asosi. M. ayni paytda ham mehnat quroli, ham mehnat mahsuloti hisoblanadi. Inson kadimdan oʻz mehnatini yengillashtirish uchun turli-tuman qurol va qurilmalarni yaratishga urinib kelgan. Mola, omoch, charxpalak, urchuq, charh, dug, toʻquvchilik dastgohlari (doʻkonlari), choʻt, palaxmon, piltatoʻp, shamol tegirmoni va boshqa vositalar M. yaratish borasidagi urinishlar edi. Shuning uchun ilgari M. inson mehnatini osonlashtiradigan mexanik tizim deb tushunilgan. Bora-bora u insonning aqliy mehnatini va fiziologik vazifasini ham bajaradigan murakkab tizimga aylandi.\n" +
                "\n" +
                "M.dan foydalanish mehnatning maz-muni va tarzini oʻzgartirish, xalq xujaligining barcha tarmoqlarida mehnatning darajasini bir-biriga yaqinlashtirish va rivojlantirish, ish unumdorligini oshirish va shu asosda yuqori iqtisodiy samaradorlikka erishish, mehnatni mashaqqatli va zerikarli gomushdan ishtiyoq bilan bajariladigan ehtiyojga aylantirishning asosi hisoblanadi.", "01.01.2024", "00 : 00"))
        binding.apply {

            val drawable = edtSearch.compoundDrawablesRelative[2] // `drawableEnd` ni olish
            drawable?.setTint(ContextCompat.getColor(requireContext(), R.color.yellow)) // yangi rangni o'rnatish

            val list1 = ArrayList<NoteData>()
            list1.addAll(list)
            notesAdapter = NotesAdapter(this@HomeFragment, list1)
            rvNotes.adapter = notesAdapter
            edtSearch.addTextChangedListener {
                list1.clear()
                list.forEach { note ->
                    if (note.name!!.toLowerCase().contains(it.toString().toLowerCase())) {
                        list1.add(note)
                    }
                }
                notesAdapter.notifyDataSetChanged()
            }
        }
        return binding.root
    }

    override fun onNoteClick(noteData: NoteData, position: Int) {
        findNavController().navigate(R.id.showFragment)
        MyData.noteClick.postValue(true)
    }
}