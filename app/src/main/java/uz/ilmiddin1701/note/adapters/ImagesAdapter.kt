package uz.ilmiddin1701.note.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ilmiddin1701.note.databinding.ItemImagesBinding

class ImagesAdapter(var list: ArrayList<String>, var addFragmentRvAction: AddFragmentRvAction) : RecyclerView.Adapter<ImagesAdapter.Vh>() {

    inner class Vh(var itemImagesBinding: ItemImagesBinding) : RecyclerView.ViewHolder(itemImagesBinding.root) {
        fun onBind(image: String, position: Int) {
            try {
                if (position == list.size-1) {
                    itemImagesBinding.visibilityView.visibility = View.VISIBLE
                }
                if (image != "") {
                    val screenWidth = getScreenWidth(itemView.context)
                    val screenHeight = getScreenHeight(itemView.context)

                    val uri = Uri.parse(image)
                    val options = BitmapFactory.Options()
                    options.inJustDecodeBounds = true // Rasmning o'lchamini olish uchun
                    val inputStream = itemView.context.contentResolver.openInputStream(uri)
                    BitmapFactory.decodeStream(inputStream, null, options)
                    inputStream?.close()

                    // O'lchamni optimallashtirish uchun 'inSampleSize' ni hisoblash
                    options.inSampleSize = calculateInSampleSize(options, screenWidth, screenHeight)
                    options.inJustDecodeBounds = false

                    // Optimizatsiyalangan rasm yuklash
                    val inputStream2 = itemView.context.contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream2, null, options)
                    inputStream2?.close()

                    itemImagesBinding.imgLoaded.setImageBitmap(bitmap)

                    itemImagesBinding.linRemove.visibility = View.VISIBLE
                    itemImagesBinding.btnRemoveImage.setOnClickListener {
                        addFragmentRvAction.removeButtonClick(image, position)
                    }
                }
            } catch (e: Exception) {
                Log.e("ImageLoadError", "Error loading image: ${e.message}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface AddFragmentRvAction {
        fun removeButtonClick(image: String, position: Int)
    }

    // Rasmni o'lchamini optimallashtirish uchun funksiyani qo'shamiz
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

    fun getScreenWidth(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.heightPixels
    }
}