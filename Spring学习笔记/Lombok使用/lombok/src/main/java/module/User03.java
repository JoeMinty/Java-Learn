package module;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor(staticName = "of")
public class User03 {
    @NonNull private Integer id ;
    @NonNull private String name = "bbbb";
    private final String phone;
}


