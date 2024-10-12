package uz.ilmiddin1701.note.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.ilmiddin1701.note.MainActivity
import uz.ilmiddin1701.note.R
import uz.ilmiddin1701.note.adapters.ImagesAdapter
import uz.ilmiddin1701.note.databinding.FragmentAddBinding
import uz.ilmiddin1701.note.db.MyDbHelper
import uz.ilmiddin1701.note.models.NoteData
import uz.ilmiddin1701.note.utils.MySharedPreferences
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class AddFragment : Fragment(), ImagesAdapter.ImageClickAction {
    private val binding by lazy { FragmentAddBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var bnb: LinearLayout
    private var boldStyle = false
    private var italicStyle = false
    private var markerStyle = false

    private lateinit var imagesList: ArrayList<String>
    private var imagesString = ""
    private lateinit var imagesAdapter: ImagesAdapter

    private val recordAudioPermissionCode = 1

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        imagesList = ArrayList()
        MySharedPreferences.init(requireContext())
        myDbHelper = MyDbHelper(requireContext())
        binding.apply {
            btnBack.setOnClickListener { findNavController().popBackStack() }
            when (MySharedPreferences.bottomNavBarColor) {
                0 -> {
                    btnHideShow.setBackgroundResource(R.drawable.bottom_style)
                    linRecycler.setBackgroundResource(R.drawable.images_linear_style)
                    actionBar.setBackgroundColor(Color.parseColor("#096EB4"))
                    functionalLinear.setBackgroundColor(Color.parseColor("#096EB4"))
                }
                1 -> {
                    btnHideShow.setBackgroundResource(R.drawable.bottom_style)
                    linRecycler.setBackgroundResource(R.drawable.images_linear_style)
                    actionBar.setBackgroundColor(Color.parseColor("#096EB4"))
                    functionalLinear.setBackgroundColor(Color.parseColor("#096EB4"))
                }
                2 -> {
                    btnHideShow.setBackgroundResource(R.drawable.bottom_style_2)
                    linRecycler.setBackgroundResource(R.drawable.images_linear_style2)
                    actionBar.setBackgroundColor(Color.parseColor("#673AB7"))
                    functionalLinear.setBackgroundColor(Color.parseColor("#673AB7"))
                }
                3 -> {
                    btnHideShow.setBackgroundResource(R.drawable.bottom_style_3)
                    linRecycler.setBackgroundResource(R.drawable.images_linear_style3)
                    actionBar.setBackgroundColor(Color.parseColor("#FF5722"))
                    functionalLinear.setBackgroundColor(Color.parseColor("#FF5722"))
                }
                4 -> {
                    btnHideShow.setBackgroundResource(R.drawable.bottom_style_4)
                    linRecycler.setBackgroundResource(R.drawable.images_linear_style4)
                    actionBar.setBackgroundColor(Color.parseColor("#FF9800"))
                    functionalLinear.setBackgroundColor(Color.parseColor("#FF9800"))
                }
                5 -> {
                    btnHideShow.setBackgroundResource(R.drawable.bottom_style_5)
                    linRecycler.setBackgroundResource(R.drawable.images_linear_style5)
                    actionBar.setBackgroundColor(Color.parseColor("#E91E63"))
                    functionalLinear.setBackgroundColor(Color.parseColor("#E91E63"))
                }
                6 -> {
                    btnHideShow.setBackgroundResource(R.drawable.bottom_style_6)
                    linRecycler.setBackgroundResource(R.drawable.images_linear_style6)
                    actionBar.setBackgroundColor(Color.parseColor("#009688"))
                    functionalLinear.setBackgroundColor(Color.parseColor("#009688"))
                }
                7 -> {
                    btnHideShow.setBackgroundResource(R.drawable.bottom_style_7)
                    linRecycler.setBackgroundResource(R.drawable.images_linear_style7)
                    actionBar.setBackgroundColor(Color.parseColor("#4CAF50"))
                    functionalLinear.setBackgroundColor(Color.parseColor("#4CAF50"))
                }
                8 -> {
                    btnHideShow.setBackgroundResource(R.drawable.bottom_style_8)
                    linRecycler.setBackgroundResource(R.drawable.images_linear_style8)
                    actionBar.setBackgroundColor(Color.parseColor("#363F5E"))
                    functionalLinear.setBackgroundColor(Color.parseColor("#363F5E"))
                }
                9 -> {
                    btnHideShow.setBackgroundResource(R.drawable.bottom_style_9)
                    linRecycler.setBackgroundResource(R.drawable.images_linear_style9)
                    actionBar.setBackgroundColor(Color.parseColor("#457B9D"))
                    functionalLinear.setBackgroundColor(Color.parseColor("#457B9D"))
                }
                10 -> {
                    btnHideShow.setBackgroundResource(R.drawable.bottom_style_10)
                    linRecycler.setBackgroundResource(R.drawable.images_linear_style10)
                    actionBar.setBackgroundColor(Color.parseColor("#B93E20"))
                    functionalLinear.setBackgroundColor(Color.parseColor("#B93E20"))
                }
            }

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
                    functionalLinear.animate()
                        .translationY(-keypadHeight.toFloat())
                        .setDuration(300)
                        .start()
                } else {
                    // Klaviatura yopildi, CardView ni eski holatiga qaytaramiz
                    functionalLinear.animate()
                        .translationY(0f)
                        .setDuration(300)
                        .start()
                }
            }

            edtNoteText.addTextChangedListener { tvSymbol.text = it.toString().length.toString() + " symbols" }

            // EditText focus listener
            edtNoteText.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    functionalLinear.visibility = View.VISIBLE
                } else {
                    functionalLinear.visibility = View.INVISIBLE
                }
            }

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

            btnGallery.setOnClickListener {
                loadImageFromExternalStorage.launch("image/*")
            }

            val imagesRvCloseAnim = AnimationUtils.loadAnimation(context, R.anim.images_rv_close_anim)
            val imagesRvOpenAnim = AnimationUtils.loadAnimation(context, R.anim.images_rv_open_anim)
            val btnHideShowDownAnim = AnimationUtils.loadAnimation(context, R.anim.btn_hide_show_down_anim)
            val btnHideShowUpAnim = AnimationUtils.loadAnimation(context, R.anim.btn_hide_show_up_anim)

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
                            linRecycler.visibility= View.VISIBLE
                        }
                        override fun onAnimationEnd(p0: Animation?) {
                            btnHideShow.setImageResource(R.drawable.ic_x)
                        }
                        override fun onAnimationRepeat(p0: Animation?) {}
                    })
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

            // Save Note
            btnSave.setOnClickListener {
                val noteData = NoteData(
                    edtNoteName.text.toString(),
                    Html.toHtml(edtNoteText.text as Spannable),
                    SimpleDateFormat("dd.MM.yyyy").format(Date()),
                    tvNoteTime.text.toString(),
                    imagesString
                )
                myDbHelper.addNote(noteData)
                findNavController().popBackStack()
            }
        }
        return binding.root
    }

    fun decodeSampledBitmapFromUri(uri: Uri, reqWidth: Int, reqHeight: Int, context: Context): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true

        // Rasmning o'lchamlarini aniqlash (bitta o'lchamli yuklash)
        val inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream, null, options)
        inputStream?.close()

        // Rasm o'lchamlarini kerakli o'lchamlarga moslashtirish
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false

        // Rasmni optimallashtirilgan o'lchamda yuklash
        val inputStream2 = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream2, null, options)
        inputStream2?.close()

        return bitmap
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
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

    private val loadImageFromExternalStorage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri ?: return@registerForActivityResult
        // Rasmni listga qo'shish va ko'rsatish
        imagesList.add(uri.toString())
        imagesAdapter = ImagesAdapter(imagesList, this)
        binding.rvImages.adapter = imagesAdapter
        binding.imagesRelative.visibility = View.VISIBLE

        // Optimallashtirilgan rasm yuklash
        val bitmap = decodeSampledBitmapFromUri(uri, 1024, 1024, requireContext())
        bitmap?.let {
            val file = File(requireActivity().filesDir, "IMG_${UUID.randomUUID()}.jpg")
            val fileOutputStream = FileOutputStream(file)
            it.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream) // Rasmni JPEG formatida saqlash
            fileOutputStream.close()
            imagesString += file.absolutePath + ","
        }
    }

    override fun imageClick(image: String, position: Int) {

    }

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO), recordAudioPermissionCode)
        }
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