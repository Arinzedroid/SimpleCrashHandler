package tech.arinzedroid.crashhandler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.arinzedroid.simplecrashhandler.CRASH_DATA

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        println(intent.getStringExtra(CRASH_DATA))
    }


}
