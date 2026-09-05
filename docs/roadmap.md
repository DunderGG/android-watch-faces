# Roadmap

This is deliberately sequenced so that each step produces something testable.

## Milestone 0 — Tooling and first install

- [X] Install Android Studio and JDK 17.
- [X] Enable developer options and ADB debugging on the Pixel Watch, or create a
  matching Wear OS emulator.
- [X] Open this project and run `Starter Digital`.
- [X] Confirm that its time changes and that it appears in the face picker.

**Done when:** the starter face is visible on a device or emulator.

## Milestone 1 — Make the first face yours

- [X] Decide on one small visual idea: e.g. minimalist, sport, retro, or data-first.
  - Starting with data-first, as it is great for learning the Watch Face Format (WFF). It plays directly to WFF's architecture and it flows naturally into Milestone 2
- [X] Change the face name and package identity.
- [X] Modify typography, spacing, and colors in `watchface.xml`.
- [X] Capture a device screenshot and replace the temporary preview.
- [X] Record the design choices in a short `docs/faces/starter-digital.md` note.

**Done when:** you can recognize it as your own face from its picker preview.

## Milestone 2 — Core watch-face capabilities

- [ ] Add date/day text using WFF time data.
- [ ] Add one configurable complication slot and verify empty and populated states.
- [ ] Add a color configuration with two coherent palettes.
- [ ] Update `watch_face_info.xml` to `Editable=true` when settings/complications exist.
- [ ] Add and test an ambient-mode variant.

**Done when:** the face remains legible and useful in active, ambient, and edit modes.

## Milestone 3 — Quality

- [ ] Use Android Studio's WFF validation until the XML is clean.
- [ ] Test on-device through a full day if possible; check burn-in/ambient behavior.
- [ ] Test 12/24-hour preferences, accessibility sizing, different complications,
  and an unavailable complication-data state.
- [ ] Run the memory-footprint evaluation before release.
- [ ] Add a changelog and versioning policy.

**Done when:** no placeholder assets remain and every supported state has been checked.

## Milestone 4 — Share or publish

- [ ] Generate a signed release AAB.
- [ ] Create accurate icon, screenshots, description, privacy disclosures, and
  category tags for Play if publishing publicly.
- [ ] Check the current Wear OS quality requirements immediately before upload.
- [ ] Publish to an internal/closed track first and test the installed artifact.
- [ ] Tag the release in Git and document installation for testers.

**Done when:** a reproducible release artifact and its source tag are available.

