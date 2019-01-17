package module;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = {"id"})
public class User {
    @Getter(AccessLevel.PROTECTED) @Setter private Integer id;
    @Getter @Setter private String name;
    @Getter @Setter private String phone;
}


