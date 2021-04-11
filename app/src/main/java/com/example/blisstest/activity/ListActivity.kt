package com.example.blisstest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.blisstest.R
import com.example.blisstest.util.ui.list.ListType
import com.example.blisstest.modules.emoji.EmojiFragment
import com.example.blisstest.list.emoji.UserFragment
import com.example.blisstest.modules.google.GoogleReposFragment
import java.lang.UnsupportedOperationException

private const val LIST_TYPE = "LIST_TYPE"
class ListActivity : AppCompatActivity() {

    companion object {
        fun createBundle(type: ListType): Bundle {
            return Bundle().apply {
                putSerializable(LIST_TYPE, type)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, getFragmentByType())
                .commitNow()
        }
    }

    private fun getFragmentByType(): Fragment {
        val type = intent.getSerializableExtra(LIST_TYPE)
        type ?: throw UnsupportedOperationException("List type not sent to activity")

        return when (type as ListType) {
            ListType.USER -> UserFragment.newInstance()
            ListType.EMOJI -> EmojiFragment.newInstance()
            ListType.GOOGLE_REPOS -> GoogleReposFragment.newInstance()
        }
    }
}