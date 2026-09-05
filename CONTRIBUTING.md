# Contributing to Android Watch Faces

Thank you for your interest in contributing! This repository is an open collection of Wear OS watch faces built using Google's declarative **Watch Face Format (WFF)**.

---

## Licensing & Open Source Guidelines

To keep this project safe and compliant for everyone in the open-source community, all contributions must adhere to the following rules:

### 1. WFF Specification & Code
* The underlying Watch Face Format specification is licensed under **Apache 2.0** by Google and Samsung.
* Code and XML layouts contributed to this repository are distributed under the project's [Apache 2.0 License](LICENSE).

### 2. Fonts & Typography
* Ensure any font file (`.ttf`, `.otf`) placed in `res/font/` has an open-source license, preferably the **SIL Open Font License (OFL)** or **Apache 2.0**.
* **Do not** commit proprietary, commercial, or copyrighted system fonts.
* Where possible, prefer the system default by referencing `family="SYNC_TO_DEVICE"`.

### 3. Graphic & Vector Assets
* All vector drawables (`.xml`), raster images (`.png`), and webp assets must be your original work or licensed under permissive terms (e.g., **CC-BY 4.0**, **Apache 2.0**, or **Public Domain**).
* Provide attribution in the face module's documentation when required by the upstream asset license.

### 4. Secret Separation & Security
* **Never commit signing keystores** (`*.jks`, `*.keystore`), `key.properties`, or certificate passwords to version control.
* These file patterns are ignored in [`.gitignore`](.gitignore) by default.

---

## Adding a New Watch Face

1. **Create the module**:
   Copy `faces/starter-digital` to a new directory under `faces/` (e.g., `faces/my-new-face`).
2. **Set unique identities**:
   In `faces/<new-face>/build.gradle.kts`:
   - Set a unique `namespace` and `applicationId` (e.g., `gg.dunder.watchfaces.mynewface`).
   - Choose the appropriate `minSdk` matching your required WFF version.
3. **Register in settings**:
   Add the new module to [`settings.gradle.kts`](settings.gradle.kts):
   ```kotlin
   include(":faces:my-new-face")
   ```
4. **Adhere to the resource-only constraint**:
   - Watch Face Format bundles must declare `android:hasCode="false"`.
   - Do **not** add Kotlin, Java, or Compose code to watch face modules.

---

## Testing Your Changes

Before opening a pull request, verify that your face compiles cleanly with Gradle:

```powershell
.\gradlew.bat :faces:<your-face-module>:assembleDebug
```

Ensure that:
- The face renders correctly within a 450x450 circular viewport.
- An ambient mode `<Variant mode="AMBIENT">` is provided and lit pixel ratio stays well under 15%.
- Preview assets in `res/drawable/` are updated.

