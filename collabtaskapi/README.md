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



# Gera a imagem do backend
docker build -t collabtask-backend .

# Roda o backend no container
docker run -p 8080:8080 collabtask-backend


docker build -t collabtask-frontend .
docker run -p 3000:80 collabtask-frontend