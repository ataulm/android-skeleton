# Mixin' Android Themes – how to combine styles from multiple sources

The Android theming system doesn’t support multiple inheritance. But uh, we can do some weird shit.

This post shows how we can use a custom layout inflater to specify multiple theme overlays for a single View, at the layout level or in a style resource, effectively including multiple styles, similar to Sass's mixin system.

## Naming

- Attribute set: the attributes we'll set on a View in an XML layout. We won't include the `style` attribute that we can set on a View in this definition (TK but maybe it's included)
- Style resource: this is a definition of a style in `res/values/somefile.xml`
- `style` attribute: this is the attribute we can set on a View in an XML layout. The value must be a style resource.
- `android:theme`/`app:theme` are attributes we can set on a View in a layout. We **cannot** set them in style resources (they'll be ignored TK check if View does know about `theme`) because they're only read by the `LayoutInflater` and the specified theme is overlaid using a `ContextThemeWrapper`.


TODO:
- check if View knows about `theme` attribute or if only LayoutInflater/AppCompatLayoutInflater knows about it
- check if `style`/`android:theme` attributes are considered part of AttributeSet
- why isn't `createThemedContext` done in `MaterialComponentsViewInflater`?

View.java
 * By default, Views are created using the theme of the Context object supplied
 * to their constructor; however, a different theme may be specified by using
 * the {@link android.R.styleable#View_theme android:theme} attribute in layout
 * XML or by passing a {@link ContextThemeWrapper} to the constructor from
 * code.
 * </p>
 * <p>
 * When the {@link android.R.styleable#View_theme android:theme} attribute is
 * used in XML, the specified theme is applied on top of the inflation
 * context's theme (see {@link LayoutInflater}) and used for the view itself as
 * well as any child elements.
