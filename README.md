TMDb app with Arch Components
=============================

#### Aim
Fetch a list of movies from the "top rated" endpoint of the TMDb API and display the title, description and the poster in a listview.

Process:

- [postman](https://www.getpostman.com/apps) to see the response of the API call
- saved example response as `get_movie_top_rated.json`
- the response includes a list of results. each result includes an id (integer), title, overview and poster path.
- poster path is like: `"/yE5d3BUhE8hCnkMUJOo1QDoOGNz.jpg"` so we need to combine it with some base url.
- need more from [configuration](https://developers.themoviedb.org/3/configuration/get-api-configuration) to get full image url.
- saved example response as `get_configuration.json`
- will immediately jump to [retrofit](http://square.github.io/retrofit/) to make API calls: well known, comfortable with integrating.

- created a stub api that returns a list of 3 forrest gumps. who's responsible for switching to a background thread? need to step back and think about the bigger pieces.
- the term "repository" is used as an abstraction over the data layer from the [archguide](https://developer.android.com/topic/libraries/architecture/guide.html#fetching_data)
- it exposes LiveData wrappers so we can operate along that line, using our stub data, and then fill in the blanks later. LiveData doesn't switch to a bg thread, but it does provide an async opportunity.

- let's do the UI, then we can return to the data layer.
