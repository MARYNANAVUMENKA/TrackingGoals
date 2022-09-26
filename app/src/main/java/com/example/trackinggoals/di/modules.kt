package com.example.trackinggoals.di

import android.app.Application
import androidx.room.Room
import com.example.trackinggoals.data.api.QuoteApi
import com.example.trackinggoals.data.repositories.RoomGoalsRepository
import com.example.trackinggoals.data.repositories.RoomNoteRepository
import com.example.trackinggoals.data.repositories.RoomQuoteRepository
import com.example.trackinggoals.data.db.AppDatabase
import com.example.trackinggoals.data.repositories.GoalsRepository
import com.example.trackinggoals.data.repositories.NoteRepository
import com.example.trackinggoals.data.repositories.QuoteRepository
import com.example.trackinggoals.presentation.constructor.GoalsConstructorFragment
import com.example.trackinggoals.presentation.constructor.GoalsConstructorViewModel
import com.example.trackinggoals.presentation.incoming.IncomingFragment
import com.example.trackinggoals.presentation.incoming.IncomingViewModel
import com.example.trackinggoals.presentation.tab.goals.list.achieved.GoalsListAchievedFragment
import com.example.trackinggoals.presentation.tab.goals.list.achieved.GoalsListAchievedViewModel
import com.example.trackinggoals.presentation.tab.goals.list.active.GoalsListActiveFragment
import com.example.trackinggoals.presentation.tab.goals.list.active.GoalsListActiveViewModel
import com.example.trackinggoals.presentation.tab.goals.start.GoalsStartFragment
import com.example.trackinggoals.presentation.tab.goals.start.GoalsStartViewModel
import com.example.trackinggoals.presentation.tab.motivation.MotivationFragment
import com.example.trackinggoals.presentation.tab.motivation.MotivationViewModel
import com.example.trackinggoals.presentation.tab.note.NoteListFragment
import com.example.trackinggoals.presentation.tab.note.NoteListViewModel
import com.example.trackinggoals.domain.usecases.goals.*
import com.example.trackinggoals.domain.usecases.goals.GetAllActiveGoalsForIncoming
import com.example.trackinggoals.domain.usecases.note.*
import com.example.trackinggoals.domain.usecases.quote.LoadQuotesInBackgroundUseCase
import com.example.trackinggoals.domain.usecases.quote.LoadQuotesUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModules = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "database.db")
            .build()
    }

    fun provideGoalsDao(database: AppDatabase) = database.getGoalsDao()
    fun provideNoteDao(database: AppDatabase) = database.getNoteDao()
    fun provideQuotesDao(database: AppDatabase) = database.getQuotesDao()


    single { provideDatabase(androidApplication()) }
    single { provideGoalsDao(get()) }
    single { provideNoteDao(get()) }
    single { provideQuotesDao(get()) }
}

val apiModules = module {

    single { QuoteApi.quoteApi }
}
val repositoryModules = module {

    single<NoteRepository> {
        RoomNoteRepository(
            noteDao = get()
        )
    }

    single<GoalsRepository> {
        RoomGoalsRepository(
            goalsDao = get()
        )
    }

    single<QuoteRepository> {
        RoomQuoteRepository(
            api = get(),
            quoteDao = get()
        )
    }
}

val applicationModules = module {

    factory { GetIdGoalsUseCase(get()) }
    factory { GetListAchievedGoalsUseCase(get()) }
    factory { GetListActiveGoalsUseCase(get()) }
    factory { GetListGoalsUseCase(get()) }
    factory { GetTextGoalsUseCase(get()) }
    factory { RemoveGoalsUseCase(get()) }
    factory { SaveGoalsUseCase(get()) }
    factory { UpdateCriterionGoalsUseCase(get()) }
    factory { UpdateDataExecutionGoalsUseCase(get()) }
    factory { UpdateIsActiveGoalsUseCase(get()) }
    factory { UpdatePhotoGoalsUseCase(get()) }
    factory { UpdateProgressGoalsUseCase(get(), get()) }
    factory { UpdateProgressWithoutNewResultGoalsUseCase(get()) }
    factory { UpdateQuantityGoalsUseCase(get()) }
    factory { UpdateTextGoalsUseCase(get()) }
    factory { UpdateUnitGoalsUseCase(get()) }
    factory { GetAllActiveGoalsForIncoming(get()) }

    factory { DeleteIncomingNoteUseCase(get()) }
    factory { GetCurrentDayNoteUseCase(get()) }
    factory { GetCurrentMonthYearNoteUseCase(get()) }
    factory { GetIncomingUseCase(get()) }
    factory { GetListCurrentMonthYearNoteUseCase(get()) }
    factory { SaveNoteWithIncomingFromGoalsUseCase(get()) }
    factory { SaveNoteWithIncomingUseCase(get()) }
    factory { UpdateIncomingNoteUseCase(get()) }
    factory { UpdateQuantityNoteUseCase(get()) }
    factory { UpdateTextGoalsNoteUseCase(get()) }

    factory { LoadQuotesUseCase(get()) }
    factory { LoadQuotesInBackgroundUseCase(get()) }

    fragment { NoteListFragment() }
    fragment { MotivationFragment() }
    fragment { GoalsStartFragment() }
    fragment { GoalsListActiveFragment() }
    fragment { GoalsListAchievedFragment() }
    fragment { IncomingFragment() }
    fragment { GoalsConstructorFragment() }
}


val viewModelsModules = module {

    viewModel {
        NoteListViewModel(
            getListCurrentMonthYearNoteUseCase = get(),
            getCurrentMonthYearNoteUseCase = get(),
            getCurrentDayNoteUseCase = get(),
        )
    }

    viewModel {
        MotivationViewModel(
            loadQuotesUseCase = get()
        )
    }

    viewModel {
        GoalsStartViewModel(
            getListActiveGoalsUseCase = get()
        )
    }

    viewModel {
        GoalsListActiveViewModel(
            getListActiveGoalsUseCase = get(),
            updateIsActiveGoalsUseCase = get(),
            removeGoalsUseCase = get(),
            updateProgressGoalsUseCase = get(),
        )
    }
    viewModel {
        GoalsListAchievedViewModel(
            getListAchievedGoalsUseCase = get(),
            updateIsActiveGoalsUseCase = get(),
            removeGoalsUseCase = get(),
            updateProgressGoalsUseCase = get(),
        )
    }

    viewModel {
        IncomingViewModel(
            getAllGoalsUseCase = get(),
            getTextGoalsUseCase = get(),
            getIncomingUseCase = get(),
            updateIncomingNoteUseCase = get(),
            updateProgressWithoutNewResultGoalsUseCase = get(),
            updateQuantityNoteUseCase = get(),
            updateTextGoalsNoteUseCase = get(),
            deleteIncomingNoteUseCase = get(),
        )
    }
    viewModel {
        GoalsConstructorViewModel(
            getIdGoalsUseCase = get(),
            saveGoalsUseCase = get(),
            updateTextGoalsUseCase = get(),
            updatePhotoGoalsUseCase = get(),
            updateDataExecutionGoalsUseCase = get(),
            updateQuantityGoalsUseCase = get(),
            updateUnitGoalsUseCase = get(),
            updateCriterionGoalsUseCase = get(),
        )
    }
}