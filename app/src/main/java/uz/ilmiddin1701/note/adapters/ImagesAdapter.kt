package uz.ilmiddin1701.note.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.ilmiddin1701.note.databinding.ItemImagesBinding

class ImagesAdapter(var list: ArrayList<String>, var imageClickAction: ImageClickAction) : Adapter<ImagesAdapter.Vh>() {

    inner class Vh(var itemImagesBinding: ItemImagesBinding) : ViewHolder(itemImagesBinding.root) {
        fun onBind(image: String, position: Int) {
            if (image != "") itemImagesBinding.imgLoaded.setImageURI(Uri.parse(image))

            itemImagesBinding.root.setOnClickListener {
                imageClickAction.imageClick(image, position)
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
        fun imageClick(image: String, position: Int)
    }
}