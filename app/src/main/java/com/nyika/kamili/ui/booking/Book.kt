package com.nyika.kamili.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyika.kamili.R
import com.nyika.kamili.ui.booking.BookingServices
import com.nyika.kamili.ui.booking.HouseSizes
import com.nyika.kamili.ui.booking.components.DateTimePicker
import com.nyika.kamili.ui.theme.KamiliDark
import com.nyika.kamili.ui.theme.KamiliGrayish
import com.nyika.kamili.utils.KamiliUiEvents
import com.nyika.kamili.utils.ui.SimpleAppBar
import com.nyika.kamili.viewmodel.book_vm.BookViewModel
import com.nyika.kamili.viewmodel.book_vm.BookViewModelEvents
import com.nyika.kamili.viewmodel.home_vm.HomeViewModel
import java.util.*

@Composable
fun Book(
    viewModel: BookViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    onPopBackStack: ()-> Unit
) {
    val context = LocalContext.current
    val houseSizes = viewModel.houseSizes.collectAsState(initial = emptyList())
    val theServices = homeViewModel.services.collectAsState(initial = emptyList())
    //  Date Picker Dialog
    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            viewModel.receiveUIEvents(BookViewModelEvents.OnDateSelected("$mDayOfMonth/${mMonth + 1}/$mYear"))
        }, viewModel.mYear, viewModel.mMonth, viewModel.mDay
    )

    // Creating a TimePicker dialog
    val mTimePickerDialog = TimePickerDialog(
        context,
        { _, mHour: Int, mMinute: Int ->
            viewModel.receiveUIEvents(BookViewModelEvents.OnTimeSelected("$mHour:$mMinute"))
        }, viewModel.mHour, viewModel.mMinute, false
    )

    LaunchedEffect(key1 = true) {
        viewModel.uiFromVm.collect() { event ->
            when (event) {
                is KamiliUiEvents.ShowSnackbar -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    val fontName = FontFamily(Font(R.font.raleway_regular))
    Column {


        SimpleAppBar(R.drawable.calendar,Color.Black,0.1f){
            onPopBackStack()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {


            Text(
                "What size is your house?",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliGrayish,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )


            HouseSizes(fontName, viewModel.selectedHouse.value,houseSizes.value) {
                it
                viewModel.receiveUIEvents(BookViewModelEvents.OnHouseSizeClick(it))
            }

            Text(
                "What services would you like?",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliGrayish,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )


            BookingServices(fontName, viewModel.services,theServices.value) {
                it
                viewModel.receiveUIEvents(BookViewModelEvents.OnServiceSelected(it))
            }

            Text(
                "Where are you located?",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliGrayish,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            TextField(
                value = viewModel.location.value,
                onValueChange = {
                    viewModel.receiveUIEvents(BookViewModelEvents.OnLocationInsert(it))
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp),
                        tint = KamiliDark
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = KamiliDark,
                    cursorColor = KamiliDark
                ),
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .scale(scaleY = 0.9F, scaleX = 1F),

                )

            DateTimePicker(
                fontName,
                viewModel.pickedDate.value,
                viewModel.pickedTime.value,
                { mDatePickerDialog.show() }) {
                mTimePickerDialog.show()
            }

            Text(
                "Additional Information",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W900,
                    fontFamily = fontName
                ),
                color = KamiliDark,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )



            OutlinedTextField(
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = KamiliDark,
                    focusedIndicatorColor = KamiliDark
                ),
                textStyle = TextStyle.Default.copy(fontFamily = fontName),
                value = viewModel.info.value,
                onValueChange = {
                    viewModel.receiveUIEvents(BookViewModelEvents.OnAdditionalInsert(it))

                },
                modifier = Modifier
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 4
            )
            Row(modifier = Modifier.padding(bottom = 50.dp)) {

                Button(
                    onClick = {
                        viewModel.receiveUIEvents(BookViewModelEvents.OnSubmitInitiated)

                    },
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = KamiliDark,
                    )
                ) {
                    Text(
                        "Submit Booking",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fontName
                        ),
                        color = Color.White,
                    )
                }
            }


        }

    }
}