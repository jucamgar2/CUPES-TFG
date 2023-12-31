package TFG.CUPES.components;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DateRangeForm {
    private LocalDate startDate;
    private LocalDate endDate;
}