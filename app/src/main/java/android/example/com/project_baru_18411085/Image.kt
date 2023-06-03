package android.example.com.project_baru_18411085

import com.google.firebase.database.Exclude

data class Image(
    val imageSrc:String? = null,
    val imageTitle:String? = null,
    val imageDesc:String? = null,
    @get:Exclude
    @set:Exclude
    var key:String? = null

)