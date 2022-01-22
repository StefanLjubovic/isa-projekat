# isa-projekat
[![CircleCI](https://circleci.com/gh/StefanLjubovic/isa-projekat.svg?style=shield)](https://app.circleci.com/pipelines/github/StefanLjubovic/isa-projekat?filter=all)

### Developers
- Student 1: Stefan Ljubović RA 148/2018
- Student 2: Marija Klještan RA 55/2018
- Student 3: Ana Gavrilović RA 65/2018

### Running our project locally
#### Backend
The backend is a Maven SpringBoot application. Source code is in the <i>./FishingBooker/backend</i> folder. It can be started normally as a Java project in Eclipse or IntelliJ. We used Java 15 version. The server is running on port 8082.\
PostgreSQL is needed for the backend. Local database connection parameters are configured in the <i>application.properties</i> file.
  
#### Frontend
The frontend is a Vue.js application. Source code is in the <i>./FishingBooker/frontend</i> folder. It can be started by running <code>npm install</code> and then <code>npm run serve</code> in the mentioned folder. The URL address is http://localhost:8081/.

### Heroku deployment
<b>Frontend</b> - https://isa-fishing-booker.herokuapp.com/ \
<b>Backend</b> - https://isa-fishing-booker-backend.herokuapp.com/

### Emails and passwords for all roles
- Client: user.fishingbooker+client1@gmail.com, 123456; user.fishingbooker+client2@gmail.com, 123456
- Cottage owner: user.fishingbooker+cottage@gmail.com, 123456
- Ship owner: user.fishingbooker+ship@gmail.com, 123456
- Instructor: user.fishingbooker+instr@gmail.com, 123456
- Administrator: user.fishingbooker+admin1@gmail.com, 123456; user.fishingbooker+admin1@gmail.com, 123456  
