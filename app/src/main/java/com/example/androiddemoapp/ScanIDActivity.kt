package  com.example.androiddemoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.assentify.sdk.AssentifySdk
import com.assentify.sdk.Core.Constants.BrightnessEvents
import com.assentify.sdk.Core.Constants.Language
import com.assentify.sdk.Core.Constants.MotionType
import com.assentify.sdk.Core.Constants.ZoomType
import com.assentify.sdk.ExtractedModel
import com.assentify.sdk.FaceMatch.FaceMatchCallback
import com.assentify.sdk.Models.BaseResponseDataModel
import com.assentify.sdk.RemoteClient.Models.KycDocumentDetails
import com.assentify.sdk.ScanIDCard.IDCardCallback
import com.assentify.sdk.ScanIDCard.IDExtractedModel
import com.assentify.sdk.ScanIDCard.IDResponseModel
import com.assentify.sdk.ScanIDCard.ScanIDCard

class ScanIDActivity : AppCompatActivity(), IDCardCallback {
    var idExtractedModel: IDExtractedModel? = null;
    private lateinit var scanID:ScanIDCard;
    val outputPropertiesValues: MutableMap<String, String> = mutableMapOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        startAssentifySdk();
    }

    /** ID **/
    fun startAssentifySdk() {
        var data: List<KycDocumentDetails> = listOf(
            KycDocumentDetails(
                name = "",
                order = 0,
                templateProcessingKeyInformation = "75b683bb-eb81-4965-b3f0-c5e5054865e7",
                templateSpecimen = ""
            ),
            KycDocumentDetails(
                name = "",
                order = 1,
                templateProcessingKeyInformation = "eae46fac-1763-4d31-9acc-c38d29fe56e4",
                templateSpecimen = ""
            ),
        )
        val assentifySdk: AssentifySdk = AssentifySdkObject.getAssentifySdkObject();
         scanID = assentifySdk.startScanIDCard(
            this@ScanIDActivity,// This activity implemented from from IDCardCallback
            data, // List<KycDocumentDetails> || Your selected template ||,
            Language.English ,// Optional the default is the doc language
        );
        var fragmentManager = supportFragmentManager
        var transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, scanID)
        transaction.addToBackStack(null) // Optional: Adds the transaction to the back stack
        transaction.commit()

    }

    override fun onCardDetected(dataModel: BaseResponseDataModel) {

    }

    override fun onClipPreparationComplete(dataModel: BaseResponseDataModel) {

    }



    var image = "";
    override fun onComplete(dataModel: IDResponseModel, order: Int) {
        Log.e("Events Here Scan ID", "onComplete" )
        dataModel.iDExtractedModel!!.outputProperties!!.forEach { t, u ->

            Log.e("Events Here Scan ID Page", "outputProperties Key" + t + " Value "+ u  )

        }
        dataModel.iDExtractedModel!!.transformedProperties!!.forEach { t, u ->

            Log.e("Events Here Scan ID Page", "transformedProperties Key" + t + " Value "+ u  )

        }
        Log.e("Events Here Scan ID Page", "______________________${order}___________________________" )
        runOnUiThread {
            if (order == 0) {
                image =  dataModel.iDExtractedModel!!.imageUrl!!;
                dataModel.iDExtractedModel!!.extractedData?.let { ExtractedModel.setExtractedModel(it) };
               outputPropertiesValues.putAll(convertMapToFilteredStringMap(dataModel.iDExtractedModel!!.outputProperties!!))
            }
            if (order == 1) {
                outputPropertiesValues.putAll(convertMapToFilteredStringMap(dataModel.iDExtractedModel!!.outputProperties!!))
                outputPropertiesValues?.let {
                    OutputPropertiesModel.setOutputPropertiesModel(
                        it
                    )
                };
                scanID.stopScanning();
                val intent = Intent(this, NavToFace::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("image", image)
                startActivity(intent)
            }
        }
    }


    override fun onDocumentCaptured(dataModel: BaseResponseDataModel) {

    }

    override fun onDocumentCropped(dataModel: BaseResponseDataModel) {

    }



    override fun onError(dataModel: BaseResponseDataModel) {
        Log.e("Events Here Scan ID", "onError" )
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
        Log.e("Events Here Scan ID", "onRetry" )
    }

    override fun onSend() {
        Log.e("Events Here Scan ID", "onSend" )
    }

    override fun onStatusUpdated(dataModel: BaseResponseDataModel) {

    }

    override fun onUpdated(dataModel: BaseResponseDataModel) {

    }

    override fun onUploadFailed(dataModel: BaseResponseDataModel) {

    }

    override fun onWrongTemplate(dataModel: BaseResponseDataModel) {

    }

    fun convertMapToFilteredStringMap(originalMap: Map<String, Any>): Map<String, String> {
        return originalMap.filterValues { it is String }.mapValues { it.value as String }
    }

    override fun onEnvironmentalConditionsChange(
        brightnessEvents: BrightnessEvents,
        motion: MotionType,
        zoom: ZoomType
    ) {
    }
}