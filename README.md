examples for non-touch compatibility
=============

- LinearLayoutManager RecyclerView
    - ? tabbing through doesn't work when scrolling is needed -> switch access is broken
    - swipe gesture (TalkBack) does scroll to the next item, but doesn't show item as focused (just green outline)
- GridLayoutManager RecyclerView
    - needs `android:descendantFocusability="afterDescendants"`
    - tabbing through works when all items fit on one screen
- Big Items GridLayoutManager RecyclerView
    - ? tabbing through doesn't work when scrolling is needed -> switch access is broken. Android Settings manages it somehow.
    - swipe gesture (TalkBack) does scroll to the next item, but doesn't show item as focused

Switch access
- easy mode: set up tab for next, enter for select, (backspace for previous)
- hard mode: not yet tested! (this'll be one key only, autoscan enabled)
