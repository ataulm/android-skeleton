continue watching
=================

#### Aim
Write an accessibility service that will automatically click "Continue Watching" button on Netflix whenever it pops up.

#### Current state (inc. next steps if applicable)
- Detects views with text "Continue watching"
- Sends a click event to that view (should work even if the listener is on one of the view's ancestors) within 500ms.

#### Next steps
- Add settings activity so user can specify the trigger text (easier than providing translations)
- Verify whether it works with apps with different package names (it should since no package name was specified in the config file)
- Update the service's name and add description/summary
