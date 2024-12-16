package importation.order.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ImportResultDto
{
    private final String result;

    private final String message;

    private SummaryDto summary;
}
