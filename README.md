# CinePlay - Cinema Management System

**CinePlay** is a comprehensive cinema management system developed in Java with a Swing GUI and MySQL database. It manages movies, sessions, tickets, and concession sales, offering a robust solution for cinema administration.

---

## Table of Contents

* [Overview](#overview)
* [Features](#features)
* [Technologies Used](#technologies-used)
* [Project Structure](#project-structure)
* [Database Model](#database-model)
* [Installation](#installation)
* [Usage](#usage)
* [User Roles](#user-roles)
* [Screenshots](#screenshots)
* [API Documentation](#api-documentation)
* [Contributing](#contributing)
* [Academic Info](#academic-info)
* [License](#license)
* [Contact & Links](#contact--links)
* [Future Improvements](#future-improvements)

---

## Overview

CinePlay is a full-featured cinema management solution covering all operational aspects: session scheduling, ticket sales, seat management, concession sales, and customer service. It offers separate interfaces for admins and clients with permission-based access control.

---

## Features

### Authentication and User Management

* Admin login with full access
* Customer login for ticket purchasing
* Role-based access control

### Movie Management

* Movie catalog: add, edit, delete
* Genre management with description
* Details: title, rating, duration, language, dubbing, subtitles
* Distributor information

### Session Management

* Session scheduling
* Room assignment
* Time and duration management
* Session types: 3D, IMAX, VIP, Standard
* Dynamic pricing per session

### Seats and Tickets

* Interactive seat selection
* Real-time seat availability
* Capacity control
* Purchase history

### Room Management

* Room configuration
* Capacity settings
* Room types
* Maintenance logging

### Concession Stand

* Product catalog
* Stock control
* Price management
* Integration with ticket sales

### Reports & Analytics

* Sales reports
* Capacity reports
* Financial reports
* Admin tools

### Customer Features

* Movie reviews
* Loyalty program with points
* Purchase history
* Email notifications

---

## Technologies Used

### Backend

* Java 8+
* Swing (Desktop GUI)
* JDBC (Database connection)

### Database

* MySQL 8.0+
* XAMPP (recommended environment)

### Development Tools

* Eclipse IDE
* Visual Studio Code
* Git
* Maven (optional)

### External Libraries

* MySQL Connector/J
* JavaMail API
* PlantUML

---

## Project Structure

```
Cinema/
├── src/
│   ├── database/
│   ├── Model/
│   ├── View/
│   ├── mail/
│   ├── multitools/
│   └── Doc/
├── BD/
│   └── BDCinema.sql
├── bin/
└── README.md
```

---

## Database Model

### Main Tables

| Table               | Description                       |
| ------------------- | --------------------------------- |
| tbfilmes            | Movie information                 |
| tbsessoes           | Sessions with dynamic pricing     |
| tbingressos         | Ticket purchases and reservations |
| tbclientes          | Customer profiles                 |
| tbsalas             | Theater room settings             |
| tbbomboniere        | Concession products               |
| tbgeneros           | Movie genres and categories       |
| tbcomentariosfilmes | Movie reviews                     |
| tbreservas          | Seat reservations                 |
| tbmarketing         | Marketing campaigns               |
| tbmanutencao        | Maintenance logs                  |
| tbchamadostec       | Tech support tickets              |

### Relationships

* Movies → Sessions (1\:N)
* Sessions → Tickets (1\:N)
* Rooms → Sessions (1\:N)
* Clients → Tickets, Reviews, Reservations (1\:N)

---

## Installation

### Prerequisites

* Java JDK 8 or higher
* MySQL 8.0 or higher
* XAMPP (recommended)
* Eclipse IDE

### Steps

1. Clone this repository
2. Import the `BDCinema.sql` file into MySQL
3. Configure DB connection in the source code
4. Add `MySQL Connector/J` to your classpath
5. Compile and run the project

---

## Usage

### Default Credentials

#### Admin Access

Allows:

* Manage movies and genres
* Schedule sessions with dynamic pricing
* Configure rooms
* Manage concession products
* View reports
* Reset system data
* Manage tech support

#### Client Access

Allows:

* Browse movies
* Select sessions and seats
* Purchase tickets and products
* Submit movie reviews

---

## User Roles

### Administrator

| Resource   | Description                    |
| ---------- | ------------------------------ |
| Movies     | Full CRUD                      |
| Sessions   | Dynamic pricing and scheduling |
| Rooms      | Setup and maintenance          |
| Concession | Stock and price management     |
| Reports    | Sales, capacity, financial     |

### Client

| Resource   | Description                   |
| ---------- | ----------------------------- |
| Movies     | Browsing and reviewing        |
| Sessions   | Choose time and seat          |
| Tickets    | Purchase with dynamic pricing |
| Concession | Integrated product purchasing |
| Profile    | Update personal data          |

### Other Roles (PlantUML)

* General Manager
* Session Programmer
* Maintenance Crew
* Film Distributor
* Marketing
* IT Administrator

---

## Screenshots

*Add screenshots here of the main screens:*

* Login screen
* Admin dashboard
* Movie management interface
* Session scheduling
* Seat selection
* Client movie browsing
* Concession stand selection

---

## API Documentation

### Architecture

* MVC pattern
* Full CRUD support
* Transaction handling
* Input validation
* Connection pooling

### Features

* Dynamic pricing per session
* Real-time seat management
* Integrated ticket + product purchase

---

## Contributing

We welcome contributions! Follow the steps below:

1. Fork this repository
2. Create a branch (`git checkout -b feature/YourFeature`)
3. Commit (`git commit -m 'Feature description'`)
4. Push (`git push origin feature/YourFeature`)
5. Open a Pull Request

### Standards

* Follow Java naming conventions
* Use JavaDoc comments
* Include tests for new features
* Keep documentation updated
* Maintain consistent UI

### Reporting Bugs

* Use GitHub Issues
* Provide full reproduction steps
* Include OS, Java version
* Screenshots if possible

---

## Academic Info

This project was developed for the Object-Oriented Programming (OOP) course at:

* Institution: IFSP (Federal Institute of São Paulo)
* Campus: Guarulhos
* Course: OOP - 2024
* Language: Java + Swing

### Learning Objectives

* OOP principles
* Database integration
* GUI development
* MVC architecture
* Real-world software project

---

## License

This project is licensed under the MIT License.

---

## Contact & Links

* E-mail: jbetodamasceno@gmail.com
* Institution: IFSP Guarulhos

---

## Future Improvements

* [ ] Web Interface using Spring Boot
* [ ] Mobile App (Android/iOS)
* [ ] Payment gateway integration
* [ ] Business intelligence dashboards
* [ ] Multilingual support (i18n)
* [ ] Cloud hosting (AWS/Azure)
* [ ] Public REST API for integrations
* [ ] Real-time notifications (WebSocket)
