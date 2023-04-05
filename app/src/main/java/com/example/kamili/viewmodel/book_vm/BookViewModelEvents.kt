package com.example.kamili.viewmodel.book_vm
sealed class BookViewModelEvents{
    data class OnHouseSizeClick(val house: Int ): BookViewModelEvents()
    data class OnServiceSelected(val service : Int ): BookViewModelEvents()
    data class OnDateSelected(val date : String ): BookViewModelEvents()
    data class OnTimeSelected(val time : String ): BookViewModelEvents()
    data class OnLocationInsert(val location: String): BookViewModelEvents()
    data class OnAdditionalInsert(val info: String): BookViewModelEvents()
    object OnPendingClicked: BookViewModelEvents()
    object OnCompletedClicked: BookViewModelEvents()
    object OnSubmitInitiated: BookViewModelEvents()
    data class  onReviewScoreSelected(val score: Int): BookViewModelEvents()
    data class  onReviewComment(val comment: String): BookViewModelEvents()
    object onReviewSubmitted: BookViewModelEvents()
}
