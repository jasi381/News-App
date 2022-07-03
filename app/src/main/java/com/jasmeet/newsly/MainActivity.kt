package com.jasmeet.newsly

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.jasmeet.newsly.adapter.NewsAdapter
import com.jasmeet.newsly.adapter.NewsItemClicked
import com.jasmeet.newsly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter:NewsAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()

        mAdapter  = NewsAdapter(this)
        binding.recyclerView.adapter = mAdapter
    }

    private fun fetchData(){
        val url ="https://newsdata.io/api/1/news?apikey=pub_889642a955fc42aae81d4d608f08c7576863&country=in&language=en&category=Entertainment "
         val jsonObjectRequest = JsonObjectRequest(
             Request.Method.GET,
             url,
             null,
             {
             val newsJsonArray = it.getJSONArray("results")
                 val newsArray = ArrayList<News>()
                 for(i in 0 until newsJsonArray.length()){
                     val newsJsonObject = newsJsonArray.getJSONObject(i)
                     val news = News(
                         newsJsonObject.getString("title"),
                         newsJsonObject.getString("link"),
                         newsJsonObject.getString("image_url"),
                         newsJsonObject.getString("pubDate")
                     )
                     newsArray.add(news)
                 }
                 mAdapter.updateNews(newsArray)
             },
             {

             }

         )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder =CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.link))
    }
}