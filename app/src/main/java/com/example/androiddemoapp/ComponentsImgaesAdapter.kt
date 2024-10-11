package  com.example.androiddemoapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

data class ComponentImagesListItem(
    val name: String,
    val value: String,
)

class ComponentsImagesAdapter(
    context: Context,
    private val resource: Int,
    private val objects: List<ComponentImagesListItem>
) :
    ArrayAdapter<ComponentImagesListItem>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val item = getItem(position)
        if (item != null) {
            holder.name.text = item.name
            Picasso.get()
                .load(item.value)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.image)
        }

        return view
    }

    private class ViewHolder(view: View) {
        val name: TextView = view.findViewById(R.id.nameImage)
        val image: ImageView = view.findViewById(R.id.imageItemView)
    }
}