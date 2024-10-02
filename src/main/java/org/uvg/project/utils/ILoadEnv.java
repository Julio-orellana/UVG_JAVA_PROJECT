package org.uvg.project.utils;

import io.github.cdimascio.dotenv.Dotenv;

public interface ILoadEnv {

    Dotenv dotenv = Dotenv.load();

    String dbUrl = dotenv.get("DB_URL");
    String dbUser = dotenv.get("DB_USER");
    String dbPassword = dotenv.get("DB_PASSWORD");
    String encryptionKey = dotenv.get("ENCRYPTION_KEY");

}
