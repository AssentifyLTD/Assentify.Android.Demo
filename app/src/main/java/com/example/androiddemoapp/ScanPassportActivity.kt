package  com.example.androiddemoapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.assentify.sdk.AssentifySdk
import com.assentify.sdk.Core.Constants.Language
import com.assentify.sdk.Core.Constants.MotionType
import com.assentify.sdk.Core.Constants.ZoomType
import com.assentify.sdk.Models.BaseResponseDataModel
import com.assentify.sdk.ScanPassport.PassportResponseModel
import com.assentify.sdk.ScanPassport.ScanPassportCallback
import java.lang.String
import kotlin.Double
import kotlin.Throwable
import kotlin.let

class ScanPassportActivity : AppCompatActivity() ,  ScanPassportCallback {
     lateinit var fragmentManager:FragmentManager ;
     lateinit var transaction:FragmentTransaction ;
     lateinit var scanPassport:Fragment ;
     lateinit var infoText:TextView ;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        infoText = findViewById(R.id.infoText)
        //infoText.visibility = View.GONE
        startAssentifySdk();
    }

    /**PASSPORT**/
    fun startAssentifySdk() {
        val assentifySdk: AssentifySdk = AssentifySdkObject.getAssentifySdkObject();
        val scanPassport = assentifySdk.startScanPassport(
            this@ScanPassportActivity, // This activity implemented from from ScanPassportCallback
            Language.Arabic // Optional the default is the doc language
        );
        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, scanPassport)
        transaction.addToBackStack(null) // Optional: Adds the transaction to the back stack
        transaction.commit()

    }

    override fun onCardDetected(dataModel: BaseResponseDataModel) {
        
    }

    override fun onClipPreparationComplete(dataModel: BaseResponseDataModel) {
        
    }

    override fun onComplete(dataModel: PassportResponseModel) {
            //infoText.visibility = View.GONE
            Log.e("onComplete",dataModel.passportExtractedModel.toString())
            dataModel.passportExtractedModel!!.extractedData?.let { ExtractedModel.setExtractedModel(it) };
            dataModel.passportExtractedModel!!.outputProperties?.let { OutputPropertiesModel.setOutputPropertiesModel(it) };
            val intent = Intent(this, NavToFace::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("image",dataModel.passportExtractedModel!!.imageUrl)
            startActivity(intent)

        dataModel.passportExtractedModel!!.outputProperties!!.forEach { t, u ->

            Log.e("MainActivity", "outputProperties Key" + t + " Value "+ u  )

        }
        dataModel.passportExtractedModel!!.transformedProperties!!.forEach { t, u ->

            Log.e("MainActivity", "transformedProperties Key" + t + " Value "+ u  )

        }
    }

    override fun onDocumentCaptured(dataModel: BaseResponseDataModel) {
        
    }

    override fun onDocumentCropped(dataModel: BaseResponseDataModel) {
        
    }


    override fun onError(dataModel: BaseResponseDataModel) {
        
    }

    override fun onFaceDetected(dataModel: BaseResponseDataModel) {
        
    }

    override fun onFaceExtracted(dataModel: BaseResponseDataModel) {
        
    }

    override fun onLivenessUpdate(dataModel: BaseResponseDataModel) {
        
    }

    override fun onMrzDetected(dataModel: BaseResponseDataModel) {
        
    }

    override fun onMrzExtracted(dataModel: BaseResponseDataModel) {
        
    }

    override fun onNoFaceDetected(dataModel: BaseResponseDataModel) {
        
    }

    override fun onNoMrzDetected(dataModel: BaseResponseDataModel) {
        
    }

    override fun onQualityCheckAvailable(dataModel: BaseResponseDataModel) {
        
    }

    override fun onRetry(dataModel: BaseResponseDataModel) {
        
    }

    override fun onSend() {
        Log.e("Events Here Scan Passport Page ","onSend")
        infoText.visibility = View.VISIBLE
    }

    override fun onStatusUpdated(dataModel: BaseResponseDataModel) {
        
    }

    override fun onUpdated(dataModel: BaseResponseDataModel) {
        
    }

    override fun onUploadFailed(dataModel: BaseResponseDataModel) {
        
    }


    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        finish();
    }
}