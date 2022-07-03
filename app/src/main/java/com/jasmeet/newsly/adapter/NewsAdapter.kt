package com.jasmeet.newsly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jasmeet.newsly.News
import com.jasmeet.newsly.R
import com.jasmeet.newsly.databinding.ItemNewsBinding

class NewsAdapter(private val listener:NewsItemClicked
):RecyclerView.Adapter<NewsViewHolder>() {

    private val items:ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder =NewsViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])

        }
        return viewHolder


    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.title.text = currentItem.title
        holder.binding.categories.text = currentItem.pubDate
        Glide.with(holder.itemView.context).load(currentItem.image_url).into(holder.binding.image)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }
}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding :ItemNewsBinding = ItemNewsBinding.bind(itemView)
}
interface NewsItemClicked{
    fun onItemClicked(item:News)

}