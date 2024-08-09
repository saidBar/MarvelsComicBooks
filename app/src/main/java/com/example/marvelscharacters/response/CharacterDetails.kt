package com.example.marvelscharacters.response

data class CharacterDetailDataWrapper(
    val code: Int,
    val status: String,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: CharacterDetailDataContainer?,
    val etag: String?
)

data class CharacterDetailDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<CharacterDetail>?
)

data class CharacterDetail(
    val id: Int,
    val name: String,
    val description: String?,
    val modified: String?,  // Date as String for simplicity, can be parsed to Date type
    val resourceURI: String?,
    val urls: List<Url>?,
    val thumbnail: Image?,
    val comics: ComicList?,
    val stories: StoryList?,
    val events: EventList?,
    val series: SeriesList?
)

data class StoryList(
    val available: Int,
    val returned: Int,
    val collectionURI: String?,
    val items: List<StorySummary>?
)

data class StorySummary(
    val resourceURI: String?,
    val name: String?,
    val type: String?
)

data class EventList(
    val available: Int,
    val returned: Int,
    val collectionURI: String?,
    val items: List<EventSummary>?
)

data class EventSummary(
    val resourceURI: String?,
    val name: String?
)

data class SeriesList(
    val available: Int,
    val returned: Int,
    val collectionURI: String?,
    val items: List<SeriesSummary>?
)

data class SeriesSummary(
    val resourceURI: String?,
    val name: String?
)

