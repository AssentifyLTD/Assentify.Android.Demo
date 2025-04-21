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
            "",
            "",
            "",
            environmentalConditions,
            this,
            true,
            true,
            false,
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
        loadingText.visibility = View.VISIBLE

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
                    println("HasTemplates templateSpecimen : " + h.templateProcessingKeyInformation)
                }
            }
        }


    }



}



