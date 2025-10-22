# Player API Tests (standard Maven layout)

```
src/
├─ main/
│  ├─ java/        # API clients, config
│  └─ resources/   # application.properties
└─ test/
   ├─ java/        # TestNG tests
   └─ resources/   # testng.xml
```

### Run
```bash
mvn -q -DbaseUrl=http://3.68.165.45 clean test
# allure serve target/allure-results
```
