package utils.tables;

import enums.ProjectTableEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.testng.Reporter;

import java.sql.PreparedStatement;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Project extends BaseTable {
    private String name;
    private Integer id;

    @Override
    public PreparedStatement sendValues(PreparedStatement pstm) {
        try {
            pstm.setString(ProjectTableEnums.PROJECT_NAME_PARAMETER_INDEX.index, getName());
            pstm.setInt(ProjectTableEnums.ID_PARAMETER_INDEX.index, getId());
            return pstm;
        } catch (Exception e) {
            Reporter.log("failed to send values to Project instance", true);
        }
        return null;
    }
}
