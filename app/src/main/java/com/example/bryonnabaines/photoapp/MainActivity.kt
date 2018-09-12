package com.example.bryonnabaines.photoapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.bryonnabaines.photoapp.api.PhotoRetriever
import com.example.bryonnabaines.photoapp.models.Photo
import com.example.bryonnabaines.photoapp.models.PhotoList
import com.example.bryonnabaines.photoapp.ui.EndlessRecyclerOnScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {


    /**
     * This app is used to retrieve photos and the information about
     * said photos using a REST API provided from Pixabay
     * You can use postman at getpostman.com to demo api's before you use
     * them. This will show you what king of data you can retrieve
     */

    var photos : MutableList<Photo>? = mutableListOf()
    lateinit var recyclerView: RecyclerView
    private var mainAdapter: MainAdapter = MainAdapter(this.photos!!, this)
    //Set an on scrollListener for when the user reaches the end of the page
    val contentScrollListener = object : EndlessRecyclerOnScrollListener(){
        override fun onLoadMore(current_page: Int) {
            fetchPhotos(current_page)
            Log.d("Main Activity", " End of list reached")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        fetchPhotos(1)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addOnScrollListener(contentScrollListener)
    }

    fun fetchPhotos( page: Int){
        val retriever = PhotoRetriever()

        //This creates a callback object that is send to the retriever. It has methods to respond
        //if the callback fails or succeeds
        val callback = object : Callback<PhotoList> {
            //TODO see if you can get the metadata from the response, if yes then make a model and reference it here
            override fun onFailure(call: Call<PhotoList>?, t: Throwable?) {
                Log.e("MainActivity ", "Problems calling API", t)
            }

            override fun onResponse(call: Call<PhotoList>?, response: Response<PhotoList>?) {
                Log.d("MAIN ACTIVITY", "Successful response")
                //todo implement this functionality
//                mainAdapter?.removeLoadingFooter()

                if (response!!.isSuccessful) response.let {
                    photos = it.body()?.hits
                    mainAdapter = MainAdapter(photos!!, this@MainActivity)
                    recyclerView.adapter = mainAdapter
                }
                mainAdapter.addLoadingFooter()
            }
        }

        retriever.getPhotos(callback, page)
        Log.d("Main Activity", "Photos being requested page: $page")
    }

    override fun onClick(view: View?) {

        val intent = Intent(this, DetailActivity::class.java)
        val holder = view?.tag as MainAdapter.PhotoViewHolder
        intent.putExtra(DetailActivity.PHOTO,
                mainAdapter?.getPhoto(holder.adapterPosition))
        startActivity(intent) //creates intent to start the photo activity
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
