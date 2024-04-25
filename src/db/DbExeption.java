package db;

import java.io.Serial;

public class DbExeption extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public DbExeption (String msg) {
        super(msg);
    }
}
