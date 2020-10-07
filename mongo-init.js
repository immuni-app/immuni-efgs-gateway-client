db.createUser(
        {
            user: "efgs",
            pwd: "efgs",
            roles: [
                {
                    role: "readWrite",
                    db: "KEYS"
                }
            ]
        }
);