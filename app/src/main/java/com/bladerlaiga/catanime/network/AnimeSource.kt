package com.bladerlaiga.catanime.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bladerlaiga.catanime.AnimeOverviewItem
import retrofit2.HttpException
import java.io.IOException

class AnimeSource(
  private val service: AnimeService,
  private val page: Int = 1
) : PagingSource<Int, AnimeOverviewItem>() {
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeOverviewItem> {
    return try {
      val response = service.getOverview()
      val nextKey = if (/*response.item_count == 0*/true) {
        null
      } else {
        page + 1
      }
      LoadResult.Page(
        data = response.anime,
        prevKey = if (page == 1) null else page - 1,
        nextKey = nextKey
      )
    } catch (exception: IOException) {
      return LoadResult.Error(exception)
    } catch (exception: HttpException) {
      return LoadResult.Error(exception)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, AnimeOverviewItem>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }

}