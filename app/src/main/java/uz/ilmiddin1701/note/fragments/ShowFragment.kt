package uz.ilmiddin1701.note.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.ContactsContract.CommonDataKinds.Note
import android.text.Editable
import android.text.Html
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.ilmiddin1701.note.R
import uz.ilmiddin1701.note.adapters.ImagesAdapter
import uz.ilmiddin1701.note.databinding.FragmentShowBinding
import uz.ilmiddin1701.note.models.NoteData
import uz.ilmiddin1701.note.utils.MySharedPreferences

@Suppress("DEPRECATION")
class ShowFragment : Fragment(), ImagesAdapter.ImageClickAction {
    private val binding by lazy { FragmentShowBinding.inflate(layoutInflater) }
    private lateinit var bnb: LinearLayout
    private lateinit var imagesAdapter: ImagesAdapter
    private var boldStyle = false
    private var italicStyle = false
    private var markerStyle = false
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loadedNote = arguments?.getSerializable("keyNoteData") as NoteData
        binding.apply {
            btnBack.setOnClickListener { findNavController().popBackStack() }
            edtNoteText.addTextChangedListener { tvSymbol.text = it.toString().length.toString() + " symbols" }
            //Background Color
            if (MySharedPreferences.backgroundColor != "empty") {
                binding.root.setBackgroundColor(Color.parseColor(MySharedPreferences.backgroundColor))
            } else binding.root.setBackgroundColor(Color.parseColor("#F0F8FF"))

            btnItalic.setOnClickListener {
                if (italicStyle) {
                    italicStyle = false
                    btnItalic.setImageResource(R.drawable.ic_un_active_italic)
                } else {
                    italicStyle = true
                    btnItalic.setImageResource(R.drawable.ic_active_italic)
                }
                vibrate(requireContext())
            }

            btnBold.setOnClickListener {
                if (boldStyle) {
                    boldStyle = false
                    btnBold.setImageResource(R.drawable.ic_un_active_bold)
                } else {
                    boldStyle = true
                    btnBold.setImageResource(R.drawable.ic_active_bold)
                }
                vibrate(requireContext())
            }

            btnMarker.setOnClickListener {
                if (markerStyle) {
                    markerStyle = false
                    btnMarker.setImageResource(R.drawable.ic_un_active_marker)
                } else {
                    markerStyle = true
                    btnMarker.setImageResource(R.drawable.ic_active_marker)
                }
                vibrate(requireContext())
            }

            // Rv Images hide show
            val imagesRvCloseAnim = AnimationUtils.loadAnimation(context, R.anim.images_rv_close_anim)
            val imagesRvOpenAnim = AnimationUtils.loadAnimation(context, R.anim.images_rv_open_anim)
            val btnHideShowDownAnim = AnimationUtils.loadAnimation(context, R.anim.btn_hide_show_down_anim)
            val btnHideShowUpAnim = AnimationUtils.loadAnimation(context, R.anim.btn_hide_show_up_anim)
            if (loadedNote.images != "") {
                imagesRelative.visibility = View.VISIBLE
                val imageArray = loadedNote.images!!.split(",").filter { it.isNotEmpty() }
                imagesAdapter = ImagesAdapter(imageArray as ArrayList<String>, this@ShowFragment)
                rvImages.adapter = imagesAdapter
                linRecycler.visibility = View.VISIBLE
                linRecycler.startAnimation(imagesRvOpenAnim)
                btnHideShow.startAnimation(btnHideShowUpAnim)
            }
            var checkedHideShow = true
            btnHideShow.setOnClickListener {
                if (checkedHideShow) {
                    checkedHideShow = false
                    linRecycler.startAnimation(imagesRvCloseAnim)
                    btnHideShow.startAnimation(btnHideShowDownAnim)
                    imagesRvCloseAnim.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {}
                        override fun onAnimationEnd(p0: Animation?) {
                            linRecycler.visibility = View.GONE
                            btnHideShow.setImageResource(R.drawable.ic_up)
                        }
                        override fun onAnimationRepeat(p0: Animation?) {}
                    })
                } else {
                    checkedHideShow = true
                    linRecycler.startAnimation(imagesRvOpenAnim)
                    btnHideShow.startAnimation(btnHideShowUpAnim)
                    imagesRvOpenAnim.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {
                            linRecycler.visibility = View.VISIBLE
                        }
                        override fun onAnimationEnd(p0: Animation?) {
                            btnHideShow.setImageResource(R.drawable.ic_x)
                        }
                        override fun onAnimationRepeat(p0: Animation?) {}
                    })
                }
            }

            // Ma'lumotlarni tenglash
            edtNoteName.setText(loadedNote.name)
            edtNoteText.setText(Html.fromHtml(loadedNote.text, Html.FROM_HTML_MODE_COMPACT).trim())
            tvNoteDate.text = loadedNote.date
            tvSymbol.text = edtNoteText.text.length.toString() + " symbols"

            // EditText focus listener
            edtNoteText.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    functionalLinear.visibility = View.VISIBLE
                } else {
                    functionalLinear.visibility = View.INVISIBLE
                }
            }

            // Matn qo'shilayotganda stilni tekshiramiz va qo'llaymiz
            edtNoteText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                var currentText: String = ""
                override fun afterTextChanged(s: Editable?) {
                    if (s == null || s.toString() == currentText) return // Matnni takroran yangilashning oldini olamiz

                    val newText = s.toString()

                    // Agar yangi belgilar qo'shilgan bo'lsa va stil o'zgarishi kerak bo'lsa
                    if (newText.length > currentText.length) {
                        val start = currentText.length // Yangi matnning boshi
                        val end = newText.length       // Oxiri

                        // Stilni qo'llaymiz faqat oxirgi kiritilgan so'zga
                        if (boldStyle) {
                            s.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                        if (italicStyle) {
                            s.setSpan(StyleSpan(Typeface.ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                        if (markerStyle) {
                            s.setSpan(BackgroundColorSpan(Color.parseColor("#FFDD00")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                    }

                    // Hozirgi matnni yangilaymiz
                    currentText = newText
                }
            })
        }
        return binding.root
    }

    override fun imageClick(image: String, position: Int) {

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

    private fun vibrate(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(40) // Old versions
        }
    }
}