how do spans affect accessibility
=================================

companion to: https://medium.com/p/814456bc3e74/edit

#### Aim
Learn how accessibility services behave if you use a single TextView with multiple spans, instead of multiple TextViews.

#### Current state (inc. next steps if applicable)
- depends on the spans
- using urlspan, it'll break up the text into separate fragments
- I guess also with layout changing spans, but need to check
- with "<br>" it breaks up too
- `FeedbackProcessingUtils#addFormattingCharacteristics(FeedbackItem item)`
- overriding the text/content description on the AccessibilityNodeInfo will replace the fragment with that text. Does it goof up the UrlSpans? or are these still available in the local context menu and is the action still exposed (double tap to activate)?


