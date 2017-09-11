android-skeleton
================
Can be used as a skeleton for new spikes. Don't modify this project except to update dependencies, copy it.

It would be good to fill this out before starting the spike and with updates before each push if necessary:

#### Aim

Wanted to see if implementing support for secondary displays would provide an easy way to do Chromecast without having to implement a receiver.

#### Current state (inc. next steps if applicable)

It does! With the secondary display implemented, you can use the Cast screen feature in Android without having to include any explicit Chromecast support in your app.

TODO:

- See how quickly the View is updated when binding new data (e.g. can you spam update a TextView with the system nanotime?)
- ensure support for adding/removing secondary screens (seemed a bit flaky with the simulated display from dev options)
