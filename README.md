android-example
===============
Can be used as a skeleton for new spikes. Don't modify this project except to update dependencies; checkout from the latest master.

It would be good to fill this out before starting the spike and with updates before each push if necessary:

#### Aim
Create and position PopupWindow.

#### Current state (inc. next steps if applicable)
Using `Gravity.NO_GRAVITY` seems to be the least confusing (vs. switching it based on the case). That way it's offset from the top left of the screen.

The thing that's still unclear is that using an offset for Y that's 0-24 seems to be ignored (that's the size of a statusbar...). I wonder if it's clamping the offset to `min(statusbar_height, value)`!

Similarly when using `Gravity.BOTTOM`, it would seem to clamp for the navigation bar height. This is pretty frustrating because it means that on a rotated device (where the nav bar might be on the side) the clamp doesn't apply, so if you used a value that's less than the nav bar height, it'll be ignored in portrait and visible in landscape 😠

This is why it makes sense to use `NO_GRAVITY` - this is equivalent to `TOP or LEFT` and since the status bar is likely to be unaffected by orientation, it seems like the best way to go.

Positioning in the x-axis needs to be done carefully, because of RTL mode. Lots of conditionals.

