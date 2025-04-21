package  com.example.androiddemoapp
import android.provider.Settings

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.assentify.sdk.AssentifySdk
import com.assentify.sdk.Core.Constants.BrightnessEvents
import com.assentify.sdk.Core.Constants.ConstantsValues
import com.assentify.sdk.Core.Constants.Language
import com.assentify.sdk.Core.Constants.MotionType
import com.assentify.sdk.Core.Constants.ZoomType
import com.assentify.sdk.Models.BaseResponseDataModel
import com.assentify.sdk.ScanNFC.ScanNfc
import com.assentify.sdk.ScanNFC.ScanNfcCallback
import com.assentify.sdk.ScanPassport.PassportResponseModel
import com.assentify.sdk.ScanPassport.ScanPassport
import com.assentify.sdk.ScanPassport.ScanPassportCallback
import kotlin.Double
import kotlin.Throwable
import kotlin.let

class ScanPassportActivity : AppCompatActivity() ,  ScanPassportCallback , ScanNfcCallback {
     lateinit var fragmentManager:FragmentManager ;
     lateinit var transaction:FragmentTransaction ;
     lateinit var scanPassport:ScanPassport ;
     lateinit var infoText:TextView ;
     private lateinit var scanNfc: ScanNfc
     private var passportResponseModel: PassportResponseModel? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        infoText = findViewById(R.id.infoText)
        //infoText.visibility = View.GONE
        val assentifySdk: AssentifySdk = AssentifySdkObject.getAssentifySdkObject();
        scanNfc = assentifySdk.startScanNfc(
            this,// This activity implemented from ScanNfcCallback
            languageCode = Language.Arabic,// Optional the default is the doc language
            context = this// Context
        )


        if (scanNfc.isNfcSupported(activity = this)) {
            if (scanNfc.isNfcEnabled(activity = this)) {
                infoText.text = "NFC Supported on this device, And Enabled.";
            } else {
                infoText.text = "NFC Supported on this device, And not Enabled.";
                val intent = Intent(Settings.ACTION_NFC_SETTINGS)
                startActivity(intent)
            }
        } else {
            infoText.text = "NFC Not supported on this device.";
        }


        startAssentifySdk();
    }

    /**PASSPORT**/
    fun startAssentifySdk() {
        val assentifySdk: AssentifySdk = AssentifySdkObject.getAssentifySdkObject();
         scanPassport = assentifySdk.startScanPassport(
            this@ScanPassportActivity,
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
        Log.e("Events Here Scan Passport Page","onComplete")
            //infoText.visibility = View.GONE
            Log.e("onComplete",dataModel.passportExtractedModel.toString())
            dataModel.passportExtractedModel!!.extractedData?.let { ExtractedModel.setExtractedModel(it) };
            dataModel.passportExtractedModel!!.outputProperties?.let { OutputPropertiesModel.setOutputPropertiesModel(it) };


        dataModel.passportExtractedModel!!.outputProperties!!.forEach { t, u ->

            Log.e("Events Here Scan Passport Page", "outputProperties Key" + t + " Value "+ u  )

        }
        dataModel.passportExtractedModel!!.transformedProperties!!.forEach { t, u ->

            Log.e("Events Here Scan Passport Page", "transformedProperties Key" + t + " Value "+ u  )

        }
        passportResponseModel = dataModel;
        scanPassport.stopScanning();
    }

    override fun onDocumentCaptured(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onDocumentCaptured")
    }

    override fun onDocumentCropped(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onDocumentCropped")
    }


    override fun onError(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onError")
    }

    override fun onFaceDetected(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onFaceDetected")
    }

    override fun onFaceExtracted(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onFaceExtracted")
    }

    override fun onLivenessUpdate(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onLivenessUpdate")
    }

    override fun onMrzDetected(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onMrzDetected")
    }

    override fun onMrzExtracted(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onMrzExtracted")
    }

    override fun onNoFaceDetected(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onNoFaceDetected")
    }

    override fun onNoMrzDetected(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onNoMrzDetected")
    }

    override fun onQualityCheckAvailable(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onQualityCheckAvailable")
    }

    override fun onRetry(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onRetry")
    }

    override fun onSend() {
        Log.e("Events Here Scan Passport Page","onSend")
        infoText.visibility = View.VISIBLE
    }

    override fun onStatusUpdated(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onStatusUpdated")
    }

    override fun onUpdated(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onUpdated")
    }

    override fun onUploadFailed(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan Passport Page","onUploadFailed")
    }

    override fun onEnvironmentalConditionsChange(
        brightnessEvents: BrightnessEvents,
        motion: MotionType,
        zoom: ZoomType
    ) {
    }


    /** NFC **/


    override fun onResume() {
        super.onResume()
        val adapter = NfcAdapter.getDefaultAdapter(this)
        if (adapter != null) {
            val intent = Intent(applicationContext, this.javaClass)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
            val filter = arrayOf(arrayOf(ConstantsValues.NfcTechTag))
            adapter.enableForegroundDispatch(this, pendingIntent, null, filter)
        }
    }

    override fun onPause() {
        super.onPause()
        val adapter = NfcAdapter.getDefaultAdapter(this)
        adapter?.disableForegroundDispatch(this)
    }

    public override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (passportResponseModel != null) {
            scanNfc.onActivityNewIntent(intent = intent, dataModel = passportResponseModel!!)
        }
    }


    override fun onStartNfcScan() {
        Log.e(
            "Events Here",
            "onStartNfcScan"
        )
    }

    override fun onCompleteNfcScan(dataModel: PassportResponseModel) {
        NfcFaceUrlModel.setUrl(dataModel.passportExtractedModel?.faces?.get(0)!!)
        Log.e(
            "Events Here",
            dataModel.passportExtractedModel?.outputProperties.toString()
        )
        Log.e(
            "Events Here",
            dataModel.passportExtractedModel?.transformedProperties.toString()
        )
        Log.e(
            "Events Here",
            dataModel.passportExtractedModel?.extractedData.toString()
        )
        Log.e(
            "Events Here",
            dataModel.passportExtractedModel?.faces.toString()
        )
        val intent = Intent(this, NavToFace::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("image",dataModel.passportExtractedModel!!.imageUrl)
        startActivity(intent)


    }

    override fun onErrorNfcScan(dataModel: PassportResponseModel, message: String) {
        Log.e(
            "Events Here",
            "onErrorNfcScan"
        )
    }



}