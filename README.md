

# 📘 API GraphQL – Réservation d’Espaces

Cette API permet de gérer la réservation d'espaces de travail (bureaux, salles de réunion, bureaux privés) avec authentification, gestion des utilisateurs, des espaces et des réservations.

---

## 🌐 URL des services

- **GraphQL Playground** : [`http://localhost:8080/graphql`](http://localhost:8080/graphql)  
- **Console H2 Database** : [`http://localhost:8080/h2-console`](http://localhost:8080/h2-console)  
  - **JDBC URL** : `jdbc:h2:mem:coworking`  
  - **Utilisateur** : `sa`  
  - **Mot de passe** : 'password'

---

## Types GraphQL

###  `User`
```graphql
type User {
  id: ID!
  email: String!
  name: String!
  role: UserRole!
  createdAt: DateTime!
}


##  Types GraphQL

###  User
```graphql
type User {
  id: ID!
  email: String!
  name: String!
  role: UserRole!
  createdAt: DateTime!
}

Space
graphql
Copier
Modifier
type Space {
  id: ID!
  name: String!
  type: SpaceType!
  capacity: Int!
  description: String
  amenities: [String!]!
  hourlyRate: Float!
  isActive: Boolean!
  createdAt: DateTime!
}
 Reservation
graphql
Copier
Modifier
type Reservation {
  id: ID!
  user: User!
  space: Space!
  startTime: DateTime!
  endTime: DateTime!
  status: ReservationStatus!
  notes: String
  totalCost: Float!
  createdAt: DateTime!
}
 Enums
graphql
Copier
Modifier
enum UserRole {
  USER
  ADMIN
}

enum SpaceType {
  DESK
  MEETING_ROOM
  PRIVATE_OFFICE
}

enum ReservationStatus {
  CONFIRMED
  CANCELLED
  COMPLETED
}
 Inputs
 Création et connexion
graphql
Copier
Modifier
input CreateUserInput {
  email: String!
  password: String!
  name: String!
}

input LoginInput {
  email: String!
  password: String!
}
🛠️ Espace
graphql
Copier
Modifier
input CreateSpaceInput {
  name: String!
  type: SpaceType!
  capacity: Int!
  description: String
  amenities: [String!]!
  hourlyRate: Float!
}
Réservation
graphql
Copier
Modifier
input CreateReservationInput {
  spaceId: ID!
  startTime: DateTime!
  endTime: DateTime!
  notes: String
}

input UpdateReservationStatusInput {
  reservationId: ID!
  status: ReservationStatus!
}
Queries
graphql
Copier
Modifier
# Utilisateurs
me: User
users: [User!]!

# Espaces
spaces: [Space!]!
space(id: ID!): Space
availableSpaces(startTime: DateTime!, endTime: DateTime!): [Space!]!

# Réservations
reservations: [Reservation!]!
myReservations: [Reservation!]!
reservation(id: ID!): Reservation
spaceReservations(spaceId: ID!, date: String!): [Reservation!]!
 Mutations
graphql
Copier
Modifier
# Authentification
register(input: CreateUserInput!): AuthPayload!
login(input: LoginInput!): AuthPayload!

# Gestion d'espaces (admin)
createSpace(input: CreateSpaceInput!): Space!
updateSpace(id: ID!, input: CreateSpaceInput!): Space!
deleteSpace(id: ID!): Boolean!

# Réservations
RegisterUser(input: CreateReservationInput!): Reservation!
updateReservationStatus(input: UpdateReservationStatusInput!): Reservation!
cancelReservation(id: ID!): Reservation!
 AuthPayload
graphql
Copier
Modifier
type AuthPayload {
  token: String!
  user: User!
}
 Exemples
Exemple de query – Espaces disponibles
graphql
Copier
Modifier
query {
  availableSpaces(
    startTime: "2025-06-10T09:00:00Z", 
    endTime: "2025-06-10T12:00:00Z"
  ) {
    id
    name
    type
    hourlyRate
  }
}
Exemple de mutation – Inscription
graphql
Copier
Modifier
mutation {
  register(input: {
    email: "john@example.com",
    password: "monMotDePasse",
    name: "John Doe"
  }) {
    token
    user {
      id
      name
    }
  }
}
Exemple de mutation – Réservation
graphql
Copier
Modifier
mutation {
  RegisterUser(input: {
    spaceId: "SPACE_ID",
    startTime: "2025-06-10T09:00:00Z",
    endTime: "2025-06-10T11:00:00Z",
    notes: "Besoin de projecteur"
  }) {
    id
