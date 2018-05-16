continue watching
=================
Can be used as a skeleton for new spikes. Don't modify this project except to update dependencies; checkout from the latest master.

It would be good to fill this out before starting the spike and with updates before each push if necessary:

#### Aim
Write an accessibility service that will automatically click "Continue Watching" button on Netflix whenever it pops up.

#### Current state (inc. next steps if applicable)
- Detects views with text "Continue watching"
- Clicks that view within 500ms

#### Next steps
- It's not clear whether this view is clickable in Netflix or whether it's one of the view's ancestors
- add settings activity which allows the user to type custom text to look for; easier than providing translations
- verify whether it works with apps with different package names (it should since no package name was specified in the config file)
- update service name and add description/summary (determine difference..)
