package com.example.blisstest.emoji

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blisstest.R
import com.example.blisstest.emoji.ui.EmojiFragment

class EmojiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.emoji_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, EmojiFragment.newInstance())
                .commitNow()
        }
    }
}