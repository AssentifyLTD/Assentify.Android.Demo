import org.json.JSONObject

/** Passport **/
data class PassportExtractedModel(
    val outputProperties: Map<String, Any>?,
    val extractedData: Map<String, Any>?,
    val imageUrl: String?,
    val faces: List<String>?
) {
    companion object {
        fun fromJson(responseData: String?): PassportExtractedModel {
            val response = JSONObject(responseData)
            val faces = mutableListOf<String>()

            response.optJSONArray("faces")?.let { facesArray ->
                for (i in 0 until facesArray.length()) {
                    val face = facesArray.getJSONObject(i)
                    face.optString("FaceUrl")?.let { faceUrl ->
                        faces.add(faceUrl)
                    }
                }
            }

            val imageUrl = response.optString("ImageUrl")
            val outputProperties = mutableMapOf<String, Any>()
            val extractedData = mutableMapOf<String, Any>()

            response.optJSONObject("OutputProperties")?.let { properties ->
                properties.keys().forEach { key ->
                    val value = properties.get(key)
                    outputProperties[key] = value
                    val newKey = key.split("_").last()
                    extractedData[newKey] = value
                }
            }

            return PassportExtractedModel(
                outputProperties = outputProperties,
                extractedData = extractedData,
                imageUrl = imageUrl,
                faces = faces
            )
        }
    }
}

/** Other **/
data class OtherExtractedModel(
    val outputProperties: Map<String, Any>?,
    val extractedData: Map<String, Any>?,
    val imageUrl: String?,
    val faces: List<String>?
) {
    companion object {
        fun fromJson(responseData: String?): OtherExtractedModel {
            val response = JSONObject(responseData)
            val faces = mutableListOf<String>()

            response.optJSONArray("faces")?.let { facesArray ->
                for (i in 0 until facesArray.length()) {
                    val face = facesArray.getJSONObject(i)
                    face.optString("FaceUrl")?.let { faceUrl ->
                        faces.add(faceUrl)
                    }
                }
            }

            val imageUrl = response.optString("ImageUrl")
            val outputProperties = mutableMapOf<String, Any>()
            val extractedData = mutableMapOf<String, Any>()

            response.optJSONObject("OutputProperties")?.let { properties ->
                properties.keys().forEach { key ->
                    val value = properties.get(key)
                    outputProperties[key] = value
                    val newKey = key.split("_").last()
                    extractedData[newKey] = value
                }
            }

            return OtherExtractedModel(
                outputProperties = outputProperties,
                extractedData = extractedData,
                imageUrl = imageUrl,
                faces = faces
            )
        }
    }
}

/** ID **/
data class IDExtractedModel(
    val outputProperties: Map<String, Any>?,
    val extractedData: Map<String, Any>?,
    val idCardImageUrl: String?,
    val faces: List<String>?
) {
    companion object {
        fun fromJson(responseData: String?): IDExtractedModel {
            val response = JSONObject(responseData)
            val faces = mutableListOf<String>()

            response.optJSONArray("faces")?.let { facesArray ->
                for (i in 0 until facesArray.length()) {
                    val face = facesArray.getJSONObject(i)
                    face.optString("FaceUrl")?.let { faceUrl ->
                        faces.add(faceUrl)
                    }
                }
            }

            val idCardImageUrl = response.optString("IdCardImageUrl")
            val outputProperties = mutableMapOf<String, Any>()
            val extractedData = mutableMapOf<String, Any>()

            response.optJSONObject("OutputProperties")?.let { properties ->
                properties.keys().forEach { key ->
                    val value = properties.get(key)
                    outputProperties[key] = value
                    val newKey = key.split("_").last()
                    extractedData[newKey] = value
                }
            }

            return IDExtractedModel(
                outputProperties = outputProperties,
                extractedData = extractedData,
                idCardImageUrl = idCardImageUrl,
                faces = faces
            )
        }
    }
}

/** Face **/
data class FaceExtractedModel(
    val outputProperties: Map<String, Any>?,
    val extractedData: Map<String, Any>?,
    val baseImageFace: String?,
    val secondImageFace: String?,
    val percentageMatch: Double?,
    val isLive: Boolean?
) {
    companion object {
        fun fromJson(responseData: String?): FaceExtractedModel {
            val response = JSONObject(responseData)

            val baseImageFaceObject = response.getJSONObject("BaseImageFace")
            val secondImageFaceObject = response.getJSONObject("SecondImageFace")

            val baseImageFace = baseImageFaceObject.optString("FaceUrl")
            val secondImageFace = secondImageFaceObject.optString("FaceUrl")

            val percentageMatch = response.optDouble("PercentageMatch")
            val isLive = response.optBoolean("IsLive")

            val outputProperties = mutableMapOf<String, Any>()
            val extractedData = mutableMapOf<String, Any>()

            response.optJSONObject("OutputProperties")?.let { properties ->
                properties.keys().forEach { key ->
                    val value = properties.get(key)
                    outputProperties[key] = value
                    val newKey = key.split("_").last()
                    extractedData[newKey] = value
                }
            }




            return FaceExtractedModel(
                outputProperties = outputProperties,
                extractedData = extractedData,
                baseImageFace = baseImageFace,
                secondImageFace = secondImageFace,
                percentageMatch = percentageMatch,
                isLive = isLive
            )
        }
    }
}
