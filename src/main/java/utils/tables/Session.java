package utils.tables;

import enums.SessionTableEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.testng.Reporter;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Session extends BaseTable {
    private Integer buildNumber;
    private Timestamp createdTime;
    private String sessionKey;
    private Integer id;

    @Override
    public PreparedStatement sendValues(PreparedStatement pstm) {
        try {
            pstm.setInt(SessionTableEnums.SESSION_BUILD_PARAMETER_INDEX.index, getBuildNumber());
            pstm.setTimestamp(SessionTableEnums.SESSION_CREATED_PARAMETER_INDEX.index, getCreatedTime());
            pstm.setString(SessionTableEnums.SESSION_KEY_PARAMETER_INDEX.index, getSessionKey());
            pstm.setInt(SessionTableEnums.SESSION_ID_PARAMETER_INDEX.index, getId());
            return pstm;
        } catch (Exception e) {
            Reporter.log("failed to send values to Session instance", true);
        }
        return null;
    }
}
