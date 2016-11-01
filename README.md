This is to demonstrate how the enabled state of nested views each with click listeners can affect the visual state:

Item view is our container
Inner action is a textview inside

Item view always has a click listener
Inner action sometimes does.

When inner action has NO click listener but is enabled,
clicking on the item view will trigger a separate ripple on the inner action

This is because viewgroups will by default propagate their pressed state to children        http://stackoverflow.com/a/6743126/494879

To fix this, set the inner action to be disabled.
