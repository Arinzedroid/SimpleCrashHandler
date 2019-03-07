package tech.arinzedroid.crashhandler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.arinzedroid.simplecrashhandler.CRASH_DATA
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener{
            throw RuntimeException("$this error occurred here")
        }

        iInited(intent)

    }

    private fun iInited(intent: Intent){
        println(intent.getStringExtra(CRASH_DATA))
    }
}
