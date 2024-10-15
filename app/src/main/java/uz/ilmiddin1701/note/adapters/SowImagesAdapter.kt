package uz.ilmiddin1701.note.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ilmiddin1701.note.databinding.ItemImagesBinding
import java.io.File

class SowImagesAdapter(var list: ArrayList<String>, var imageClickAction: ImageClickAction) : RecyclerView.Adapter<SowImagesAdapter.Vh>() {

    inner class Vh(var itemImagesBinding: ItemImagesBinding) : RecyclerView.ViewHolder(itemImagesBinding.root) {
        fun onBind(image: String, position: Int) {
            val screenWidth = getScreenWidth(itemView.context)
            val screenHeight = getScreenHeight(itemView.context)

            try {
                // absolute path orqali faylga murojaat qilish
                val file = File(image)
                if (file.exists()) {
                    // Faylni optimallashtirish bilan yuklash
                    val options = BitmapFactory.Options()
                    options.inJustDecodeBounds = true
                    BitmapFactory.decodeFile(file.absolutePath, options)

                    options.inSampleSize = calculateInSampleSize(options, screenWidth, screenHeight)
                    options.inJustDecodeBounds = false

                    val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)

                    itemImagesBinding.imgLoaded.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace() // Xatoliklarni loglash
            }

            itemImagesBinding.rvImageCard.setOnClickListener {
                imageClickAction.imageClick(image)
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

    interface ImageClickAction {
        fun imageClick(image: String)
    }

    fun getScreenWidth(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.heightPixels
    }

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
}