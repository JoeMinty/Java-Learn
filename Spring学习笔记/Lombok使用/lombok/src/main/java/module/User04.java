package module;

import lombok.AllArgsConstructor;
import lombok.NonNull;
@AllArgsConstructor
public class User04 {
    @NonNull
    private Integer id;
    @NonNull
    private String name = "bbbb";
    private final String phone;
}