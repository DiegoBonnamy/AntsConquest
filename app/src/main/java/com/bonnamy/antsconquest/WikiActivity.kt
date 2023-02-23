package com.bonnamy.antsconquest

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.bonnamy.antsconquest.ui.screen.WikiScreen
import com.bonnamy.antsconquest.ui.theme.AntsConquestTheme

class WikiActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            AntsConquestTheme {
                WikiScreen(
                    onBottomBarItemClick = { position ->
                        when(position) {
                            // Explore
                            1 -> {
                                val intent = Intent(this, ExploreActivity::class.java)
                                this.startActivity(intent)
                            }
                            // AntHill
                            2 -> {
                                val intent = Intent(this, AntHillActivity::class.java)
                                this.startActivity(intent)
                            }
                            // Ants
                            3 -> {
                                val intent = Intent(this, MainActivity::class.java)
                                this.startActivity(intent)
                            }
                            // Attack
                            4 -> {
                                val intent = Intent(this, AttackActivity::class.java)
                                this.startActivity(intent)
                            }
                            // Wiki -> do nothing
                            5 -> { }
                            // Do nothing
                            else -> {}
                        }
                    }
                )
            }
        }
    }
}