package  com.example.androiddemoapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.assentify.sdk.AssentifySdk
import com.assentify.sdk.AssentifySdkCallback
import com.assentify.sdk.Core.Constants.EnvironmentalConditions
import com.assentify.sdk.LanguageTransformation.LanguageTransformationCallback
import com.assentify.sdk.RemoteClient.Models.ConfigModel
import org.intellij.lang.annotations.Language

class SplashScreen : AppCompatActivity(), AssentifySdkCallback {
    private lateinit var assentifySdk: AssentifySdk
    private lateinit var passportClick: LinearLayout
    private lateinit var idClick: LinearLayout
    private lateinit var otherClick: LinearLayout
    private lateinit var loadingText: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val environmentalConditions = EnvironmentalConditions(
            true,
            true,
            "#61A03AAA",
            "#FFC400",
        );


        assentifySdk = AssentifySdk(
            "7UXZBSN2CeGxamNnp9CluLJn7Bb55lJo2SjXmXqiFULyM245nZXGGQvs956Fy5a5s1KoC4aMp5RXju8w",
            "4232e33b-1a90-4b74-94a4-08dcab07bc4d",
            "F0D1B6A7D863E9E4089B70EE5786D3D8DF90EE7BDD12BE315019E1F2FC0E875A",
            environmentalConditions,
            this,
            true,
            true,
            false,
            true,
            true,
            true,
            true,
        );

        loadingText = findViewById(R.id.loadingText);
        loadingText.visibility = View.VISIBLE

        passportClick = findViewById(R.id.passportClick);
        idClick = findViewById(R.id.idClick);
        otherClick = findViewById(R.id.otherClick);


        passportClick.visibility = View.GONE
        idClick.visibility = View.GONE
        otherClick.visibility = View.GONE

        passportClick.setOnClickListener {
            val intent = Intent(this, ScanPassportActivity::class.java);
            startActivity(intent)
        }
        idClick.setOnClickListener {
            val intent = Intent(this, ScanIDActivity::class.java);
            startActivity(intent)
        }
        otherClick.setOnClickListener {
            val intent = Intent(this, ScanOtherActivity::class.java);
            startActivity(intent)
        }
    }

    override fun onAssentifySdkInitError(message: String) {
        Log.e("Events Here onAssentifySdkInitError", message)
    }

    override fun onAssentifySdkInitSuccess(configModel: ConfigModel) {
        Log.e("Events Here onAssentifySdkInitSuccess", configModel.toString())

        AssentifySdkObject.setAssentifySdkObject(assentifySdk)
        StepDefinitionsModel.setStepDefinitionsModel(configModel.stepDefinitions)
        passportClick.visibility = View.VISIBLE
        idClick.visibility = View.VISIBLE
        otherClick.visibility = View.VISIBLE
        loadingText.visibility = View.GONE

        val templates = assentifySdk.getTemplates();

        templates.forEach { it ->
            println("HasTemplates name : " + it.name)
            println("HasTemplates flag : " + it.flag)
            it.templates.forEach { t ->
                println("HasTemplates sourceCountryFlag : " + t.sourceCountryFlag)
                println("HasTemplates sourceCountryCode : " + t.sourceCountryCode)
                t.kycDocumentDetails.forEach { h ->
                    println("HasTemplates name : " + h.name)
                    println("HasTemplates order : " + h.order)
                    println("HasTemplates templateSpecimen : " + h.templateSpecimen)
                }
            }
        }


    }



}



