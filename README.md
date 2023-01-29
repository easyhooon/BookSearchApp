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
