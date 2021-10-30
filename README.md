# talkback headers in lists

TalkBack used to have an issue with navigating to items in a ListView that were off-screen. It would sometimes skip the off-screen item and go to the next one (when it had the auto-scroll behavior enabled).

- [View.setAccessibilityHeading](https://developer.android.com/reference/android/view/View#setAccessibilityHeading(boolean)) was added in API 28
- [ViewCompat.setAccessibilityHeading](https://developer.android.com/reference/androidx/core/view/ViewCompat#setAccessibilityHeading(android.view.View,%20boolean)) is no-op on pre-19 and it should just work on 19+?
- [ViewCompat.isAccessibilityHeading](https://developer.android.com/reference/androidx/core/view/ViewCompat#isAccessibilityHeading(android.view.View)) always returns false pre-API 19.

Compose semantics also has a heading property which should expose elements to accessibility services in a similar way. The question is whether with LazyColumn (or Column), this behaves as expected, and allows users to skip between headings even when the next heading is off screen.

## Todo

Create 4 lists:

- RecyclerView
- LinearLayout
- Compose Column
- Compose LazyColumn

with 26 sections (letters of the English alphabet).

Each section has a heading and 5 items numbered 1-5, so in total there should be 156 (26 + 26 * 5) items in the list.

Check with the latest version of TalkBack whether navigating by heading works in all cases. In Compose, use semantics and in Views use ViewCompat.