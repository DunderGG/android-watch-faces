# <img width="64" height="64" alt="appicon" src="https://github.com/user-attachments/assets/ba2344ea-54d9-4eaa-a521-638695f51351" /> Android Wear Watch Faces

A resource-only Wear OS watch-face project using **Watch Face Format (WFF)**.
The included `Starter Digital` face displays a 24-hour clock and is designed to
be the small, safe place to begin.

## First run

1. Install Android Studio (Canary is currently required for the best WFF editor
   and run-configuration support) and a JDK 17.
2. Open this folder in Android Studio. Let it install the requested Android SDK
   platforms if prompted.
3. Connect the watch with developer options and ADB debugging enabled, or create
   a Wear OS emulator.
4. Select the **starter-digital** run configuration and click Run. Android Studio
   installs the debug build and makes it active.

From a terminal, use the included Gradle wrapper:

```powershell
.\gradlew.bat :faces:starter-digital:assembleDebug
.\gradlew.bat :faces:starter-digital:bundleRelease
```

The first command produces an APK for local testing; the second produces the
Android App Bundle (AAB) needed for Google Play.

## Where to edit

- `faces/<watch-face-module>/src/main/res/raw/watchface.xml` — the face's layout and dynamic data.
- `faces/<watch-face-module>/src/main/res/xml/watch_face_info.xml` — picker metadata and preview.
- `faces/<watch-face-module>/src/main/res/drawable/preview.xml` — temporary preview; replace with
  a real screenshot before publishing.
- `docs/architecture.md` — how the pieces fit together.
- `docs/roadmap.md` — the suggested next tasks.
- `CONTRIBUTING.md` — guidelines for licensing, assets, and adding new faces.

Before changing the package name, update both `namespace` and `applicationId` in
`faces/<watch-face-module>/build.gradle.kts`. Use a globally unique value when you
publish.

## Important constraint

Watch Face Format bundles must have `android:hasCode="false"`. Do not add
Kotlin, Java, Compose, network clients, or a companion application to this
module. If a later feature truly needs app logic, make it a separate Wear OS app
bundle and keep this WFF bundle resource-only.

## References

- [Watch Face Format setup](https://developer.android.com/training/wearables/wff/setup)
- [WFF XML reference](https://developer.android.com/reference/wear-os/wff/watch-face)
- [Official codelab](https://developer.android.com/codelabs/watch-face-format)
