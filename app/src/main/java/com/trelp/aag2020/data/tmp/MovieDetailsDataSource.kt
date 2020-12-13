package com.trelp.aag2020.data.tmp

import com.trelp.aag2020.R

class MovieDetailsDataSource {

    private var randomActors = listOf(
        Actor(R.drawable.pic_actor_photo_robert_downey_jr, "Robert Downey Jr."),
        Actor(R.drawable.pic_actor_photo_antonio_banderas, "Antonio Banderas"),
        Actor(R.drawable.pic_actor_photo_michael_sheen, "Michael Sheen"),
        Actor(R.drawable.pic_actor_photo_jim_broadbent, "Jim Broadbent"),
        Actor(R.drawable.pic_actor_photo_jessie_buckley, "Jessie Buckley"),
        Actor(R.drawable.pic_actor_photo_harry_colett, "Harry Colett"),
        Actor(R.drawable.pic_actor_photo_kristen_stewart, "Kristen Stewart"),
        Actor(R.drawable.pic_actor_photo_vincent_cassel, "Vincent Cassel"),
        Actor(R.drawable.pic_actor_photo_mamoudou_athie, "Mamoudou Athie"),
        Actor(R.drawable.pic_actor_photo_tj_miller, "T. J. Miller"),
        Actor(R.drawable.pic_actor_photo_john_gallagher_jr, "John Gallagher Jr."),
    ).shuffled().take(6)

    fun getMovieDetails(movieId: Int) = movieDetails.first { it.id == movieId }

    private val movieDetails = listOf(
        MovieDetails(
            0,
            R.drawable.pic_movie_image_in_details_dolittle,
            "13+",
            "Dolittle",
            "Adventure, Comedy, Family",
            4F,
            87,
            "A physician who can talk to animals embarks on an adventure to find a legendary island with a young apprentice and a crew of strange pets.",
            randomActors
        ),
        MovieDetails(
            1,
            R.drawable.pic_movie_image_in_details_underwater,
            "18+",
            "Underwater",
            "Action, Horror, Sci-Fi",
            5F,
            113,
            "A crew of oceanic researchers working for a deep sea drilling company try to get to safety after a mysterious earthquake devastates their deepwater research and drilling facility located at the bottom of the Mariana Trench.",
            randomActors
        ),
        MovieDetails(
            2,
            R.drawable.pic_movie_image_in_details_the_call_of_the_wild,
            "13+",
            "The Call Of The Wild",
            "Adventure, Drama, Family",
            3F,
            321,
            "A sled dog struggles for survival in the wilds of the Yukon.",
            randomActors
        ),
        MovieDetails(
            3,
            R.drawable.pic_movie_image_in_details_last_christmas,
            "13+",
            "Last Christmas",
            "Comedy, Drama, Romance",
            2F,
            208,
            "Kate is a young woman subscribed to bad decisions. Working as an elf in a year round Christmas store is not good for the wannabe singer. However, she meets Tom there. Her life takes a new turn. For Kate, it seems too good to be true.",
            randomActors
        ),
        MovieDetails(
            4,
            R.drawable.pic_movie_image_in_details_the_invisible_guest,
            "16+",
            "The Invisible Guest",
            "Crime, Drama, Mystery",
            5F,
            173,
            "A successful entrepreneur accused of murder and a witness preparation expert have less than three hours to come up with an impregnable defense.",
            randomActors
        ),
        MovieDetails(
            5,
            R.drawable.pic_movie_image_in_details_fantasy_island,
            "13+",
            "Fantasy Island",
            "Action, Adventure, Fantasy",
            4F,
            45,
            "When the owner and operator of a luxurious island invites a collection of guests to live out their most elaborate fantasies in relative seclusion, chaos quickly descends.",
            randomActors
        )
    )
}