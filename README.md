# ToDo
- [x] 테스트 코드 작성
- [x] 멀티 모듈화
- [ ] 이전에 단일 모듈에서 통과되었던 테스트 코드를 멀티 모듈로 바꾼 시점에서 통과될 수 있도록 테스트 코드를 수정
  - 단일 모듈로 구성했을 땐, UseCase 와 DataSource를 사용하지 않았었기 때문에 테스트코드의 대대적인 수정이 필요함...


## Architecture

- MVVM
- Multi-Module
- Clean Architecture

## Tech Stack

- Navigation
- Coroutine
- Paging3
- Hilt
- Retrofit
- Room
- DataStore
- WorkManager

## Package Structure 

```java
app
├── ...
├── src
│   ├── app //Application            
│   │                  
data
├── ...
├── src
│   ├── data
│   │   ├── di // dependency injection module
│   │   ├── local         
│   │   │   ├── db        
│   │   │   ├── datasource    
│   │   ├── mapper
│   │   ├── model // API response, data model 
│   │   ├── paging // pagingsource
│   │   ├── remote
│   │   │   ├── api // API interface        
│   │   │   ├── datasource
│   │   ├── repository
domain                                       
├── ...                                     
├── src               
│   ├── domain                              
│   │   ├── entity // domain model 
│   │   ├── repository
│   |   ├── usecase 
presentation                                    
├── ...                                 
├── src                                         
│   ├── presentation                                  
│   │   ├── adapter          
│   │   ├── base 
│   │   ├── item // presentation model                
│   │   ├── mapper
│   │   ├── screen // activity, fragment
│   │   ├── viewmodel
│   │   ├── worker // workmanager 
```
## Description

## TODO 
- [x] ktlint, detekt 적용 
- [ ] remoteMediator 적용 
- [ ] TestCode 보완 및 작성

# MAD Score

![summary](https://user-images.githubusercontent.com/51016231/178445295-a6979959-65ba-4dae-847d-503cd963b897.png)
![kotlin](https://user-images.githubusercontent.com/51016231/178445407-ddcbc9f5-0f32-4d11-b1ea-12bf6855e981.png)
![jetpack](https://user-images.githubusercontent.com/51016231/178445424-77062177-18f9-498c-b3b2-4edfd14e6b35.png)
