# Starter Digital — Design Documentation

> A data-first Wear OS watch face built with Google's declarative Watch Face Format (WFF).

---

## 1. Visual Direction & Concept

* **Purpose**: **Educational & Reference Implementation**. This face is designed primarily as a clean, practical learning vehicle — both as a hands-on project to master the declarative Watch Face Format (WFF) and as an accessible, well-documented open-source reference for other developers learning how to build modern Wear OS watch faces.
* **Design Philosophy**: **Data-First Digital**.
* **Rationale**: Designed to leverage the native strengths of Wear OS Watch Face Format. Rather than relying purely on static styling, the face prioritizes fast, glanceable metrics and sets up a structured canvas for user-configurable complications in Milestone 2.
* **Target Display**: Circular Wear OS displays (logical `450 x 450` canvas, centered viewport).

---

## 2. Layout Grid & Spatial Structure

The circular canvas is divided into three distinct vertical zones to ensure all content stays within the circular safe area while leaving room for future widgets:

| Zone | Coordinates | Content | Purpose |
| :--- | :--- | :--- | :--- |
| **Top Zone** | `x="25" y="90" w="400" h="45"` | Date & Day (`[DAY_OF_WEEK_S], [MONTH_S] [DAY]`) | Quick date orientation in the upper safe arc |
| **Center Zone** | `x="25" y="145" w="400" h="135"` | 24-Hour Digital Clock (`[HOUR_0_23]:[MINUTE]`) | Primary time-telling read with bold legibility |
| **Bottom Zone** | `x="25" y="295" w="400" h="40"` | Battery Indicator (`[BATTERY_PERCENT]%`) | Secondary hardware metric; reserved for Milestone 2 complication slots |

---

## 3. Color Palette & Hierarchy

The color scheme is designed for high contrast and OLED power efficiency:

* **Background (`#FF000000`)**: Solid black background ensures individual OLED pixels remain turned off, conserving battery and preventing backlight bleed.
* **Accent — Sport Amber (`#FFFFB300`)**: Used for the date header. Amber delivers high outdoor visibility and gives the face its signature character without overpowering the time.
* **Primary — Crisp White (`#FFFFFFFF`)**: Maximum contrast for the digital clock digits.
* **Secondary / Subtext — Cool Slate (`#FF8A9BA8`)**: Muted low-priority color for metadata and units, preventing visual clutter.

---

## 4. Typography

* **Font Family**: `family="SYNC_TO_DEVICE"`
  * Uses the platform's adaptive font stack, aligning with Wear OS Material 3 design and the user's system font preference.
* **Scale Hierarchy**:
  * **Clock**: `size="92"` — dominant focal point.
  * **Date**: `size="24"` — clear secondary read.
  * **Metrics / Battery**: `size="20"` — compact supporting detail.

---

## 5. Technical Identity

* **Module**: `:faces:starter-digital`
* **Application ID**: `gg.dunder.watchfaces.starterdigital`
* **WFF Version**: `4` (requires Wear OS 6 / `minSdk = 36`)
* **Publisher**: `DunderGG`
* **Constraint**: Resource-only (`android:hasCode="false"`), zero executable app logic.

---

## 6. Milestone 2 Evolution Plan

The 3-zone structure directly facilitates the next roadmap tasks:
1. **Complication Slots**: Replace or augment the bottom battery text with standard `<ComplicationSlot>` containers (e.g. `SHORT_TEXT` and `RANGED_VALUE` for steps/weather).
2. **User Configurations**: Add color palette switches (e.g. Amber, Cyan, Monochrome).
3. **Ambient Mode Variant**: Provide a dedicated `<Variant mode="AMBIENT">` that strips out secondary elements and dims the clock to maintain lit pixel ratio < 10%.

