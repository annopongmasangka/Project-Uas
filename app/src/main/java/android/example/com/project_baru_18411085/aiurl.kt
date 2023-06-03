package android.example.com.project_baru_18411085

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadImage(Url: String?) {
    val option = RequestOptions().placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)
    Glide.with(context).setDefaultRequestOptions(option)
        .load(Url)
        .into(this)
}