package utils.tables;

import java.sql.PreparedStatement;

public abstract class BaseTable {
    public PreparedStatement sendValues(PreparedStatement pstm) {
        return pstm;
    }

}
