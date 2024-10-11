package  com.example.androiddemoapp

import FaceKeys
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.assentify.sdk.AssentifySdk
import com.assentify.sdk.RemoteClient.Models.StepDefinitions
import com.assentify.sdk.RemoteClient.Models.SubmitRequestModel
import com.assentify.sdk.SubmitData.SubmitDataCallback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResultActivity : AppCompatActivity() ,SubmitDataCallback {
    private lateinit var componentList: ListView
    private lateinit var componentImagesList: ListView
    private lateinit var imageView: ImageView
    private lateinit var percentageMatch: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        /** Submit List **/
        val assentifySdk: AssentifySdk = AssentifySdkObject.getAssentifySdkObject();
        AssentifySdkObject.clearAssentifySdkObject();



        val steps: List<StepDefinitions>? = StepDefinitionsModel.getStepDefinitionsModel();
        val outputPropertiesModel = OutputPropertiesModel.getOutputPropertiesModel();
        val outputPropertiesModelFace = OutputPropertiesModelFace.getOutputPropertiesModel();

        var wrapUp: SubmitRequestModel? = null;
        var blockLoader: SubmitRequestModel? = null;
        var sharedStepDefinitionsStepDoc: SubmitRequestModel? = null;
        var sharedStepDefinitionsStepFace: SubmitRequestModel? = null;

        steps!!.forEach { item ->

            /** DocumentCapture **/
            if (item.stepDefinition == StepsName.DocumentCapture) {
                val values: MutableMap<String, String> = mutableMapOf()
                outputPropertiesModel.forEach { (key, value) ->
                    if(value.toString().isNotEmpty()){
                        values.put(key,value.toString())
                    }
                }

                sharedStepDefinitionsStepDoc = SubmitRequestModel(
                    item.stepId, StepsName.DocumentCapture,
                    values
                );
            }
            /** FaceMatch **/
            if (item.stepDefinition == StepsName.FaceMatch) {
                val values: MutableMap<String, String> = mutableMapOf()
                outputPropertiesModelFace.forEach { (key, value) ->
                    if(value.toString().isNotEmpty()){
                        values.put(key,value.toString())
                    }
                }
                sharedStepDefinitionsStepFace = SubmitRequestModel(
                    item.stepId, StepsName.FaceMatch,
                    values
                );
            }

            /** WrapUp **/
            if (item.stepDefinition == StepsName.WrapUp) {
                val values: MutableMap<String, String> = mutableMapOf()
                item.outputProperties.forEach { property ->
                    if (property.key.contains(WrapUpKeys.TimeEnded)) {
                        values.put(property.key, getCurrentDateTime())
                    }
                }
                wrapUp = SubmitRequestModel(
                    item.stepId, StepsName.WrapUp,
                    values
                );

            }

            /** BlockLoader **/

            if (item.stepDefinition == StepsName.BlockLoader) {
                val values: MutableMap<String, String> = mutableMapOf()
                item.outputProperties.forEach { property ->
                    if (property.key.contains(BlockLoaderKeys.TimeStarted)) {
                        values.put(property.key, getCurrentDateTime())
                    }
                    if (property.key.contains(BlockLoaderKeys.DeviceName)) {
                        values.put(property.key, "INTER YOUR DEVICE NAME HERE")
                    }
                    if (property.key.contains(BlockLoaderKeys.Application)) {
                        values.put(property.key, "INTER YOUR APPLICATION HERE")
                    }
                    if (property.key.contains(BlockLoaderKeys.FlowName)) {
                        values.put(property.key, "INTER YOUR FLOW NAME HERE")
                    }
                    if (property.key.contains(BlockLoaderKeys.InstanceHash)) {
                        values.put(property.key, "INTER YOUR INSTANCE HASH HERE")
                    }
                    if (property.key.contains(BlockLoaderKeys.UserAgent)) {
                        values.put(property.key, "INTER YOUR USER AGENT HERE")
                    }
                    if (property.key.contains(BlockLoaderKeys.InteractionID)) {
                        values.put(property.key, "INTER YOUR INTERACTION ID HERE")
                    }
                    if (property.key.contains(BlockLoaderKeys.userID)) {
                        values.put(property.key, "111")
                    }
                    if (property.key.contains(BlockLoaderKeys.salary)) {
                        values.put(property.key, "1000")
                    }
                }
                blockLoader = SubmitRequestModel(
                    item.stepId, StepsName.BlockLoader,
                    values
                );
            }
        }

        val listData: MutableList<SubmitRequestModel> = mutableListOf()
        listData.add(sharedStepDefinitionsStepDoc!!)
        listData.add(sharedStepDefinitionsStepFace!!)
        listData.add(wrapUp!!)
        listData.add(blockLoader!!)


        assentifySdk.startSubmitData(this, /// This activity implemented from from SubmitDataCallback
            listData  //  Your data
        )
        /** End **/
    }

    override fun onSubmitError(message: String) {
        Log.e("Events Here onSubmitError",message)
    }

    override fun onSubmitSuccess(message: String) {
        Log.e("Events Here ","Done" + message)
        runOnUiThread {
            Toast.makeText(this, "Data Submitted Successfully", Toast.LENGTH_SHORT).show()

        }
    }

    fun getCurrentDateTime(): String {
        val currentDate = Date()

        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

        return dateFormat.format(currentDate)
    }
}