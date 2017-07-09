#talkback #a11y #gridlayoutmanager #recyclerview #focus

Wanted to determine when and what causes TalkBack to identify some items in a RecyclerView as a "Heading".

Seems like it's attributes set by the layout manager (grid layout manager specifically) on the accessibility node info.

The layout manager is able to identify the "visual" position of an item in terms of (row, column), which it reports, as well as whether an item is a header (just a boolean).

TalkBack uses this information (available to any accessibility service) to decide what to read aloud. It doesn't seem possible (so far) to change what's read aloud (like the labels we can override for accessibility actions)

---

also here is demonstrating the force setting of accessibility focus on dismiss of the dialog, to return focus to the item that opened it.

it's not recommended to force focus (https://stackoverflow.com/questions/28472985/android-set-talkback-accessibility-focus-to-a-specific-view) and alan v mentions it works in this version of talkback but maybe not in future. can't really see them deprecating this API/accessibility event, although the general guideline that it's not great to have to force focus like this seems right.
