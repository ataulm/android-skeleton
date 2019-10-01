package com.ataulm.materialdesignshowcase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity

class MaterialDesignShowcaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(intent.getIntExtra(EXTRA_THEME_RES_ID, 0))
        setContentView(R.layout.activity_material_design_showcase)
    }

    companion object {
        internal const val EXTRA_THEME_RES_ID = "${BuildConfig.LIBRARY_PACKAGE_NAME}.extra_theme_res_id"

        fun startActivity(context: Context, @StyleRes themeResId: Int) {
            val intent = Intent(context, MaterialDesignShowcaseActivity::class.java)
                    .putExtra(EXTRA_THEME_RES_ID, themeResId)
            context.startActivity(intent)
        }
    }
}
