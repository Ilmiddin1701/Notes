package uz.ilmiddin1701.note.fragments

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.text.style.StyleSpan
import android.view.ActionMode
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.ilmiddin1701.note.R
import uz.ilmiddin1701.note.databinding.FragmentAddBinding
import uz.ilmiddin1701.note.db.MyDbHelper
import uz.ilmiddin1701.note.models.NoteData
import uz.ilmiddin1701.note.utils.MyData
import uz.ilmiddin1701.note.utils.MySharedPreferences
import java.text.SimpleDateFormat
import java.util.Date

class AddFragment : Fragment() {
    private val binding by lazy { FragmentAddBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var bnb: LinearLayout
    private var boldStyle = false
    private var italicStyle = false
    private var markerStyle = false
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myDbHelper = MyDbHelper(requireContext())
        binding.apply {
            tvNoteDate.text = SimpleDateFormat("dd.MM.yyyy").format(Date())

            //Background Color
            if (MySharedPreferences.backgroundColor != "empty") {
                binding.root.setBackgroundColor(Color.parseColor(MySharedPreferences.backgroundColor))
            } else binding.root.setBackgroundColor(Color.parseColor("#F0F8FF"))

            // Keyboard Listener
            relativeRootView.viewTreeObserver.addOnGlobalLayoutListener {
                val r = Rect()
                // Root view ko'rinadigan maydonini olamiz
                relativeRootView.getWindowVisibleDisplayFrame(r)

                // Ekran balandligini aniqlaymiz
                val screenHeight = relativeRootView.height
                // Klaviatura ochilganda ekran pastki qismi kichrayadi
                val keypadHeight = screenHeight - r.bottom

                // Klaviatura ochilganini aniqlaymiz
                if (keypadHeight > screenHeight * 0.15) {
                    // Klaviatura ochildi, CardView ni yuqoriga ko'taramiz
                    functionalCard.animate()
                        .translationY(-keypadHeight.toFloat())
                        .setDuration(300)
                        .start()
                } else {
                    // Klaviatura yopildi, CardView ni eski holatiga qaytaramiz
                    functionalCard.animate()
                        .translationY(0f)
                        .setDuration(300)
                        .start()
                }
            }

            edtNoteText.addTextChangedListener { tvSymbol.text = it.toString().length.toString() + " symbols" }

            // EditText focus listener
            edtNoteText.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    functionalCard.visibility = View.VISIBLE
                } else {
                    functionalCard.visibility = View.GONE
                }
            }

            val bottomNavExitAnim = AnimationUtils.loadAnimation(context, R.anim.bottom_nav_exit)
            bnb = requireActivity().findViewById(R.id.bottomLinear)
            bnb.startAnimation(bottomNavExitAnim)
            bottomNavExitAnim.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(p0: Animation?) {}
                override fun onAnimationEnd(p0: Animation?) { bnb.visibility = View.GONE }
                override fun onAnimationRepeat(p0: Animation?) {}
            })

            btnBack.setOnClickListener { findNavController().popBackStack() }

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

            // Save Note
            btnSave.setOnClickListener {
                val noteData = NoteData(
                    edtNoteName.text.toString(),
                    Html.toHtml(edtNoteText.text as Spannable),
                    SimpleDateFormat("dd.MM.yyyy").format(Date()),
                    tvNoteTime.text.toString(),
                    "","","",""
                )
                myDbHelper.addNote(noteData)
                findNavController().popBackStack()
            }
        }
        return binding.root
    }

    private val loadImageFromExternalStorage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it ?: return@registerForActivityResult

    }

    override fun onPause() {
        super.onPause()
        val bottomNavEnterAnim = AnimationUtils.loadAnimation(context, R.anim.bottom_nav_enter)
        bnb.visibility = View.VISIBLE
        bnb.startAnimation(bottomNavEnterAnim)
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