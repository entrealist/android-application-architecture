# Google Play Publisher
В семпле демонстрируется выгрузка AppBundle в Google Play для Closed Alpha Track
#### How to install
1. Получите google-play-auto-publisher.json у владельца аккаунта
2. Получите keystore у владельца аккаунта и пропишите соответствующие для него
данные в keystore.properties в директории модуля
3. Вы готовы к публикации с помощью плагина

#### Update release notes and language
Файлы release-notes лежат в соответсвующей директории проекта

#### Use it to upload distribution:
./gradlew publish

#### How use with Circle CI:
Практически тоже самое, что и выгрузка в Fabric
``` 
    name:  Upload to Google Play...
    command: ./gradlew publish
```
p.s.: Весь Production пухаем в Master. Соответствующией workflow для CI
```
workflows:
  version: 2
  build_to_publish:
    jobs:
      - build_and_upload:
          filters:
            branches:
              only: master
```