# android-application-architecture
Android application sample based on Clean Architecture, MVP(Moxy) and Navigation pattern(Cicerone) 
#### Use it to upload distribution:
./gradlew assembleFabric crashlyticsUploadDistributionFabricDebug
#### Use it to check dependencies updates
./gradlew dependencyUpdates

#### WorkManager sample
Пример работы с WorkManager для следующего кейса:
Я хочу чтобы выполнялась работа (в данном случае создание и показ нотификации) в заданном промежутке дат, с заданным интервалом (в днях), в заданное время.

Ключевые моменты:
- Планирование на день происходит при наступлении полночи
- При смене времени на девайсе происходит перепланирование (см. логи)
- Работа не будет выполнена точно в срок при doze mode
- Нотификация покажет на какое время было запланировано срабатывание триггера в противопоставлении фактическому времени прибытия
