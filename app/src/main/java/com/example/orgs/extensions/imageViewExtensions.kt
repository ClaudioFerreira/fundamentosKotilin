package com.example.orgs.extensions

import android.util.Log
import android.widget.ImageView
import coil.load
import com.example.orgs.R

fun ImageView.tentaCarregarImagem(url: String? = null) {
    load(url) {
        Log.i("AQUI tentaCarregarImagem", "url: $url")
        fallback(R.drawable.erro)
        error(R.drawable.erro)
        placeholder(R.drawable.placeholder)
    }
}