android-example
===============

Does anyone have any clues as to when `View.requestAccessibilityFocus` would be called on a view, by the system? e.g. in response to some event.

We have a button which, when tapped, animates an off-screen view _onto_ the screen, like an overlay. This overlay contains a RecyclerView of RecyclerViews, and we're finding that TalkBack is focusing on the first item in the first recyclerview *after some time* (so not immediately).

Prior to the RV is a toolbar, so it's not like it's focusing on the first child in the overlay either :thinking_face: (edited)

:grin: it looks like it's happening when the clock rolls over to the next minute :facepalm:

1) not sure why it's doing that in the first place - maybe because the window content has changed but there's nothing in focus currently?
2) not sure why it's choosing that view to do it

testing on an oreo emulator. (edited)

Ataul Munim [10:47 AM]
For a "workaround" :face_vomiting: , I'm manually requesting focus on the toolbar title - it seems if there's something focused already, it doesn't try to "find focus" on something else when the time changes.

this is not ideal because:

- there's a dismiss button on the left of the toolbar title
- it might break with future versions of talkback
- unsure if the find focus thing is a bug which will be fixed so I'm left with a patch to a non-issue

---

state: couldn't yet repro the issue. on this branch, it doesn't care that nothing is focused when the clock ticks over to the next minute.
I wonder if the fact we had a video playing affected it.