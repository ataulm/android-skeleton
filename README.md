continue watching
=================

#### Aim
Write an accessibility service that will automatically click "Continue Watching" button on Netflix whenever it pops up.

#### Current state (inc. next steps if applicable)
- Detects views with text "Continue watching"
- Sends a click event to that view (should work even if the listener is on one of the view's ancestors) within 500ms.
- Add settings activity so user can specify clickable words (translations, other apps, ...)
- Read list of clickable words for usage in service

#### Next steps

- Use ClickableWordsRepository in ContinueWatchingService (or introduce a ViewModel for both this and Settings)
- Allow user to specify package names for clickable words (use a picker Activity)
- Allow user to enable/disable entries
- Update the service's name and add description/summary
