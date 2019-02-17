recycler view changes animations
================================

The default `RecyclerView.ItemAnimator` will transition between two `ViewHolder`s when the content for a given item changes.

To avoid this, we can disable `ItemAnimator.supportsChangeAnimations`, so that it just updates the existing `ViewHolder`.

Alternatively, we can specify our own animations.

#### Aim
Animate changes for items.
