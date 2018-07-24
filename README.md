sharing-text-from-multiple-itemviews
------------------------------------

It's recommended to split really long passages of text into separate paragraphs and use a RecyclerView to render them as distinct ItemViews to improve performance.

If you're displaying an article or something, it can mess up the UX for selecting and copying text, because now the user can only select text from one paragraph at a time.

#### Aim
Create UI demonstrating text sharing from multiple item views.

#### Current state (inc. next steps if applicable)
RecyclerView displays 5 paragraphs with selectable text in _each_ paragraph.
