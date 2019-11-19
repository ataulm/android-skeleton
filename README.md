highlights how fragments that aren't on top are still focusable by talkback.

expected behaviour:
- iterating through UI elements should include only "Button B"

actual:
- starts with "Button A", then "Button B", even though A is not visible
