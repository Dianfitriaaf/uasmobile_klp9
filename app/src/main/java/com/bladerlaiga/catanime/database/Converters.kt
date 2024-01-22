package com.bladerlaiga.catanime.database

import androidx.room.TypeConverter
import com.bladerlaiga.catanime.*
import com.bladerlaiga.catanime.network.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
  @TypeConverter
  fun fromGenres(value: List<Genre>) = Json.encodeToString(value)
  @TypeConverter
  fun toGenres(value: String) = Json.decodeFromString<List<Genre>>(value)

  @TypeConverter
  fun fromProducers(value: List<Producer>) = Json.encodeToString(value)
  @TypeConverter
  fun toProducers(value: String) = Json.decodeFromString<List<Producer>>(value)

  @TypeConverter
  fun fromThemes(value: List<Theme>) = Json.encodeToString(value)
  @TypeConverter
  fun toThemes(value: String) = Json.decodeFromString<List<Theme>>(value)

  @TypeConverter
  fun fromAireds(value: List<Aired>) = Json.encodeToString(value)
  @TypeConverter
  fun toAireds(value: String) = Json.decodeFromString<List<Aired>>(value)

  @TypeConverter
  fun fromAired(value: Aired) = Json.encodeToString(value)
  @TypeConverter
  fun toAired(value: String) = Json.decodeFromString<Aired>(value)

  @TypeConverter
  fun fromRelated(value: Related) = Json.encodeToString(value)
  @TypeConverter
  fun toRelated(value: String) = Json.decodeFromString<Related>(value)

  @TypeConverter
  fun fromStudios(value: List<Studio>) = Json.encodeToString(value)
  @TypeConverter
  fun toStudios(value: String) = Json.decodeFromString<List<Studio>>(value)

  @TypeConverter
  inline fun <reified T> from(value: T) = Json.encodeToString(value)
  @TypeConverter
  inline fun <reified T> to(value: String) = Json.decodeFromString<T>(value)

  @TypeConverter
  fun fromAnimeOverviewItem(value: AnimeOverviewItem) = Json.encodeToString(value)
  @TypeConverter
  fun fromAnimeOverviewItems(value: List<AnimeOverviewItem>) = Json.encodeToString(value)
  @TypeConverter
  fun fromAnimeInfo(value: AnimeFavorite) = Json.encodeToString(value)
  @TypeConverter
  fun fromAnimeInfos(value: List<AnimeFavorite>) = Json.encodeToString(value)

  @TypeConverter
  fun toAnimeOverviewItem(value: String) = Json.decodeFromString<AnimeOverviewItem>(value)
  @TypeConverter
  fun toAnimeOverviewItems(value: String) = Json.decodeFromString<List<AnimeOverviewItem>>(value)
  @TypeConverter
  fun toAnimeInfo(value: String) = Json.decodeFromString<AnimeFavorite>(value)
  @TypeConverter
  fun toAnimeInfos(value: String) = Json.decodeFromString<List<AnimeFavorite>>(value)
}