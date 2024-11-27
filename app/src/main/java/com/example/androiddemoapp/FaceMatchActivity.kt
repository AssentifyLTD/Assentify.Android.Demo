package  com.example.androiddemoapp

import ImageToBase64Converter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.assentify.sdk.AssentifySdk
import com.assentify.sdk.Core.Constants.MotionType
import com.assentify.sdk.Core.Constants.ZoomType
import com.assentify.sdk.FaceMatch.FaceMatch
import com.assentify.sdk.FaceMatch.FaceMatchCallback
import com.assentify.sdk.FaceMatch.FaceResponseModel
import com.assentify.sdk.Models.BaseResponseDataModel
import com.assentify.sdk.ScanIDCard.IDCardCallback

class FaceMatchActivity : AppCompatActivity() , FaceMatchCallback {
    private lateinit var image: String;
    private lateinit var base64Image:String;
    private lateinit var infoText:TextView;
    private lateinit var face:FaceMatch;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        image = intent.getStringExtra("image")!!
         base64Image =
            ImageToBase64Converter().execute(image).get()
        Thread.sleep(1000)
        infoText = findViewById(R.id.infoTextTest)
        startAssentifySdk();
    }


    /** FACE NATCH **/
    fun startAssentifySdk() {
        val assentifySdk: AssentifySdk = AssentifySdkObject.getAssentifySdkObject();
        face = assentifySdk.startFaceMatch(
            this@FaceMatchActivity, // This activity implemented from from FaceMatchCallback
           base64Image , // Target  Image
            true // Show 3 - 2 - 1 Count Down before  start scanning
        );
        face.startScanning()
       var fragmentManager = supportFragmentManager
       var transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, face)
        transaction.addToBackStack(null) // Optional: Adds the transaction to the back stack
        transaction.commit()

    }

    override fun onCardDetected(dataModel: BaseResponseDataModel) {
        
    }

    override fun onClipPreparationComplete(dataModel: BaseResponseDataModel) {
        
    }

    override fun onComplete(dataModel: FaceResponseModel) {
        Log.e("Events Here Face Page",dataModel.toString())
        Log.e("Events Here Face Page", dataModel.faceExtractedModel!!.isLive!!.toString())
        Log.e("Events Here Face Page",dataModel.faceExtractedModel!!.secondImageFace!!)
        Log.e("Events Here Face Page",dataModel.faceExtractedModel!!.baseImageFace!!)
        Log.e("Events Here Face Page", dataModel.faceExtractedModel!!.percentageMatch!!.toString())
       // var faceExtractedModel = FaceExtractedModel.fromJson(dataModel.response);
        runOnUiThread{
            infoText.visibility = View.VISIBLE
        }

        face.stopScanning();
        val intent = Intent(this, ResultActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("baseImageFace",dataModel.faceExtractedModel!!.baseImageFace)
        intent.putExtra("secondImageFace",dataModel.faceExtractedModel!!.secondImageFace)
        intent.putExtra("percentageMatch",dataModel.faceExtractedModel!!.percentageMatch)
        intent.putExtra("isLive",dataModel.faceExtractedModel!!.isLive)
        startActivity(intent)
        finish();

        dataModel.faceExtractedModel!!.outputProperties?.let { OutputPropertiesModelFace.setOutputPropertiesModel(it) };


    }

    override fun onDocumentCaptured(dataModel: BaseResponseDataModel) {
        
    }

    override fun onDocumentCropped(dataModel: BaseResponseDataModel) {
        
    }

    override fun onEnvironmentalConditionsChange(brightness: Double, motion: MotionType) {
        runOnUiThread{
            infoText.visibility = View.VISIBLE
        }
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
        Log.e("Events Here Face Page","onSend")
    }

    override fun onStatusUpdated(dataModel: BaseResponseDataModel) {
        
    }

    override fun onUpdated(dataModel: BaseResponseDataModel) {
        
    }

    override fun onUploadFailed(dataModel: BaseResponseDataModel) {
        
    }

}