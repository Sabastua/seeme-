package com.example.seeme.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seeme.data.model.CreatorCountry
import com.example.seeme.data.model.CreatorProfile
import kotlinx.coroutines.flow.*

class DiscoverViewModel : ViewModel() {

    val categories = listOf("All", "🔴 Live Now", "💻 Coding", "🎮 Gaming", "🎵 Music", "🎨 Art", "🏋️ Fitness", "📱 Tech")

    val countries = listOf(
        CreatorCountry("ALL", "All Countries", "🌍"),
        CreatorCountry("KE", "Kenya", "🇰🇪"),
        CreatorCountry("US", "USA", "🇺🇸"),
        CreatorCountry("UK", "UK", "🇬🇧"),
        CreatorCountry("NG", "Nigeria", "🇳🇬"),
        CreatorCountry("JP", "Japan", "🇯🇵"),
        CreatorCountry("BR", "Brazil", "🇧🇷")
    )

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedCountry = MutableStateFlow(countries.first())
    val selectedCountry: StateFlow<CreatorCountry> = _selectedCountry.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _allCreators = MutableStateFlow<List<CreatorProfile>>(emptyList())

    val filteredCreators: StateFlow<List<CreatorProfile>> = combine(
        _allCreators,
        _selectedCategory,
        _selectedCountry,
        _searchQuery
    ) { creators, category, country, query ->
        creators.filter { creator ->
            val matchesCategory = when (category) {
                "All" -> true
                "🔴 Live Now" -> creator.isLive
                else -> category.contains(creator.category, ignoreCase = true) || creator.category.contains(category.replace(Regex("[^a-zA-Z]"), ""), ignoreCase = true)
            }

            val matchesCountry = if (country.code == "ALL") true else creator.country.code == country.code

            val matchesQuery = if (query.isBlank()) true else {
                creator.name.contains(query, ignoreCase = true) ||
                creator.handle.contains(query, ignoreCase = true) ||
                creator.category.contains(query, ignoreCase = true)
            }

            matchesCategory && matchesCountry && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val topRatedCreator: StateFlow<CreatorProfile?> = _allCreators.map { creators ->
        creators.maxByOrNull { it.streamQualityScore * it.hookedAudiencePercent }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val newCreators: StateFlow<List<CreatorProfile>> = _allCreators.map { creators ->
        creators.filter { it.isNewCreator }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        loadCreators()
    }

    private fun loadCreators() {
        val kenya = CreatorCountry("KE", "Kenya", "🇰🇪")
        val usa = CreatorCountry("US", "USA", "🇺🇸")
        val uk = CreatorCountry("UK", "UK", "🇬🇧")
        val nigeria = CreatorCountry("NG", "Nigeria", "🇳🇬")
        val japan = CreatorCountry("JP", "Japan", "🇯🇵")
        val brazil = CreatorCountry("BR", "Brazil", "🇧🇷")

        _allCreators.value = listOf(
            CreatorProfile(
                id = "c1",
                name = "Alex Riviera",
                handle = "@alex_live",
                country = usa,
                category = "Coding",
                isLive = true,
                viewerCount = 2840,
                followers = "12.4K",
                bio = "Live Android & Compose development stream!",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                isNewCreator = false,
                streamQualityScore = 4.9f,
                hookedAudiencePercent = 98,
                rankPosition = 1
            ),
            CreatorProfile(
                id = "c2",
                name = "Amina Wanjiku",
                handle = "@amina_tech",
                country = kenya,
                category = "Tech",
                isLive = true,
                viewerCount = 1950,
                followers = "18.2K",
                bio = "Building Fintech & Mobile AI apps in Nairobi 🇰🇪",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                isNewCreator = true,
                streamQualityScore = 4.8f,
                hookedAudiencePercent = 95,
                rankPosition = 2
            ),
            CreatorProfile(
                id = "c3",
                name = "Kenji Sato",
                handle = "@kenji_beats",
                country = japan,
                category = "Music",
                isLive = true,
                viewerCount = 3400,
                followers = "45K",
                bio = "Tokyo Lo-Fi Beatmaking & Synth Sessions 🇯🇵",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                isNewCreator = false,
                streamQualityScore = 4.9f,
                hookedAudiencePercent = 96,
                rankPosition = 3
            ),
            CreatorProfile(
                id = "c4",
                name = "David Olamide",
                handle = "@david_gaming",
                country = nigeria,
                category = "Gaming",
                isLive = true,
                viewerCount = 4120,
                followers = "28.9K",
                bio = "Esports live tournament host 🎮 🇳🇬",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                isNewCreator = true,
                streamQualityScore = 4.7f,
                hookedAudiencePercent = 94,
                rankPosition = 4
            ),
            CreatorProfile(
                id = "c5",
                name = "Maya Lin",
                handle = "@maya_arts",
                country = uk,
                category = "Art",
                isLive = true,
                viewerCount = 1250,
                followers = "9.8K",
                bio = "3D Digital Art & Blender modeling 🎨",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                isNewCreator = true,
                streamQualityScore = 4.6f,
                hookedAudiencePercent = 91,
                rankPosition = 5
            ),
            CreatorProfile(
                id = "c6",
                name = "Lucas Silva",
                handle = "@lucas_fitness",
                country = brazil,
                category = "Fitness",
                isLive = false,
                viewerCount = 0,
                followers = "15.4K",
                bio = "Calisthenics & Live Workout routines 🏋️ 🇧🇷",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoylikes.mp4",
                isNewCreator = false,
                streamQualityScore = 4.5f,
                hookedAudiencePercent = 88,
                rankPosition = 6
            )
        )
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun selectCountry(country: CreatorCountry) {
        _selectedCountry.value = country
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
