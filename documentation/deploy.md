## Инсрукция по запуску:

### Запуск докера

 - Создание network (если нет): 
```shell
    docker network create --driver bridge docker_becloud
```

 - Создать .env на основе env-ex (лежит в корне проекта)


 - Запуск контейнеров: 
```shell
  docker compose up -d --build app
  docker compose up -d --build db
```

 - Вставка sql скрипта в контейнер db
```shell
  docker cp src/main/resources/db/migration/V1__initial_schema.sql db:/tmp/
  docker exec -it db psql -U postgres  -f /tmp/V1__initial_schema.sql  
```

 - Тестирование через postmen 
    
    Ипорд json, который лежит в src/main/resources/test_task_collection.postman_collection.json