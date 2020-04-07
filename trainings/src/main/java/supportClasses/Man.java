package supportClasses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@author} Stanislav Tretyakov
 * 07.04.2020
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Man {
    int age;
    String name;
    String[] skills;
}
