# Тестовое задание - TaskManagerApp

Выполнено в соответствии со всеми пунктами требований за 14 дней.

<details>
  <summary><b>Скриншоты</b></summary>
  
  | Список дел | Список дел + скролл | Завершенное дело | Свайп завершения | Свайп удаления |
  | - | - | - | - | - |
  | ![alt text](https://github.com/lavdev4/TaskManagerApp/assets/103329075/8c7b5144-9f12-4895-a392-2725418ae19f) | ![alt text](https://github.com/lavdev4/TaskManagerApp/assets/103329075/5385a6a9-0b40-48af-a7a3-fcb8bffb6aa6) | ![alt text](https://github.com/lavdev4/TaskManagerApp/assets/103329075/60e83f0a-6089-4595-a311-120c8e4b3f5a)  | ![alt text](https://github.com/lavdev4/TaskManagerApp/assets/103329075/5c015c21-ce6a-41c7-a29e-ce3762839df8) | ![alt text](https://github.com/lavdev4/TaskManagerApp/assets/103329075/c16e2be3-1e33-4ce1-b88e-bbfe8a225ed9) |
  
  | Экран информации о деле | Экран создания дела | Выбор времени | Выбор даты |
  | - | - | - | - |
  | ![alt text](https://github.com/lavdev4/TaskManagerApp/assets/103329075/37759b59-85ca-414e-bc41-021e29c80dbd) | ![alt text](https://github.com/lavdev4/TaskManagerApp/assets/103329075/b5ce7db8-127e-40ed-aec0-f5433acbf12d) | ![alt text](https://github.com/lavdev4/TaskManagerApp/assets/103329075/d870d2e5-f51b-4416-947b-d5e62d252284) | ![alt text](https://github.com/lavdev4/TaskManagerApp/assets/103329075/3449c645-761e-4e75-add0-b3455293dff1) |
</details>

### Использованные инструменты:
Clean Architecture, MVVM, Dagger, Room, Kotlin Coroutines, Kotlin Flow, Coordinator Layout, Custom View, Gson, JUnit, DataStore, Navigation

## Требования к заданию
**Необходимо написать мобильное приложение, представляющее из себя ежедневник.**  
**Функционал:** В приложении присутствуют 2 экрана - список дел с календарем, подробное описание дела.  
Список дел должен быть в формате JSON.  
<details>
  <summary><b>Пример</b></summary>

  {  
    “id”:1,  
    “date_start”:”147600000”,  
    “date_finish”:”147610000”,  
    “name”:”My task”,  
    “description”:”Описание моего дела”  
  } 
  
  (date_start, date_finish имеют тип timestamp)
</details>


**Список дел с календарем** - экран на котором присутствует возможность выбрать один день, после выбора дня в конце экрана должна обновиться таблица с делами, в каждой ячейке таблицы указан 1 час из дня (например 14.00-15.00). Если в это время есть дело, оно должно отобразиться блоком(название и время).  Подробное описание дела включает в себя: название, дату и время, краткое описание дела текстом. Календарь можно использовать любой.  
**Критерии выполнения задания.**

**1й уровень:**
- Структурированный чистый код
- Использование сервисного слоя для подготовки данных
- Адаптивная верстка с использованием Constraint Layout или
сопутствующих технологий в xml разметках
- Использование архитектурных паттернов
- Поддержка версий - Android 8+
- Ориентация - портретная
- Код на GitHub

**2й уровень:**
- Добавить экран создания дела, на котором присутствует
возможность указать название, выбрать дату и время, краткое
описание дела текстом
- Создание компонентов экрана кодом с помощью кастомных вью на
Kotlin или верстка с помощью Jetpack Compose
- Для локального хранения используем Room
- Покрытие Unit-тестами: 1-2 тест
