# Marchers Only

SDC340 Mobile App Development (ECPI) — Final Project. An original Android app for
the Marchers Only real estate brand ("You Are Meant For More"), built by
Donavan McFadden.

## Features
- **Home** — brand hero + two feature cards that route into the app's other screens.
- **Prosperity Path** — a 5-step guide from mortgage pre-approval to building equity.
- **Agent Contact** — a contact card for the agent with one-tap call, text, and email.
- Custom launcher icon and matching action bar icon (Marchers Only mark).
- Custom typography: Playfair Display (headings) + Inter (body), bundled as
  Android font resources — not system defaults.
- Shared bottom navigation bar across all three activities.

## Structure
- `HomeActivity` — launcher activity
- `ProsperityActivity` — second activity, builds its step list in code
- `AgentContactActivity` — third activity, launches dialer/SMS/email intents
- `NavHelper` — shared bottom-navigation wiring used by all three activities

## How to run
1. Open this folder in Android Studio (Jellyfish or newer).
2. Let Gradle sync.
3. Run on an emulator or device (API 24+).
4. Capture your required screenshots (Design view, Text view, .kt files, and the
   running app) directly from your machine so the system clock is genuinely yours.

## Push to GitHub
```
git init
git add .
git commit -m "Initial commit - Marchers Only final project"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main
```
