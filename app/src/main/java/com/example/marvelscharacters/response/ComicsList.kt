package com.example.marvelscharacters.response

data class ComicDataWrapper(
    val code: Int,
    val status: String,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: ComicDataContainer?,
    val etag: String?
)

data class ComicDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Comic>?
)

data class Comic(
    val id: Int,
    val digitalId: Int,
    val title: String,
    val issueNumber: Double,
    val variantDescription: String?,
    val description: String?,
    val modified: String?, // Use appropriate date type if you have a date formatter
    val isbn: String?,
    val upc: String?,
    val diamondCode: String?,
    val ean: String?,
    val issn: String?,
    val format: String?,
    val pageCount: Int,
    val resourceURI: String?,
    val urls: List<Url>?,
    val series: SeriesSummary?,
    val variants: List<ComicSummary>?,
    val collections: List<ComicSummary>?,
    val collectedIssues: List<ComicSummary>?,
    val dates: List<ComicDate>?,
    val thumbnail: Image?,
    val images: List<Image>?,
    val stories: StoryList?,
    val events: EventList?
)


data class ComicDate(
    val type: String?,
    val date: String? // Use appropriate date type if you have a date formatter
)
