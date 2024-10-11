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

data class ComponentListItem(
    val name: String,
    val value: String,
)

class ComponentsAdapter(
    context: Context,
    private val resource: Int,
    private val objects: List<ComponentListItem>
) :
    ArrayAdapter<ComponentListItem>(context, resource, objects) {

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
            holder.value.text = item.value


        }

        return view
    }

    private class ViewHolder(view: View) {
        val name: TextView = view.findViewById(R.id.name)
        val value: TextView = view.findViewById(R.id.value)
    }
}