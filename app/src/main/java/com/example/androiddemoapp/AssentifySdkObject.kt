package  com.example.androiddemoapp

import com.assentify.sdk.AssentifySdk
import com.assentify.sdk.RemoteClient.Models.StepDefinitions

object AssentifySdkObject {
    private  var assentifySdk: AssentifySdk? = null
    fun setAssentifySdkObject(assentifySdk: AssentifySdk) {
        this.assentifySdk = assentifySdk;
    }

    fun getAssentifySdkObject(): AssentifySdk {
        return this.assentifySdk!!
    }

    fun clearAssentifySdkObject() {
        this.assentifySdk = null
    }
}

object OutputPropertiesModel {
    private lateinit var outputPropertiesModel:Map<String, Any>;
    fun setOutputPropertiesModel(extractedModel: Map<String, Any>) {
        this.outputPropertiesModel = extractedModel;
    }

    fun getOutputPropertiesModel(): Map<String, Any> {
        return this.outputPropertiesModel
    }

}

object OutputPropertiesModelFace {
    private lateinit var outputPropertiesModel:Map<String, Any>;
    fun setOutputPropertiesModel(extractedModel: Map<String, Any>) {
        this.outputPropertiesModel = extractedModel;
    }

    fun getOutputPropertiesModel(): Map<String, Any> {
        return this.outputPropertiesModel
    }

}


object ExtractedModel {
    private lateinit var extractedModel:Map<String, Any>;
    fun setExtractedModel(extractedModel: Map<String, Any>) {
        this.extractedModel = extractedModel;
    }

    fun getExtractedModel(): Map<String, Any> {
        return this.extractedModel
    }

}



object StepDefinitionsModel {
    private lateinit var stepDefinitions: List<StepDefinitions>;
    fun setStepDefinitionsModel(stepDefinitions: List<StepDefinitions>) {
        this.stepDefinitions = stepDefinitions;
    }

    fun getStepDefinitionsModel() : List<StepDefinitions>{
        return this.stepDefinitions
    }

}