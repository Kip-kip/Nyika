//package com.example.kamili.datastore
//
//import SessionManagement
//import androidx.datastore.core.Serializer
//import kotlinx.serialization.SerializationException
//import kotlinx.serialization.json.Json
//import kotlinx.serialization.json.JsonNull.serializer
//import java.io.InputStream
//import java.io.OutputStream
//
//
//@Suppress("BlockingMethodInNonBlockingContext")
//object SessionManagementSerializer : Serializer<SessionManagement> {
//    override val defaultValue: SessionManagement
//        get() = SessionManagement()
//
//    override suspend fun readFrom(input: InputStream): SessionManagement {
//        return try {
//            Json.decodeFromString(
//                deserializer = SessionManagement.serializer(),
//                string = input.readBytes().decodeToString()
//            )
//        } catch (e: SerializationException) {
//            e.printStackTrace()
//            defaultValue
//        }
//    }
//
//    override suspend fun writeTo(t: SessionManagement, output: OutputStream) {
//        TODO("Not yet implemented")
//    }
//
//}