spike process lifecycle owner
=============================

#### Aim
determine how to use process lifecycle owner

#### Current state (inc. next steps if applicable)
- got logs on app moving to foreground/background
- it's triggered for the whole app, not individual activities
- I guess you gotta be careful not to introduce memory leaks (e.g. by attaching a LifecycleObserver
that has a reference to your activity)
- couldn't get the onDestroyed event to happen (tried swiping away the app)
- probably useful for analytics :sob:
