package com.example.kamili.viewmodel.book_vm

import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.IntegerRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kamili.data.BookingData
import com.example.kamili.data.HouseSizeData
import com.example.kamili.data.ServicesData
import com.example.kamili.database.bookings.BookingsRepository
import com.example.kamili.database.house_size.HousesRepository
import com.example.kamili.database.kamili_services.ServicesRepository
import com.example.kamili.database.kamili_services.ServicesRepositoryImpl
import com.example.kamili.network.KamiliApi
import com.example.kamili.utils.KamiliUiEvents
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.collections.List
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class BookViewModel @Inject constructor(
    private val retrofitInstance: KamiliApi,
    private val housesRepositoryImpl: HousesRepository,
    private val bookingsRepositoryImpl: BookingsRepository,
    private val servicesRepositoryImpl: ServicesRepository
) : ViewModel() {

    private val _vmToUi = Channel<KamiliUiEvents>()
    val uiFromVm = _vmToUi.receiveAsFlow()

    //house sizes
    private val _houseSizes = housesRepositoryImpl.getHouseSizes()
    val houseSizes = _houseSizes

    //    bookings list
    private val _bookings = bookingsRepositoryImpl.getBookings()
    val bookings = _bookings

    val housesizesRef = Firebase.firestore.collection("Housesizes")

    val db = Firebase.firestore
    val loggedInUser = FirebaseAuth.getInstance().currentUser

    init {
        getFirebaseHouseSizes()
        getFirebaseBookings()
    }


    //Booking status
    private val _bookingStatus = mutableStateOf("Pending")
    val bookingStatus = _bookingStatus

    //location
    private val _location = mutableStateOf("")
    val location = _location

    //additional info
    private val _info = mutableStateOf("")
    val info = _info


    private val _selectedHouse = mutableStateOf<Int>(0)
    val selectedHouse = _selectedHouse

    //services
    private val _services = mutableStateListOf<Int>()
    val services = _services

    // Date and Time
    private val _pickedDate = mutableStateOf("")
    val pickedDate = _pickedDate
    private val _pickedTime = mutableStateOf("")
    val pickedTime = _pickedTime


    val mCalendar = Calendar.getInstance()
    val mYear: Int = mCalendar.get(Calendar.YEAR)
    val mMonth: Int = mCalendar.get(Calendar.MONTH)
    val mDay: Int = mCalendar.get(Calendar.DAY_OF_MONTH)
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]


    fun receiveUIEvents(event: BookViewModelEvents) {
        when (event) {
            is BookViewModelEvents.OnHouseSizeClick -> {
                selectHouseSize(event.house)
            }
            is BookViewModelEvents.OnServiceSelected -> {
                selectService(event.service)
            }
            is BookViewModelEvents.OnDateSelected -> {
                setDate(event.date)
            }
            is BookViewModelEvents.OnTimeSelected -> {
                setTime(event.time)
            }
            is BookViewModelEvents.OnLocationInsert -> {
                updateLocation(event.location)
            }
            is BookViewModelEvents.OnAdditionalInsert -> {
                updateAdditional(event.info)
            }
            is BookViewModelEvents.OnPendingClicked -> {
                updateBookingStatus("Pending")
            }
            is BookViewModelEvents.OnCompletedClicked -> {
                updateBookingStatus("Completed")
            }
            is BookViewModelEvents.OnSubmitInitiated -> {
                submitBooking()
            }
        }
    }

    private fun getBookings() {
        viewModelScope.launch {
            try {
                val remoteBookings = retrofitInstance.getKamiliBookings("a98b51263a")
                remoteBookings.let {
                    it
                    for (item in it.message) {
                        bookingsRepositoryImpl.insertBooking(item)
                    }
                }
            } catch (e: IOException) {
                println("IOo issue")
            } catch (e: HttpException) {
                println("HTTP issue")
            }

        }
    }


    private fun getFirebaseBookings() {
        db.collection("Bookings").whereEqualTo("client", loggedInUser!!.email).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val additional_info = document.data["additional_info"].toString()
                    val client = document.data["client"].toString()
                    val date = document.data["date"].toString()
                    val house_size = document.data["house_size"].toString()
                    val id = document.reference.id
//                        Integer.parseInt(document.data["id"].toString())
                    val location = document.data["location"].toString()
                    val price = Integer.parseInt(document.data["price"].toString())
                    val review_comment = document.data["review_comment"].toString()
                    val review_score =
                        Integer.parseInt(document.data["review_score"].toString())
                    val status = document.data["status"].toString()
                    val time = document.data["time"].toString()
                    var services =
                        getServiceByIdFromRoom(document.data["services"] as List<Int>)


                    viewModelScope.launch {
//                        delete before adding
                        bookingsRepositoryImpl.deleteBookings()
                        bookingsRepositoryImpl.insertBooking(
                            BookingData(
                                id,
                                client,
                                house_size,
                                location,
                                services,
                                date,
                                time,
                                additional_info,
                                status,
                                price,
                                review_score,
                                review_comment
                            )


                        )
                    }
                }
            }

    }


    fun getServiceByIdFromRoom(ids: List<Int>): MutableList<ServicesData> {
        var myServices = mutableListOf<ServicesData>()
        for (id in ids) {
            viewModelScope.launch {
                servicesRepositoryImpl.getServiceById(id)?.let {
                    myServices.add(
                        it
                    )
                }
            }
        }
        return myServices
    }


    fun getHouseSizes() {
        viewModelScope.launch {
            try {
                val remoteHouses = retrofitInstance.getKamiliHouseSizes()
                remoteHouses.let { it ->
                    for (item in it.message) {
                        housesRepositoryImpl.insertHouseSize(item)

                    }
                }

            } catch (e: IOException) {
                println("IO issue")
            } catch (e: HttpException) {
                println("HTTP issue")
            }

        }
    }

    private fun getFirebaseHouseSizes() {
        housesizesRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                sendUiEvent(KamiliUiEvents.ShowSnackbar(message = firebaseFirestoreException.message.toString()))
            }
            querySnapshot?.let {
                for (document in it) {
                    viewModelScope.launch {
                        val title = document.data["title"].toString()
                        val price = Integer.parseInt(document.data["price"].toString())
                        val id = Integer.parseInt(document.data["id"].toString())
                        housesRepositoryImpl.insertHouseSize(
                            HouseSizeData(
                                title,
                                id, price
                            )
                        )
                    }
                }
            }
        }

    }


    fun updateLocation(value: String) {
        _location.value = value
    }

    fun updateAdditional(value: String) {
        _info.value = value
    }

    fun selectHouseSize(house: Int) {
        _selectedHouse.value = house
    }

    fun selectService(service: Int) {
        if (_services.contains(service)) {
            _services.remove(service)
        } else {
            _services.add(service)
        }
    }

    fun setDate(date: String) {
        _pickedDate.value = date
    }

    fun setTime(time: String) {
        _pickedTime.value = time
    }

    private fun updateBookingStatus(status: String) {
        _bookingStatus.value = status
    }


    private fun submitBooking() {

        if(selectedHouse.value == 0){
            sendUiEvent(KamiliUiEvents.ShowSnackbar("Please pick the size of your house!"))
        }
        else if(services.isEmpty()){
            sendUiEvent(KamiliUiEvents.ShowSnackbar("Please select at least one service!"))
        }
        else if(location.value == ""){
            sendUiEvent(KamiliUiEvents.ShowSnackbar("Please enter your location!"))
        }
        else if(pickedDate.value == ""){
            sendUiEvent(KamiliUiEvents.ShowSnackbar("Please pick a date!"))
        }
        else if(pickedTime.value == ""){
            sendUiEvent(KamiliUiEvents.ShowSnackbar("Please pick a time!"))
        }
        else{

            var price = 0
            viewModelScope.launch {
                runBlocking {
                    price = housesRepositoryImpl.getHouseSizeById(selectedHouse.value)?.price!!
                }
            }

            val booking = hashMapOf(
                "additional_info" to info.value ,
                "client" to loggedInUser!!.email,
                "date" to pickedDate.value,
                "house_size" to selectedHouse.value,
                "id" to 17,
                "location" to location.value,
                "price" to price,
                "review_comment" to "",
                "review_score" to 0,
                "services" to services,
                "status" to "Pending",
                "time" to pickedTime.value
            )

            db.collection("Bookings")
                .add(booking)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
            sendUiEvent(KamiliUiEvents.ShowSnackbar("Your booking has been submitted. You will be contacted in a short while. Thank you!"))

        }

    }


    private fun sendUiEvent(event: KamiliUiEvents) {
        viewModelScope.launch {
            _vmToUi.send(event)
        }

    }
}