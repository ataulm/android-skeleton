log key events in a11y service
==============================

#### Aim
- Log key events in accessibility service
- Determine whether it's possible to inject keyevents into the message queue of the main looper to trigger keyevents on the a11y service (or to the foreground window)

#### Current state (inc. next steps if applicable)

- logged fine :ok_hand:
- couldn't trigger a11y service by dispatching a keyevent via the main/mylooper :(

