db.createCollection('Users', { capped: false });
db.Users.insert({ "mail": "admin@wololo.com", "username": "admin", "password": "b48ffcbb6e4ce307f851b0bddc15837bf9ff0fc65153eab79b43293de800bbe8af2099d79f3bb95075cafebdb7066446ded7127ac3f237b19005fbfcf11dec5b", "isAdmin": true, "stats": { "gamesWon": 0, "gamesLost": 0 }, "_class": "com.grupox.wololo.model.User" });