package com.hientran.wallpaper.mockmodule

import androidx.paging.PagingData
import com.hientran.wallpaper.R
import com.hientran.wallpaper.data.model.PhotoCollection
import com.hientran.wallpaper.data.model.WallpaperPhoto
import com.hientran.wallpaper.data.model.WallpaperUrl
import com.hientran.wallpaper.domain.repositories.PexelsRepository
import com.hientran.wallpaper.presentation.ui.home.HomeItemView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakePexelRepositoryImpl: PexelsRepository {
    private val sampleUrls = WallpaperUrl(portrait = "https://images.pexels.com/photos/13918727/pexels-photo-13918727.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800")

    override fun search(keyword: String): Flow<PagingData<WallpaperPhoto>> {
        return flowOf(PagingData.from(listOf(WallpaperPhoto())))
    }

    override fun getHomeData(): Flow<PagingData<HomeItemView>> {
        return flowOf(
            PagingData.from(
                listOf(
                    HomeItemView.SearchBar(),
                    HomeItemView.Title(R.string.home_best_of_month),
                    HomeItemView.CuratedPhotos(
                        photos = listOf(WallpaperPhoto(1, urls = sampleUrls))
                    ),
                    HomeItemView.Title(R.string.home_categories),
                    HomeItemView.Collection(PhotoCollection(title = "A", photosCount = 1)),
                    HomeItemView.Collection(PhotoCollection(title = "B", photosCount = 2)),
                    HomeItemView.Collection(PhotoCollection(title = "C", photosCount = 3))
                )
            )
        )
    }

    override fun getCollectionMedia(id: String): Flow<PagingData<WallpaperPhoto>> {
        return flowOf(
            PagingData.from(
                listOf(
                    WallpaperPhoto(1, urls = sampleUrls),
                    WallpaperPhoto(2, urls = sampleUrls),
                    WallpaperPhoto(3, urls = sampleUrls)
                )
            )
        )
    }
}
