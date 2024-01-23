package com.bladerlaiga.catanime

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import androidx.room.*

@Serializable
@Entity(tableName = "anime_detail")
data class AnimeDetail(
  @PrimaryKey(autoGenerate = true)
  @SerializedName(value = "mal_id")
  var id: Long = 0L,
  @SerializedName(value = "url")
  @ColumnInfo(name = "url")
  var url: String = "",
  @SerializedName(value = "title")
  @ColumnInfo(name = "title")
  val title: String = "",
  @SerializedName(value = "title_japanese")
  @ColumnInfo(name = "title_japanese")
  val title_alt: String? = "",
  @SerializedName(value = "image_url")
  @ColumnInfo(name = "image_url")
  val image_url: String = "",
  @SerializedName(value = "trailer_url")
  @ColumnInfo(name = "trailer_url")
  val trailer_url: String? = "",
  @SerializedName(value = "type")
  @ColumnInfo(name = "type")
  val type: String = "",
  @SerializedName(value = "source")
  @ColumnInfo(name = "source")
  val source: String = "",
  @SerializedName(value = "episodes")
  @ColumnInfo(name = "episodes")
  val episodes: Int = 0,
  @SerializedName(value = "status")
  @ColumnInfo(name = "status")
  val status: String = "",
  @SerializedName(value = "airing")
  @ColumnInfo(name = "airing")
  val airing: Boolean = false,
  @SerializedName(value = "aired")
  @ColumnInfo(name = "aired")
  val aired: Aired = Aired(),
  @SerializedName(value = "duration")
  @ColumnInfo(name = "duration")
  val duration: String = "",
  @SerializedName(value = "rating")
  @ColumnInfo(name = "rating")
  val rating: String = "",
  @SerializedName(value = "score")
  @ColumnInfo(name = "score")
  val score: Float = 0F,
  @SerializedName(value = "synopsis")
  @ColumnInfo(name = "synopsis")
  val synopsis: String = "",
  @SerializedName(value = "premiered")
  @ColumnInfo(name = "premiered")
  val premiered: String = "",
  @SerializedName(value = "related")
  @ColumnInfo(name = "related")
  val related: Related = Related(),
  @SerializedName(value = "producers")
  @ColumnInfo(name = "producers")
  val producers: List<Producer> = emptyList(),
  @SerializedName(value = "studios")
  @ColumnInfo(name = "studios")
  val studios: List<Studio> = emptyList(),
  @SerializedName(value = "genres")
  @ColumnInfo(name = "genres")
  val genres: List<Genre> = emptyList(),
  @SerializedName(value = "themes")
  @ColumnInfo(name = "themes")
  val themes: List<Theme> = emptyList(),
)

@Serializable
@Entity(tableName = "anime_overview_item")
data class AnimeOverviewItem(
  @SerializedName(value = "mal_id")
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0L,
  @SerializedName(value = "title")
  @ColumnInfo(name = "title")
  val title: String = "",
  @SerializedName(value = "image_url")
  @ColumnInfo(name = "image_url")
  val image_url: String = "",
  @SerializedName(value = "synopsis")
  @ColumnInfo(name = "synopsis")
  val synopsis: String = "",
  @SerializedName(value = "type")
  @ColumnInfo(name = "type")
  val type: String = "",
  @SerializedName(value = "airing_start")
  @ColumnInfo(name = "airing_start")
  val airing_start: String? = "",
  @SerializedName(value = "episodes")
  @ColumnInfo(name = "episodes")
  val episodes: Int = 0,
  @SerializedName(value = "genres")
  @ColumnInfo(name = "genres")
  val genres: List<Genre> = emptyList(),
  @SerializedName(value = "themes")
  @ColumnInfo(name = "themes")
  val themes: List<Theme> = emptyList(),
  @SerializedName(value = "producers")
  @ColumnInfo(name = "producers")
  val producers: List<Producer> = emptyList(),
  @SerializedName(value = "source")
  @ColumnInfo(name = "source")
  val source: String = "",
  @SerializedName(value = "score")
  @ColumnInfo(name = "score")
  val score: Float = 0F,
)

@Serializable
@Entity(
  tableName = "anime_favorite",
  foreignKeys = [ForeignKey(
    entity = AnimeOverviewItem::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("id_anime_overview_item")
  )],
  indices = [
    Index(value = ["id_anime_overview_item"], unique = true)
  ]
)
data class AnimeFavorite(
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0L,
  @ColumnInfo(name = "id_anime_overview_item")
  var id_anime_overview_item: Long = 0L,
)

@Serializable
@Entity(
  tableName = "anime_reminder",
  foreignKeys = [ForeignKey(
    entity = AnimeDetail::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("id_anime_detail")
  )],
  indices = [
    Index(value = ["id_anime_detail"], unique = true)
  ]
)
data class AnimeReminder(
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0L,
  @ColumnInfo(name = "id_anime_detail")
  var id_anime_detail: Long = 0L,
  @ColumnInfo(name = "time")
  var time: Long = 0L,
  @ColumnInfo(name = "every")
  var every: Long = 0L,
)

@Serializable
data class AnimeFavoriteAndOverviewItem(
  @Embedded
  val animeFavorite: AnimeFavorite,
  @Relation(
    parentColumn = "id_anime_overview_item",
    entityColumn = "id"
  )
  val animeOverviewItem: AnimeOverviewItem,
)

@Serializable
data class AnimeReminderAndDetail(
  @Embedded
  val animeReminder: AnimeReminder,
  @Relation(
    parentColumn = "id_anime_detail",
    entityColumn = "id"
  )
  val animeDetail: AnimeDetail,
)

@Serializable
data class Related(
  @SerializedName(value = "Adaptation")
  @ColumnInfo(name = "adaptation")
  val adaptation: List<Adaptation> = emptyList(),
  @SerializedName(value = "Side story")
  @ColumnInfo(name = "side_story")
  val side_story: List<SideStory> = emptyList(),
  @SerializedName(value = "Sequel")
  @ColumnInfo(name = "sequel")
  val sequel: List<Sequel> = emptyList(),
  @SerializedName(value = "Other")
  @ColumnInfo(name = "other")
  val other: List<Other> = emptyList(),
  @SerializedName(value = "Summary")
  @ColumnInfo(name = "summary")
  val summary: List<Summary> = emptyList(),
)

@Serializable
data class Adaptation(
  @SerializedName(value = "mal_id")
  @ColumnInfo(name = "mal_id")
  val id: String = "",
  @SerializedName(value = "name")
  @ColumnInfo(name = "name")
  val name: String = "",
  @SerializedName(value = "type")
  @ColumnInfo(name = "type")
  val type: String = "",
)

@Serializable
data class SideStory(
  @SerializedName(value = "mal_id")
  @ColumnInfo(name = "mal_id")
  val id: String = "",
  @SerializedName(value = "name")
  @ColumnInfo(name = "name")
  val name: String = "",
  @SerializedName(value = "type")
  @ColumnInfo(name = "type")
  val type: String = "",
)

@Serializable
data class Sequel(
  @SerializedName(value = "mal_id")
  @ColumnInfo(name = "mal_id")
  val id: String = "",
  @SerializedName(value = "name")
  @ColumnInfo(name = "name")
  val name: String = "",
  @SerializedName(value = "type")
  @ColumnInfo(name = "type")
  val type: String = "",
)

@Serializable
data class Other(
  @SerializedName(value = "mal_id")
  @ColumnInfo(name = "mal_id")
  val id: String = "",
  @SerializedName(value = "name")
  @ColumnInfo(name = "name")
  val name: String = "",
  @SerializedName(value = "type")
  @ColumnInfo(name = "type")
  val type: String = "",
)

@Serializable
data class Summary(
  @SerializedName(value = "mal_id")
  @ColumnInfo(name = "mal_id")
  val id: String = "",
  @SerializedName(value = "name")
  @ColumnInfo(name = "name")
  val name: String = "",
  @SerializedName(value = "type")
  @ColumnInfo(name = "type")
  val type: String = "",
)

@Serializable
data class Aired(
  @SerializedName(value = "string")
  @ColumnInfo(name = "string")
  val string: String = "",
)

@Serializable
data class Studio(
  @SerializedName(value = "name")
  @ColumnInfo(name = "name")
  val name: String = "",
)

@Serializable
data class Genre(
  @SerializedName(value = "name")
  @ColumnInfo(name = "name")
  val name: String = "",
)

@Serializable
data class Theme(
  @SerializedName(value = "name")
  @ColumnInfo(name = "name")
  val name: String = "",
)

@Serializable
data class Producer(
  @SerializedName(value = "name")
  @ColumnInfo(name = "name")
  val name: String = "",
)

@Serializable
data class AnimeOverview(
  @SerializedName(value = "anime")
  @ColumnInfo(name = "anime")
  val anime: List<AnimeOverviewItem>,
)