package module;

import lombok.NoArgsConstructor;
import lombok.NonNull;
@NoArgsConstructor(force = true)
public class User01 {
    @NonNull private Integer id;
    @NonNull private String name;
    private final String phone ;
}


