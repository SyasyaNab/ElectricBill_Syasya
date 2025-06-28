# ⚡ Electric Bill Estimator – Android App

An Android application that estimates monthly electricity bills based on block tariffs, applies rebate discounts, and stores billing data locally using SQLite. Developed as part of a Mobile Technology course assignment.

---

## 📱 Features

- 🔢 Input electricity usage (in kWh), month, and rebate percentage
- 🧮 Automatic calculation of:
    - Total charges based on tiered block rates
    - Final cost after applying rebate
- 💾 Save bills to local SQLite database
- 📋 View saved bills in a ListView by month
- 🔎 Click list items to see full bill details
- ℹ️ About page with student info and clickable GitHub link

---

## 🧮 Sample Calculation

Example for 250 kWh with 5% rebate:

- First 200 kWh → `200 * 0.218 = RM 43.60`
- Next 50 kWh → `50 * 0.334 = RM 16.70`
- Total = `RM 60.30`
- Final = `60.30 - (60.30 * 0.05) = RM 57.29`

---

## 🎨 Screenshots

| Input | Output | Bill List | Detail | About |
|-------|--------|-----------|--------|-------|
| ![input](screenshots/input.png) | ![output](screenshots/output.png) | ![list](screenshots/list.png) | ![detail](screenshots/detail.png) | ![about](screenshots/about.png) |

> *(Add these screenshots to your `/screenshots` folder and commit them)*

---

## 🛠 Technologies Used

- Android Studio (Java)
- SQLite Database
- XML Layouts & Custom Themes
- Intent-based Navigation

---

## 👩‍🎓 Student Info

- **Name**: Syasya Nabilah Binti Syalihuddin
- **Student ID**: 2023697608
- **Course**: ICT602 – Mobile Technology And Development
- **Copyright** ©
- [📎 YouTube Demo](https://youtube.com/your-video-link)

---

## 🚀 Getting Started

1. Clone the repo:
   ```bash
   git clone https://github.com/SyasyaNab/ElectricBill_Syasya.git
   ```
2. Open in Android Studio
3. Click **Run** to launch on emulator or device

---

## 📂 APK Download

Download the latest signed APK from the [Releases](https://github.com/syasya/electricbill-estimator/releases) section.

---

## 📜 License

This project is for educational use only.
