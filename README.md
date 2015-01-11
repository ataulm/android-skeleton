android-basic
=============
accessible-storyviews attempts to solve the problem of non-interactive views that contain important information.

these views are not typically focusable, so keyboard/trackball accessibility becomes an issue when talkback is enabled; talkback only works with
focusable elements.

the solution is to set focusability programmatically dependending on whether accessibility is enabled. This must be done in `onResume` given that
accessibility can be enabled/disabled during an app's uptime.

---

There is another consideration for accessible story (very long text) views; default behaviour means that text cannot be scanned. Accidentally
clicking away will stop talkback, and it'll start reading from the beginning again. Imagine having to read a long news article from the start
every time you looked away/checked a notification/touched your screen. This has not been addressed yet, but I suspect it'll involve having to split
 the text into multiple shorter parts and adding more views (listview/listadapter style).
