core/
├── domain/
│   └── model/             ← Task, Account (sem anotações)
├── application/
│   ├── ports/
│   │   ├── in/            ← UseCases
│   │   └── out/           ← TokenService, TaskRepository
│   └── usecase/           ← UseCaseImpl (sem @Service)

adapters/
├── in/
│   ├── rest/              ← Controllers Spring
│   └── security/
│       ├── entities/      ← UserDetails
│       └── impl/          ← UserDetailsServiceImpl
├── out/
│   ├── persistence/
│   │   ├── entities/      ← Entidades JPA
│   │   ├── repositories/  ← Spring Data
│   │   └── impl/          ← TaskRepositoryImpl, etc.
│   └── security/
│       └── JwtServiceImpl
