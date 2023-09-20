package com.kauproject.kausanhak.data.remote.response


import com.squareup.moshi.Json

data class SeoulPersonDataResponse(
    @field:Json(name = "SeoulRtd.citydata_ppltn")
    val seoulRtdCitydataPpltn: List<SeoulRtdCitydataPpltn?>?
)

data class SeoulRtdCitydataPpltn(
    @field:Json(name = "AREA_CD")
    val aREACD: String?,
    @field:Json(name = "AREA_CONGEST_LVL")
    val aREACONGESTLVL: String?,
    @field:Json(name = "AREA_CONGEST_MSG")
    val aREACONGESTMSG: String?,
    @field:Json(name = "AREA_NM")
    val aREANM: String?,
    @field:Json(name = "AREA_PPLTN_MAX")
    val aREAPPLTNMAX: String?,
    @field:Json(name = "AREA_PPLTN_MIN")
    val aREAPPLTNMIN: String?,
    @field:Json(name = "FCST_PPLTN")
    val fCSTPPLTN: List<FCSTPPLTN?>?,
    @field:Json(name = "FCST_YN")
    val fCSTYN: String?,
    @field:Json(name = "FEMALE_PPLTN_RATE")
    val fEMALEPPLTNRATE: String?,
    @field:Json(name = "MALE_PPLTN_RATE")
    val mALEPPLTNRATE: String?,
    @field:Json(name = "NON_RESNT_PPLTN_RATE")
    val nONRESNTPPLTNRATE: String?,
    @field:Json(name = "PPLTN_RATE_0")
    val pPLTNRATE0: String?,
    @field:Json(name = "PPLTN_RATE_10")
    val pPLTNRATE10: String?,
    @field:Json(name = "PPLTN_RATE_20")
    val pPLTNRATE20: String?,
    @field:Json(name = "PPLTN_RATE_30")
    val pPLTNRATE30: String?,
    @field:Json(name = "PPLTN_RATE_40")
    val pPLTNRATE40: String?,
    @field:Json(name = "PPLTN_RATE_50")
    val pPLTNRATE50: String?,
    @field:Json(name = "PPLTN_RATE_60")
    val pPLTNRATE60: String?,
    @field:Json(name = "PPLTN_RATE_70")
    val pPLTNRATE70: String?,
    @field:Json(name = "PPLTN_TIME")
    val pPLTNTIME: String?,
    @field:Json(name = "REPLACE_YN")
    val rEPLACEYN: String?,
    @field:Json(name = "RESNT_PPLTN_RATE")
    val rESNTPPLTNRATE: String?
)

data class FCSTPPLTN(
    @field:Json(name = "FCST_CONGEST_LVL")
    val fCSTCONGESTLVL: String?,
    @field:Json(name = "FCST_PPLTN_MAX")
    val fCSTPPLTNMAX: String?,
    @field:Json(name = "FCST_PPLTN_MIN")
    val fCSTPPLTNMIN: String?,
    @field:Json(name = "FCST_TIME")
    val fCSTTIME: String?
)