package com.example.presentation.pupzlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.RxSchedulerRule
import com.example.data.FakeBreedsRepository
import com.example.domain.Breed
import com.example.domain.GetBreedsUsecase
import com.example.domain.Subbreed
import com.example.presentation.EVENT_HANDLER
import com.google.common.truth.Truth.assertThat
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test

class PupzListViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val rxSchedulerRule = RxSchedulerRule()

    private val fakeBreedsRepository = FakeBreedsRepository()
    private val getBreedsUsecase = GetBreedsUsecase(fakeBreedsRepository)

    private val breed1 = Breed(
            name = "Retriever",
            subbreeds = listOf(Subbreed("Golden Retriever"), Subbreed("Labrador Retriever"))
    )

    private val breed1AsUiModel = listOf(
            PupzListItemUiModel.Breed("Retriever", EVENT_HANDLER),
            PupzListItemUiModel.Subbreed("Golden Retriever", EVENT_HANDLER),
            PupzListItemUiModel.Subbreed("Labrador Retriever", EVENT_HANDLER)
    )

    @Test
    fun `fetches list of breeds on initialization`() {
        fakeBreedsRepository.getBreeds = Single.just(listOf(breed1))
        val viewModel = initViewModel()
        assertThat(viewModel.breeds.value).isEqualTo(breed1AsUiModel)
    }

    /**
     * Since [PupzListViewModel] fetches data on initialization, to test the initial LiveData value
     * we have to initialize the VM after we've had a chance to set our test fixtures.
     *
     * Further, the VM uses Android RxSchedulers which we want to swap out in tests to get
     * deterministic results. We do this with the rules at the top of this file, which will be run
     * after the fields are initialized, which is another reason we can't initialize the VM as a
     * field.
     */
    private fun initViewModel(): PupzListViewModel {
        return PupzListViewModel(getBreedsUsecase)
    }
}