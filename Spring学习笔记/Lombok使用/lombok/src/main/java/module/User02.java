package module;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class User02 {
    @NonNull private Integer id  ;
    @NonNull private String name = "bbbb";
    private final String phone;
}

