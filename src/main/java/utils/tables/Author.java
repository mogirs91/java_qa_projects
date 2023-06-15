package utils.tables;

import enums.AuthorTableEnums;
import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.testng.Reporter;

import java.sql.PreparedStatement;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Author extends BaseTable {
    private String email;
    private String login;
    private String name;
    private Integer id;

    @Override
    public PreparedStatement sendValues(PreparedStatement pstm) {
        try {
            pstm.setString(AuthorTableEnums.AUTHOR_EMAIL_PARAMETER_INDEX.index, getEmail());
            pstm.setString(AuthorTableEnums.AUTHOR_LOGIN_PARAMETER_INDEX.index, getLogin());
            pstm.setString(AuthorTableEnums.AUTHOR_NAME_PARAMETER_INDEX.index, getName());
            pstm.setInt(AuthorTableEnums.AUTHOR_ID_PARAMETER_INDEX.index, getId());
            return pstm;
        } catch (Exception e) {
            Reporter.log("failed to send values to Author instance", true);
        }
        return null;
    }
}
