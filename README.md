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
