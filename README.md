** NOTE: The server is too slow, the average response time of the home screen is 60 seconds,
    and the average response time of the character's details is 50 seconds.

Project Structure:
  - Android Gradle Plugin Version: $versions.agp : 8.5.2
  - Gradle Version : 8.7
  - Compile Sdk Version: API 34
  - Target SDK Version: API 34
  - Min SDK Version: API 28

Dependencies:
  - SplashScreen
  - OKHttp
  - Retrofit
  - Dagger
  - Recyclerview
  - Glide
  - Fragment Navigation

- Some of the features mentioned are done in this project.
- Custom splash screen is added.
- Dagger Hilt is used to inject dependencies.
- The server is too slow. To reduce the avrage respone time, the requested batch's size of each page can be reduced.
  For production, pre-fetch mechanism can be designed, and data can be cached for offline access.


---Turkish

**NOT: Sunucu çok yavaş, ana ekranın ortalama yanıt süresi 60 saniye ve karakterin detaylarının ortalama yanıt süresi 50 saniye.

-Sunucu çok yavaş. Ortalama yanıt süresini azaltmak için her sayfanın istenen toplu veri boyutu azaltılabilir.
Üretim için, önceden getirme mekanizması tasarlanabilir ve veriler çevrimdışı erişim için önbelleğe alınabilir.
