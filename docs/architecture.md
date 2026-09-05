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
Wear OS display and face editor
```

That is why there is no `MainActivity`, service, Kotlin source set, or Compose UI
in this module. As of January 2026, Watch Face Format is required for installing
watch faces on Wear OS devices. The architecture is intentionally declarative:
describe *what* should appear, not a rendering loop for *how* to draw it.

## Key architectural benefits

> Why declarative XML watch faces outperform traditional code-driven implementations.

- **Battery Optimization**: System-level batching and sleep intervals prevent rogue CPU/GPU wake locks.
- **Crash Resilience**: Watch faces cannot trigger Application Not Responding (ANR) dialogs or JVM crashes.
- **Security**: Zero executable code means minimal attack surface.

## Coordinate system and layers

> Logical canvas scaling, circular viewport boundaries, and z-index ordering.

`WatchFace width="450" height="450"` is a logical coordinate system, not the
watch's physical resolution. Wear OS scales it for the device. Keep important
content away from the corners because this starter declares `clipShape="CIRCLE"`.
Within `<Scene>`, elements declared later appear over earlier elements.

The starter uses `PartText` and a `Template`. The template receives WFF data
tokens (`[HOUR_0_23]` and `[MINUTE]`) as parameters and formats them with
`%02d:%02d`; the system updates the result without application code.

## Scene layout & visual pipeline

> Watch faces are structured as a 2D canvas (standard reference: 450x450 pixels) centered on circular viewports.

- **`<Scene>`**: Top-level canvas container. Specifies properties like background color.
- **`<PartDraw>`**: Vector or basic geometric shapes (rectangles, circles, strokes).
- **`<PartText>`**: Text rendered using device fonts (`SYNC_TO_DEVICE`) or custom bundled fonts.
- **`<PartImage>`**: Static bitmap or vector drawables.
- **`<ComplicationSlot>`**: Interoperable widgets populated by external apps (Step Counter, Heart Rate, Weather).

## Ambient mode (Always-On Display / AOD)

> OLED displays on smartwatches require strict burn-in protection and power management.

- **Pixel Ratio Limit**: Total lit pixels in Ambient mode should stay well below 15% (ideally < 10%).
- **Color Palette**: Turn bright background fills off (`#FF000000` / transparent). Use muted colors or grayscale for text and icons.
- **De-cluttering**: Hide seconds counters, sub-dials, and non-essential complications during ambient mode using `<Variant mode="AMBIENT">`.

## The normal extension path

> Recommended progression for taking a watch face from a basic clock to an interactive, customizable design.

1. Add static decoration with `PartDraw` / shapes or a `PartImage` resource.
2. Add a date or another system expression using `PartText` plus `Template`.
3. Add a `Complication` slot so the wearer can select a platform data source.
4. Add `UserConfigurations` for colors, styles, and presets; set `Editable` to
   `true` when the face exposes a setting or non-fixed complication.
5. Add `Variant` content for ambient mode: lower visual complexity, no animated
   effects, and readable high-contrast time.

## Repository strategy: one module per face

> Structuring each watch face as an independent Android application module and App Bundle.

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

> Directory overview and specific roles of manifests, resources, scene graphs, and build scripts.

| File | Responsibility |
| --- | --- |
| `AndroidManifest.xml` | Identifies a watch-only, no-code application and declares the WFF version. |
| `watch_face_info.xml` | Defines picker metadata such as preview and editability. |
| `watchface.xml` | The scene graph: text, shapes, images, clocks, complications, and variants. |
| `drawable/` | Preview and other image/vector assets referenced from WFF. |
| `values/strings.xml` | Localizable labels. |
| Gradle files | Package identity, SDK compatibility, and debug/release builds. |

## Compatibility policy

> Balancing XML feature capabilities against minimum Wear OS and Android API version targets.

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

> Producing local debug APKs for device testing and signed release AABs for Google Play publishing.

Android Studio is the recommended path because it offers WFF-aware completion,
validation, and a watch-face run configuration. Build an APK for local device or
emulator testing; build an AAB for Play. Before release, replace the temporary
preview with real capture(s), validate XML, test active and ambient modes, test
complications/settings, and run the memory-footprint tooling.

Keep a WFF watch-face AAB separate from an AAB containing normal Wear OS app
logic; Google Play does not accept a single bundle that includes both.

## Design constraints worth remembering

> Core guidelines for battery longevity, typography, circular displays, and edge cases.

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
