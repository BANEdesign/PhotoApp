package com.example.bryonnabaines.photoapp.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by bryonnabaines on 9/6/18.
 */

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {

    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private var visibleThreshold = 0 // The minimum amount of items to have below your current scroll position before loading more.
    internal var firstVisibleItem: Int = 0
    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0

    var current_page = 1

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView!!.childCount
        totalItemCount = recyclerView.layoutManager.itemCount
        firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached

            // Do something
            current_page++

            onLoadMore(current_page)

            loading = true
        }
    }

    abstract fun onLoadMore(current_page: Int)

    fun reset() {
        previousTotal = 0
        loading = true
        visibleThreshold = 0
        firstVisibleItem = 0
        visibleItemCount = 0
        totalItemCount = 0
    }

    companion object {
        var TAG = EndlessRecyclerOnScrollListener::class.java.simpleName
    }
}