# Architecture: Wear OS Watch Face Format

## The model

This repository is an Android application module, but it is not a normal Android
app. A Watch Face Format (WFF) bundle is **resource-only**. Wear OS reads the
manifest, metadata, WFF XML, and image resources, then owns rendering, timing,
ambient-mode behavior, and the watch-face picker.

```text
Android Studio / Gradle
          |
          v
resource-only Android bundle (APK for debug, AAB for release)
          |
          v
Wear OS WFF runtime ----> renders scene + platform data + user settings
          |
          v
Pixel Watch display and face editor
```

That is why there is no `MainActivity`, service, Kotlin source set, or Compose UI
in this module. As of January 2026, Watch Face Format is required for installing
watch faces on Wear OS devices. The architecture is intentionally declarative:
describe *what* should appear, not a rendering loop for *how* to draw it.

## Repository strategy: one module per face

Treat every independently installable face as its own Android application module
and, for Play distribution, its own WFF Android App Bundle (AAB). Each module
must have a unique `applicationId`, its own label, preview asset, metadata, and
`watchface.xml`. This keeps each face independently versioned, tested, signed,
and publishable.

The starter module is `faces:starter-digital`. When adding a second face, copy
this module, rename it to a descriptive module name such as
`faces:metro-digital`, add it to `settings.gradle.kts`, and change its
`namespace` and `applicationId`. Do not put normal Wear OS application logic
inside any WFF module. If a companion app is needed later, keep it in a separate
module and publish its bundle separately.

## Files and responsibilities

| File | Responsibility |
| --- | --- |
| `AndroidManifest.xml` | Identifies a watch-only, no-code application and declares the WFF version. |
| `watch_face_info.xml` | Defines picker metadata such as preview and editability. |
| `watchface.xml` | The scene graph: text, shapes, images, clocks, complications, and variants. |
| `drawable/` | Preview and other image/vector assets referenced from WFF. |
| `values/strings.xml` | Localizable labels. |
| Gradle files | Package identity, SDK compatibility, and debug/release builds. |

## Coordinate system and layers

`WatchFace width="450" height="450"` is a logical coordinate system, not the
watch's physical resolution. Wear OS scales it for the device. Keep important
content away from the corners because this starter declares `clipShape="CIRCLE"`.
Within `<Scene>`, elements declared later appear over earlier elements.

The starter uses `PartText` and a `Template`. The template receives WFF data
tokens (`[HOUR_0_23]` and `[MINUTE]`) as parameters and formats them with
`%02d:%02d`; the system updates the result without application code.

## The normal extension path

1. Add static decoration with `PartDraw` / shapes or a `PartImage` resource.
2. Add a date or another system expression using `PartText` plus `Template`.
3. Add a `Complication` slot so the wearer can select a platform data source.
4. Add `UserConfigurations` for colors, styles, and presets; set `Editable` to
   `true` when the face exposes a setting or non-fixed complication.
5. Add `Variant` content for ambient mode: lower visual complexity, no animated
   effects, and readable high-contrast time.

## Compatibility policy

WFF features are versioned. The manifest's WFF version and the module's
`minSdk` jointly determine which watches can install the face. This starter uses
WFF v4 with `minSdk 36`, matching Wear OS 6. It is a sensible baseline for a
new face aimed at current devices, but it intentionally excludes older Wear OS
releases.

Choose the **lowest WFF version** that provides the features you actually use,
then set the matching minimum SDK. Lowering the WFF version expands the range of
supported watches but can remove newer XML capabilities. Raising it permits
newer features but narrows compatibility. Make that choice per face, document it
in that face's README or release notes, and verify it against the current
official WFF version documentation before changing either value.

## Build, test, release

Android Studio is the recommended path because it offers WFF-aware completion,
validation, and a watch-face run configuration. Build an APK for local device or
emulator testing; build an AAB for Play. Before release, replace the temporary
preview with real capture(s), validate XML, test active and ambient modes, test
complications/settings, and run the memory-footprint tooling.

Keep a WFF watch-face AAB separate from an AAB containing normal Wear OS app
logic; Google Play does not accept a single bundle that includes both.

## Design constraints worth remembering

- Battery and legibility are product requirements, not cleanup work.
- Test round displays and all editor states, not only the happy-path screenshot.
- Prefer platform complications to fetching data yourself; WFF has no runtime
  code or networking.
- Treat preview assets and Play listing images as deliverables, not placeholders.

## Primary documentation

- [WFF setup and project structure](https://developer.android.com/training/wearables/wff/setup)
- [WFF root XML reference](https://developer.android.com/reference/wear-os/wff/watch-face)
- [Build and deploy guidance](https://developer.android.com/training/wearables/wff/build)
- [Wear OS watch-face quality requirements](https://developer.android.com/docs/quality-guidelines/wear-app-quality)
