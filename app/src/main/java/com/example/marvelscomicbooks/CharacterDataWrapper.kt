package com.example.marvelscomicbooks

data class CharacterDataWrapper(
    val code: Int,
    val status: String,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: CharacterDataContainer,
    val etag: String?
)

data class CharacterDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Character>
)

data class Character(
    val id: Int,
    val name: String,
    val description: String?,
    val urls: List<Url>?,
    val thumbnail: Image?,
    val comics: ComicList?,
)

data class Url(
    val type: String?,
    val url: String?
)

data class Image(
    val path: String,
    val extension: String
) {
    val fullPath: String get() = "$path.$extension"
}

data class ComicList(
    val available: Int,
    val returned: Int,
    val collectionURI: String?,
    val items: List<ComicSummary>
)

data class ComicSummary(
    val resourceURI: String?,
    val name: String?
)