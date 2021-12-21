# TakeAway Application Sample
This is a sample Application for showing list of restaurants and their details, that implements clean architecture MVVM/MVI using Kotlin, coroutines, Hilt, Retrofit, Room, navigation component 
and architecture components with unit tests using Junit 4 and Mockk.

<img src="app/screenshots/splash.jpg" width="250"> <img src="app/screenshots/restaurants.jpg" width="250"> <img src="app/screenshots/restaurant_details.jpg" width="250"> 

## Architecture
- Clean Architecture
- MVVM / MVI 

## Stack
- Kotlin
- Coroutines
- Architecture Components
    * View Binding
    * ViewModel
    * LiveData
    * Room
- Navigation Components
- Hilt (Dependency Injection)
- Retrofit

## Testing
- Unit tests
    * Junit 4
    * Mockk

## Network APIS
- upload json into https://getsandbox.com/
- API URL: https://take-away.getsandbox.com:443/restaurants

## How to run a sample 

### To run the sample in Android studio
 
- clone the repository : git clone https://github.com/RomisaaAttiaa/RestaurantsSample.git
- The API is hosted on Sandbox : https://take-away.getsandbox.com:443/restaurants
- You need first to connect the mobile to the internet to fetch the data from the API.
- To run the unit tests you need to run the tests in the test modules of the three modules app, domain, data
- you can get the APK directly from : https://github.com/RomisaaAttiaa/RestaurantsSample/releases/tag/Takeaway_release_v1.0


# License

    Copyright 2021 Romisaa Hussien

    Licensed under the Apache License, Version 2.0 (the "License");

    you may not use this file except in compliance with the License.

    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software

    distributed under the License is distributed on an "AS IS" BASIS,

    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

    See the License for the specific language governing permissions and

    limitations under the License.
