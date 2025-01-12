package dev.sakura.bank.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BinInfo(
    @SerialName("number") val number: NumberInfo? = null,
    @SerialName("scheme") val scheme: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("brand") val brand: String? = null,
    @SerialName("prepaid") val prepaid: Boolean? = null,
    @SerialName("country") val country: Country?,
    @SerialName("bank") val bank: Bank?,
)

@Serializable
data class NumberInfo(
    @SerialName("length") val length: Int? = null,
    @SerialName("luhn") val luhn: Boolean? = null,
)

@Serializable
data class Country(
    @SerialName("numeric") val numeric: String? = null,
    @SerialName("alpha2") val alpha2: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("emoji") val emoji: String? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("latitude") val latitude: Double? = null,
    @SerialName("longitude") val longitude: Double? = null,
)

@Serializable
data class Bank(
    @SerialName("name") val name: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("city") val city: String? = null,
)
